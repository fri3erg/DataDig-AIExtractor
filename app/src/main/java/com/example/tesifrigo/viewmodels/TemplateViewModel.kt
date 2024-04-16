package com.example.tesifrigo.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.tesifrigo.model.Field
import com.example.tesifrigo.model.Template
import com.example.tesifrigo.model.TemplateTags

class TemplateViewModel : ViewModel() {


    private val _templates = mutableStateListOf<Template>()
    val templates: List<Template> = _templates

    init {
        // Load templates from the database
        // For now, let's add some test templates:
        _templates.clear()

        // New Test Template 1: Item Registration
        addTemplate(Template(title = "Item Registration", fields = listOf(
            Field(0,"Item Name", "Enter the item's name"),
            Field(0,"Description", "Brief description of the item"),
            Field(0,"Quantity", "Enter the quantity"),
            Field(0,"Storage Location", "Where is the item stored?")
        ),
        tags = listOf(TemplateTags("Inventory"))
        ))

        // New Test Template 2: Stocktaking
        addTemplate(Template(title = "Stocktaking", fields = listOf(
            Field(0,"Product Code", "Product code or SKU"),
            Field(0,"Location", "Storage location"),
            Field(0,"Current Quantity", "Quantity in stock"),
            Field(0,"Notes", "Any relevant notes")
        )))
    ***REMOVED***
    fun addTemplate(template: Template) {
        _templates.add(template)
    ***REMOVED***

    fun updateTemplate(template: Template) {
        val index = _templates.indexOfFirst { it.id == template.id ***REMOVED***
        if (index != -1) {
            _templates[index] = template
        ***REMOVED***
    ***REMOVED***

    fun deleteTemplate(template: Template) {
        _templates.remove(template)
    ***REMOVED***
    fun deleteTemplateById(id: Int) {
        val index = _templates.indexOfFirst { it.id == id ***REMOVED***
        if (index != -1) {
            _templates.removeAt(index)
            // Consider adding logic to persist the change in your database if needed
        ***REMOVED***

    ***REMOVED***

    fun updateTemplateItem(templateId: Int, itemId: Int, newText: String) {
        val templateIndex = _templates.indexOfFirst { it.id == templateId ***REMOVED***
        if (templateIndex != -1) {
            val itemIndex = _templates[templateIndex].fields.indexOfFirst { it.id == itemId ***REMOVED***
            if (itemIndex != -1) {
                val updatedItem = _templates[templateIndex].fields[itemIndex].copy(title = newText)
                val updatedTemplate = _templates[templateIndex].copy(fields = _templates[templateIndex].fields.toMutableList().apply {
                    set(itemIndex, updatedItem)
                ***REMOVED***)
                _templates[templateIndex] = updatedTemplate
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***
