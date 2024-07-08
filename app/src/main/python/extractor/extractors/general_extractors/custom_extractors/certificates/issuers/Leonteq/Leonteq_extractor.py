from extractors.general_extractors.custom_extractors.certificates.derivati_extractor import DerivatiKidExtractor
import os
import re
import pandas as pd
from extractors.general_extractors.llm_functions import (
    llm_extraction_and_tag,
)
from extractors.general_extractors.llm_functions import general_table_inspection

from extractors.models import Models
from extractors.general_extractors.utils import check_valid, extract_regex_text, upload_df_as_excel
from .tags_leonteq import (
    InformazioniBaseLeonteq,
    TabellaCedola,
    TabellaMainInfo,
    TabellaSottostanti,
    TabellaSottostantiHeader,
)
from .config_leonteq import prompts, word_representation, header_mappings, regex_callable
from .....config.json_config.json_leonteq import renaming


class LeonteqDerivatiKidExtractor(DerivatiKidExtractor):

    def __init__(self, doc_path) -> None:
        self.doc_path = doc_path
        super().__init__(doc_path, "it")

    def extract_basic_info(self):
        """
        Extract general data from the document (ISIN, description, issuer_desc).

        Returns: dict(): data extracted
        """
        extraction = dict()
        try:
            # Extract and clean data
            extraction = dict(
                llm_extraction_and_tag(
                    [self.text[0]],
                    prompts[self.language]["general_info_certificati"],
                    InformazioniBaseLeonteq,
                    self.file_id,
    ***REMOVED***
***REMOVED***

            # Extract ISIN and update the extraction dictionary
            extraction["isin"] = self.extract_isin()

            # Uncomment the following line if you need to clean the response with regex
            # extraction = clean_response_regex("general_info_certificati", self.language, extraction)

        except Exception as error:
            print(f"Extract general data error: {error!r***REMOVED***")
            error_keys = ["isin", "description", "issuer_desc"]
            extraction = {key: extraction.get(key, "ERROR") for key in error_keys***REMOVED***

        return extraction

    def extract_isin(self):
        """extract isin with regex

        Returns:
            str: isin or -
        """
        to_search = self.text[0].page_content[:1600]
        isin = re.search(r"[A-Z]{2***REMOVED***[A-Z0-9]{9***REMOVED***\d", to_search)
        return isin.group(0) if isin else "-"

    def extract_cedola(self, table):
        """extracts the cedola from the table
        can be a complex table or a dense text

        Args:
            table (pd.DataFrame): table containing the cedola

        Returns:
            dict(): dictionary containing the cedola
        """
        extraction = dict()
        try:
            if isinstance(table, str):
                extraction = self.regex_cedola(table)
            else:
                # kind of complex table extraction without tagging
                table = upload_df_as_excel(table)
                extraction = dict(
                    general_table_inspection(
                        table,
                        TabellaCedola,
                        self.file_id,
        ***REMOVED***
    ***REMOVED***
                extraction["data_realizzazione_cedola"] = ["N/A"]  # fields only in regex needs to still be present
                extraction["callable_valuation_date"] = ["N/A"]
        except Exception as error:
            print("extract_cedola error" + repr(error))
            error_list = [
                "coupon_valuation_date",
                "liv_attiv_cedola",
                "coupon_payment_date",
                "importo_cedola",
                "autocall_valuation_date",
                "barrier_autocall",
                "autocall_payment_date",
            ]

            extraction = {key: extraction.get(key, "ERROR") for key in error_list***REMOVED***

        return extraction

    def regex_cedola(self, og_string):
        """splits dense text into a list of dictionaries

        Args:
            og_string (str): the entire page as a string

        Returns:
            dict: cedole
        """  # brace yourself, regex is coming
        # why it is so complicated? because it is a dense text
        # idea is to cut before and after, extract headers, split by indexes and create dictionary
        # if changing, go look at what passes around
        # Initial split and early return if no divider found

        data_list = [
            "coupon_valuation_date",
            "liv_attiv_cedola",
            "coupon_payment_date",
            "importo_cedola",
            "autocall_valuation_date",
            "barrier_autocall",
            "autocall_payment_date",
            "data_realizzazione_cedola",
            "callable_valuation_date",
        ]

        parts = re.split(r"▪", og_string)
        if len(parts) == 1:
            return [{"cedola": "not found"***REMOVED***]

        # Extract and remove the last date part
        last_date_match = re.search(r"\d{1,2***REMOVED***[/\-.]\d{1,2***REMOVED***[/\-.]\d{1,4***REMOVED***", parts[-1])
        parts = parts[:-1]  # Remove last part as it's not needed

        # Extract header names
        names = []
        first_header_match = re.search(r"data.*$", parts[0], re.IGNORECASE)
        if first_header_match:
            names.append("Data " + first_header_match.group(0))
        parts = parts[1:]

        # Remove and append other headers until a date pattern is found
        while parts and not re.search("1[/\-.]\d+[/\-.]\d+[/\-.]\d+", parts[0]):
            names.append(parts.pop(0))

        # Re-merge remaining parts and find all dates
        merged_parts = "▪".join(parts)
        dates = re.findall("(\d{1,2***REMOVED***[/\-.](?:\n)?\d{1,2***REMOVED***[/\-.]\d{1,2***REMOVED***[/\-.]\d{1,4***REMOVED***)", merged_parts)

        # Adjust the last name to exclude the date and clean names
        if dates:
            names.append(parts[0].replace(dates[0], ""))
        names = self.clean_names(names)  # Assuming clean_names is a method defined elsewhere

        # Clean dates and split the merged parts by date pattern
        cleaned_dates = [date[date.find(".") + 1 :] for date in dates]
        parts_by_date = re.split("\d{1,2***REMOVED***[/\-.](?:\n)?\d{1,2***REMOVED***[/\-.]\d{1,2***REMOVED***[/\-.]\d{1,4***REMOVED***", merged_parts)

        # Construct the final result
        result = []
        for i, part in enumerate(parts_by_date[1:], start=1):
            entries = re.split("▪", part)
            entry_dict = {
                names[j] if j < len(names) else "name not found": cleaned_dates[i - 1] if j == 0 else entries[j]
                for j in range(len(entries))
            ***REMOVED***
            for entry in data_list:  # add missing keys
                if entry not in names:
                    entry_dict[entry] = "-"

            result.append(entry_dict)

        # Add the last date to the last entry, if found
        if last_date_match and result:
            result[-1].update({names[-1]: last_date_match.group(0)***REMOVED***)

        return result

    def clean_names(self, names):
        """cleans the names of the headers

        Args:
            names ([str]): the uncleaned names

        Returns:
            [str]: the cleaned names
        """
        for idx, name in enumerate(names):
            name = re.sub(r"\s+", " ", name)
            for search, value in header_mappings.items():
                if re.search(search, name, re.IGNORECASE):
                    names[idx] = value
                    break
        return names

    def extract_sottostanti(self, table):
        """Extracts the sottostanti from the table.

        Args:
            table (pd.DataFrame): Table containing the sottostanti.
            extracts from table and from header

        Returns:
            dict: Dictionary containing the sottostanti.
                First extractions are arrays, second are single data.
        """
        extraction = dict()
        try:

            functions_parameters = {
                "sottostanti": {
                    "function": general_table_inspection,
                    "args": {
                        "table": table,
                        "pydantic_class": TabellaSottostanti,
                        "file_id": TabellaSottostantiHeader,
                    ***REMOVED***,
                ***REMOVED***,
                "sottostanti_header": {
                    "function": general_table_inspection,
                    "args": {
                        "table": table.iloc[0],
                        "pydantic_class": TabellaSottostantiHeader,
                        "file_id": self.file_id,
                    ***REMOVED***,
                ***REMOVED***,
            ***REMOVED***
            results = self.threader(functions_parameters)
            # Merging results from the tasks
            extraction.update({**dict(results["sottostanti"]), **dict(results["sottostanti_header"])***REMOVED***)

        except Exception as error:
            error_list = [
                "liv_fixing_iniziale",
                "liv_att_cedola_perc",
                "underlying_strike_level",
                "barrier",
                "livello_cap",
                "underlying_description",
                "underlying_type",
                "underlying_stockexchange",
                "underlying_bloombergcode",
                "underlying_isin",
                "fixing_eur",
                "barriera_eur",
                "coupon_eur",
            ]
            extraction = {key: extraction.get(key, "ERROR") for key in error_list***REMOVED***
            print(f"extract_sottostanti first phase error: {error***REMOVED***")

        try:
            # Search for coupon trigger percentage in reverse order
            found_liv = "N/A"
            for cell in table.iloc[0][::-1]:
                if cell and re.search(r"coupon.{0,4***REMOVED***trigge", cell, re.IGNORECASE):
                    if percentage_match := re.search(r"\d+\.?\d* ?%", cell):
                        found_liv = percentage_match.group(0)
                        break
            extraction["liv_att_cedola_perc"] = found_liv
        except Exception as error:
            print(f"extract_sottostanti second phase error: {error***REMOVED***")

        return extraction

    def extract_main_info(self, table):
        """Extracts the main info from the table

        Args:
            table (pd.DataFrame): Table containing the main info

        Returns:
            dict: Dictionary containing the main info
        """
        try:
            # Attempt to extract main info using general table inspection
            extraction = general_table_inspection(
                table,
                TabellaMainInfo,
                self.file_id,
***REMOVED***
        except Exception as error:
            print(f"extract_main_info error: {repr(error)***REMOVED***")
            # Define keys to check in case of error
            error_keys = [
                "currency",
                "issue_date",
                "periodo",
                "expiry_day",
                "nominal",
                "liv_fix_fin",
                "market",
                "perf_peg",
                "inv_min",
                "unconditional_protection",
                "strike_date",
                "data_negoziazione",
                "final_valuation_date",
                "liv_fix_ini",
                "mod_pagamento",
                "tasso_cedola_cond",
                "unconditional_coupon",
                "rischio_cambio",
                "importo_rimborso",
                "importo_protezione_capitale",
                "leva_cedolare",
            ]
            # Update extraction with 'ERROR' for missing keys in case of exception
            extraction = {key: extraction.get(key, "ERROR") for key in error_keys***REMOVED***

        return extraction

    def check_validity_cedola(self, cedola_table, cedola_table_2, sottostanti_table, main_info_table):
        """check validity of table cedola and cedola2, if both are valid, it concatenates them

        Args:
            sottostanti_table (pd.Dataframe): to confront
            main_info_table (pd.Dataframe): to confront
            cedola_table (pd.Dataframe): found cedola table
            cedola_table_2 (pd.Dataframe): found cedola table

        Returns:
            pd.Dataframe: valid cedola table
        """
        cedola_valid = check_valid(cedola_table, [sottostanti_table, main_info_table])

        cedola2_valid = check_valid(cedola_table_2, [sottostanti_table, main_info_table])

        if not cedola_valid and not cedola2_valid:
            cedola_table = (
                str(getattr(self.text[0], "page_content", "")) + " " + str(getattr(self.text[1], "page_content", ""))
***REMOVED***
        elif cedola_valid and cedola2_valid:
            try:
                while not cedola_table_2.empty and re.search(r"[A-Za-z].*[A-Za-z]", cedola_table_2.iloc[0, 0]):
                    cedola_table_2 = cedola_table_2.iloc[1:, :]
                cedola_table = pd.concat([cedola_table, cedola_table_2], ignore_index=True)
            except Exception as error:
                print("more lines ignored:" + self.file_id + error)
        elif not cedola_valid and cedola2_valid:
            cedola_table = cedola_table_2

        return cedola_table

    def get_tables(self):
        """Calc table extractor, it extracts the tables from the document asynchronously

        Returns:
            dict([pandas.dataframe]): tables as dataframe
        """
        try:
            self.fill_tables([1, 2, 3])

            sottostanti_table, _ = self._extract_table_only_header(
                word_representation[self.language]["sottostanti"], api_version="2023-10-31-preview"
***REMOVED***
            main_info_table, _ = self._extract_table(word_representation[self.language]["main_info"])
            cedola_table, _ = self._extract_table(word_representation[self.language]["cedola"], black_list_pages=[1])
            cedola_table_2, _ = self._extract_table(word_representation[self.language]["cedola"], black_list_pages=[0])

            cedola_table = self.check_validity_cedola(cedola_table, cedola_table_2, main_info_table, sottostanti_table)

        except Exception as error:
            print("calc table error" + repr(error))
            error_list = ["cedola_table", "sottostanti_table", "main_info_table"]
            for error in error_list:
                if error not in locals():
                    locals()[error] = "ERROR"

        return {"cedola": cedola_table, "sottostanti": sottostanti_table, "main_info": main_info_table***REMOVED***

    def extract_callable(self):
        """extracts the callable from the document
        looks if the word is in the document thats it

        Returns:
            dict(): dictionary containing the callable
        """
        is_callable = {
            "autocall": 0,
            "callable": 0,
            "putable": 0,
            "memory": 0,
        ***REMOVED***
        is_callable = extract_regex_text(regex_callable, None, self.text[0], is_callable)

        return is_callable

    def fill_array(self, dictionary):
        """fills the arrays in the dictionary with '-'
        so that they all have the same length for excel

        Args:
            dictionary (dict): dictionary to fill

        Returns:
            dict: dictionary with filled arrays
        """

        max_length = max((len(value) for value in dictionary.values() if isinstance(value, list)), default=0)
        # Iterate through the dictionary and fill the arrays with '-'
        for key, value in dictionary.items():
            if isinstance(value, list):
                # If the array is shorter than max_length, fill with '-'
                dictionary[key] = value + ["-"] * (max_length - len(value))

        return dictionary

    def _write_to_excel(self, complete, exploded_cedola, sottostanti, api_costs, filename):
        """writes the results to an excel file

        Args:
            args (dict()): all the results
        """

        with pd.ExcelWriter(
            "results\\30gennaio\\resultsmarco_{***REMOVED***.xlsx".format(os.path.basename(self.file_id)),
            engine="xlsxwriter",
        ) as excel_writer:
            # Write the first DataFrame to Sheet1
            results1 = pd.DataFrame(complete, index=[filename]).T
            results1.to_excel(excel_writer, sheet_name="info_anagrafiche", header=True)

            results2 = pd.DataFrame(self.raccorda(exploded_cedola, renaming, keep=True))
            results2.to_excel(excel_writer, sheet_name="date cedole autocall", header=True)

            # Write the second DataFrame to Sheet2
            results3 = pd.DataFrame(self.raccorda(dict(sottostanti), renaming, keep=True)).T
            results3.to_excel(excel_writer, sheet_name="sottostanti", header=True)

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
                "tables": {"function": self.get_tables***REMOVED***,
                "basic_information": {"function": self.extract_basic_info***REMOVED***,
                "is_callable": {"function": self.extract_callable***REMOVED***,
            ***REMOVED***
            results = self.threader(functions_parameters)
            tables, basic_information, is_callable = [results[key] for key in results]

        except Exception as error:
            print("first stage error" + repr(error))
        # SECOND STAGE: extract RIY, costs, commissions and performances
        try:
            functions_parameters = {
                "cedola": {"function": self.extract_cedola, "args": {"table": tables["cedola"]***REMOVED******REMOVED***,
                "sottostanti": {"function": self.extract_sottostanti, "args": {"table": tables["sottostanti"]***REMOVED******REMOVED***,
                "main_info": {"function": self.extract_main_info, "args": {"table": tables["main_info"]***REMOVED******REMOVED***,
            ***REMOVED***
            results = self.threader(functions_parameters)
            cedola, sottostanti, main_info = [results[key] for key in results]

        except Exception as error:
            print("second stage error" + repr(error))

        try:
            # Merge and orders all the results
            exploded_cedola = dict()

            if isinstance(tables["cedola"], str):
                for ced in cedola:
                    for key, value in ced.items():
                        exploded_cedola.setdefault(key, []).append(value)
            else:
                for key, value in cedola.items():
                    exploded_cedola.update({key: value***REMOVED***)

            exploded_cedola = self.fill_array(exploded_cedola)

            sottostanti = self.fill_array(dict(sottostanti))

            filename = os.path.splitext(os.path.basename(self.doc_path))[0]

            api_costs = self._process_costs()

            # raccordo
            complete = self.raccorda(
                {
                    **dict(is_callable),
                    **dict(basic_information),
                    **dict(main_info),
                ***REMOVED***,
                renaming,
                keep=True,
***REMOVED***

            json = self.create_json(
                {
                    "file_name": filename,
                    **dict(is_callable),
                    **dict(basic_information),
                    **dict(api_costs),
                    **dict(sottostanti),
                    **dict(exploded_cedola),
                    **dict(main_info),
                ***REMOVED***,
                "leonteq",
***REMOVED***

            self._write_to_excel(complete, exploded_cedola, sottostanti, api_costs, filename)

            return json

        except Exception as error:
            print("dictionary error: " + repr(error))
            filename = os.path.splitext(os.path.basename(self.doc_path))[0]
            complete = None

        Models.clear_resources_file(filename)
        return complete
