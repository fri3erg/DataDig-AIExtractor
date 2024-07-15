import re
from typing import Any, List

from classes.Extracted import ExtractedField, ExtractedTable
from classes.Options import Options
from classes.Template import Template, TemplateTable
from .insurance.kid.cleaning_kid import regex_cleaning, strips_cleaning


from extractors.general_extractors.custom_extractors.kid.insurance.kid.tags_kid import (
    InformazioniBase,
    TabellaCostiGestione,
    TabellaCostiIngresso,
    TabellaRiy,
    TabellaScenariPerformance,
)
from .insurance.kid.config_kid import prompts, word_representation

from ...llm_functions import complex_table_inspection, general_table_inspection, llm_extraction, tag_only
from ...extractor import GeneralScanner
from ..kid.kid_utils import create_pydantic_class, create_intelligent_pydantic_class

from ...llm_functions import (
    llm_extraction_and_tag,
)
from .kid_utils import clean_response_regex, clean_response_strips, extracted_from_pydantic


class Extractor(GeneralScanner):

    def __init__(self, images,template: Template, options:Options) -> None:
        super().__init__(images,template, options)

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
                error_list = [table.title for table in self.template.tables]
                for i, key in enumerate(error_list):
                    if not key:
                        tables[i] = dict([("ERROR", "ERROR")])
                        
        try:
            for table in self.template.tables:
                tables.update({table: self._extract_table(table.keywords)***REMOVED***)

        except Exception as error:
            #REDO ERROR HANDLING
            print("calc table error" + repr(error))
            error_list = [table.title for table in self.template.tables]
            for i, key in enumerate(error_list):
                if not key:
                    tables[i] = dict([("ERROR", "ERROR")])

        return dict(tables)
    
    
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
            extraction = []
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
            print("basic info extraction error" + repr(error))
            extraction = []

        return extraction_fields

    # REVIEW: NEED TO UPLOAD TABLE AS DF
    def extract_from_tables(self, tables:dict[TemplateTable, Any]):
        """extracts riy from the document

        Returns:
            dict(): riy extracted
        """
        extraction = []
        try:
            for table in tables:
            # Select page with RIY
                extraction = tag_only(
                    self.text[page:], table.fields, create_pydantic_class(table.fields), self.file_id
    ***REMOVED***
                extracted_fields: List[ExtractedField]= extracted_from_pydantic(self, extraction)
                extracted_table= ExtractedTable(table,rows=extracted_fields, model_used=self.model)
                extraction.append(extracted_table)
                
                #extraction = clean_response_regex(regex_cleaning, extraction)
        except Exception as error:
            print("extract riy error" + repr(error))


        return extraction

    # REVIEW: NEED TO UPLOAD TABLE AS DF
    def extract_complex_info(self, extracted):

        try:
            extraction = llm_extraction_and_tag(
                extracted, self.template, self.file_id, create_pydantic_class(self.template), self.model, self.language
***REMOVED***
            extraction = clean_response_regex(regex_cleaning, extraction)
        except Exception as error:
            print("extract entry exit costs error" + repr(error))

        return extraction
        


***REMOVED***
    # testing
    doc_folder = "data\C\MEDIOLANUM\MYLIFEPIC_FR0011660851.pdf"
    kid_extractor = Extractor(doc_folder)
