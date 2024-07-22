from typing import Any, Dict, List, Optional, Union

from classes.Options import ExceptionsExtracted
from .Template import TemplateField, TemplateTable, Template

class ExtractedField:
    def __init__(self, template_field: TemplateField, value: str):
        self.template_field: TemplateField = template_field
        self.value: str = value

class ExtractedTable:
    def __init__(self, template_table: TemplateTable, fields: Dict[str,Dict[str,ExtractedField]],dataframe:Any):
        self.template_table: TemplateTable = template_table
        self.fields: Dict[str,Dict[str,ExtractedField]] = fields  # List of TemplateField objects
        self.dataframe = dataframe

class Extracted:
    def __init__(self, template: Template, fields: Optional[List[ExtractedField]] = None, tables: Optional[List[ExtractedTable]] = None, costs:dict = {***REMOVED***, exceptions: List[ExceptionsExtracted] = [], image:bytes|None =None, format:str|None=None, tags:List[str]=[]):
        self.extracted_fields: List[ExtractedField] = fields or []
        self.extracted_tables: List[ExtractedTable] = tables or []
        self.extraction_costs = costs or {***REMOVED***
        self.exceptions_occurred: List[ExceptionsExtracted] = exceptions
        self.image= image
        self.template: Template=template
        self.format=format
        self.tags=tags
        
        
        
    def add_exceptions(self, exceptions:List[ExceptionsExtracted]):
        self.exceptions_occurred += exceptions

