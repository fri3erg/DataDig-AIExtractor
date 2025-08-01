import os
from azure.core.polling._poller import LROPoller
import pandas as pd
from azure.ai.formrecognizer._models import AnalyzeResult
from azure.ai.formrecognizer import DocumentAnalysisClient
from azure.core.credentials import AzureKeyCredential
from azure.core.exceptions import ClientAuthenticationError, ServiceRequestError

# Will need authentication for prod app
# https://medium.com/@tophamcherie/using-python-to-programmatically-authenticate-to-azure-use-resources-6997ff326fb6


def analyze_general_documents(
    image: bytes, specific_pages=None, language="it", api_version="2023-07-31", query_list=None
) -> AnalyzeResult:
    """Analyze a document with the Azure Form Recognizer API.

    Args:
        doc_path (str): path to the document to analyze.
        specific_pages (str, optional): specific pages to analyze, indexing starts from 1, can be multiple pages e.g. 2-7.
            Defaults to None.
        language (str, optional): language to help the model to analize, t the moment supported 'en' and 'it'. Defaults to "it".

    Returns:
        _type_: _description_
    """
    language_locale_config = {
        "it": "it-IT",
        "en": "en-US",
        "es": "es-ES",
        "fr": "fr-FR",
        "de": "de-DE",
    ***REMOVED***
    language_locale = language_locale_config[language]

    # Get variables form environment
    endpoint = os.getenv("AZURE_FORM_RECOGNIZER_ENDPOINT") or "no_endpoint"
    key = os.getenv("AZURE_FORM_RECOGNIZER_KEY") or "no_key"

    # create your `DocumentIntelligenceClient` instance and `AzureKeyCredential` variable

    document_analysis_client = DocumentAnalysisClient(
        endpoint=endpoint,
        credential=AzureKeyCredential(key),
        api_version=api_version,
    )
    features_chosen: list[str] = ["ocrHighResolution"]
    if query_list is not None:
        features_chosen.append("queryFields")

    # specific_pages = format_pages_num(specific_pages)

    try:

        # Analyze full document or specific pages
        poller: LROPoller[AnalyzeResult] = document_analysis_client.begin_analyze_document(
            document=image,
            model_id="prebuilt-layout",
            locale=language_locale,
            features=features_chosen,
            # pages=specific_pages,
        )
        result: AnalyzeResult = poller.result()
    except ClientAuthenticationError as auth_err:
        raise ValueError("Invalid Azure credentials (endpoint or key).")
    except ServiceRequestError as req_err:
        raise ValueError(f"Check Internet Connection. {req_err***REMOVED***")
    return result


def get_tables_from_doc(
    image: bytes, specific_pages: str | None = None, language="it", api_version="2023-07-31", query_list=None
):
    """Get tables from a document, can be used generally to save, or directly for query_list, in that case, return query_list also

    Args:
        doc_path (str): path to the document to analyze.
        specific_pages (str, optional): specific pages to analyze, indexing starts from 1, can be multiple pages e.g. 2-7.
            Defaults to None.
        language (str, optional): language to help the model to analize, t the moment supported 'en' and 'it'. Defaults to "it".

    Returns:
        dataframe[]: tables
    """
    # Analyze document
    result = analyze_general_documents(
        image, specific_pages=specific_pages, language=language, api_version=api_version, query_list=query_list
    )
    # Get tables
    df_tables = []
    for table in getattr(result, "tables", []):
        df_tab = table_json_to_df_v2(table)
        df_tables.append(df_tab)

    if query_list:
        return df_tables, getattr(next(getattr(result, "documents", [])[0], None), "fields")

    return df_tables, result


def table_json_to_df_v2(tab):

    num_rows = tab.row_count
    num_columns = tab.column_count
    columns = [""] * num_columns
    df = pd.DataFrame(columns=columns, index=range(0, num_rows))

    # Set headers
    for cell in tab.cells:
        row = cell.row_index
        col = cell.column_index
        col_span = cell.column_span
        row_span = cell.row_span

        content = cell.content
        # convert None to 0
        col_span = 0 if col_span is None else col_span
        row_span = 0 if row_span is None else row_span
        df.iloc[row : row + row_span + 1, col : col + col_span + 1] = content

    return df
