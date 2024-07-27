from abc import abstractmethod
from typing import Any, Dict, List
from ...classes.Extracted import Extracted, ExtractedField, ExtractedTable
from ...classes.Options import ExceptionsExtracted, Options
from ..ai_manager.models import Models
from ..config.cost_config import cost_per_token
from .utils import create_intelligent_pydantic_class, create_pydantic_class, create_pydantic_table_class, extracted_from_pydantic, extracted_from_pydantic_table, get_document_text, upload_df_as_excel
from .llm_functions import get_doc_language, llm_extraction, llm_extraction_and_tag
from ...scanner.ai_manager.document_intelligence import get_tables_from_doc
from .utils import select_desired_page, select_desired_table
from ...scanner.extractors.llm_functions import general_table_inspection
from ..config.JsonClasses import JSONExtraction
import threading
from ...classes.Template import Template, TemplateTable


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

    def __init__(self, images:list[bytes], text:List[str], template:Template, options:Options):
        self.file_id = template.title
        self.images = images
        self.options = options
        self.text: List[str] = text
        if not options.language:
            self.options.language = get_doc_language(self.text, self.file_id)
        self.template: Template = template
        self.progress:float= 0
        self.di_tables_pages = {***REMOVED***
        self.raw_data_pages = {***REMOVED***
        self.exceptions_occurred: List[ExceptionsExtracted] = []
        self.extracted_tables:List[ExtractedTable]= []
        self.extracted_fields:List[ExtractedField]= []
        self.extraction:Extracted = Extracted(template)

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
    
    
    
    
    def get_tables(self):
        """calc table extractor, it extracts the three tables from the document asynchronously

        Returns:
            dict([pandas.dataframe]): tables as dataframe
        """
        tables=dict()
        if self.options.azure_ocr:
            try:
                self.fill_tables()
            except Exception as error:
                print("calc table error" + repr(error))
                self.exceptions_occurred.append(ExceptionsExtracted( error, "filling tables",repr(error)))
                    
        for table in self.template.tables:    
            try:
                tables.update({table: self._extract_table(table.keywords)***REMOVED***)

            except Exception as error:
                #REDO ERROR HANDLING
                print("calc table error" + repr(error))
                self.exceptions_occurred.append(ExceptionsExtracted( error, f"extracting tables: {table.title***REMOVED***",repr(error)))


        return tables
    
    
    def extract_intelligent_info(self, template:Template) -> List[ExtractedField]:
        
        extraction_fields=[]
        try:
            # extract and clean
            extraction = llm_extraction_and_tag(
                self.text, template, self.file_id,create_intelligent_pydantic_class(template), self.options
***REMOVED***
            #extraction = clean_response_regex(regex_cleaning, extraction)
            extraction_fields: List[ExtractedField] = extracted_from_pydantic(self, extraction)
            
        except Exception as error:
            print("intelligent info extraction error" + repr(error))
            self.exceptions_occurred.append(ExceptionsExtracted( error, "intelligent_info",repr(error)))
            
        return extraction_fields
        

    def extract_basic_info(self, template: Template) -> List[ExtractedField]:
        """
        Extract general data from the document. Namely RHP and SRI.

        Returns:
            List[ExtractedField]: extracted data
        """
        extraction_fields=[]
        try:
            # extract and clean
            extraction = llm_extraction_and_tag(
                self.text, template, self.file_id, create_pydantic_class(template), self.options
***REMOVED***
            #extraction = clean_response_regex(regex_cleaning, extraction)
            extraction_fields: List[ExtractedField] = extracted_from_pydantic(self, extraction)
            

        except Exception as error:
            self.exceptions_occurred.append(ExceptionsExtracted( error, "basic_info",repr(error)))
            print("basic info extraction error" + repr(error))

        return extraction_fields

    # REVIEW: NEED TO UPLOAD TABLE AS DF
    def extract_from_tables(self, tables:dict[TemplateTable, Any]) -> list[ExtractedTable]:
        """extracts riy from the document

        Returns:
            dict(): riy extracted
        """
        extracted_table = []
        
        for template, table in tables.items():
            try:
            # Select page with RIY
            
                extraction = general_table_inspection(table, create_pydantic_table_class(template), self.file_id, add_text=template.description)
                extracted_fields: Dict[str,Dict[str,ExtractedField]]= extracted_from_pydantic_table(self, extraction, template)
                extracted_table.append(ExtractedTable(template,fields=extracted_fields,dataframe=table))
                
                
                #extraction = clean_response_regex(regex_cleaning, extraction)
            except Exception as error:
                print("extract riy error" + repr(error))
                self.exceptions_occurred.append(ExceptionsExtracted( error, f"extracting tables: {template.title***REMOVED***",repr(error)))


        return extracted_table

    # REVIEW: NEED TO UPLOAD TABLE AS DF
    def extract_complex_info(self, extracted) -> List[ExtractedField]:
        
        extraction: List[ExtractedField] =[]
        try:
            extracted = llm_extraction_and_tag(
                extracted, self.template, self.file_id, create_pydantic_class(self.template), self.options
***REMOVED***
            extraction= extracted_from_pydantic(self, extracted)
        except Exception as error:
            print("extract entry exit costs error" + repr(error))
            self.exceptions_occurred.append(ExceptionsExtracted( error, "complex_info",repr(error)))

        return extraction
        


    def _extract_table(self, keywords, black_list_pages=[]) -> Any:
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
            page, _ = select_desired_page(text, keywords)

            # Get all the tables from the page
            if self.di_tables_pages is not None and page not in self.di_tables_pages.keys():
                page_num = str(int(page) + 1)
                tables, raw_data = get_tables_from_doc(self.images[page], specific_pages=page_num, language=self.options.language or "it")
                document=getattr(raw_data,"pages", None)
                all_text = ""
                for line in getattr(document[int(page)] if document else [],"lines"):
                    all_text += line.content + "\n"
                self.text[int(page)] = all_text
                self.di_tables_pages[page] = tables
                self.raw_data_pages[page] = raw_data
            else:
                tables = self.di_tables_pages[page]
                raw_data = self.raw_data_pages[page]

            # Select the right table
            table_nr = select_desired_table(tables, keywords)
            return tables[int(table_nr)]

        except Exception as error:
            print("extract table error" + repr(error))
            return "", ""
            # @ELIA?

    def fill_tables(self, pages:List[int] | None = None):
        #experimental for faster runs, fills the tables in the document asynchronously all in one

        #Args:
            #page (_type_): _description_
        function_parameters = {***REMOVED***
        for i, image in enumerate(self.images):
            pages_exists= pages and i in pages
            if str(i) not in self.di_tables_pages.keys() and (pages_exists or not pages):
                function_parameters[f"{i***REMOVED***"] = {"function": get_tables_from_doc, "args": {"image": image, "language": self.options.language if self.options.language else "it"***REMOVED******REMOVED***
        result = self.threader(function_parameters)
        for key, value in result.items():
            tables, raw_data = value
            self.di_tables_pages[key] = tables
            self.raw_data_pages[key] = raw_data
            for document in getattr(raw_data, "pages", []):
                all_text = ""
                for line in getattr(document, "lines"):
                    all_text += line.content + "\n"
                self.text.append(all_text)
            
            
            
        

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
    async def process(self)->Extracted: ...
