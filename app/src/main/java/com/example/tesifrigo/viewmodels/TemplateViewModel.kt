package com.example.tesifrigo.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tesifrigo.MyApp
import com.example.tesifrigo.model.Extraction
import com.example.tesifrigo.model.Field
import com.example.tesifrigo.model.Template
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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
        createSampleTemplates()
    ***REMOVED***
    fun queryTemplate(id: Int): StateFlow<List<Template>?> {
        return realm.query<Template>("id == $id").asFlow().map { result->result.list.toList() ***REMOVED***.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )
    ***REMOVED***

    private fun createSampleTemplates() {
        viewModelScope.launch {
            realm.write {

                val field1= Field().apply {
                    title = "Field 1"
                    description = "This is a sample field"
                    tags = realmListOf("freezer")
                ***REMOVED***
                val field2= Field().apply {
                    title = "Field 2"
                    description = "This is a sample field 2"
                    tags = realmListOf("freezer")
                ***REMOVED***
                val field3= Field().apply {
                    title = "Field 3"
                    description = "This is a sample field 3"
                    tags = realmListOf("freezer")
                ***REMOVED***
                val template1 = Template().apply {
                    title = "Sample Template"
                    description = "This is a sample template"
                    fields = realmListOf(
                        field1,
                        field2,
                        field3
        ***REMOVED***
                    tags = realmListOf("freezer")
                ***REMOVED***
                val template2 = Template().apply {
                    title = "Sample Template 2"
                    description = "This is a sample template 2"
                    fields = realmListOf(
                        field1,
                        field2,
        ***REMOVED***
                    tags = realmListOf("freezer")
                ***REMOVED***
                val template3 = Template().apply {
                    title = "Sample Template 3"
                    description = "This is a sample template 3"
                    fields = realmListOf(
                        field2,
                        field3
        ***REMOVED***
                    tags = realmListOf("freezer")
                ***REMOVED***
                copyToRealm(field1)
                copyToRealm(field2)
                copyToRealm(field3)
                copyToRealm(template1)
                copyToRealm(template2)
                copyToRealm(template3)

            ***REMOVED***
        ***REMOVED***

    ***REMOVED***

    fun deleteTemplateById(id: String) {
        viewModelScope.launch {
            realm.write {
                val template = realm.query<Template>("id == $id")
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    fun updateTemplateItem(templateId: Int, id: String, newText: String) {
        viewModelScope.launch {

        ***REMOVED***

    ***REMOVED***
***REMOVED***
