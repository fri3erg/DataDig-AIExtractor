from typing import Any, Dict, List
from classes.Extracted import ExtractedField, ExtractedTable
from classes.Options import ExceptionsExtracted, Options
from classes.Template import Template, TemplateTable
from ...llm_functions import complex_table_inspection, general_table_inspection, llm_extraction, tag_only
from ...extractor import GeneralScanner
from ..kid.kid_utils import create_pydantic_class, create_intelligent_pydantic_class
from ...llm_functions import (
    llm_extraction_and_tag,
)
from .kid_utils import clean_response_regex, clean_response_strips, create_pydantic_table_class, extracted_from_pydantic, extracted_from_pydantic_table


class Extractor(GeneralScanner):

    def __init__(self, images:list[bytes],template: Template, options:Options) -> None:
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
                extracted_table.append(ExtractedTable(template,fields=extracted_fields,dataframe=table, model_used=self.options.model))
                
                
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
        


***REMOVED***
    # testing
    doc_folder = "data\C\MEDIOLANUM\MYLIFEPIC_FR0011660851.pdf"
    kid_extractor = Extractor(doc_folder)
