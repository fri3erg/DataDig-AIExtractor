import os
import re
import pandas as pd
from extractors.general_extractors.custom_extractors.certificates.derivati_extractor import DerivatiKidExtractor
from extractors.general_extractors.custom_extractors.certificates.issuers.Vontobel.tags_vontobel import (
    TabellaDeductableVontobel,
    TabellaFirstInfoVontobel,
    TabellaMainInfoVontobel,
    TabellaMainInfoVontobel2,
)
from extractors.general_extractors.llm_functions import llm_extraction_and_tag
from extractors.general_extractors.utils import filter_list_by_pattern, set_flag
from extractors.models import Models
from .config_vontobel import prompts
from .....config.json_config.json_vontobel import renaming


class VontobelDerivatiKidExtractor(DerivatiKidExtractor):

    def __init__(self, doc_path) -> None:
        self.doc_path = doc_path
        super().__init__(doc_path, "en")

    def extract_deductables(self):
        """extracts the callable from the document
        looks if the word is in the document thats it
        bool for some checks for regex in regex in other

        Returns:
            dict(): dictionary containing the callable
        """
        extraction = dict()
        try:
            extraction = llm_extraction_and_tag(
                [self.text[i] for i in range(2, min(len(self.text), 10))],
                prompts[self.language]["deductables_vontobel"],
                TabellaDeductableVontobel,
                self.file_id,
***REMOVED***

            extraction = dict(extraction)

            extraction["market"] = set_flag(extraction["market"], r".*sedex.*")

            extraction["issue_price_perc"] = set_flag(extraction["issue_price_perc"], r".*%.*")

            # extraction = clean_response_regex( "first_info_bnp", self.language, extraction)
        except Exception as error:
            print("extract_deductable error" + repr(error))
            error_list = ["market", "issue_price_perc"]
            extraction = {key: (extraction[key] if extraction.get(key) is not None else "ERROR") for key in error_list***REMOVED***

        return extraction

    def extract_main_info(self):
        """extracts the main info from the main table/tables


        Returns:
            dict(): dictionary containing the main info

        """

        extraction = dict()
        try:
            extraction = llm_extraction_and_tag(
                [self.text[i] for i in range(1, min(len(self.text), 9))],
                prompts[self.language]["main_info_vontobel"],
                TabellaMainInfoVontobel,
                self.file_id,
***REMOVED***

            # extraction = clean_response_regex( "first_info_bnp", self.language, extraction)

        except Exception as error:
            print("extract_main_info error" + repr(error))
            error_list = [
                "conditional_protection",
                "currency",
                "strike_date",
                "issue_date",
                "expiry_date",
                "final_valuation_date",
                "nominal",
                "barrier_autocall",
                "conditional_coupon_barrier",
                "memory",
                "strike_level",
                "autocall",
                "payment_callable_date",
                "instrument_description",
                "instrument_isin",
                "instrument_bloombergcode",
            ]
            extraction = {key: (extraction[key] if extraction.get(key) is not None else "ERROR") for key in error_list***REMOVED***

        return extraction

    def extract_main_info_2(self):
        """extracts the main info from the main table/tables


        Returns:
            dict(): dictionary containing the main info

        """

        extraction = dict()
        try:
            extraction = llm_extraction_and_tag(
                [self.text[i] for i in range(1, min(len(self.text), 9))],
                prompts[self.language]["main_info_vontobel_2"],
                TabellaMainInfoVontobel2,
                self.file_id,
***REMOVED***

            # extraction = clean_response_regex( "first_info_bnp", self.language, extraction)
        except Exception as error:
            print("extract_main_info_2 error" + repr(error))
            error_list = [
                "coupon",
                "payment_coupon_date",
                "observation_coupon_date",
                "payment_autocall_date",
                "observation_autocall_date",
                "barrier_type",
            ]

            extraction = {key: (extraction[key] if extraction.get(key) is not None else "ERROR") for key in error_list***REMOVED***

        return extraction

    def extract_first_info(self):
        """extracts the first info from the text
        info is in the first page

        Returns:
            dict(): dictionary containing the first info
        """
        extraction = dict()
        try:
            extraction = llm_extraction_and_tag(
                [self.text[0]], prompts[self.language]["first_info_vontobel"], TabellaFirstInfoVontobel, self.file_id
***REMOVED***

            # extraction = clean_response_regex( "first_info_bnp", self.language, extraction)
        except Exception as error:
            print("extract_first_info error" + repr(error))
            error_list = ["isin", "description", "issuer_desc"]
            extraction = {key: (extraction[key] if extraction.get(key) is not None else "ERROR") for key in error_list***REMOVED***

        return extraction

    def fill_array(self, dictionary):
        """fills the arrays in the dictionary with '-'
        to have the same length for excel

        Args:
            dictionary (_type_): _description_

        Returns:
            _type_: _description_
        """

        max_length = max((len(value) for value in dictionary.values() if isinstance(value, list)), default=0)
        # Iterate through the dictionary and fill the arrays with '-'
        for key, value in dictionary.items():
            if isinstance(value, list):
                # If the array is shorter than max_length, fill with '-'
                dictionary[key] = value + ["-"] * (max_length - len(value))

        return dictionary

    def _write_to_excel(self, complete, singles, api_costs, filename):
        """writes the results to an excel file

        Args:
            args (dict()): all the results
        """

        with pd.ExcelWriter(
            "results\\20febbraio\\resultsmarco_{***REMOVED***.xlsx".format(os.path.basename(self.file_id)),
            engine="xlsxwriter",
        ) as excel_writer:
            # Write the first DataFrame to Sheet1
            results1 = pd.DataFrame(complete).T
            results1.to_excel(excel_writer, sheet_name="info_anagrafiche", header=True)

            results2 = pd.DataFrame(singles, index=[filename]).T
            results2.to_excel(excel_writer, sheet_name="other-info", header=True)

            # Write the second DataFrame to Sheet2
            results4 = pd.DataFrame.from_dict(api_costs, orient="index")
            results4 = pd.concat(
                [
                    pd.DataFrame([results4.columns], columns=results4.columns),
                    results4,
                ]
***REMOVED***
            results4.reset_index(inplace=True)
            results4.columns = ["API"] + list(results4.columns)[1:]
            results4.to_excel(excel_writer, sheet_name="api costs", header=True, index=False)

    def clean_main_regex(self, main_info):
        """cleans the main info dictionary from regex

        Args:
            main_info (dict()): dictionary containing the main info

        Returns:
            dict(): dictionary containing the main info
        """
        main_info["strike_level"] = filter_list_by_pattern(main_info["strike_level"], r"(\d+.{0,3***REMOVED***%)")

        if set_flag(main_info["barrier_type"], r".*on.*th.*Fina.*Valuatio.*Date.*"):  # TODO: check
            main_info["barrier_type"] = "Europea"
        else:
            main_info["barrier_type"] = "other"

        main_info["memory"] = set_flag(main_info["memory"], r".*applicabl.*")

        main_info["conditional_protection"] = filter_list_by_pattern(
            main_info["conditional_protection"], r"(\d+.{0,3***REMOVED***%)"
        )

        main_info["conditional_coupon"] = ["N/A"]
        main_info["unconditional_coupon"] = ["N/A"]

        if any(re.search(r"\d", element) for element in main_info["observation_coupon_date"]):
            main_info["conditional_coupon"] = main_info.pop("coupon", None)
        else:
            main_info["unconditional_coupon"] = main_info.pop("coupon", None)

        return main_info

    def process(self):
        """main processor in different phases, first phases extracts the tables and general information,
        and target market, second phase extracts the rest of the fields.

        Returns:
            dict(filename,dict()): dictionary containing the results for the file
        """
        # FIRST STAGE: get tables and general information
        try:
            print("REAL START")

            functions_parameters = {
                "first_info": {"function": self.extract_first_info***REMOVED***,
                "main_info": {"function": self.extract_main_info***REMOVED***,
                "main_info_2": {"function": self.extract_main_info_2***REMOVED***,
                "deductables": {"function": self.extract_deductables***REMOVED***,
            ***REMOVED***

            results = self.threader(functions_parameters)

            first_info, main_info, main_info_2, deductables = [results[key] for key in results]

        except Exception as error:
            print("first stage error" + repr(error))

        # SECOND STAGE: extract RIY, costs, commissions and performances
        """
        try:

            riy, exit_entry_management_costs, performance = [task.result() for task in tasks]
            
            
            functions_parameters={
                "riy": {"function":self.extract_riy,"args":{2***REMOVED******REMOVED***,
                "exit_entry_management_costs": {"function":self.extract_entryexit_management_costs***REMOVED***,
                "performance": {"function":self.extract_performances,"args":{tables["performance"]***REMOVED******REMOVED***,
                ***REMOVED***
            results = self.threader(functions_parameters)
            riy = results["riy"]
            exit_entry_management_costs = results["exit_entry_management_costs"]
            performance = results["performance"]
            

        except Exception as error:
            print("second stage error" + repr(error))"""

        try:
            # Merge and orders all the results

            filename = os.path.splitext(os.path.basename(self.doc_path))[0]

            api_costs = self._process_costs()
            main_info = dict(main_info)
            main_info.update(dict(main_info_2))
            main_info = self.clean_main_regex(main_info)

            main_info = {key: [value] if not isinstance(value, list) else value for key, value in main_info.items()***REMOVED***
            main_info = self.fill_array(main_info)

            # raccordo
            complete = self.raccorda(
                main_info,
                renaming,
                keep=True,
***REMOVED***

            singles = self.raccorda(
                {**dict(first_info), **dict(deductables)***REMOVED***,
                renaming,
                keep=True,
***REMOVED***

            """#if want to add these too careful about self.language, links to wrong tags and prompt
            complete2 = self.raccorda(
                {
                    "file_name": filename,
                    **dict(performance),
                    **dict(riy),
                    **dict(exit_entry_management_costs),
                ***REMOVED***,
                "kid",
                keep=True,
***REMOVED***
            """

            self._write_to_excel(complete, singles, api_costs, filename)

            json = self.create_json(
                {
                    "file_name": filename,
                    # **dict(performance),
                    # **dict(riy),
                    # **dict(exit_entry_management_costs),
                    **dict(api_costs),
                    **dict(deductables),
                    # **dict(clean_response_regex("vontobel_main","it", main_info)),
                    **dict(main_info),
                    **dict(first_info),
                ***REMOVED***,
                "vontobel",
***REMOVED***

            return json

        except Exception as error:
            print("dictionary error: " + repr(error))
            filename = os.path.splitext(os.path.basename(self.doc_path))[0]
            complete = None

        Models.clear_resources_file(filename)
        return complete
