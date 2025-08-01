from typing import Any, Dict, List, Optional

from .Template import TemplateField, TemplateTable, Template


class ExtractedField:
    def __init__(self, template_field: Optional[TemplateField] = None, value: Any = None):
        self.template_field = template_field
        self.value: Any = value


class ExtractedTable:
    def __init__(
        self,
        title: str,
        template_table: TemplateTable,
        fields: Optional[Dict[str, Dict[str, ExtractedField]]] = None,
        dataframe: Optional[str] = "",
    ):
        self.title = title
        self.template_table: TemplateTable = template_table
        self.fields: Dict[str, Dict[str, ExtractedField]] = fields or {***REMOVED***  # List of TemplateField objects
        self.dataframe = dataframe


class ExtractionCosts:
    def __init__(self, name: str, tokens: int, cost: float, currency: str):
        self.name = name
        self.tokens = tokens
        self.cost = cost
        self.currency = currency



class ExceptionsExtracted:
    def __init__(self, error: Exception, error_location: str, error_description: str):
        self.error = error
        self.error_type = error_location
        self.error_description = error_description

class Extracted:
    def __init__(
        self,
        template: Template,
        extraction_costs: Optional[List[ExtractionCosts]] = [],
        fields: Optional[List[ExtractedField]] = None,
        tables: Optional[List[ExtractedTable]] = None,
        exceptions: Optional[List[ExceptionsExtracted]] = None,
        image: Optional[List[bytes]] = None,
        format: Optional[str] = "json",
        tags: Optional[List[str]] = None,
    ):
        self.extracted_fields: List[ExtractedField] = fields or []
        self.extracted_tables: List[ExtractedTable] = tables or []
        self.extraction_costs: List[ExtractionCosts] = extraction_costs or []
        self.exceptions_occurred: List[ExceptionsExtracted] = exceptions or []
        self.image = image or []
        self.template: Template = template
        self.format = format
        self.tags = tags or []
        self.title = template.title

    def add_exceptions(self, exceptions: List[ExceptionsExtracted]):
        self.exceptions_occurred += exceptions
