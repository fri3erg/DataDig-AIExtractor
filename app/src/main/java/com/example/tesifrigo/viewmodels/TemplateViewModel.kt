package com.example.tesifrigo.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.tesifrigo.model.Template

class TemplateViewModel : ViewModel() {

    private val _templates = mutableStateListOf<Template>()
    val templates: List<Template> = _templates

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
***REMOVED***
