package com.example.tesifrigo.model

data class Field(
    val id: Int = 0, // Optionally add an ID for persistence
    val title: String,
    val description: String,
    val extraDescription: String =" ",
    val tags: List<FieldTags> = emptyList()
)

data class FieldTags(
    val tag: String
)

data class Template(
    val id: Int = 0, // Optionally add an ID for persistence
    val title: String,
    val fields: List<Field>,
    val tags: List<TemplateTags> = emptyList()
)

data class TemplateTags(
    val tag: String
)