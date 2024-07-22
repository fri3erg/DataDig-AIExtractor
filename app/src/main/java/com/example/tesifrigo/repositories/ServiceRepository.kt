package com.example.tesifrigo.repositories

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys
import com.example.tesifrigo.models.Extracted
import com.example.tesifrigo.models.Extraction
import com.example.tesifrigo.models.Template
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceRepository @Inject constructor() {
    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress.asStateFlow()

    private val _result = MutableStateFlow<Extraction?>(null)
    val result: StateFlow<Extraction?> = _result.asStateFlow()

    private val _progressChannel = Channel<Float>()
    val progressChannel: Flow<Float> = _progressChannel.receiveAsFlow()

    private val _template = MutableStateFlow<Template?>(null)
    val template: StateFlow<Template?> = _template.asStateFlow()

    fun setTemplate(template: Template) {
        _template.value = template
    ***REMOVED***

    fun getTemplate(): Template? {
        return _template.value
    ***REMOVED***

    fun updateProgress(newProgress: Float) {
        _progress.value = newProgress
        _progressChannel.trySend(newProgress) // Send to Channel for UI update
    ***REMOVED***

    fun updateResult(newResult: Extraction) {
        _result.value = newResult
    ***REMOVED***
***REMOVED***
