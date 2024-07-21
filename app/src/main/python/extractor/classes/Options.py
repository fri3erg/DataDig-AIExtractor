from typing import List


class Options:
    def __init__(self, model:str = "gpt-3.5-turbo", language:str|None=None, azure_ocr=False):
        self.model= model
        self.language = language
        self.azure_ocr = azure_ocr


class ExceptionsExtracted:
    def __init__(self, error: Exception, error_location: str, error_description: str):
        self.error = error
        self.error_type = error_location
        self.error_description = error_description
        
