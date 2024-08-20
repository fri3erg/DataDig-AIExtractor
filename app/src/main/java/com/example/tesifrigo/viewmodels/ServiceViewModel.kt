package com.example.tesifrigo.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.tesifrigo.models.Options
import com.example.tesifrigo.models.Template
import com.example.tesifrigo.repositories.KeyManager
import com.example.tesifrigo.repositories.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(
    private val progressRepository: ServiceRepository, private val keyManager: KeyManager
) : ViewModel() {

    val progress: StateFlow<Float> = progressRepository.progress
    val result: StateFlow<String?> = progressRepository.result
    val template: StateFlow<Template?> = progressRepository.template
    val options: StateFlow<Options?> = progressRepository.options


    private val _imageUris = MutableStateFlow(emptyList<Uri>())
    val imageUris: StateFlow<List<Uri>> = _imageUris.asStateFlow()

    private val _activePhoto = MutableStateFlow(true)
    val activePhoto: StateFlow<Boolean> = _activePhoto.asStateFlow()

    private val _activeExtraction = MutableStateFlow(false)
    val activeExtraction: StateFlow<Boolean> = _activeExtraction.asStateFlow()

    init {
        //progressRepository.deleteOptions()
    ***REMOVED***

    fun setActiveExtraction(active: Boolean) {
        _activeExtraction.value = active
    ***REMOVED***

    fun setActivePhoto(active: Boolean) {
        _activePhoto.value = active
    ***REMOVED***

    fun setTemplate(template: Template) {
        progressRepository.setTemplate(template)

    ***REMOVED***

    fun addImageUri(uri: Uri) {
        _imageUris.value += uri
    ***REMOVED***

    fun setOptions(options: Options) {
        progressRepository.setOptions(options)
    ***REMOVED***

    fun changeOptions(field: String, value: Any) {
        progressRepository.changeOptions(field, value)
    ***REMOVED***


    fun storeApiKey(key: Keys, apiKey: String) {
        when (key) {
            Keys.API_KEY_1 -> keyManager.storeApiKey(apiKey, Keys.API_KEY_1)
            Keys.API_KEY_2 -> keyManager.storeApiKey(apiKey, Keys.API_KEY_2)
            Keys.API_KEY_3 -> keyManager.storeApiKey(apiKey, Keys.API_KEY_3)
        ***REMOVED***
    ***REMOVED***


    fun getApiKey(key: Keys): String? {
        return when (key) {
            Keys.API_KEY_1 -> keyManager.getApiKey(Keys.API_KEY_1)
            Keys.API_KEY_2 -> keyManager.getApiKey(Keys.API_KEY_2)
            Keys.API_KEY_3 -> keyManager.getApiKey(Keys.API_KEY_3)

        ***REMOVED***
    ***REMOVED***


    fun keyExists(key: Keys): Boolean {
        return when (key) {
            Keys.API_KEY_1 -> keyManager.getApiKey(Keys.API_KEY_1) != null
            Keys.API_KEY_2 -> keyManager.getApiKey(Keys.API_KEY_2) != null
            Keys.API_KEY_3 -> keyManager.getApiKey(Keys.API_KEY_3) != null
        ***REMOVED***
    ***REMOVED***

    fun clearImageUris() {
        _imageUris.value = emptyList()
    ***REMOVED***

    fun gptKeysExist(): Boolean {
        return keyManager.getApiKey(Keys.API_KEY_1) != null

    ***REMOVED***

    fun azureKeysExist(): Boolean {
        return keyManager.getApiKey(Keys.API_KEY_2) != null && keyManager.getApiKey(Keys.API_KEY_3) != null
    ***REMOVED***

    fun removeImageUri(index: Int) {
        val list = _imageUris.value.toMutableList()
        list.removeAt(index)
        _imageUris.value = list
    ***REMOVED***


***REMOVED***

enum class Keys {
    API_KEY_1, API_KEY_2, API_KEY_3
***REMOVED***

