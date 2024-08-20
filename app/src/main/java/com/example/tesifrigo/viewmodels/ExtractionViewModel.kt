package com.example.tesifrigo.viewmodels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tesifrigo.BuildConfig
import com.example.tesifrigo.MyApp
import com.example.tesifrigo.fileCreator.CsvCreator
import com.example.tesifrigo.fileCreator.JsonCreator
import com.example.tesifrigo.fileCreator.TextCreator
import com.example.tesifrigo.fileCreator.XmlCreator
import com.example.tesifrigo.models.ExceptionOccurred
import com.example.tesifrigo.models.Extraction
import com.example.tesifrigo.models.ExtractionCosts
import com.example.tesifrigo.models.ExtractionField
import com.example.tesifrigo.models.ExtractionTable
import com.example.tesifrigo.models.ExtractionTableRow
import com.example.tesifrigo.models.Review
import com.example.tesifrigo.models.Template
import com.example.tesifrigo.models.TemplateField
import com.example.tesifrigo.models.TemplateTable
import com.example.tesifrigo.utils.calculateCloseness
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
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
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.Properties
import javax.inject.Inject

@HiltViewModel
class ExtractionViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
) : ViewModel() {
    private val realm = MyApp.realm

    private val _sortOrder = MutableStateFlow(SortOrder.BY_TITLE)
    val sortOrder: StateFlow<SortOrder> = _sortOrder.asStateFlow()

    private val _ascending = MutableStateFlow(true)
    val ascending: StateFlow<Boolean> = _ascending.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    private lateinit var supabaseClient: SupabaseClient


    init {

        //Log.d("ExtractionViewModel", "init")
        createSampleExtraction()
        try {
            supabaseClient = createSupabaseClient(
                supabaseUrl = BuildConfig.EXPO_PUBLIC_SUPABASE_URL,
                supabaseKey = BuildConfig.EXPO_PUBLIC_SUPABASE_ANON_KEY
***REMOVED*** {
                install(Auth)
                install(Postgrest)
                //install other modules
            ***REMOVED***

            // ... use the supabaseClient
        ***REMOVED*** catch (e: IOException) {
            // Handle the exception (e.g., log, show error message)
            Log.e("ExtractionViewModel", "Error reading local.properties", e)
        ***REMOVED***
    ***REMOVED***


    private val extractions = realm.query<Extraction>().asFlow().map {
        it.list.toList()
    ***REMOVED***.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), emptyList()
    )


    val sortedExtractions: StateFlow<List<Extraction>> = combine(
        extractions, sortOrder, ascending, searchText
    ) { extractionList, order, isAscending, searchQuery ->
        extractionList.sortedWith { t1, t2 ->
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
                val closenessComparison = compareValuesBy(t1, t2) {
                    calculateCloseness(it.title, searchQuery)
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


    fun queryExtraction(id: String): StateFlow<Extraction?> {

        val ex = extractions.map { extractionList ->
            extractionList.find {
                it.id.toHexString() == id
            ***REMOVED***
        ***REMOVED***.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)
        return ex
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
                val extractionCostsToDelete = query<ExtractionCosts>().find()
                delete(extractionCostsToDelete)
                val extractionExceptionToDelete = query<ExceptionOccurred>().find()
                delete(extractionExceptionToDelete)


                val templateField1 = copyToRealm(TemplateField().apply {
                    title = "Field 1"
                    description = "This is a sample field"
                    extraDescription = "This is an extra description"
                    type = "Text"
                    required = true
                    intelligentExtraction = false
                    list = true
                ***REMOVED***)
                val templateField2 = copyToRealm(TemplateField().apply {
                    title = "Field 2"
                    description = "This is a sample field 2"
                    extraDescription = "This is an extra description 2"
                    type = "Text"
                    required = true
                    intelligentExtraction = true
                ***REMOVED***)
                val templateField3 = copyToRealm(TemplateField().apply {
                    title = "Field 3"
                    description = "This is a sample field 3"
                    extraDescription = "This is an extra description 3"
                    type = "Text"
                    required = true
                    intelligentExtraction = false
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
                        templateField2, templateField3
        ***REMOVED***
                    tables = realmListOf()

                    tags = realmListOf("freezer", "fridge")
                    tables = realmListOf()
                ***REMOVED***)

                val extractionField1 = copyToRealm(ExtractionField().apply {
                    templateField = templateField1
                    value = "This is an extracted value| this is a second value| third"
                ***REMOVED***)
                val extractionField2 = copyToRealm(ExtractionField().apply {
                    templateField = templateField2
                    value = "This is an extracted value 2"


                ***REMOVED***)
                val extractionField3 = copyToRealm(ExtractionField().apply {
                    templateField = templateField3
                    value = "This is an extracted value 3"
                ***REMOVED***)
                val extractionField4 = copyToRealm(ExtractionField().apply {
                    templateField = templateField1
                    value = "This is an extracted value 4"
                ***REMOVED***)
                val extractionField5 = copyToRealm(ExtractionField().apply {
                    templateField = templateField2
                    value = "This is an extracted value 5"
                ***REMOVED***)

                val extractionTableRow1 = copyToRealm(ExtractionTableRow().apply {
                    rowName = "1"
                    fields = realmListOf(extractionField3, extractionField2)
                ***REMOVED***)
                val extractionTableRow2 = copyToRealm(ExtractionTableRow().apply {
                    rowName = "2"
                    fields = realmListOf(extractionField5, extractionField4)
                ***REMOVED***)

                val extractedTable1 = copyToRealm(ExtractionTable().apply {
                    title = "Extracted Table 1"
                    templateTable = templateTable1
                    dataframe = "This is a dataframe"
                    fields = realmListOf(extractionTableRow1, extractionTableRow2)
                ***REMOVED***)


                val extractionCost = copyToRealm(ExtractionCosts().apply {
                    name = "gpt-3.5-turbo"
                    cost = 100f
                    currency = "USD"
                    tokens = 1000
                ***REMOVED***)

                val extractionCost2 = copyToRealm(ExtractionCosts().apply {
                    name = "gpt-4"
                    cost = 200f
                    currency = "USD"
                    tokens = 2000
                ***REMOVED***)

                val extractionException = copyToRealm(ExceptionOccurred().apply {
                    error = "This is an exception"
                    errorType = "error"
                    errorDescription = "This is an error message"
                ***REMOVED***)
                val extractionException2 = copyToRealm(ExceptionOccurred().apply {
                    error = "This is an exception 2"
                    errorType = "error 2"
                    errorDescription = "This is an error message 2"
                ***REMOVED***)

                val extraction1 = Extraction().apply {
                    title = template1.title
                    image = realmListOf("https://example.com/image.jpg")
                    format = "cvs"
                    extractedFields = realmListOf(extractionField1, extractionField5)
                    extractedTables = realmListOf(extractedTable1)
                    extractionCosts = realmListOf(extractionCost, extractionCost2)
                    exceptionsOccurred = realmListOf(extractionException, extractionException2)
                    template = template1
                    tags = realmListOf("freezer")
                    fileUri = ""
                ***REMOVED***
                val extraction2 = Extraction().apply {
                    title = template2.title
                    image = realmListOf("https://example.com/image.jpg")
                    format = "cvs"
                    extractedFields = realmListOf(extractionField2, extractionField1)
                    extractionCosts = realmListOf(extractionCost)
                    exceptionsOccurred = realmListOf(extractionException2)
                    template = template2
                    tags = realmListOf("freezer", "fridge")
                    fileUri = ""
                ***REMOVED***
                val extraction3 = Extraction().apply {
                    title = template3.title
                    image = realmListOf()
                    format = "json"
                    extractedFields = realmListOf(extractionField3, extractionField2)
                    extractionCosts = realmListOf(extractionCost2)
                    exceptionsOccurred = realmListOf(extractionException)
                    template = template3
                    tags = realmListOf("freezer", "fridge")
                    fileUri = ""
                ***REMOVED***

                copyToRealm(extraction1)
                copyToRealm(extraction2)
                copyToRealm(extraction3)

            ***REMOVED***
        ***REMOVED***

    ***REMOVED***

    fun saveReview(review: Review) {
        viewModelScope.launch {

            try {

                supabaseClient.from("reviews").insert(review)
            ***REMOVED*** catch (e: Exception) {
                // Handle any unexpected exceptions (e.g., network errors)
                println("Exception during review save: $e")
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***


    fun deleteExtraction(id: String) {
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

    fun removeTag(extraction: Extraction, tag: String) {
        viewModelScope.launch {
            realm.writeBlocking {
                extraction.tags.remove(tag)
            ***REMOVED***
        ***REMOVED***

    ***REMOVED***

    fun changeFormat(extraction: Extraction, format: String, context: Context) {
        var newFile: String? = extraction.fileUri
        when (format) {
            "json" -> {
                newFile = JsonCreator().convertToJsonFile(extraction, context).toString()
            ***REMOVED***

            "csv" -> {
                newFile = CsvCreator().convertToCsvFile(extraction, context).toString()
            ***REMOVED***

            "text" -> {
                newFile = TextCreator().convertToTextFile(extraction, context).toString()
            ***REMOVED***
            "xml" -> {
                newFile= XmlCreator().convertToXmlFile(extraction, context).toString()
            ***REMOVED***
        ***REMOVED***
        viewModelScope.launch {
            realm.writeBlocking {
                val latestExtraction = findLatest(extraction) ?: return@writeBlocking
                latestExtraction.format = format
                latestExtraction.fileUri = newFile
            ***REMOVED***
        ***REMOVED***

    ***REMOVED***

    fun updateField(field: ExtractionField, newValue: String) {
        viewModelScope.launch {
            realm.writeBlocking {
                val latestField = findLatest(field) ?: return@writeBlocking
                latestField.value = newValue
            ***REMOVED***
        ***REMOVED***

    ***REMOVED***

    fun addExtraImage(extraction: Extraction, uri: Uri) {
        viewModelScope.launch {
            realm.writeBlocking {
                val latestExtraction = findLatest(extraction) ?: return@writeBlocking
                latestExtraction.extraImages.add(uri.toString())
            ***REMOVED***
        ***REMOVED***

    ***REMOVED***

    fun changeReview(extraction: Extraction) {

        viewModelScope.launch {
            realm.writeBlocking {
                val latestExtraction = findLatest(extraction) ?: return@writeBlocking
                latestExtraction.review = true
            ***REMOVED***

        ***REMOVED***
    ***REMOVED***

    fun addTag(extraction: Extraction, newKey: String) {
        viewModelScope.launch {
            realm.writeBlocking {
                val latestExtraction = findLatest(extraction) ?: return@writeBlocking
                latestExtraction.tags.add(newKey)
            ***REMOVED***
        ***REMOVED***

    ***REMOVED***

    fun removeExtraImage(extraction: Extraction, index: Int) {
        viewModelScope.launch {
            realm.writeBlocking {
                val latestExtraction = findLatest(extraction) ?: return@writeBlocking
                latestExtraction.extraImages.removeAt(index)
            ***REMOVED***
        ***REMOVED***

    ***REMOVED***

    fun removeColumn(extractionTable: ExtractionTable, header: String) {
        viewModelScope.launch {
            realm.writeBlocking {
                val latestTable = findLatest(extractionTable) ?: return@writeBlocking
                for (field in latestTable.fields) {
                        field.fields.removeIf { it.templateField?.title == header***REMOVED***
                        ***REMOVED***
                    ***REMOVED***


        ***REMOVED***

    ***REMOVED***

    fun removeRow(extractionTable: ExtractionTable, row: ExtractionTableRow) {
        viewModelScope.launch {
            realm.writeBlocking {
                val latestTable = findLatest(extractionTable) ?: return@writeBlocking
                latestTable.fields.remove(row)
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    fun addRow(extractionTable: ExtractionTable, it: String) {
        viewModelScope.launch {
            realm.writeBlocking {
                val latestTable = findLatest(extractionTable) ?: return@writeBlocking
                val newFields = realmListOf<ExtractionField>()
                for (field in latestTable.fields[0].fields) {
                    val newField = ExtractionField().apply {
                        templateField = field.templateField
                        value = ""
                    ***REMOVED***
                    newFields.add(newField)
                ***REMOVED***
                val newRow = ExtractionTableRow().apply {
                    rowName = it
                    fields= newFields
                ***REMOVED***
                latestTable.fields.add(newRow)
            ***REMOVED***
        ***REMOVED***

    ***REMOVED***

    fun addColumn(extractionTable: ExtractionTable, it: String) {
        viewModelScope.launch {
            realm.writeBlocking {
                val latestTable = findLatest(extractionTable) ?: return@writeBlocking
                val newTemplateField = TemplateField().apply {
                    title = it
                    type = "text"
                ***REMOVED***
                for (row in latestTable.fields) {
                    val newField = ExtractionField().apply {
                        templateField = newTemplateField
                        value = ""
                    ***REMOVED***
                    row.fields.add(newField)
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***

    ***REMOVED***
***REMOVED***