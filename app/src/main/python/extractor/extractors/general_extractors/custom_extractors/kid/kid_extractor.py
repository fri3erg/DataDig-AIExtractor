import re

from classes.Template import Template
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
from ..kid.kid_utils import create_pydantic_class

from ...llm_functions import (
    llm_extraction_and_tag,
)
from .kid_utils import clean_response_regex, clean_response_strips


class Extractor(GeneralScanner):

    def __init__(self, images,template: Template, language:str|None, model= "gpt-3.5-turbo") -> None:
        super().__init__(images,template, language, model)

    def get_tables(self):
        """calc table extractor, it extracts the three tables from the document asynchronously

        Returns:
            dict([pandas.dataframe]): tables as dataframe
        """
        try:
            tables=dict()
            for table in self.template.tables:
                tables.update({table.title: self._extract_table(table.keywords)***REMOVED***)

        except Exception as error:
            #REDO ERROR HANDLING
            print("calc table error" + repr(error))
            error_list = [table.title for table in self.template.tables]
            for i, key in enumerate(error_list):
                if not key:
                    tables[i] = dict([("ERROR", "ERROR")])

        return dict(tables)

    def extract_basic_info(self):
        """
        Extract general data from the document. Namely RHP and SRI.


        Returns: dict(): data extracted
        """
        extraction = dict()
        try:
            # extract and clean
            extraction = llm_extraction_and_tag(
                self.text, self.template, self.file_id, create_pydantic_class(self.template), self.model, self.language
***REMOVED***
            #extraction = clean_response_regex(regex_cleaning, extraction)
            extraction = dict(extraction)
            

        except Exception as error:
            print("basic info extraction error" + repr(error))
            error_list = ["indicatore_sintetico_rischio", "periodo_detenzione_raccomandato", "indicatore_sintetico_rischio_max"]
            extraction = {key: (extraction[key] if extraction.get(key) is not None else "ERROR") for key in error_list***REMOVED***

        return extraction

    # REVIEW: NEED TO UPLOAD TABLE AS DF
    def extract_from_tables(self, page=1):
        """extracts riy from the document

        Returns:
            dict(): riy extracted
        """
        extraction = dict()
        try:
            for table in self.template.tables:
            # Select page with RIY
                extraction = tag_only(
                    self.text[page:], table.fields, create_pydantic_class(table.fields), self.file_id
    ***REMOVED***
                #extraction = clean_response_regex(regex_cleaning, extraction)
        except Exception as error:
            print("extract riy error" + repr(error))


        return extraction

    # REVIEW: NEED TO UPLOAD TABLE AS DF
    """def extract_complex_info(self, extracted):

        try:
            extraction = llm_extraction_and_tag(
                extracted, self.template, self.file_id, create_pydantic_class(self.template), self.model, self.language
***REMOVED***
            extraction = clean_response_regex(regex_cleaning, extraction)
        except Exception as error:
            print("extract entry exit costs error" + repr(error))

        return extraction
        
        """


***REMOVED***
    # testing
    doc_folder = "data\C\MEDIOLANUM\MYLIFEPIC_FR0011660851.pdf"
    kid_extractor = Extractor(doc_folder)
