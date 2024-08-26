package com.example.tesifrigo.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tesifrigo.MyApp
import com.example.tesifrigo.R
import com.example.tesifrigo.models.TemplateField
import com.example.tesifrigo.models.Template
import com.example.tesifrigo.models.TemplateTable
import com.example.tesifrigo.utils.RealmListStringAdapter
import com.example.tesifrigo.utils.TemplateFieldListAdapter
import com.example.tesifrigo.utils.TemplateTableListAdapter
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
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.realm.kotlin.types.RealmList
import org.mongodb.kbson.ObjectId


class TemplateViewModel : ViewModel() {

    private val realm = MyApp.realm

    private val _sortOrder = MutableStateFlow(SortOrder.BY_DATE)
    val sortOrder: StateFlow<SortOrder> = _sortOrder.asStateFlow()

    private val _ascending = MutableStateFlow(false)
    val ascending: StateFlow<Boolean> = _ascending.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    val _focusRequesterIndex = MutableStateFlow<Int?>(null) // Index of the element to focus
    val focusRequesterIndex: StateFlow<Int?> get() = _focusRequesterIndex.asStateFlow()

    private val _template = MutableStateFlow<Template?>(null) // Initialize with your template data
    val template: StateFlow<Template?> = _template.asStateFlow()



    val templates = realm.query<Template>().asFlow().map {
        it.list.toList()
    ***REMOVED***.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), emptyList()
    )

    val sortedTemplates: StateFlow<List<Template>> = combine(
        templates, sortOrder, ascending, searchText
    ) { templateList, order, isAscending, searchQuery ->
        templateList.sortedWith { t1, t2 ->
            if (searchQuery.isBlank()) { // Check if searchQuery is empty
                // Default sorting if searchQuery is empty
                when (order) {
                    SortOrder.BY_TITLE -> compareValuesBy(
                        t1, t2
        ***REMOVED*** { it.title ***REMOVED*** * if (isAscending) 1 else -1

                    SortOrder.BY_DATE -> compareValuesBy(
                        t1, t2
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
                            t1, t2
            ***REMOVED*** { it.title ***REMOVED*** * if (isAscending) 1 else -1

                        SortOrder.BY_DATE -> compareValuesBy(
                            t1, t2
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
        if (id.isEmpty()) {
            return template
        ***REMOVED***
        return templates.map { templateList ->
            templateList.find {
                it.id.toHexString() == id
            ***REMOVED***
        ***REMOVED***.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    ***REMOVED***

    // Function to load JSON from res/raw
    private fun loadTemplateFromJson(context: Context, resource: Int): Template? {
        val jsonString = context.resources.openRawResource(resource)
            .bufferedReader().use { it.readText() ***REMOVED***
        println(jsonString)

        val gson = GsonBuilder()
            .registerTypeAdapter(object : TypeToken<RealmList<String>>() {***REMOVED***.type, RealmListStringAdapter())
            .registerTypeAdapter(object : TypeToken<RealmList<TemplateField>>() {***REMOVED***.type, TemplateFieldListAdapter())
            .registerTypeAdapter(object : TypeToken<RealmList<TemplateTable>>() {***REMOVED***.type, TemplateTableListAdapter())
            .create()

        return gson.fromJson(jsonString, Template::class.java)
    ***REMOVED***

    fun createSampleTemplates(context: Context) {

        val article = loadTemplateFromJson(context, R.raw.article)
        val businessCard = loadTemplateFromJson(context, R.raw.business_card)
        val idPassport = loadTemplateFromJson(context, R.raw.id_passport)
        val prescription = loadTemplateFromJson(context, R.raw.prescription)
        val receipt= loadTemplateFromJson(context, R.raw.receipt)
        val resumeCv = loadTemplateFromJson(context, R.raw.resume_cv)
        val termsConditions = loadTemplateFromJson(context, R.raw.terms_conditions)
        val utilityBill = loadTemplateFromJson(context, R.raw.utility_bill)


        viewModelScope.launch {
            realm.write {
/*
                val templateToDelete = query<Template>().find()
                delete(templateToDelete)

                val fieldsToDelete = query<TemplateField>().find()
                delete(fieldsToDelete)

                val tablesToDelete = query<TemplateTable>().find()
                delete(tablesToDelete)
                */

                if (receipt != null) {
                    copyToRealm(receipt)
                ***REMOVED***
                if (article != null) {
                    copyToRealm(article)
                ***REMOVED***
                if (businessCard != null) {
                    copyToRealm(businessCard)
                ***REMOVED***
                if (idPassport != null) {
                    copyToRealm(idPassport)
                ***REMOVED***
                if (prescription != null) {
                    copyToRealm(prescription)
                ***REMOVED***
                if (resumeCv != null) {
                    copyToRealm(resumeCv)
                ***REMOVED***
                if (termsConditions != null) {
                    copyToRealm(termsConditions)
                ***REMOVED***
                if (utilityBill != null) {
                    copyToRealm(utilityBill)
                ***REMOVED***

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

                        "default" -> {
                            latestTemplate.fields[index].default = newText.toString()
                        ***REMOVED***

                        "list" -> {
                            latestTemplate.fields[index].list = newText as Boolean
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
            title = ""
            description = ""
            type = "Text"
            list = false
            required = false
            intelligentExtraction = false
            default = "N/A"
        ***REMOVED***
        viewModelScope.launch {
            realm.write {
                val latestTemplate = findLatest(template) ?: return@write
                latestTemplate.fields.add(newField)
                _focusRequesterIndex.value =
                    latestTemplate.fields.size - 1 // Focus on the last added field


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



    fun addTable(template: Template) {
        val newField = TemplateField().apply {
            title = ""
            type = "Text"
            required = true

        ***REMOVED***
        val newField2 = TemplateField().apply {
            title = ""
            type = "Text"
            required = true
        ***REMOVED***
        val newField3 = TemplateField().apply {
            title = ""
            type = "Text"
            required = true
        ***REMOVED***
        val newField4 = TemplateField().apply {
            title = ""
            type = "Text"
            required = true
        ***REMOVED***
        val newTable = TemplateTable().apply {
            title = ""
            description = ""
            keywords = realmListOf()
            rows = realmListOf(newField, newField2)
            columns = realmListOf(newField3, newField4)
        ***REMOVED***
        viewModelScope.launch {
            realm.write {
                val latestTemplate = findLatest(template) ?: return@write
                latestTemplate.tables.add(newTable)
                _focusRequesterIndex.value =
                    template.tables.size - 1 + template.fields.size // Focus on the last added table


            ***REMOVED***
        ***REMOVED***


    ***REMOVED***

    fun updateTableItem(template: Template, pair: Pair<String, Any>, tableIndex: Int) {
        viewModelScope.launch {
            realm.writeBlocking {
                val latestTemplate = findLatest(template) ?: return@writeBlocking
                val table = latestTemplate.tables[tableIndex]
                pair.let { (field, newText) ->
                    when (field) {
                        "title" -> {
                            table.title = newText.toString()
                        ***REMOVED***

                        "description" -> {
                            table.description = newText.toString()
                        ***REMOVED***

                        "keywords" -> {
                            table.keywords.add(newText.toString())
                        ***REMOVED***
                        "all" -> {
                            table.all = newText.toString().toBoolean()
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

    fun updateTableRowHeader(
        template: Any, tableIndex: Int, rowIndex: Int, newField: TemplateField
    ) {
        viewModelScope.launch {
            realm.writeBlocking {
                val latestTemplate = findLatest(template as Template) ?: return@writeBlocking
                val table = latestTemplate.tables[tableIndex]
                table.rows[rowIndex] = newField
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    fun updateTableColumnHeader(
        template: Any, tableIndex: Int, columnIndex: Int, newField: TemplateField
    ) {
        viewModelScope.launch {
            realm.writeBlocking {
                val latestTemplate = findLatest(template as Template) ?: return@writeBlocking
                val table = latestTemplate.tables[tableIndex]
                table.columns[columnIndex] = newField
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

    fun setActiveTemplate(template: Template?) {
        viewModelScope.launch {
            _template.value = template
        ***REMOVED***

    ***REMOVED***

    fun removeKeyword(table: TemplateTable, indexOf: Int) {
        viewModelScope.launch {
            realm.writeBlocking {
                val latest = findLatest(table) ?: return@writeBlocking
                latest.keywords.removeAt(indexOf)
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    fun addKeyword(table: TemplateTable, newKey: String) {
        viewModelScope.launch {
            realm.writeBlocking {
                val latest = findLatest(table) ?: return@writeBlocking
                latest.keywords.add(newKey)
            ***REMOVED***
        ***REMOVED***

    ***REMOVED***

    fun deleteRowFromTable(table: TemplateTable, rowIndex: Int) {
        viewModelScope.launch {
            realm.writeBlocking {
                val latest = findLatest(table) ?: return@writeBlocking
                latest.rows.removeAt(rowIndex)
            ***REMOVED***
        ***REMOVED***

    ***REMOVED***

    fun deleteColumnFromTable(table: TemplateTable, columnIndex: Int) {
        viewModelScope.launch {
            realm.writeBlocking {
                val latest = findLatest(table) ?: return@writeBlocking
                latest.columns.removeAt(columnIndex)
            ***REMOVED***
        ***REMOVED***

    ***REMOVED***

    fun updateTemplateDescription(template: Template, newValue: String) {
        viewModelScope.launch {
            realm.writeBlocking {
                val latestTemplate = findLatest(template) ?: return@writeBlocking
                latestTemplate.description = newValue
            ***REMOVED***
        ***REMOVED***

    ***REMOVED***
***REMOVED***



enum class SortOrder {
    BY_TITLE, BY_DATE,
***REMOVED***