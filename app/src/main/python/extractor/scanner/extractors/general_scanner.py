from abc import abstractmethod
from typing import Any, Dict, List

from pandas import DataFrame
from ...classes.Extracted import Extracted, ExtractedField, ExtractedTable, ExtractionCosts
from ...classes.Options import ExceptionsExtracted, Options
from ..ai_manager.models import Models
from ...configs.cost_config import cost_per_token
from .utils import create_intelligent_pydantic_class, create_pydantic_class, create_pydantic_table_class, extracted_from_pydantic, extracted_from_pydantic_table
from .llm_functions import get_doc_language, llm_extraction_and_tag
from ...scanner.ai_manager.document_intelligence import get_tables_from_doc
from .utils import select_desired_page, select_desired_table
from ...scanner.extractors.llm_functions import general_table_inspection
import threading
from ...classes.Template import Template, TemplateTable
from ...configs.configs import prompts, prompts_intelligent


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
        if  options.language == None or options.language == "auto-detect":
            self.options.language = get_doc_language(self.text, self.file_id, self.options)
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
        """
    	Retrieves tables from the document based on the provided template.

    	Tables are extracted using the keywords specified in the template. If Azure OCR is enabled, 
    	the function attempts to fill the tables before extraction. Any errors encountered during 
    	this process are caught and appended to the exceptions_occurred list.

    	Returns:
    		dict: A dictionary containing the extracted tables, where each key is a table object 
    		  and its corresponding value is the extracted table data.
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
        """
        Extracts intelligent information from the given template using the text, file ID, and options.
        
        Args:
            template (Template): The template object containing the information to be extracted.
        
        Returns:
            List[ExtractedField]: A list of ExtractedField objects representing the extracted information.
        
        Raises:
            Exception: If an error occurs during the extraction process.
        """
        
        extraction_fields=[]
        try:
            # extract and clean
            extraction, optional_error = llm_extraction_and_tag(
                self.text, template, self.file_id,create_intelligent_pydantic_class(template), self.options, prompts_intelligent[self.options.language or "it"]
***REMOVED***
            if optional_error:
                self.exceptions_occurred.append(optional_error)
            extraction_fields: List[ExtractedField] = extracted_from_pydantic(self.template, extraction)
            
        except Exception as error:
            print("intelligent info extraction error" + repr(error))
            self.exceptions_occurred.append(ExceptionsExtracted( error, "intelligent_info",repr(error)))
            
        return extraction_fields
        

    def extract_basic_info(self, template: Template) -> List[ExtractedField]:
        """
        Extract general data from the document.  The data extracted is based on the template provided and has no intelligent extraction flag

        Returns:
            List[ExtractedField]: extracted data
        """
        extraction_fields=[]
        try:
            # extract and clean
            extraction , optional_error = llm_extraction_and_tag(
                self.text, template, self.file_id, create_pydantic_class(template), self.options, prompts[self.options.language or "it"]
***REMOVED***
            
            if optional_error:
                self.exceptions_occurred.append(optional_error)

            extraction_fields: List[ExtractedField] = extracted_from_pydantic(self.template, extraction)
            

        except Exception as error:
            self.exceptions_occurred.append(ExceptionsExtracted( error, "basic_info",repr(error)))
            print("basic info extraction error" + repr(error))

        return extraction_fields

    def extract_from_tables(self, tables:dict[TemplateTable, Any]) -> list[ExtractedTable]:
        """extracts riy from the document

        Returns:
            dict(): riy extracted
        """
        extracted_table = []
        
        for template, table in tables.items():
            try:
            # Select page with RIY
            
                extraction = general_table_inspection(table, create_pydantic_table_class(template), self.file_id,self.options, add_text=template.description)
                extracted_fields: Dict[str,Dict[str,ExtractedField]]= extracted_from_pydantic_table(self, extraction, template)
                extracted_table.append(ExtractedTable(template,fields=extracted_fields,dataframe=table))
                
                
            except Exception as error:
                print("extract riy error" + repr(error))
                self.exceptions_occurred.append(ExceptionsExtracted( error, f"extracting tables: {template.title***REMOVED***",repr(error)))


        return extracted_table

    # REVIEW: NEED TO UPLOAD TABLE AS DF
    def extract_complex_info(self, extracted) -> List[ExtractedField]:
        
        extraction: List[ExtractedField] =[]
        try:
            extracted, optional_error = llm_extraction_and_tag(
                extracted, self.template, self.file_id, create_pydantic_class(self.template), self.options, prompts[self.options.language or "it"]
***REMOVED***
            if optional_error:
                self.exceptions_occurred.append(optional_error)
                
            extraction= extracted_from_pydantic(self.template, extracted)
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
        # Select page with table

        text = [page if i not in black_list_pages else "" for i, page in enumerate(self.text)]
        page, _ = select_desired_page(text, keywords)

        # Get all the tables from the page
        if (self.di_tables_pages is not None and page not in self.di_tables_pages.keys()) or len(self.di_tables_pages.get(page, []))==0:
            page_num = str(int(page) + 1)
            tables, raw_data = get_tables_from_doc(self.images[int(page)], specific_pages=page_num, language=self.options.language or "it")
            document=getattr(raw_data,"pages", None)
            all_text = ""
            for line in getattr(document[int(page)] if document else [],"lines"):
                all_text += line.content + "\n"
            self.text[int(page)] = all_text
            self.di_tables_pages[page] = tables
            self.raw_data_pages[page] = raw_data
        else:
            tables = self.di_tables_pages[page]
            #raw_data = self.raw_data_pages[page]

        # Select the right table
        table_nr = select_desired_table(tables, keywords)
        return tables[int(table_nr)] if table_nr is not None else DataFrame()

            

    def fill_tables(self, pages:List[int] | None = None):
        """
        Fills the tables in the document asynchronously for faster runs.

        Args:
            pages (List[int] | None, optional): List of pages to fill tables for. If None, all pages will be filled. Defaults to None.

        Returns:
            None

        This function fills the tables in the document asynchronously for faster runs. It takes an optional parameter `pages` which is a list of pages to fill tables for. If `pages` is None, all pages will be filled. The function first creates a dictionary `function_parameters` with the pages to fill tables for. It then calls the `threader` method with `function_parameters` to fill the tables asynchronously. The results are then stored in `self.di_tables_pages` and `self.raw_data_pages`. The text for each page is also extracted and stored in `self.text`.
        """
        #experimental for faster runs, fills the tables in the document asynchronously all in one

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
            for i, document in enumerate(getattr(raw_data, "pages", [])):
                all_text = ""
                for line in getattr(document, "lines"):
                    all_text += line.content + "\n"
                self.text[int(i)]=all_text
            
            
            
        

    def _process_costs(self) -> List[ExtractionCosts]:
        """processes the cost of the calls given local config and prepares them for the output

        Returns:
            _type_: _description_
        """
        
        api_costs:List[ExtractionCosts] = Models.get_costs(self.file_id)
        api_costs.append(ExtractionCosts("azure", len(self.di_tables_pages), len(self.di_tables_pages) * cost_per_token["azure"], "EUR"))

        total_tokens = sum(getattr(entry, "tokens", 0) for entry in api_costs)
        total_cost = sum(getattr(entry, "cost", 0.0) for entry in api_costs)

        # Add the "total" element to the dictionary
        api_costs.append(ExtractionCosts("total", total_tokens, total_cost, "EUR"))
        for entry in api_costs:
            setattr(entry,"cost", round(getattr(entry, "cost", 0.0), 2))
        return api_costs


    @abstractmethod
    async def process(self)->Extracted: ...
