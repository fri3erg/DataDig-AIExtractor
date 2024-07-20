from typing import Any, Dict, List, Optional, Union
from .Template import TemplateField, TemplateTable, Template

class ExtractedField:
    def __init__(self, template_field: TemplateField, value: str, model_used: str):
        self.id = template_field.id
        self.title = template_field.title
        self.description = template_field.description
        self.extra_description = template_field.extra_description
        self.value = value
        self.model_used = model_used

class ExtractedTable:
    def __init__(self, template_table: TemplateTable, fields: Dict[str,Dict[str,ExtractedField]],dataframe:Any, model_used: str):
        self.id = template_table.id
        self.title = template_table.title
        self.keywords = template_table.keywords
        self.fields: Dict[str,Dict[str,ExtractedField]] = fields  # List of TemplateField objects
        self.dataframe = dataframe
        self.model_used = model_used
        self.extraction_cost = {***REMOVED***  # To be filled in later

class Extracted:
    def __init__(self, template: Template, fields: Optional[List[ExtractedField]] = None, tables: Optional[List[ExtractedTable]] = None, costs:dict = {***REMOVED***):
        self.template_id = template.id
        self.template_title = template.title
        self.template_description = template.description
        self.extracted_fields = fields or []
        self.extracted_tables = tables or []
        self.extraction_costs = costs or {***REMOVED***

