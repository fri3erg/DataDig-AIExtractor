import re
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
from ...extractor import Extractor

from ...llm_functions import (
    llm_extraction_and_tag,
)
from .kid_utils import clean_response_regex, clean_response_strips


class KidExtractor(Extractor):

    def __init__(self, doc_path, predefined_language=False) -> None:
        super().__init__(doc_path, predefined_language)

    def get_tables(self):
        """calc table extractor, it extracts the three tables from the document asynchronously

        Returns:
            dict([pandas.dataframe]): tables as dataframe
        """
        try:
            performance_table, _ = self._extract_table(word_representation[self.language]["performance"])
            costi_ingresso_table, _ = self._extract_table(
                word_representation[self.language]["costi_ingresso"], black_list_pages=[0]
***REMOVED***
            costi_gestione_table, _ = self._extract_table(word_representation[self.language]["costi_gestione"])
        except Exception as error:
            print("calc table error" + repr(error))
            error_list = [performance_table, costi_ingresso_table, costi_gestione_table]
            for i, key in enumerate(error_list):
                if not key:
                    error_list[i] = dict([("ERROR", "ERROR")])

        return dict(
            [
                ("costi_ingresso", costi_ingresso_table),
                ("costi_gestione", costi_gestione_table),
                ("performance", performance_table),
            ]
        )

    def extract_general_data(self):
        """
        Extract general data from the document. Namely RHP and SRI.


        Returns: dict(): data extracted
        """
        extraction = dict()
        try:
            # extract and clean
            extraction = llm_extraction_and_tag(
                self.text, prompts[self.language]["general_info"], InformazioniBase, self.file_id
***REMOVED***
            extraction = clean_response_regex(regex_cleaning[self.language]["general_info"], extraction)
            extraction = dict(extraction)

            # REVIEW: ISIN EXTRACTION TO BE MOVED OUTSIDE?
            isin = self.extract_isin()
            extraction.update({"isin": isin***REMOVED***)
            if (
                "periodo_detenzione_raccomandato" in extraction
                and extraction["periodo_detenzione_raccomandato"] != "-"
                and re.search(r"\d+", extraction["periodo_detenzione_raccomandato"])
***REMOVED***:

                rhp_temp = extraction["periodo_detenzione_raccomandato"]
                number = re.search(r"\d+", rhp_temp).group(0)
                if re.search(r"(?i)mesi", rhp_temp):
                    extraction["periodo_detenzione_raccomandato"] = "1"
                else:
                    extraction["periodo_detenzione_raccomandato"] = str(int(number))

                self.rhp = extraction["periodo_detenzione_raccomandato"]
            else:
                self.rhp = "multiple"

        except Exception as error:
            print("extract general data error" + repr(error))
            error_list = ["isin", "indicatore_sintetico_rishio"]
            extraction = {key: (extraction[key] if extraction.get(key) is not None else "ERROR") for key in error_list***REMOVED***
            self.rhp = extraction["periodo_detenzione_raccomandato"] = "multiple"

        return extraction

    def extract_isin(self):
        to_search = self.text[0].page_content[20:1600]
        isin = re.search(r"[A-Z]{2***REMOVED***[A-Z0-9]{9***REMOVED***\d", to_search)
        return isin.group(0) if isin else "-"

    def extract_market(self, market_type="market"):
        """extracts market from the document

        Args:
            market_type (str, optional): type of market to extract. Defaults to "market".
                Can also be "maket_gkid".

        Returns:
            dict(): market extracted
        """
        market = None
        market_type = prompts[self.language][market_type]
        try:
            market = llm_extraction(self.text[0], market_type, self.file_id)
            # procedural cleaning
            market = clean_response_strips(strips_cleaning[self.language]["market"], market)

        except Exception as error:
            print("market extraction error" + repr(error))
            if not market:
                market = "ERROR"

        market = dict([("target_market", market)])
        return market

    # REVIEW: NEED TO UPLOAD TABLE AS DF
    def extract_riy(self, page=1):
        """extracts riy from the document

        Returns:
            dict(): riy extracted
        """
        extraction_riy = dict()
        try:
            # Select page with RIY
            extraction_riy = tag_only(
                self.text[page:], word_representation[self.language]["riy"], TabellaRiy, self.file_id, rhp=self.rhp
***REMOVED***
            extraction_riy = clean_response_regex(regex_cleaning[self.language]["riy"], extraction_riy)
        except Exception as error:
            print("extract riy error" + repr(error))
            error_list = ["incidenza_costo_1", "incidenza_costo_rhp"]

            extraction_riy = {
                key: (extraction_riy[key] if extraction_riy.get(key) is not None else "ERROR") for key in error_list
            ***REMOVED***

        return extraction_riy

    # REVIEW: NEED TO UPLOAD TABLE AS DF
    def extract_entryexit_costs(self, table):

        try:
            extraction = general_table_inspection(
                table,
                TabellaCostiIngresso,
                self.file_id,
                add_text="estrai il valore % dopo {***REMOVED*** anni".format(self.rhp),
***REMOVED***
            extraction = clean_response_regex(regex_cleaning[self.language]["costi_ingresso"], extraction)
        except Exception as error:
            print("extract entry exit costs error" + repr(error))
            error_list = ["costi_ingresso", "costi_uscita"]
            extraction = {key: (extraction[key] if extraction.get(key) is not None else "ERROR") for key in error_list***REMOVED***

        return extraction

    # REVIEW: NEED TO UPLOAD TABLE AS DF
    def extract_management_costs(self, table):

        try:
            extraction = dict()
            extraction = general_table_inspection(
                table,
                TabellaCostiGestione,
                self.file_id,
                add_text="estrai il valore % dopo {***REMOVED*** anni".format(self.rhp),
***REMOVED***
            extraction = clean_response_regex(regex_cleaning[self.language]["costi_gestione"], extraction)
        except Exception as error:
            print("extract management costs error" + repr(error))
            error_list = ["commissione_gestione", "commissione_transazione", "commissione_performance"]
            extraction = {key: (extraction[key] if extraction.get(key) is not None else "ERROR") for key in error_list***REMOVED***

        return extraction

    def extract_performances(self, table):
        """extracts performances from scenarios in the document

        Args:
            table (pandas.dataframe): table containing the performances

        Returns:
            dict(): dict containing the performances
        """
        performance = dict()
        try:
            performance = dict(
                complex_table_inspection(
                    table,
                    self.rhp,
                    None,
                    TabellaScenariPerformance,
                    self.file_id,
                    direct_tag=True,
    ***REMOVED***
***REMOVED***

            morte = dict(
                [
                    ("scenario_morte_1", performance.get("scenario_morte_1")),
                    ("scenario_morte_rhp", performance.get("scenario_morte_rhp")),
                ]
***REMOVED***
            performance = clean_response_regex(regex_cleaning[self.language]["performance"], performance)
            morte = clean_response_regex(regex_cleaning[self.language]["performance_morte"], morte)
            performance["scenario_morte_1"] = morte.get("scenario_morte_1")
            performance["scenario_morte_rhp"] = morte.get("scenario_morte_rhp")
        except Exception as error:
            print("extract performances error" + repr(error))
            error_list = [
                "scenario_morte_1",
                "scenario_morte_rhp",
                "stress_return",
                "sfavorevole_return",
                "moderato_return",
                "favorable_return",
                "stress_return_rhp",
                "sfavorevole_return_rhp",
                "moderato_return_rhp",
                "favorable_return_rhp",
            ]
            performance = {
                key: (performance[key] if performance.get(key) is not None else "ERROR") for key in error_list
            ***REMOVED***

        return performance


***REMOVED***
    # testing
    doc_folder = "data\C\MEDIOLANUM\MYLIFEPIC_FR0011660851.pdf"
    kid_extractor = KidExtractor(doc_folder)
