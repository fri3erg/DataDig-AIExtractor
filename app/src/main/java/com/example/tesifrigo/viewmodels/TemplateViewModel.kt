package com.example.tesifrigo.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tesifrigo.MyApp
import com.example.tesifrigo.models.TemplateField
import com.example.tesifrigo.models.Template
import com.example.tesifrigo.models.TemplateTable
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.tesifrigo.utils.calculateCloseness
import org.mongodb.kbson.ObjectId

class TemplateViewModel : ViewModel() {

    private val realm = MyApp.realm

    private val _sortOrder = MutableStateFlow(SortOrder.BY_TITLE)
    val sortOrder: StateFlow<SortOrder> = _sortOrder.asStateFlow()

    private val _ascending = MutableStateFlow(true)
    val ascending: StateFlow<Boolean> = _ascending.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    // ... other StateFlows (sortOrder, ascending) ...

    // Function to update search text


    val templates = realm
        .query<Template>()
        .asFlow()
        .map {
            it.list.toList()
        ***REMOVED***
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    val sortedTemplates: StateFlow<List<Template>> = combine(
        templates, sortOrder, ascending, searchText
    ) { templateList, order, isAscending, searchQuery ->
        templateList.sortedWith { t1, t2 ->
            if (searchQuery.isBlank()) { // Check if searchQuery is empty
                // Default sorting if searchQuery is empty
                when (order) {
                    SortOrder.BY_TITLE -> compareValuesBy(
                        t1,
                        t2
        ***REMOVED*** { it.title ***REMOVED*** * if (isAscending) 1 else -1

                    SortOrder.BY_DATE -> compareValuesBy(
                        t1,
                        t2
        ***REMOVED*** { it.id ***REMOVED*** * if (isAscending) 1 else -1
                ***REMOVED***
            ***REMOVED*** else {
                // Closeness-based sorting if searchQuery is not empty
                val closenessComparison = compareValuesBy(t1, t2) { template ->
                    calculateCloseness(template.title, searchQuery)
                ***REMOVED***
                if (closenessComparison == 0) {
                    // If a tie, apply secondary comparison
                    when (order) {
                        SortOrder.BY_TITLE -> compareValuesBy(
                            t1,
                            t2
            ***REMOVED*** { it.title ***REMOVED*** * if (isAscending) 1 else -1

                        SortOrder.BY_DATE -> compareValuesBy(
                            t1,
                            t2
            ***REMOVED*** { it.id ***REMOVED*** * if (isAscending) 1 else -1
                    ***REMOVED***
                ***REMOVED*** else {
                    closenessComparison
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


    init {
        //createSampleTemplates()
    ***REMOVED***

    fun queryTemplate(id: String): StateFlow<Template?> {
        return templates.map { templateList ->
            templateList.find {
                Log.d(
                    "TemplateViewModel",
                    "Querying template with ID: ${it.id.toHexString()***REMOVED*** and title: $id and $templateList"
    ***REMOVED***
                it.id.toHexString() == id
            ***REMOVED***
        ***REMOVED***.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    ***REMOVED***

    private fun createSampleTemplates() {
        viewModelScope.launch {
            realm.write {


                val templateToDelete = query<Template>().find()
                delete(templateToDelete)

                val fieldsToDelete = query<TemplateField>().find()
                delete(fieldsToDelete)

                val tablesToDelete = query<TemplateTable>().find()
                delete(tablesToDelete)


                val templateField1 = TemplateField().apply {
                    title = "Field 1"
                    description = "This is a sample field"
                    extraDescription = "This is an extra description"
                    type = "text"
                    required = true
                    intelligentExtraction = false
                    tags = realmListOf("freezer")
                ***REMOVED***
                val templateField2 = TemplateField().apply {
                    title = "Field 2"
                    description = "This is a sample field 2"
                    extraDescription = "This is an extra description 2"
                    type = "text"
                    required = true
                    intelligentExtraction = true
                    tags = realmListOf("freezer")
                ***REMOVED***
                val templateField3 = TemplateField().apply {
                    title = "Field 3"
                    description = "This is a sample field 3"
                    extraDescription = "This is an extra description 3"
                    type = "text"
                    required = true
                    intelligentExtraction = false
                    tags = realmListOf("freezer")
                ***REMOVED***

                val templateTable1 = TemplateTable().apply {
                    title = "Table 1"
                    description = "This is a sample table"
                    keywords = realmListOf("freezer")
                    rows = realmListOf(templateField1, templateField2)
                    columns = realmListOf(templateField2)
                ***REMOVED***

                val template1 = Template().apply {
                    title = "Sample Template"
                    description = "This is a sample template"
                    fields = realmListOf(
                        templateField1,
        ***REMOVED***
                    tables = realmListOf(templateTable1)
                    tags = realmListOf("freezer")
                    tables = realmListOf()
                ***REMOVED***
                val template2 = Template().apply {
                    title = "Sample Template 2"
                    description = "This is a sample template 2"
                    fields = realmListOf(
                        templateField2,
        ***REMOVED***
                    tables = realmListOf()
                    tags = realmListOf("freezer", "fridge")
                    tables = realmListOf()

                ***REMOVED***
                val template3 = Template().apply {
                    title = "Sample Template 3"
                    description = "This is a sample template 3"
                    fields = realmListOf(
                        templateField3
        ***REMOVED***
                    tables = realmListOf()

                    tags = realmListOf("freezer", "fridge")
                    tables = realmListOf()
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
                val template = query<Template>("id == $0", ObjectId(id)).find().first()
                delete(template)
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    fun updateTemplateItem(template: Template, modifiedValue: Pair<String, Any>, index: Int) {
        viewModelScope.launch {
            realm.writeBlocking {
                val latestTemplate = findLatest(template)
                    ?: copyToRealm(template)  // Find or create the latest extraction
                modifiedValue.let { (field, newText) ->
                    when (field) {
                        "title" -> {
                            latestTemplate.fields[index].title = newText.toString()
                        ***REMOVED***

                        "description" -> {
                            latestTemplate.fields[index].description = newText.toString()
                        ***REMOVED***

                        "type" -> {
                            latestTemplate.fields[index].type = newText.toString()
                        ***REMOVED***

                        "required" -> {
                            latestTemplate.fields[index].required = newText as Boolean
                        ***REMOVED***

                        "intelligentExtraction" -> {
                            latestTemplate.fields[index].intelligentExtraction = newText as Boolean
                        ***REMOVED***

                        else -> {
                            // Handle other cases if needed
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***

                // Update properties on latestExtraction, not extraction
                latestTemplate.apply {
                    title = template.title
                    description = template.description
                    // fields = extraction.fields // Don't update RealmList directly
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***

    ***REMOVED***

    fun addTemplate(): String {
        val newField = TemplateField().apply {
            title = "New Field"
            description = "This is a new field"
            tags = realmListOf("tag1 ")
        ***REMOVED***
        val newTemplate = Template().apply {
            title = "New Template"
            description = "This is a new template"
            tags = realmListOf("freezer")
            fields = realmListOf(newField)
        ***REMOVED***
        viewModelScope.launch {
            realm.write {
                copyToRealm(newTemplate)

            ***REMOVED***
        ***REMOVED***
        return newTemplate.id.toHexString()
    ***REMOVED***

    fun addField(template: Template) {
        val newField = TemplateField().apply {
            title = "New Field"
            description = "This is a new field"
            tags = realmListOf("tag1 ")
        ***REMOVED***
        viewModelScope.launch {
            realm.write {
                val latestTemplate = findLatest(template) ?: return@write
                latestTemplate.fields.add(newField)
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    fun updateSearchText(newText: String) {
        _searchText.value = newText
    ***REMOVED***

    // Function to update sort order
    fun updateSortOrder(newOrder: SortOrder) {
        _sortOrder.value = newOrder
    ***REMOVED***

    // Function to toggle ascending/descending order
    fun toggleAscending() {
        _ascending.value = !_ascending.value
    ***REMOVED***

    fun updateTemplateTitle(template: Template, it: String) {
        viewModelScope.launch {
            realm.writeBlocking {
                val latestTemplate = findLatest(template) ?: return@writeBlocking
                latestTemplate.title = it
            ***REMOVED***

        ***REMOVED***
    ***REMOVED***


    fun removeTag(template: Template, tag: String) {
        viewModelScope.launch {
            realm.writeBlocking {
                val latestTemplate = findLatest(template) ?: return@writeBlocking
                latestTemplate.tags.remove(tag)
            ***REMOVED***

        ***REMOVED***


    ***REMOVED***

    fun changeTags(template: Template, newTag: String) {
        viewModelScope.launch {
            realm.writeBlocking {
                val latestTemplate = findLatest(template) ?: return@writeBlocking
                latestTemplate.tags.add(newTag)
            ***REMOVED***
        ***REMOVED***

    ***REMOVED***

    fun addTable(template: Template) {
        val newField = TemplateField().apply {
            title = ""
        ***REMOVED***
        val newField2 = TemplateField().apply {
            title = ""
        ***REMOVED***
        val newField3 = TemplateField().apply {
            title = ""
        ***REMOVED***
        val newField4 = TemplateField().apply {
            title = ""
        ***REMOVED***
        val newTable = TemplateTable().apply {
            title = "New Table"
            description = "This is a new table"
            keywords = realmListOf("tag1 ")
            rows = realmListOf(newField, newField2)
            columns = realmListOf(newField3, newField4)
        ***REMOVED***
        viewModelScope.launch {
            realm.write {
                val latestTemplate = findLatest(template) ?: return@write
                latestTemplate.tables.add(newTable)

            ***REMOVED***
        ***REMOVED***


    ***REMOVED***

    fun updateTableItem(template: Template, pair: Pair<String, String>, tableIndex: Int) {
        viewModelScope.launch {
            realm.writeBlocking {
                val latestTemplate = findLatest(template) ?: return@writeBlocking
                val table = latestTemplate.tables[tableIndex]
                pair.let { (field, newText) ->
                    when (field) {
                        "title" -> {
                            table.title = newText
                        ***REMOVED***

                        "description" -> {
                            table.description = newText
                        ***REMOVED***

                        "keywords" -> {
                            table.keywords.add(newText)
                        ***REMOVED***

                        else -> {
                            // Handle other cases if needed
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***

        ***REMOVED***
    ***REMOVED***



    fun deleteTable(template: Template, tableIndex: Int) {
        viewModelScope.launch {
            realm.writeBlocking {
                val latestTemplate = findLatest(template) ?: return@writeBlocking
                latestTemplate.tables.removeAt(tableIndex)
            ***REMOVED***
        ***REMOVED***

    ***REMOVED***

    fun updateTableRowHeader(template: Any, tableIndex: Int, rowIndex: Int, newField: TemplateField) {
        viewModelScope.launch {
            realm.writeBlocking {
                val latestTemplate = findLatest(template as Template) ?: return@writeBlocking
                val table = latestTemplate.tables[tableIndex]
                table.rows[rowIndex] = newField
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    fun updateTableColumnHeader(template: Any, tableIndex: Int, columnIndex: Int, newField: TemplateField) {
        viewModelScope.launch {
            realm.writeBlocking {
                val latestTemplate = findLatest(template as Template) ?: return@writeBlocking
                val table = latestTemplate.tables[tableIndex]
                table.columns[columnIndex]= newField
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    fun addRowToTable(template: Template, tableIndex: Int, newField: TemplateField) {

        viewModelScope.launch {
            realm.write {
                val latestTemplate = findLatest(template) ?: return@write
                latestTemplate.tables[tableIndex].rows.add(newField)
            ***REMOVED***
        ***REMOVED***

    ***REMOVED***

    fun addColumnToTable(template: Template, tableIndex: Int, newField: TemplateField) {

        viewModelScope.launch {
            realm.write {
                val latestTemplate = findLatest(template) ?: return@write
                latestTemplate.tables[tableIndex].columns.add(newField)
            ***REMOVED***

        ***REMOVED***
    ***REMOVED***

    fun deleteField(template: Template, index: Int) {
        viewModelScope.launch {
            realm.writeBlocking {
                val latestTemplate = findLatest(template) ?: return@writeBlocking
                latestTemplate.fields.removeAt(index)
            ***REMOVED***

        ***REMOVED***
    ***REMOVED***
***REMOVED***

    enum class SortOrder{
    BY_TITLE,
    BY_DATE,
***REMOVED***