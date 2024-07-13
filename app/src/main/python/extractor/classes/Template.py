
from typing import List
class TemplateField:
    def __init__(self, id, title: str, description:str, extra_description: str, tags: List[str], type: type, required: bool):
        self.id = id
        self.title = title
        self.description = description
        self.extra_description = extra_description
        self.type = type
        self.required= required
        self.tags = tags  # List of strings 


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




def template_to_readable_string(template: Template):
    """Converts a Template instance to a human-readable string (excluding tables)."""

    output_str = f"Title: {template.title***REMOVED***\n  "
    output_str += f"Description: {template.description***REMOVED***\n  "
    output_str += "Fields:\n  "
    for field in template.fields:
        required_str = "Required" if field.required else "Optional" 
        output_str += f"- {field.title***REMOVED*** ({field.type.__name__***REMOVED***): {field.description***REMOVED*** ({required_str***REMOVED***)\n  "
        
        # Include extra description if available
        #if field.extra_description:
        #   output_str += f"  - Extra Description: {field.extra_description***REMOVED***\n"

    return output_str

