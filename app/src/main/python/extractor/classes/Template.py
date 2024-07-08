
from typing import List
class TemplateField:
    def __init__(self, id, title: str, description:str, extra_description: str, tags: List[str]):
        self.id = id
        self.title = title
        self.description = description
        self.extra_description = extra_description
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
