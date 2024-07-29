package com.example.tesifrigo.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tesifrigo.MyApp
import com.example.tesifrigo.models.Extraction
import com.example.tesifrigo.models.ExtractionField
import com.example.tesifrigo.models.ExtractionTable
import com.example.tesifrigo.models.ExtractionTableRow
import com.example.tesifrigo.models.Template
import com.example.tesifrigo.models.TemplateField
import com.example.tesifrigo.models.TemplateTable
import com.example.tesifrigo.repositories.ServiceRepository
import com.example.tesifrigo.utils.calculateCloseness
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

@HiltViewModel
class ExtractionViewModel @Inject constructor() : ViewModel(){
    private  val realm =MyApp.realm

    private val _sortOrder = MutableStateFlow(SortOrder.BY_TITLE)
    val sortOrder: StateFlow<SortOrder> = _sortOrder.asStateFlow()

    private val _ascending = MutableStateFlow(true)
    val ascending: StateFlow<Boolean> = _ascending.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()



    val extractions = realm
        .query<Extraction>()
        .asFlow()
        .map {
            it.list.toList()
        ***REMOVED***
        .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList())



    val sortedExtractions: StateFlow<List<Extraction>> = combine(
        extractions, sortOrder, ascending, searchText
    ) { extractionList, order, isAscending, searchQuery ->
        extractionList.sortedWith { t1, t2 ->
            if (searchQuery.isBlank()) { // Check if searchQuery is empty
                // Default sorting if searchQuery is empty
                when (order) {
                    SortOrder.BY_TITLE -> compareValuesBy(t1, t2) { it.template?.title ***REMOVED*** * if (isAscending) 1 else -1
                    SortOrder.BY_DATE -> compareValuesBy(t1, t2) { it.id ***REMOVED*** * if (isAscending) 1 else -1
                ***REMOVED***
            ***REMOVED*** else {
                // Closeness-based sorting if searchQuery is not empty
                val closenessComparison = compareValuesBy(t1, t2) { extraction ->
                    extraction.template?.let { calculateCloseness(it.title, searchQuery) ***REMOVED***
                ***REMOVED***
                if (closenessComparison == 0) {
                    // If a tie, apply secondary comparison
                    when (order) {
                        SortOrder.BY_TITLE -> compareValuesBy(t1, t2) { it.template?.title ***REMOVED*** * if (isAscending) 1 else -1
                        SortOrder.BY_DATE -> compareValuesBy(t1, t2) { it.id ***REMOVED*** * if (isAscending) 1 else -1
                    ***REMOVED***
                ***REMOVED*** else {
                    closenessComparison
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init {
        //createSampleExtraction()
    ***REMOVED***

    fun queryTemplate(id: String): StateFlow<Extraction?> {
        return extractions.map { extractionList ->
            extractionList.find {
                Log.d("TemplateViewModel", "Querying template with ID: ${it.id.toHexString()***REMOVED*** and title: $id and $extractionList")
                it.id.toHexString() == id ***REMOVED***
        ***REMOVED***.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    ***REMOVED***

    private fun createSampleExtraction() {
        viewModelScope.launch {
            realm.write {

                val extractionToDelete = query<Extraction>().find()
                delete(extractionToDelete)
                val extractionFieldToDelete = query<ExtractionField>().find()
                delete(extractionFieldToDelete)
                val extractionTableToDelete = query<ExtractionTable>().find()
                delete(extractionTableToDelete)
                val extractionTableRowToDelete = query<ExtractionTableRow>().find()
                delete(extractionTableRowToDelete)


                val templateField1 = copyToRealm(TemplateField().apply {
                    title = "Field 1"
                    description = "This is a sample field"
                    extraDescription = "This is an extra description"
                    type = "text"
                    required = true
                    intelligent_extraction = false
                    tags = realmListOf("freezer")
                ***REMOVED***)
                val templateField2 = copyToRealm(TemplateField().apply {
                    title = "Field 2"
                    description = "This is a sample field 2"
                    extraDescription = "This is an extra description 2"
                    type = "text"
                    required = true
                    intelligent_extraction = true
                    tags = realmListOf("freezer")
                ***REMOVED***)
                val templateField3 = copyToRealm(TemplateField().apply {
                    title = "Field 3"
                    description = "This is a sample field 3"
                    extraDescription = "This is an extra description 3"
                    type = "text"
                    required = true
                    intelligent_extraction = false
                    tags = realmListOf("freezer")
                ***REMOVED***)

                val templateTable1 = copyToRealm(TemplateTable().apply {
                    title = "Table 1"
                    description = "This is a sample table"
                    keywords = realmListOf("freezer")
                    rows = realmListOf(templateField1, templateField2)
                    columns = realmListOf(templateField3)
                ***REMOVED***)

                val template1 = copyToRealm(Template().apply {
                    title = "Sample Template"
                    description = "This is a sample template"
                    fields = realmListOf(
                        templateField1,
        ***REMOVED***
                    tables = realmListOf(templateTable1)
                    tags = realmListOf("freezer")
                    tables = realmListOf()
                ***REMOVED***)
                val template2 = copyToRealm(Template().apply {
                    title = "Sample Template 2"
                    description = "This is a sample template 2"
                    fields = realmListOf(
                        templateField1,
                        templateField2,
        ***REMOVED***
                    tables = realmListOf()
                    tags = realmListOf("freezer", "fridge")
                    tables = realmListOf()

                ***REMOVED***)
                val template3 = copyToRealm(Template().apply {
                    title = "Sample Template 3"
                    description = "This is a sample template 3"
                    fields = realmListOf(
                        templateField2,
                        templateField3
        ***REMOVED***
                    tables = realmListOf()

                    tags = realmListOf("freezer", "fridge")
                    tables = realmListOf()
                ***REMOVED***)

                val extractionField1 = copyToRealm(ExtractionField().apply {
                    templateField = templateField1

                    value = "This is an extracted value"
                ***REMOVED***)
                val extractionField2 = copyToRealm(ExtractionField().apply {
                    templateField = templateField2
                    value = "This is an extracted value 2"


                ***REMOVED***)
                val extractionField3 = copyToRealm(ExtractionField().apply {
                    templateField = templateField3
                    value = "This is an extracted value 3"
                ***REMOVED***)

                val extractionTableRow1 = copyToRealm(ExtractionTableRow().apply {
                    rowIndex = "1"
                    fields = realmListOf(extractionField1, extractionField2)
                ***REMOVED***)
                val extractionTableRow2 = copyToRealm(ExtractionTableRow().apply {
                    rowIndex = "2"
                    fields = realmListOf(extractionField2, extractionField3)
                ***REMOVED***)

                val extractedTable1 = copyToRealm(ExtractionTable().apply {
                    templateTable = templateTable1
                    dataframe = "This is a dataframe"
                    fields = realmListOf(extractionTableRow1, extractionTableRow2)
                ***REMOVED***)

                val extraction1 = Extraction().apply {
                    image =  realmListOf("https://example.com/image.jpg")
                    format= "cvs"
                    extractedFields = realmListOf(extractionField1)
                    extractedTables = realmListOf(extractedTable1)
                    extractionCosts = "100"
                    exceptionsOccurred = realmListOf()
                    template = template1
                ***REMOVED***
                val extraction2 = Extraction().apply {
                    image = realmListOf("https://example.com/image.jpg")
                    format= "cvs"
                    extractedFields = realmListOf(extractionField2, extractionField1)
                    extractionCosts = "200"
                    exceptionsOccurred = realmListOf()
                    template = template2
                ***REMOVED***
                val extraction3 = Extraction().apply {
                    image = realmListOf()
                    format= "json"
                    extractedFields = realmListOf(extractionField3, extractionField2)
                    extractionCosts = "300"
                    exceptionsOccurred = realmListOf()
                    template = template3
                ***REMOVED***

                copyToRealm(extraction1)
                copyToRealm(extraction2)
                copyToRealm(extraction3)

            ***REMOVED***
        ***REMOVED***

    ***REMOVED***
    fun updateExtraction(extraction: Extraction, modifiedValue: String, index: Int) {
        viewModelScope.launch {
            realm.write { // Start a write transaction
                val latestExtraction = findLatest(extraction) ?: copyToRealm(extraction)  // Find or create the latest extraction
                latestExtraction.extractedFields[index].value = modifiedValue

                // Update properties on latestExtraction, not extraction
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
    fun addExtraction(extraction: Extraction) { // Add a new extraction
        viewModelScope.launch {
            realm.write {
                copyToRealm(extraction)
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***


    fun deleteExtraction(id:String){
        viewModelScope.launch {
            realm.write {
                    val extractionToDelete = query<Extraction>("id == $0", ObjectId(id)).find().first()
                    // Delete the parent object (deletes all embedded objects)
                    delete(extractionToDelete)
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
***REMOVED***