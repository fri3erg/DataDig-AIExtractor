package com.example.tesifrigo.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tesifrigo.MyApp
import com.example.tesifrigo.model.TemplateField
import com.example.tesifrigo.model.Template
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class TemplateViewModel : ViewModel() {

    private  val realm = MyApp.realm

    val templates = realm
        .query<Template>()
        .asFlow()
        .map {
            it.list.toList()
        ***REMOVED***
        .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList())

    init {
        //createSampleTemplates()
    ***REMOVED***
    fun queryTemplate(id: String): StateFlow<Template?> {
        return templates.map { templateList ->
            templateList.find {
                Log.d("TemplateViewModel", "Querying template with ID: ${it.id.toHexString()***REMOVED*** and title: ${id***REMOVED*** and ${templateList***REMOVED***")
                it.id.toHexString() == id ***REMOVED***
        ***REMOVED***.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    ***REMOVED***

    private fun createSampleTemplates() {
        viewModelScope.launch {
            realm.write {


                val templateToDelete = query<Template>().find()
                delete(templateToDelete)

                val fieldsToDelete = query<TemplateField>().find()
                delete(fieldsToDelete)


                val templateField1= TemplateField().apply {
                    title = "Field 1"
                    description = "This is a sample field"
                    tags = realmListOf("freezer")
                ***REMOVED***
                val templateField2= TemplateField().apply {
                    title = "Field 2"
                    description = "This is a sample field 2"
                    tags = realmListOf("freezer")
                ***REMOVED***
                val templateField3= TemplateField().apply {
                    title = "Field 3"
                    description = "This is a sample field 3"
                    tags = realmListOf("freezer")
                ***REMOVED***
                val template1 = Template().apply {
                    title = "Sample Template"
                    description = "This is a sample template"
                    fields = realmListOf(
                        templateField1,
        ***REMOVED***
                    tags = realmListOf("freezer")
                ***REMOVED***
                val template2 = Template().apply {
                    title = "Sample Template 2"
                    description = "This is a sample template 2"
                    fields = realmListOf(
                        templateField2,
        ***REMOVED***
                    tags = realmListOf("freezer", "fridge")
                ***REMOVED***
                val template3 = Template().apply {
                    title = "Sample Template 3"
                    description = "This is a sample template 3"
                    fields = realmListOf(
                        templateField3
        ***REMOVED***
                    tags = realmListOf("freezer", "fridge")
                ***REMOVED***
                copyToRealm(template1)
                copyToRealm(template2)
                copyToRealm(template3)

            ***REMOVED***
        ***REMOVED***

    ***REMOVED***

    fun deleteTemplateById(id: String) {
        viewModelScope.launch {
            realm.write {
                val template =  query<Template>("id == $0", ObjectId(id)).find().first()
                delete(template)
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    fun updateTemplateItem(template: Template) {
        viewModelScope.launch {
            realm.write {
                copyToRealm(template, updatePolicy = io.realm.kotlin.UpdatePolicy.ALL)
            ***REMOVED***
        ***REMOVED***

    ***REMOVED***
***REMOVED***
