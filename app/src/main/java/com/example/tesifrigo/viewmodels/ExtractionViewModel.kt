package com.example.tesifrigo.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tesifrigo.MyApp
import com.example.tesifrigo.models.Extraction
import com.example.tesifrigo.models.ExtractionField
import com.example.tesifrigo.models.Template
import com.example.tesifrigo.utils.calculateCloseness
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
import org.mongodb.kbson.ObjectId

class ExtractionViewModel: ViewModel(){
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
    ) { templateList, order, isAscending, searchQuery ->
        templateList.sortedWith { t1, t2 ->
            if (searchQuery.isBlank()) { // Check if searchQuery is empty
                // Default sorting if searchQuery is empty
                when (order) {
                    SortOrder.BY_TITLE -> compareValuesBy(t1, t2) { it.title ***REMOVED*** * if (isAscending) 1 else -1
                    SortOrder.BY_DATE -> compareValuesBy(t1, t2) { it.id ***REMOVED*** * if (isAscending) 1 else -1
                ***REMOVED***
            ***REMOVED*** else {
                // Closeness-based sorting if searchQuery is not empty
                val closenessComparison = compareValuesBy(t1, t2) { template ->
                    calculateCloseness(template.title, searchQuery)
                ***REMOVED***
                if (closenessComparison == 0) {
                    // If a tie, apply secondary comparison
                    when (order) {
                        SortOrder.BY_TITLE -> compareValuesBy(t1, t2) { it.title ***REMOVED*** * if (isAscending) 1 else -1
                        SortOrder.BY_DATE -> compareValuesBy(t1, t2) { it.id ***REMOVED*** * if (isAscending) 1 else -1
                    ***REMOVED***
                ***REMOVED*** else {
                    closenessComparison
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init {
        createSampleExtraction()
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


                val template1= Template().apply {
                    title = "Sample Template"
                    description = "This is a sample template"

                ***REMOVED***
                val template2= Template().apply {
                    title = "Sample Template 2"
                    description = "This is a sample template 2"
                ***REMOVED***
                val template3= Template().apply {
                    title = "Sample Template 3"
                    description = "This is a sample template 3"
                ***REMOVED***

                val extractionField1 = ExtractionField().apply {
                    title = "Field 1"
                    description = "This is a sample field"
                    extraDescription = "This is an extra description"
                    extracted = "This is an extracted value"
                    type = "text"
                    tags = realmListOf("Tag 1",
                        "Tag 2"
        ***REMOVED***
                ***REMOVED***
                val extractionField2 = ExtractionField().apply {
                    title = "Field 2"
                    description = "This is a sample field 2"
                    extraDescription = "This is an extra description 2"
                    extracted = "This is an extracted value 2"
                    type = "text"
                    tags = realmListOf("Tag 1",
                        "Tag 2"
        ***REMOVED***
                ***REMOVED***
                val extractionField3 = ExtractionField().apply {
                    title = "Field 3"
                    description = "This is a sample field 3"
                    extraDescription = "This is an extra description 3"
                    extracted = "This is an extracted value 3"
                    type = "text"
                    tags = realmListOf("Tag 1",
                        "Tag 2"
        ***REMOVED***
                ***REMOVED***



                val extraction1 = Extraction().apply {
                    title = "Sample Extraction"
                    description = "This is a sample extraction"
                    image = "https://www.example.com/image.jpg"
                    format= "cvs"
                    fields = realmListOf(extractionField1)
                    template = template1
                ***REMOVED***
                val extraction2 = Extraction().apply {
                    title = "Sample Extraction 2"
                    description = "This is a sample extraction 2"
                    image = "https://www.example.com/image2.jpg"
                    format= "cvs"
                    fields = realmListOf( extractionField2)
                    template = template2
                ***REMOVED***
                val extraction3 = Extraction().apply {
                    title = "Sample Extraction 3"
                    description = "This is a sample extraction 3"
                    image = "https://www.example.com/image3.jpg"
                    format= "json"
                    fields = realmListOf(extractionField3)
                    template = template3
                ***REMOVED***

                copyToRealm(extraction1)
                copyToRealm(extraction2)
                copyToRealm(extraction3)

            ***REMOVED***
        ***REMOVED***

    ***REMOVED***
    fun updateExtraction(extraction: Extraction, modifiedValue: Pair<String, String>, index: Int) {
        viewModelScope.launch {
            realm.write { // Start a write transaction
                val latestExtraction = findLatest(extraction) ?: copyToRealm(extraction)  // Find or create the latest extraction
                modifiedValue.let { (field, newText) ->
                    when (field) {
                        "extra" -> {
                            latestExtraction.fields[index].extraDescription = newText
                        ***REMOVED***
                        "description" -> {
                            latestExtraction.fields[index].description = newText
                        ***REMOVED***
                        "extracted" -> {
                            latestExtraction.fields[index].extracted = newText
                    ***REMOVED***
                        else -> {
                            // Handle other cases if needed
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***

                // Update properties on latestExtraction, not extraction
                latestExtraction.apply {
                    title = extraction.title
                    description = extraction.description
                    format = extraction.format
                    // fields = extraction.fields // Don't update RealmList directly
                ***REMOVED***
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