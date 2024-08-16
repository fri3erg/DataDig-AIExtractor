from typing import Literal
from pydantic import BaseModel, Field


class DocLanguage(BaseModel):
    language: Literal["it", "en", "fr", "de", "es"] = Field( description="language of the document", default="it")
