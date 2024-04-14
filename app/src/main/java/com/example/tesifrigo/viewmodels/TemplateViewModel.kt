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
        addTemplate(Template(title = "Product Entry", fields = listOf(
            Field("Product Name", "Enter the product's name"),
            Field("Quantity", "Enter the quantity of the product"),
            Field("Expiration Date", "Enter the expiration date (optional)")
        )))

        addTemplate(Template(title = "Inventory Check", fields = listOf(
            Field("Barcode", "Scan or enter product barcode"),
            Field("Location", "Enter the storage location"),
            Field("Notes", "Any additional notes (optional)")
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
