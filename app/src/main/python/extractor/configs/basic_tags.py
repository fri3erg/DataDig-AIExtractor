from typing import Literal
from pydantic import BaseModel, Field


class DocLanguage(BaseModel):
    language: Literal["it", "en", "fr", "de", "es"] = Field(description="language the document is written in", default="it")


class ExtractedTable(BaseModel):
    table_data: list[list[str]] = Field(default_factory=list)
    title: str = Field(description="what the table is about", default="title", required=True)
