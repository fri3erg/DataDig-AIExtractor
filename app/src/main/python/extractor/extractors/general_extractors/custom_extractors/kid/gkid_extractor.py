from .insurance.gkid.cleaning_gkid import regex_cleaning, regex_search, strips_cleaning
from extractors.general_extractors.custom_extractors.kid.insurance.gkid.tags_gkid import InformazioniBaseGkid
from .kid_extractor import KidExtractor
from .kid_utils import clean_response_strips, clean_response_regex, regex_extract
from ...llm_functions import llm_extraction_and_tag
from .insurance.gkid.config_gkid import prompts, word_representation


class GKidExtractor(KidExtractor):

    def __init__(self, doc_path, predefined_language="it") -> None:
        super().__init__(doc_path, predefined_language)

    def get_tables(self):
        """Get GKID tables asynchronusly.

        Returns:
            dict([pandas.dataframe]): tables as dataframe
        """
        try:
            riy_table, _ = self._extract_table(word_representation[self.language]["riy_perc_gkid"])
            costi_ingresso, _ = self._extract_table(
                word_representation[self.language]["costi_ingresso_gkid"], black_list_pages=[0]
***REMOVED***
            costi_gestione, _ = self._extract_table(word_representation[self.language]["costi_gestione_gkid"])

        except Exception as error:
            print("calc table error" + repr(error))
            error_list = [costi_ingresso, costi_gestione, riy_table]
            for i, key in enumerate(error_list):
                if not key:
                    error_list[i] = dict([("ERROR", "ERROR")])

        return dict(
            [
                ("costi_ingresso", costi_ingresso),
                ("costi_gestione", costi_gestione),
                ("riy_table", riy_table),
            ]
        )

    def extract_general_data(self):
        """
        Extract general data from the document. Namely ISIN, RHP and SRI.

        Returns: dict(): data extracted
        """
        extraction = dict()
        try:
            for page in self.text:
                setattr(
                    page,
                    "page_content",
                    clean_response_strips(strips_cleaning[self.language]["text_gkid"], page.page_content),
    ***REMOVED***

            # extract and clean
            extraction = llm_extraction_and_tag(
                self.text, prompts[self.language]["general_info_gkid"], InformazioniBaseGkid, self.file_id
***REMOVED***
            # extraction = clean_response_regex(regex_cleaning[self.language]["general_info_gkid"], extraction)
            extraction = dict(extraction)
            if extraction["periodo_detenzione_raccomandato"] != []:
                extraction["periodo_detenzione_raccomandato"] = str(
                    max(extraction["periodo_detenzione_raccomandato"], key=lambda x: x)
    ***REMOVED***
            else:
                # bad but trying to save the other data
                extraction["periodo_detenzione_raccomandato"] = "multipli"

            self.rhp = extraction["periodo_detenzione_raccomandato"]

        except Exception as error:
            print("extract general data error" + repr(error))
            error_list = ["indicatore_sintetico_rischio_max", "indicatore_sintetico_rischio_min"]
            extraction = {key: (extraction[key] if extraction.get(key) is not None else "ERROR") for key in error_list***REMOVED***
            self.rhp = extraction["periodo_detenzione_raccomandato"] = "multiple"

        return extraction

    def extract_riy(self, table):
        """
        Extracts the riy from the given table.

        Parameters:
        - table: The table containing the riy information.

        Returns:
        - extraction_riy: A dictionary containing the extracted riy information.
        """

        try:
            # clean rhp to avoid errors
            clean = clean_response_regex(regex_cleaning[self.language]["rhp"], dict([("rhp", self.rhp)]))["rhp"]
            rhp = str(int(clean if clean != "-" else "10"))

            extraction_riy = dict()
            # iterativly extract last column and then remove it, rename results and repeat
            transform = {
                "costi_totali-gkid_min": "incidenza_costo_eur_rhp_min",
                "costi_totali-gkid_max": "incidenza_costo_eur_rhp_max",
                "incidenza-gkid_min": "incidenza_costo_perc_rhp_min",
                "incidenza-gkid_max": "incidenza_costo_perc_rhp_max",
            ***REMOVED***
            reg = {
                k: regex_search[self.language][k]
                for k in ["costi_totali-gkid", "incidenza-gkid"]
                if k in regex_search[self.language]
            ***REMOVED***
            extraction_riy.update(self.raccorda(regex_extract(reg, table), transform, keep=True))

            table = table.iloc[:, :-1]

            # rhp/2 exist only if rhp>=10
            if int(rhp) >= 10:
                transform.update(
                    {
                        "costi_totali-gkid_min": "incidenza_costo_eur_2_min",
                        "costi_totali-gkid_max": "incidenza_costo_eur_2_max",
                        "incidenza-gkid_min": "incidenza_costo_perc_2_min",
                        "incidenza-gkid_max": "incidenza_costo_perc_2_max",
                    ***REMOVED***
    ***REMOVED***
                riy_2_rhp = self.raccorda(
                    regex_extract(
                        reg,
                        table,
        ***REMOVED***,
                    transform,
    ***REMOVED***

                table = table.iloc[:, :-1]
            else:
                riy_2_rhp = {
                    "incidenza_costo_eur_2_min": "-",
                    "incidenza_costo_eur_2_max": "-",
                    "incidenza_costo_perc_2_min": "-",
                    "incidenza_costo_perc_2_max": "-",
                ***REMOVED***

            transform.update(
                {
                    "costi_totali-gkid_min": "incidenza_costo_eur_1_min",
                    "costi_totali-gkid_max": "incidenza_costo_eur_1_max",
                    "incidenza-gkid_min": "incidenza_costo_perc_1_min",
                    "incidenza-gkid_max": "incidenza_costo_perc_1_max",
                ***REMOVED***
***REMOVED***

            reg = {
                k: regex_search[self.language][k]
                for k in ["costi_totali-gkid", "incidenza-gkid"]
                if k in regex_search[self.language]
            ***REMOVED***
            riy_1 = self.raccorda(regex_extract(reg, table), transform)

            extraction_riy = {**extraction_riy, **riy_1, **riy_2_rhp***REMOVED***
            # divide ,clean, reunite
            # codice di @elia da rivedere
            eur = {key: value for key, value in extraction_riy.items() if "eur" in key***REMOVED***
            perc = {key: value for key, value in extraction_riy.items() if "perc" in key***REMOVED***
            perc = clean_response_regex(regex_cleaning[self.language]["riy%/-gkid"], perc, to_add="")
            eur = clean_response_regex(regex_cleaning[self.language]["riy€-gkid"], eur, to_add="")
            extraction_riy = {**perc, **eur***REMOVED***

            """ 
                #legacy code for riy extraction
                #rhmezzi=None
                #if(int(rhp)>=10):
                    #rhmezzi=str(math.ceil(int(rhp)/2))
                #complex table extraction
                tasks=[]
                tasks.append(asyncio.create_task(complex_table_inspection(table, rhp,rhmezzi, 'riy%',self.file_id, direct_tag=True, language=self.language)))
                tasks.append(asyncio.create_task(complex_table_inspection(table, rhp,rhmezzi, 'riy€',self.file_id, direct_tag=True, language=self.language)))

                await asyncio.wait(tasks, return_when=asyncio.ALL_COMPLETED)

                extraction_riy=tasks[0].result()
                other_one=tasks[1].result()
                """

            """
                #tag only text extraction
                extraction_riy = tag_only(self.text,'riy%',self.language,self.file_id, rhp=self.rhp, rhmezzi=str(int(rhp)/2))
                extraction_riy=clean_response("riy%", self.language, extraction_riy, regex=True,to_add="%")
                
                other_one=tag_only(self.text,'riy€',self.language,self.file_id, rhp=self.rhp, rhmezzi=str(int(rhp)/2))
                other_one=clean_response("riy€", self.language, other_one, regex=True,to_add="€")
                
                extraction_riy=dict(extraction_riy)
                extraction_riy.update(dict(other_one))
                """
        except Exception as error:
            print("extract riy error" + repr(error))
            error_list = [
                "incidenza_costo_perc_1_min",
                "incidenza_costo_perc_1_max",
                "incidenza_costo_perc_2_min",
                "incidenza_costo_perc_2_max",
                "incidenza_costo_perc_rhp_min",
                "incidenza_costo_perc_rhp_max",
                "incidenza_costo_eur_1_min",
                "incidenza_costo_eur_1_max",
                "incidenza_costo_eur_2_min",
                "incidenza_costo_eur_2_max",
                "incidenza_costo_eur_rhp_min",
                "incidenza_costo_eur_rhp_max",
            ]
            extraction_riy = {
                key: (extraction_riy[key] if extraction_riy.get(key) is not None else "ERROR") for key in error_list
            ***REMOVED***

        return extraction_riy

    def extract_cost_commissions(self, table_ingresso, table_gestione):
        """extracts costs and commissions from the document

        Args:
            table_ingresso (pandas.dataframe): table for ingresso and uscita
            table_gestione (pandas.dataframe): table for gestione, transazione and performance

        Returns:
            dict(): data extracted
        """
        try:
            # extract and clean
            reg = {
                k: regex_search[self.language][k]
                for k in ["costi_ingresso_gkid", "costi_uscita_gkid"]
                if k in regex_search[self.language]
            ***REMOVED***
            costi_ingresso = regex_extract(
                reg,
                table_ingresso,
***REMOVED***

            reg = {
                k: regex_search[self.language][k]
                for k in ["commissione_gestione_gkid", "commissione_transazione_gkid", "commissione_performance_gkid"]
                if k in regex_search[self.language]
            ***REMOVED***
            costi_gestione = regex_extract(
                reg,
                table_gestione,
***REMOVED***
            reg = {
                k: regex_search[self.language][k] for k in ["costi_ingresso_gkid"] if k in regex_search[self.language]
            ***REMOVED***
            costi_ingresso = clean_response_regex(reg, costi_ingresso, to_add="%")
            reg = {
                k: regex_search[self.language][k] for k in ["costi_gestione_gkid"] if k in regex_search[self.language]
            ***REMOVED***
            costi_gestione = clean_response_regex(reg, costi_gestione, to_add="%")

        except Exception as error:
            print("extract cost commissions error" + repr(error))
            if not costi_ingresso or costi_ingresso == {"ERROR": "ERROR"***REMOVED***:
                error_list = [
                    "costi_ingresso_min",
                    "costi_ingresso_max",
                    "costi_uscita_min",
                    "costi_uscita_max",
                ]
                costi_ingresso = {
                    key: (costi_ingresso[key] if costi_ingresso.get(key) is not None else "ERROR") for key in error_list
                ***REMOVED***
            if not costi_gestione or costi_gestione == {"ERROR": "ERROR"***REMOVED***:
                error_list = [
                    "commissione_gestione_min",
                    "commissione_gestione_max",
                    "commissione_transazione_min",
                    "commissione_transazione_max",
                    "commissione_performance_min",
                    "commissione_performance_max",
                ]
                costi_gestione = {
                    key: (costi_gestione[key] if costi_gestione.get(key) is not None else "ERROR") for key in error_list
                ***REMOVED***

        costi = {**dict(costi_gestione), **dict(costi_ingresso)***REMOVED***

        return costi
