
from typing import List, Tuple
class TemplateField:
    def __init__(self, id, title: str, description:str, extra_description: str, tags: List[str], type: type, required: bool, intelligent_extraction= False):
        self.id = id
        self.title = title
        self.description = description
        self.extra_description = extra_description
        self.type = type
        self.required= required
        self.tags = tags  # List of strings 
        self.intelligent_extraction = intelligent_extraction # Boolean indicating whether intelligent extraction is enabled for this field


class TemplateTable:
    def __init__(self, id, title:str, keywords: List[str], fields: List[TemplateField]):
        self.id = id
        self.title = title
        self.keywords = keywords
        self.fields = fields  # List of TemplateField objects
        
        
        
class Template:
    def __init__(self, id, title:str , description: str, fields: List[TemplateField] ,tables: List[TemplateTable], tags:List[str]):
        self.id = id
        self.title = title
        self.description = description
        self.fields = fields  # List of TemplateField objects
        self.tables = tables
        self.tags = tags     # List of strings




    def template_to_readable_string(self) -> str:
        """Converts a Template instance to a human-readable string (excluding tables)."""

        output_str = f"Title: {self.title***REMOVED***\n  "
        output_str += f"Description: {self.description***REMOVED***\n  "
        output_str += "Fields:\n  "
        for field in self.fields:
            required_str = "Required" if field.required else "Optional" 
            output_str += f"- {field.title***REMOVED*** ({field.type.__name__***REMOVED***): {field.description***REMOVED*** ({required_str***REMOVED***)\n  "
            
            # Include extra description if available
            #if field.extra_description:
            #   output_str += f"  - Extra Description: {field.extra_description***REMOVED***\n"

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