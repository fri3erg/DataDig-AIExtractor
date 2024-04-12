package com.example.tesifrigo.model

data class Field(
    val title: String,
    val description: String
)

data class Template(
    val id: Int = 0, // Optionally add an ID for persistence
    val title: String,
    val fields: List<Field>
)