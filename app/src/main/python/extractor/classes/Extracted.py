from typing import Any, Dict, List, Optional, Union

from ..classes.Options import ExceptionsExtracted
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

class ExtractionCosts:
    def __init__(self, name:str, tokens: int,  cost: float, currency: str):
        self.name = name
        self.tokens = tokens
        self.cost = cost
        self.currency = currency
        
class Extracted:
    def __init__(self, template: Template,extraction_costs:List[ExtractionCosts]=[], fields: Optional[List[ExtractedField]] = None, tables: Optional[List[ExtractedTable]] = None,  exceptions: List[ExceptionsExtracted] = [], image:List[bytes]|None =None, format:str|None=None, tags:List[str]=[]):
        self.extracted_fields: List[ExtractedField] = fields or []
        self.extracted_tables: List[ExtractedTable] = tables or []
        self.extraction_costs: List[ExtractionCosts] = extraction_costs
        self.exceptions_occurred: List[ExceptionsExtracted] = exceptions
        self.image= image
        self.template: Template=template
        self.format=format
        self.tags=tags
        self.title=template.title
        
        
        
    def add_exceptions(self, exceptions:List[ExceptionsExtracted]):
        self.exceptions_occurred += exceptions

