from math import cos
import os

from attr import field

from classes.Extracted import Extracted, ExtractedField
from classes.Options import Options
from extractors.models import Models
from extractors.general_extractors.custom_extractors.kid.kid_extractor import Extractor

from .....config.json_config.json_kid import renaming
from classes.Template import Template, TemplateTable, split_template
from typing import Any, Callable, List

class DataExtractor(Extractor):

    def __init__(self, images, template: Template, progress_callback: Callable, options:Options) -> None:
        self.progress_callback = progress_callback
        super().__init__(images,template, options)
        
        

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
        try:
            tables_present: bool = self.template.tables != [] or self.options.azure_ocr
            #complex_info_present: bool = any(field.extra_description for field in self.template.fields)

            if tables_present:
                tables:dict[TemplateTable, Any] =self.get_tables()  or {***REMOVED***
            
            intelligent_template, basic_template = self.template.split_template()
            
            functions_parameters = {
                "basic_info": {"function": self.extract_basic_info, "args": {"template": basic_template***REMOVED******REMOVED***,
                "intelligent_info": {"function": self.extract_intelligent_info, "args": {"template": intelligent_template***REMOVED******REMOVED***,
            ***REMOVED***
            results = self.threader(functions_parameters)
            self.extracted_fields += results["basic_info"] or []
            self.extracted_fields += results["intelligent_info"] or []

        except Exception as error:
            print("first stage error" + repr(error))

        # SECOND STAGE: extract RIY, costs, commissions and performances
        try:
            functions_parameters = {
                **({"info_from_tables": {"function": self.extract_from_tables, "args": {"table": tables***REMOVED******REMOVED******REMOVED*** if tables_present else {***REMOVED***),
                **({"complex_info": {"function": self.extract_complex_info, "args": {"results": results***REMOVED******REMOVED******REMOVED*** if complex_info_present else {***REMOVED***)
                ***REMOVED***
                
            if functions_parameters:
                results = self.threader(functions_parameters)
                
            self.extracted_tables += results.get("info_from_tables") or []
            self.extracted_fields += results.get("complex_info") or []

        except Exception as error:
            print("second stage error" + repr(error))

        try:

            # REVIEW: what name do they need?
            filename = self.template.title

            api_costs = self._process_costs()
            
            self.extraction= Extracted(template=self.template, fields=self.extracted_fields, tables=self.extracted_tables, costs=api_costs)


        except Exception as error:
            print("dictionary error" + repr(error))
            self.extraction = Extracted(template=self.template, fields=self.extracted_fields, tables=self.extracted_tables)
            filename = self.template.title

        print(self.extraction)
        Models.clear_resources_file(filename)

        return self.extraction
