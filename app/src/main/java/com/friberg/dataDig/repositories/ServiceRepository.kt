package com.friberg.dataDig.repositories


import android.content.Context
import android.net.Uri
import com.friberg.dataDig.MyApp
import com.friberg.dataDig.fileCreator.CsvCreator
import com.friberg.dataDig.fileCreator.JsonCreator
import com.friberg.dataDig.fileCreator.TextCreator
import com.friberg.dataDig.fileCreator.XmlCreator
import com.friberg.dataDig.models.Extraction
import com.friberg.dataDig.models.Options
import com.friberg.dataDig.models.Template
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.isManaged
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Service repository to handle the extraction progress, result and other utilities
 *
 * @constructor Create empty Service repository
 */
@Singleton
class ServiceRepository @Inject constructor(

) {

    private val repositoryScope =
        CoroutineScope(SupervisorJob() + Dispatchers.IO) // IO dispatcher for Realm operations


    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress.asStateFlow()

    private val _result = MutableStateFlow<String?>(null)
    val result: StateFlow<String?> = _result.asStateFlow()


    private val _template = MutableStateFlow<Template?>(null) // Current template user is using
    val template: StateFlow<Template?> = _template.asStateFlow()

    private val _options = MutableStateFlow<Options?>(null) // Current options user is using
    val options: StateFlow<Options?> = _options.asStateFlow()

    private val _imageUris = MutableStateFlow(emptyList<Uri>()) // List of image uris user has selected
    val imageUris: StateFlow<List<Uri>> = _imageUris.asStateFlow()

    private val _activePhoto = MutableStateFlow(true) // Whether the photo view is active
    val activePhoto: StateFlow<Boolean> = _activePhoto.asStateFlow()

    private val _activeExtraction = MutableStateFlow(false) // Whether the extraction is active
    val activeExtraction: StateFlow<Boolean> = _activeExtraction.asStateFlow()



    private val realm = MyApp.realm

    fun setTemplate(template: Template?) {
        _template.value = template
    ***REMOVED***

    fun getTemplate(): Template? {
        return _template.value
    ***REMOVED***

    fun setOptions(options: Options) {
        _options.value = options
    ***REMOVED***

    fun getOptions(): Options? {
        return _options.value
    ***REMOVED***

    fun updateProgress(newProgress: Float) {
        _progress.value = newProgress
    ***REMOVED***

    /**
     * Update result
     *
     * @param newResult The new result to update from the service
     * @param context The context for the file creation
     */
    fun updateResult(newResult: Extraction, context: Context) {
        repositoryScope.launch {

            realm.writeBlocking {
                // Find the latest version of the template it is based on
                newResult.template = newResult.template?.let { findLatest(it) ***REMOVED*** // Smart cast

                //need to check that the template is not created by the backend for the tables
                for (table in newResult.extractedTables) {
                    table.templateTable = table.templateTable?.let { findLatest(it) ***REMOVED***
                    for (row in table.fields) {
                        for (field in row.fields) {
                            val managedField = field.templateField?.let {
                                if (it.isManaged()) {
                                    findLatest(it)
                                ***REMOVED*** else {
                                    null
                                ***REMOVED***
                            ***REMOVED***
                            if (managedField != null) {
                                field.templateField = managedField
                            ***REMOVED*** else {
                                copyToRealm(field, UpdatePolicy.ALL) // If not found, copy to Realm
                            ***REMOVED***
                        ***REMOVED***
                        ***REMOVED***
                ***REMOVED***
                for (field in newResult.extractedFields) {
                    field.templateField = field.templateField?.let { findLatest(it) ***REMOVED***
                ***REMOVED***
                when (newResult.format) {
                    "json" -> {
                        newResult.fileUri =
                            JsonCreator().convertToJsonFile(newResult, context).toString()
                    ***REMOVED***

                    "csv" -> {
                        newResult.fileUri =
                            CsvCreator().convertToCsvFile(newResult, context).toString()

                    ***REMOVED***
                    "text" -> {
                        newResult.fileUri = TextCreator().convertToTextFile(newResult, context).toString()
                    ***REMOVED***
                    "xml" -> {
                        newResult.fileUri = XmlCreator().convertToXmlFile(newResult, context).toString()
                    ***REMOVED***

                ***REMOVED***
                copyToRealm(newResult, UpdatePolicy.ALL)
                _result.value = newResult.id.toHexString()

            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    fun changeOptions(field: String, value: Any) {
        repositoryScope.launch {
            when (field) {
                "language" -> {
                    _options.value?.language = value.toString()
                ***REMOVED***

                "model" -> {
                    _options.value?.model = value.toString()
                ***REMOVED***

                "format" -> {
                    _options.value?.format = value.toString()
                ***REMOVED***

                "azureOcr" -> {
                    _options.value?.azureOcr = value as Boolean
                ***REMOVED***
                "resize" -> {
                    _options.value?.resize = value as Boolean
                ***REMOVED***

            ***REMOVED***

        ***REMOVED***

    ***REMOVED***

    fun setActiveExtraction(active: Boolean) {
        _activeExtraction.value = active

    ***REMOVED***

    fun setActivePhoto(active: Boolean) {
        _activePhoto.value = active
    ***REMOVED***

    fun addImageUri(uri: Uri) {
        _imageUris.value += uri

    ***REMOVED***

    fun clearImageUris() {
        _imageUris.value = emptyList()
    ***REMOVED***

    fun removeImageUri(index: Int) {
        _imageUris.value = _imageUris.value.toMutableList().apply { removeAt(index) ***REMOVED***

    ***REMOVED***

    fun setProgress(fl: Float) {
        _progress.value = fl

    ***REMOVED***

    fun clearResult() {
        _result.value = null
    ***REMOVED***

***REMOVED***
