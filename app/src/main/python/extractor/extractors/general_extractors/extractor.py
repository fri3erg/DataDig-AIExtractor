from abc import abstractmethod
from typing import List
from ..models import Models
from .config.cost_config import cost_per_token
from .utils import get_document_text, upload_df_as_excel
from extractors.general_extractors.config.cost_config import cost_per_token
from .llm_functions import get_doc_language, llm_extraction
from extractors.azure.document_intelligence import get_tables_from_doc
from .utils import select_desired_page, select_desired_table
from extractors.general_extractors.llm_functions import general_table_inspection

from .config.JsonClasses import JSONExtraction
import threading
from classes.Template import Template


class ThreadFunction(threading.Thread):
    def __init__(self, function, args):
        threading.Thread.__init__(self)
        self.function = function
        self.args = args

    def run(self):
        if self.args is None:
            self.result = self.function()
        else:
            self.result = self.function(**self.args)

    def get_result(self):
        return self.result


class GeneralScanner:
    """parent class for all extractors"""

    def __init__(self, images,template:Template, language="it", model= "gpt-3.5-turbo"):
        self.file_id = template.title
        self.images = images
        self.text: List[str] = get_document_text(images)
        self.template = template
        self.model= model
        if language:
            self.language = language
        else:
            self.language = get_doc_language(self.text, self.file_id)

        self.di_tables_pages = {***REMOVED***
        self.raw_data_pages = {***REMOVED***
        self.extraction = {***REMOVED***

    # DOCSTRING MISSING
    def threader(self, functions_parameters):

        threads = {***REMOVED***
        results = {***REMOVED***
        for function_name, parameters in functions_parameters.items():
            func, args = parameters["function"], parameters.get("args")

            thread = ThreadFunction(func, args)
            threads[function_name] = thread
            thread.start()
        for _, thread in threads.items():
            thread.join()
        for function_name, thread in threads.items():
            results[function_name] = thread.get_result()
        return results

    def _extract_table(self, keywords, black_list_pages=[]):
        """General table extractor, given a table type it first finds the page within
        the document where the table is located, it then extracts all the tables from
        that page and returns the one with the most occurrences of the words of the table
        type.

        Args:
           keywords (str[]): Keywords to look for in the page.
           black_list_pages (int[], optional): Pages to ignore. Defaults to [].

        Returns:
            pandas.DataFrame: dataframe containing the table.
        """
        try:
            # Select page with table

            text = [page if i not in black_list_pages else "" for i, page in enumerate(self.text)]
            page = select_desired_page(text, keywords)

            # Get all the tables from the page
            if self.di_tables_pages is not None and page not in self.di_tables_pages.keys():
                page_num = int(page) + 1
                tables, raw_data = get_tables_from_doc(self.images, specific_pages=page_num, language=self.language)
                self.di_tables_pages[page] = tables
                self.raw_data_pages[page] = raw_data
            else:
                tables = self.di_tables_pages[page]
                raw_data = self.raw_data_pages[page]

            # Select the right table
            table_nr = select_desired_table(tables, keywords)
            return tables[int(table_nr)], raw_data

        except Exception as error:
            print("extract table error" + repr(error))
            return None
            # @ELIA?

    """def fill_tables(self, pages):
        #experimental for faster runs, fills the tables in the document asynchronously all in one

        #Args:
            #page (_type_): _description_
        
        fill, raw_data = get_tables_from_doc(self.images, specific_pages=pages, language=self.language)
        for idx, table in enumerate(fill):
            safe_number = getattr(raw_data.tables[idx] if idx < len(raw_data.tables) else None, "bounding_regions", None)[0].get("pageNumber", None)
            if safe_number:
                self.di_tables_pages.setdefault(str(safe_number - 1), []).append(table)
                self.raw_data_pages.setdefault(str(safe_number - 1), []).append(raw_data)
        """

    def _process_costs(self):
        """processes the cost of the calls given local config and prepares them for the output

        Returns:
            _type_: _description_
        """
        api_costs = Models.get_costs(self.file_id)
        azure_costs = {
            "azure": {"pages": len(self.di_tables_pages), "cost": len(self.di_tables_pages) * cost_per_token["azure"]***REMOVED***
        ***REMOVED***
        api_costs.update(azure_costs)

        total_tokens = sum(entry.get("tokens", 0) for entry in api_costs.values())
        total_cost = sum(entry.get("cost", 0) for entry in api_costs.values())

        # Add the "total" element to the dictionary
        api_costs["total"] = {"tokens": total_tokens, "cost": total_cost***REMOVED***
        for entry in api_costs.values():
            if "cost" in entry:
                entry["cost"] = round(entry["cost"], 2)
        return api_costs

    def extract_from_multiple_tables(self, pages, prompts_and_tags, complex=False):
        """extracts from multiple tables

        Args:
            pages (int[]): pages to extract from
            prompts_and_tags ([pydantic_class] || [(str,pydantic_class)] ): prompts and tags to use, takes only tags if not complex, an array of tuples of prompts and tags if complex

        Returns:
            dict(): dict containing the results
        """
        try:

            extraction = dict()
            list_tables = list(self.di_tables_pages)
            tables = []
            concatenated_str = "i valori sono in una di queste tabelle e solo in una o in nessuna, se si riferisce all'allegato ignoralo "
            for page in pages:
                tables += self.di_tables_pages[list_tables[page]]

            for idx, table in enumerate(tables):
                table = upload_df_as_excel(table)
                concatenated_str = concatenated_str + f"||||||||||||tabella numero {idx***REMOVED***:{table***REMOVED*** "

            for tag in prompts_and_tags:
                llm_extract = concatenated_str
                if complex:
                    prompt, tag = tag
                    llm_extract = llm_extraction(concatenated_str, prompt, self.file_id)
                extraction.update(
                    dict(
                        general_table_inspection(
                            llm_extract,
                            tag,
                            self.file_id,
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

        except Exception as error:
            print("extract multiple tables error" + repr(error))

        return extraction

    def create_json(self, results, type="kid"):
        """creates a json from the results

        Args:
            results (dict()): results to convert

        Returns:
            json: json of the results
        """
        # dict for filename and costs, replace is for json format

        # complete={renaming[key]:value for key,value in results.items() if key in renaming.keys()***REMOVED***

        # return complete

        extraction = JSONExtraction(doc_type=type, results=results, doc_path=self.images)

        json_output = extraction.to_json()

        return json_output

    def raccorda(self, dictionary, renaming: dict, keep=False):  # could tecnically go to utils
        """renames fiels

        Args:
            dictionary (dict()): dict to rename
            rename (dict()): dict containing the renaming
            keep (bool, optional): if true, keeps the old field. Defaults to False.

        Returns:
            new_dict dict(): dict renamed
        """
        # uncomment for extra fields
        # dictionary=self.create_json(dictionary)
        new_dict = {renaming[key]: value for key, value in dictionary.items() if key in renaming.keys()***REMOVED***
        if keep:
            new_dict.update({key: value for key, value in dictionary.items() if key not in renaming.keys()***REMOVED***)
        return new_dict

    @abstractmethod
    def process(self): ...
