class Options:
    def __init__(self, model:str = "gpt-3.5-turbo", language:str|None=None, azure_ocr=False):
        self.model= model
        self.language = language
        self.azure_ocr = azure_ocr


class ExceptionsExtracted:
    def __init__(self, error: str, error_type: str, error_description: str):
        self.error = error
        self.error_type = error_type
        self.error_description = error_description