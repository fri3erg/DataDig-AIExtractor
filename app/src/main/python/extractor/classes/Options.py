from typing import Callable


class Options:
    def __init__(self, model:str = "gpt-3.5-turbo", language:str|None=None, azure_ocr=False, get_api_key: Callable = lambda x: x, format="json"):
        self.model= model
        self.language = language
        self.azure_ocr = azure_ocr
        self.get_api_key = get_api_key
        self.format=format


class ExceptionsExtracted:
    def __init__(self, error: Exception, error_location: str, error_description: str):
        self.error = error
        self.error_type = error_location
        self.error_description = error_description
        
