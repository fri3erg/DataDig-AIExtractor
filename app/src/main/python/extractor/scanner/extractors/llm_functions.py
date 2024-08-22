from typing import Any, Optional

# import instructor
# import instructor.exceptions
from ...classes.Options import Options
from ...classes.Template import Template
from ...classes.Extracted import ExceptionsExtracted
from ...scanner.extractors.utils import num_tokens_from_string
from langchain.prompts import PromptTemplate
from ..ai_manager.models import Models
from ...configs.basic_tags import DocLanguage
from ...configs.configs import desc_tabella
from ...configs.configs import create_language_tag_messages
from .utils import sanitize_text


def get_doc_language(text, file_id, options: Options):
    """Get the language of the document.

    Args:
        pages (lst): list of pages text
        file_id (str): file_id for costs

    Returns:
        str: language of the document
    """
    # Analyze first page
    prompt = create_language_tag_messages(text=text[0][:300], language="en")
    language, error_occurred = Models.tag(prompt, DocLanguage, file_id, options.model)
    if error_occurred:
        raise error_occurred
    # except instructor.exceptions.InstructorRetryException:)

    # Check if language is mapped
    # NOTE: need to add more languages
    doc_language = getattr(language, "language", "it")
    if doc_language not in ["it", "en", "fr", "de", "es"]:
        doc_language = "it"

    return doc_language


def general_table_inspection(
    table, pydantic_class, file_id, options: Options, add_text=""
) -> tuple[Any, Optional[Exception]]:
    """tags data in a table

    Args:
        table (dataframe): dataframe to tag
        pydantic_class (PydanticModel): schema to use for tagging
        file_id (str): file_id for costs
        language (str, optional): language of the doc. Defaults to 'it'.
        add_text (str, optional): text to add in case. Defaults to "".

    Returns:
        dict: extracted data
    """

    try:

        # First normal extraction, then tagging
        add_text = f"{desc_tabella[options.language or 'en']***REMOVED*** {add_text***REMOVED*** " if add_text else ""
        table = sanitize_text(f"{add_text***REMOVED*** TABLE-> {table.to_string()***REMOVED***")
        prompt = create_language_tag_messages(text=table, language=options.language or "it", is_table=True)
        print(prompt.template)
        extraction_adapted, errors_occurred = Models.tag(prompt, pydantic_class, file_id, options.model)

    except Exception as error:
        print("table extraction error" + repr(error))
        return pydantic_class(), error

    return extraction_adapted, errors_occurred


def llm_extraction_and_tag(page, template: Template, file_id, pydantic_class, options: Options, initial_prompt: str):
    """extracts data from a document text

    Args:
        page ([str]): page in which data is found
        type (str): type of data to extract
        file_id (str): file_id for costs
        template (str): prompt to use for extraction
        model (str, optional): model to use for extraction. Defaults to 'gpt-4'.
        rhp (str, optional): rhp of the document. Defaults to None.

    Returns:
        dict(): data extracted
    """
    model = options.model
    template_readable = template.template_to_readable_string()
    optional_error: Optional[ExceptionsExtracted] = None
    input_variables: list[str] = ["template", "context"]
    prompt = PromptTemplate(input_variables=input_variables, template=initial_prompt)
    # Select model size based on context
    if options.model == "gpt-3.5-turbo":
        total_token = num_tokens_from_string(str(page) + template_readable)
        if total_token > 4000:
            model = "gpt-3.5-turbo-16k"
        else:
            model = "gpt-3.5-turbo"
    # Construct chain and extract relevan info
    try:
        response = Models.extract(file_id, model, prompt, page, template_readable, 0)
    except Exception as e:
        print("error in extraction", e)
        optional_error = ExceptionsExtracted(e, "llm_extraction", repr(e))
        response = str(page)
    response = str.replace(r"\*\*", "", response)

    print("response:", response)
    # To ensure optimal data standardization
    try:
        prompt = create_language_tag_messages(text=response, language=options.language or "it")
        tagged, optional_error_tag = Models.tag(prompt, pydantic_class, file_id, options.model)
        if optional_error_tag and not optional_error:
            optional_error = ExceptionsExtracted(optional_error_tag, "llm_extraction", repr(optional_error_tag))

    except Exception as e:
        print("error in tagging", e)
        tagged = pydantic_class()

    return tagged, optional_error
