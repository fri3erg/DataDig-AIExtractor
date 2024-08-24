from typing import  List, Optional


class TemplateField:
    def __init__(
        self,
        id: str,
        title: str,
        description: Optional[str] = None,
        extra_description: Optional[str] = "",
        type: Optional[str] = None,
        list: Optional[bool] = False,
        required: Optional[bool] = False,
        intelligent_extraction: Optional[bool] = False,
        default: Optional[str] = "N/A",
    ):
        self.id = id
        self.title = title
        self.description = description
        self.extra_description = extra_description
        self.type = type or "text"
        self.list = list or False
        self.required = required
        self.intelligent_extraction = (
            intelligent_extraction  # Boolean indicating whether intelligent extraction is enabled for this field
        )
        self.default = default


class TemplateTable:
    def __init__(
        self,
        id: str,
        title: str,
        keywords: Optional[List[str]] = None,
        description: Optional[str] = "",
        rows: Optional[List[TemplateField]] = None,
        columns: Optional[List[TemplateField]] = None,
        all: bool = False,
    ):
        self.id = id
        self.title = title
        self.keywords = keywords or []
        self.description = description
        self.rows = rows or []
        self.columns = columns or []
        self.all: bool = all


class Template:
    def __init__(
        self,
        id: str,
        title: str,
        description: Optional[str],
        fields: Optional[List[TemplateField]] = None,
        tables: Optional[List[TemplateTable]] = None,
        tags: Optional[List[str]] = None,
    ):
        self.id = id
        self.title = title
        self.description = description or ""
        self.fields = fields or []  # List of TemplateField objects
        self.tables = tables or []
        self.tags = tags or []  # List of strings

    def template_to_readable_string(self) -> str:
        """Converts a Template instance to a human-readable string (excluding tables)."""

        output_str = f"Title: {self.title***REMOVED***\n  "
        output_str += f"Description: {self.description***REMOVED***\n  "
        output_str += "Fields:\n  "
        for field in self.fields:
            required_str = "Required" if field.required else "Optional"
            output_str += f"- {field.title***REMOVED*** ({field.type***REMOVED***): {field.description***REMOVED*** ({required_str***REMOVED***)(Default: {field.default***REMOVED***)\n  "
        print(output_str)
        return output_str

    def split_template(self):
        """Splits a Template into two based on the 'intelligent_extraction' flag."""

        intelligent_fields = []
        non_intelligent_fields = []
        for field in self.fields:
            if field.intelligent_extraction:
                intelligent_fields.append(field)
            else:
                non_intelligent_fields.append(field)

        # Create new templates with the split fields
        intelligent_template = Template(
            id=self.id,
            title=self.title,
            description=self.description,
            fields=intelligent_fields,
            tables=[],  # Exclude tables
            tags=self.tags,
        )
        non_intelligent_template = Template(
            id=self.id,
            title=self.title,
            description=self.description,
            fields=non_intelligent_fields,
            tables=[],  # Exclude tables
            tags=self.tags,
        )

        return intelligent_template, non_intelligent_template

    def sanitize(self):
        for table in self.tables:
            for row in table.rows:
                row.title.replace("|", "\\")
            for column in table.columns:
                column.title.replace("|", "\\")
