from extractors.azure.document_intelligence import get_tables_from_doc
from extractors.general_extractors.custom_extractors.kid.kid_extractor import Extractor
from extractors.general_extractors.utils import is_in_text, select_desired_table_only_header


class DerivatiKidExtractor(Extractor):

    def __init__(self, doc_path, predefined_language=False) -> None:
        self.doc_path = doc_path
        super().__init__(doc_path, predefined_language)

    def _extract_table_only_header(
        self, keywords, pages_to_check=[0, 1], api_version="2023-10-31-preview"
    ):  # change from normal is select_desired_table_only_header
        """General table extractor, given a table type it first finds the page within
        the document where the table is located, it then extracts all the tables from
        that page and returns the one with the most occurrences of the words of the table
        type.

        Args:
           keywords(str[]): keywords to search for in the table.
           black_list_pages (int[], optional): Pages to ignore. Defaults to [].

        Returns:
            pandas.DataFrame: dataframe containing the table.
        """
        try:
            # Select page with table
            tables = []
            raw_data = []
            # Get all the tables from the page
            if self.di_tables_pages is not None and not all(
                str(page) in self.di_tables_pages for page in pages_to_check
***REMOVED***:
                for page in [page for page in pages_to_check if str(page) not in self.di_tables_pages.keys()]:
                    page_num = int(page) + 1
                    new_tables, new_raw_data = get_tables_from_doc(
                        self.doc_path,
                        specific_pages=page_num,
                        language=self.language,
                        api_version=api_version,
        ***REMOVED***
                    self.di_tables_pages[str(page)] = new_tables
                    self.raw_data_pages[str(page)] = new_raw_data

                    tables.extend(table for table in new_tables)
                    raw_data.extend(data for data in new_raw_data)
            else:
                for page in pages_to_check:
                    tables.extend(table for table in self.di_tables_pages[str(page)])
                    raw_data.extend(data for data in self.raw_data_pages[str(page)])

            # Select the right table
            table_nr = select_desired_table_only_header(tables, keywords)
            return tables[int(table_nr)], raw_data[int(table_nr)]
        except Exception as error:
            print("extract table error" + repr(error))
            return None
            # @ELIA?
