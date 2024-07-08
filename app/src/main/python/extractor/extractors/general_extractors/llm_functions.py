from extractors.general_extractors.utils import select_desired_page, upload_df_as_excel
from extractors.general_extractors.utils import num_tokens_from_string
from langchain.prompts import PromptTemplate
from .config.basic_tags import *
from ..models import Models
from configs.configs import prompts


def get_doc_language(text, file_id):
    """Get the language of the document.

    Args:
        pages (lst): list of pages text
        file_id (str): file_id for costs

    Returns:
        str: language of the document
    """
    # Analyze first page
    language = Models.tag(text[:300], DocLanguage, file_id, model="gpt-3.5-turbo")

    # Check if language is mapped
    # NOTE: need to add more languages
    doc_language = language.language
    if doc_language not in ["it", "en", "fr", "de", "es"]:
        doc_language = "it"

    return doc_language


def llm_extraction(page, template, file_id, model="gpt-3.5-turbo", language="it"):
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
    initial_prompt: str= prompts[language]
    input_variables: list[str] = ["template","context"]
    prompt = PromptTemplate(input_variables=input_variables, template=initial_prompt)
    # Select model size based on context
    if model == "gpt-3.5-turbo":
        total_token = num_tokens_from_string(prompt.template.format(context=page, template=template))
        if total_token > 4000:
            model = "gpt-3.5-turbo-16k"
        else:
            model = "gpt-3.5-turbo"
    # Construct chain and extract relevan info
    response = Models.extract(file_id, model, prompt, page, template)
    return response


def general_table_inspection(table, pydantic_class, file_id, add_text=""):
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
        tag_model = "gpt-4-turbo"
        if not add_text:
            table = f"considera questo quando analizzi la tabella=-> {add_text***REMOVED*** TABELLA-> {table***REMOVED***"

        extraction_adapted = Models.tag(table, pydantic_class, file_id, model=tag_model)
    except Exception as error:
        print("table extraction error" + repr(error))
        extraction_adapted = {"ERROR": "ERROR"***REMOVED***

    return extraction_adapted


def complex_table_inspection(table, rhp, prompt, pydantic_class, file_id, direct_tag=True):
    """
    searches table for information
    saves excel file with table in tmp to get around llm bug with incomplete stringified dataframe
    llm extraction first if direct_tag is false
    Args:
        table (pandas dataframe): dataframe to search
        rhp (str): rhp to insert
        type (str): type of data to extract
        file_id (str): file_id for costs
        direct_tag (bool, optional): if skip llm extraction. Defaults to True.
        language (str, optional): language of the doc. Defaults to 'it'.

    Returns:
        dict: extracted data
    """

    try:

        table = upload_df_as_excel(table)

        # First normal extraction, then tagging
        tag_model = "gpt-4-turbo"
        if not direct_tag:
            table = llm_extraction(table, prompt, file_id, model=tag_model, rhp=rhp)

        if rhp is None:
            adapt_extraction = "CONSIDERA 1 ANNO , EXTRACTION={***REMOVED***".format(table)
        else:
            adapt_extraction = "RHP={***REMOVED*** EXTRACTION={***REMOVED***".format(rhp, table)
        extraction_adapted = Models.tag(adapt_extraction, pydantic_class, file_id, model=tag_model)
    except Exception as error:
        print("table extraction error" + repr(error))
        extraction_adapted = {"ERROR": "ERROR"***REMOVED***

    return extraction_adapted


def tag_only(pages, keywords, pydantic_class, file_id):
    """
    Extracts basic information from a document, the basic information are the ones contained
    in the InformazioniBase class.

    Args:
        pages (): The text of the document to extract information from.
        keywords ([str]): keywords to search for
        pydantic_class(PydanticModel): schema to use for tagging
        file_id (str): file_id for costs

    Returns:
        str: The extracted basic information.
    """
    # Select page with RIY
    page = select_desired_page(pages, keywords)
    page = pages[int(page)]

    # To ensure optimal data standardization
    total_prompt = "EXTRACTION={***REMOVED***".format(page)
    extraction = Models.tag(total_prompt, pydantic_class, file_id)

    return extraction


def llm_extraction_and_tag(page, template, file_id, pydantic_class, model="gpt-3.5-turbo", language="it"):
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
    initial_prompt: str= prompts[language]
    
    input_variables: list[str] = ["template","context"]
    prompt = PromptTemplate(input_variables=input_variables, template=initial_prompt)
    # Select model size based on context
    if model == "gpt-3.5-turbo":
        total_token = num_tokens_from_string(prompt.template.format(context=page, template=template))
        if total_token > 4000:
            model = "gpt-3.5-turbo-16k"
        else:
            model = "gpt-3.5-turbo"
    # Construct chain and extract relevan info
    response = Models.extract(file_id, model, prompt, page, template)
    # To ensure optimal data standardization
    tagged = Models.tag(response, pydantic_class, file_id)

    return tagged
