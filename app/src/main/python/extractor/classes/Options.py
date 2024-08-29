from typing import Callable


class Options:
    def __init__(
        self,
        model: str = "gpt-3.5-turbo",
        language: str | None = None,
        azure_ocr=False,
        get_api_key: Callable = lambda x: x,
        format="json",
        resize = True
    ):
        self.model = model
        self.language = language
        self.azure_ocr = azure_ocr
        self.get_api_key = get_api_key
        self.format = format
        self.resize = resize


