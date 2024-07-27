from ....classes.Extracted import Extracted, ExtractedField
from ....classes.Options import ExceptionsExtracted, Options
from ....scanner.ai_manager.models import Models
from ....classes.Template import Template, TemplateTable
from typing import Any, Callable, List

from ..general_scanner import GeneralScanner
class MainExtractor(GeneralScanner):

    def __init__(self, images:list[bytes],text:list[str], template: Template, progress_callback: Callable, options:Options) -> None:
        self.progress_callback = progress_callback
        super().__init__(images,text, template, options)
        
        

    async def process(self) -> Extracted:
        """main processor in different phases, first phases extracts the tables and general information,
        and target market, second phase extracts the rest of the fields.

        Returns:
            dict(filename,dict()): dictionary containing the results for the file
        """
        # FIRST STAGE: get tables and general information
        tables_present: bool = False
        complex_info_present: bool = False
        results = {***REMOVED***
        tables = {***REMOVED***
        tables_present: bool = self.template.tables != [] or self.options.azure_ocr
        intelligent_present: bool = any(field.intelligent_extraction for field in self.template.fields)
        
        try:
            #complex_info_present: bool = any(field.extra_description for field in self.template.fields)

            if tables_present:
                tables:dict[TemplateTable, Any] =self.get_tables()  or {***REMOVED***
            
            intelligent_template, basic_template = self.template.split_template()
            
            functions_parameters = {
                "basic_info": {"function": self.extract_basic_info, "args": {"template": basic_template***REMOVED******REMOVED***,
                **({"intelligent_info": {"function": self.extract_intelligent_info, "args": {"template": intelligent_template***REMOVED******REMOVED******REMOVED*** if intelligent_present else {***REMOVED***)
            ***REMOVED***
            results = self.threader(functions_parameters)
            self.extracted_fields += results.get("basic_info") or []
            self.extracted_fields += results.get("intelligent_info") or []
            for condition in [tables_present, complex_info_present, False]:
                self.progress += 0.30 if not condition else 0
            self.progress_callback(self.progress)

        except Exception as error:
            self.exceptions_occurred.append(ExceptionsExtracted(error=error, error_location="first stage",error_description=repr(error)))
            print("first stage error" + repr(error))

        # SECOND STAGE: extract RIY, costs, commissions and performances
        try:
            functions_parameters = {
                **({"info_from_tables": {"function": self.extract_from_tables, "args": {"tables": tables***REMOVED******REMOVED******REMOVED*** if tables_present else {***REMOVED***),
                **({"complex_info": {"function": self.extract_complex_info, "args": {"extracted": results***REMOVED******REMOVED******REMOVED*** if complex_info_present else {***REMOVED***)
                ***REMOVED***
                
            if functions_parameters:
                results = self.threader(functions_parameters)
                
            self.extracted_tables += results.get("info_from_tables") or []
            self.extracted_fields += results.get("complex_info") or []

        except Exception as error:
            self.exceptions_occurred.append(ExceptionsExtracted(error=error, error_location="second stage",error_description=repr(error)))
            print("second stage error" + repr(error))

        try:

            # REVIEW: what name do they need?
            filename = self.template.title
            
            self.progress_callback(0.95)

            api_costs = self._process_costs()
            
            self.extraction= Extracted(template=self.template, fields=self.extracted_fields, tables=self.extracted_tables, costs=api_costs, exceptions=self.exceptions_occurred)


        except Exception as error:
            print("final phase error" + repr(error))
            self.exceptions_occurred.append(ExceptionsExtracted(error=error, error_location="final stage",error_description=repr(error)))
            self.extraction = Extracted(template=self.template, fields=self.extracted_fields, tables=self.extracted_tables)
            filename = self.template.title

        print(self.extraction)
        Models.clear_resources_file(filename)
        
        self.progress_callback(0.99)

        return self.extraction
