package com.example.tesifrigo.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.tesifrigo.model.Field
import com.example.tesifrigo.model.Template

class TemplateViewModel : ViewModel() {


    private val _templates = mutableStateListOf<Template>()
    val templates: List<Template> = _templates

    init {
        // Load templates from the database
        // For now, let's add some test templates:
        _templates.clear()

        // New Test Template 1: Item Registration
        addTemplate(Template(title = "Item Registration", fields = listOf(
            Field("Item Name", "Enter the item's name"),
            Field("Description", "Brief description of the item"),
            Field("Quantity", "Enter the quantity"),
            Field("Storage Location", "Where is the item stored?")
        )))

        // New Test Template 2: Stocktaking
        addTemplate(Template(title = "Stocktaking", fields = listOf(
            Field("Product Code", "Product code or SKU"),
            Field("Location", "Storage location"),
            Field("Current Quantity", "Quantity in stock"),
            Field("Notes", "Any relevant notes")
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
***REMOVED***
