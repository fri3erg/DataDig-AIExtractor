import os

from extractors.models import Models
from extractors.general_extractors.custom_extractors.kid.kid_extractor import Extractor

from .....config.json_config.json_kid import renaming
from classes.Template import Template
from typing import Callable

class DataExtractor(Extractor):

    def __init__(self, images, template: Template, progress_callback: Callable, language="it", model="gpt-3.5-turbo") -> None:
        self.progress_callback = progress_callback
        super().__init__(images,template, language, model)

    async def process(self):
        """main processor in different phases, first phases extracts the tables and general information,
        and target market, second phase extracts the rest of the fields.

        Returns:
            dict(filename,dict()): dictionary containing the results for the file
        """
        # FIRST STAGE: get tables and general information
        try:
            tables_present: bool = any(self.template.tables)
            #complex_info_present: bool = any(field.extra_description for field in self.template.fields)

            functions_parameters = {
                "basic_info": {"function": self.extract_basic_info***REMOVED***,
            ***REMOVED***
            if tables_present:
                functions_parameters.update({"tables": {"function": self.get_tables***REMOVED******REMOVED***)
                
            results = self.threader(functions_parameters)

            tables = results["tables"]
            basic_info = results["basic_info"]

        except Exception as error:
            print("first stage error" + repr(error))

        # SECOND STAGE: extract RIY, costs, commissions and performances
        try:
            functions_parameters = {***REMOVED***
            if tables_present:
                functions_parameters.update({"info_from_tables": {"function": self.extract_from_tables, "args": {"table": tables***REMOVED******REMOVED******REMOVED***)
                
            #if complex_info_present:
            #    functions_parameters.update({"complex_info": {"function": self.extract_complex_info, "args": {"results": results***REMOVED******REMOVED******REMOVED***)
                
            if functions_parameters:
                results = self.threader(functions_parameters)
                
            info_from_tables = results.get("info_from_tables") or {***REMOVED***
            complex_info = results.get("complex_info") or {***REMOVED***

        except Exception as error:
            print("second stage error" + repr(error))

        try:

            # REVIEW: what name do they need?
            filename = self.template.title

            api_costs = self._process_costs()

            # raccordo
            complete = self.raccorda(
                {
                    "file_name": filename,
                    **dict(basic_info),
                    **dict(complex_info),
                    **dict(info_from_tables),
                    **dict(api_costs),
                ***REMOVED***,
                renaming,
***REMOVED***

            complete = self.create_json(
                {
                    "file_name": filename,
                    **dict(basic_info),
                    **dict(complex_info),
                    **dict(info_from_tables),
                    **dict(api_costs),
                ***REMOVED***,
                "kid",
***REMOVED***

        except Exception as error:
            print("dictionary error" + repr(error))
            filename = os.path.splitext(os.path.basename(self.doc_path))[0]
            complete = dict([(filename), dict()])

        print(complete)
        Models.clear_resources_file(filename)

        return complete
