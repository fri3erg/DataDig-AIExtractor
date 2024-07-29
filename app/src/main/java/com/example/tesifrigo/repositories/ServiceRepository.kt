package com.example.tesifrigo.repositories

import android.content.Context
import android.content.SharedPreferences
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys
import com.example.tesifrigo.MyApp
import com.example.tesifrigo.models.Extracted
import com.example.tesifrigo.models.Extraction
import com.example.tesifrigo.models.Options
import com.example.tesifrigo.models.Template
import com.example.tesifrigo.viewmodels.ExtractionViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.UpdatePolicy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceRepository @Inject constructor(

) {
    val repositoryScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)  // Custom scope

    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress.asStateFlow()

    private val _result = MutableStateFlow<Extraction?>(null)
    val result: StateFlow<Extraction?> = _result.asStateFlow()

    private val _progressChannel = Channel<Float>()
    val progressChannel: Flow<Float> = _progressChannel.receiveAsFlow()

    private val _template = MutableStateFlow<Template?>(null)
    val template: StateFlow<Template?> = _template.asStateFlow()

    private val _options =MutableStateFlow<Options?>(null)
    val options: StateFlow<Options?> = _options.asStateFlow()

    private  val realm = MyApp.realm

    fun setTemplate(template: Template) {
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
        _progressChannel.trySend(newProgress) // Send to Channel for UI update
    ***REMOVED***

    fun updateResult(newResult: Extraction) {
        _result.value = newResult  // Directly update the MutableStateFlow
        realm.writeBlocking {
            // Update existing nested objects (example)
            newResult.template = newResult.template?.let { findLatest(it) ***REMOVED*** // Smart cast

            for (table in newResult.extractedTables) {
                table.templateTable = table.templateTable?.let { findLatest(it) ***REMOVED***
                for (row in table.fields) {
                    for (field in row.fields) {
                        field.templateField = field.templateField?.let { findLatest(it) ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
            for(field in newResult.extractedFields) {
                field.templateField = field.templateField?.let { findLatest(it) ***REMOVED***
            ***REMOVED***
            copyToRealm(newResult, UpdatePolicy.ALL)
        ***REMOVED***
    ***REMOVED***


    fun onDestroy() {
        repositoryScope.cancel()  // Cancel the scope when the repository is no longer needed
    ***REMOVED***
***REMOVED***
