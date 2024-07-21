from math import e
from typing import Any, Dict, List, Optional, Union

from classes.Options import ExceptionsExtracted
from .Template import TemplateField, TemplateTable, Template

class ExtractedField:
    def __init__(self, template_field: TemplateField, value: str, model_used: str):
        self.id = template_field.id
        self.title: str = template_field.title
        self.description: str = template_field.description
        self.extra_description: str = template_field.extra_description
        self.value: str = value
        self.model_used: str = model_used

class ExtractedTable:
    def __init__(self, template_table: TemplateTable, fields: Dict[str,Dict[str,ExtractedField]],dataframe:Any, model_used: str):
        self.id = template_table.id
        self.title: str = template_table.title
        self.keywords: List[str] = template_table.keywords
        self.fields: Dict[str,Dict[str,ExtractedField]] = fields  # List of TemplateField objects
        self.dataframe = dataframe
        self.model_used: str = model_used
        self.extraction_cost = {***REMOVED***  # To be filled in later

class Extracted:
    def __init__(self, template: Template, fields: Optional[List[ExtractedField]] = None, tables: Optional[List[ExtractedTable]] = None, costs:dict = {***REMOVED***, exceptions: List[ExceptionsExtracted] = []):
        self.template_id = template.id
        self.template_title: str = template.title
        self.template_description: str = template.description
        self.extracted_fields: List[ExtractedField] = fields or []
        self.extracted_tables: List[ExtractedTable] = tables or []
        self.extraction_costs = costs or {***REMOVED***
        self.exceptions_occurred: List[ExceptionsExtracted] = exceptions
        
    def add_exceptions(self, exceptions:List[ExceptionsExtracted]):
        self.exceptions_occurred += exceptions

