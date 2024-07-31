from ....classes.Extracted import Extracted, ExtractedField
from ....classes.Options import ExceptionsExtracted, Options
from ....scanner.ai_manager.models import Models
from ....classes.Template import Template, TemplateTable
from typing import Any, Callable, List

from ..general_scanner import GeneralScanner
class MainExtractor(GeneralScanner):

    def __init__(self, images:list[bytes],text:list[str], template: Template, options:Options) -> None:
        super().__init__(images,text, template, options)
        self.tables:dict[TemplateTable, Any] = {***REMOVED***
        self.tables_present: bool = False
        self.complex_info_present: bool = False
        self.results = {***REMOVED***
        self.tables_present: bool = self.template.tables != [] or self.options.azure_ocr
        self.intelligent_present: bool = any(field.intelligent_extraction for field in self.template.fields)
        
    def first_stage(self) -> float | None: 
        
        try:
            #complex_info_present: bool = any(field.extra_description for field in self.template.fields)

            if self.tables_present:
                self.tables:dict[TemplateTable, Any] =self.get_tables()  or {***REMOVED***
            
            intelligent_template, basic_template = self.template.split_template()
            
            functions_parameters = {
                "basic_info": {"function": self.extract_basic_info, "args": {"template": basic_template***REMOVED******REMOVED***,
                **({"intelligent_info": {"function": self.extract_intelligent_info, "args": {"template": intelligent_template***REMOVED******REMOVED******REMOVED*** if self.intelligent_present else {***REMOVED***)
            ***REMOVED***
            results = self.threader(functions_parameters)
            self.extracted_fields += results.get("basic_info") or []
            self.extracted_fields += results.get("intelligent_info") or []
            for condition in [self.tables_present, self.complex_info_present, False]:
                self.progress += 0.30 if not condition else 0
            return self.progress

        except Exception as error:
            self.exceptions_occurred.append(ExceptionsExtracted(error=error, error_location="first stage",error_description=repr(error)))
            print("first stage error" + repr(error))
    
    
    def second_stage(self):
        try:
            functions_parameters = {
                **({"info_from_tables": {"function": self.extract_from_tables, "args": {"tables": self.tables***REMOVED******REMOVED******REMOVED*** if self.tables_present else {***REMOVED***),
                **({"complex_info": {"function": self.extract_complex_info, "args": {"extracted": self.results***REMOVED******REMOVED******REMOVED*** if self.complex_info_present else {***REMOVED***)
                ***REMOVED***
                
            if functions_parameters:
                self.results = self.threader(functions_parameters)
                
            self.extracted_tables += self.results.get("info_from_tables") or []
            self.extracted_fields += self.results.get("complex_info") or []

        except Exception as error:
            self.exceptions_occurred.append(ExceptionsExtracted(error=error, error_location="second stage",error_description=repr(error)))
            print("second stage error" + repr(error))
            
    def end_phase(self) -> Extracted:
        
        try:

            # REVIEW: what name do they need?
            filename = self.template.title
            

            api_costs = self._process_costs()
            
            self.extraction= Extracted(template=self.template, fields=self.extracted_fields, tables=self.extracted_tables, costs=api_costs, exceptions=self.exceptions_occurred, format=self.options.format, tags=["tag 1"])


        except Exception as error:
            print("final phase error" + repr(error))
            self.exceptions_occurred.append(ExceptionsExtracted(error=error, error_location="final stage",error_description=repr(error)))
            self.extraction = Extracted(template=self.template, fields=self.extracted_fields, tables=self.extracted_tables)
            filename = self.template.title

        print(self.extraction)
        Models.clear_resources_file(filename)
        

        return self.extraction

