package com.example.tesifrigo.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.tesifrigo.models.Options
import com.example.tesifrigo.models.Template
import com.example.tesifrigo.repositories.KeyManager
import com.example.tesifrigo.repositories.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(
    private val progressRepository: ServiceRepository, private val keyManager: KeyManager
) : ViewModel() {

    val progress: StateFlow<Float> = progressRepository.progress
    val result: StateFlow<String?> = progressRepository.result
    val template: StateFlow<Template?> = progressRepository.template
    val options: StateFlow<Options?> = progressRepository.options
    val imageUris : StateFlow<List<Uri>> = progressRepository.imageUris
    val activePhoto : StateFlow<Boolean> = progressRepository.activePhoto
    val activeExtraction : StateFlow<Boolean> = progressRepository.activeExtraction





    fun setActiveExtraction(active: Boolean) {
        progressRepository.setActiveExtraction(active)
    ***REMOVED***

    fun setActivePhoto(active: Boolean) {
        progressRepository.setActivePhoto(active)
    ***REMOVED***

    fun setTemplate(template: Template) {
        progressRepository.setTemplate(template)

    ***REMOVED***

    fun addImageUri(uri: Uri) {
        progressRepository.addImageUri(uri)
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


    fun keyExists(key: Keys): Boolean {
        return when (key) {
            Keys.API_KEY_1 -> keyManager.getApiKey(Keys.API_KEY_1) != null
            Keys.API_KEY_2 -> keyManager.getApiKey(Keys.API_KEY_2) != null
            Keys.API_KEY_3 -> keyManager.getApiKey(Keys.API_KEY_3) != null
        ***REMOVED***
    ***REMOVED***

    fun clearImageUris() {
        progressRepository.clearImageUris()
    ***REMOVED***

    fun gptKeysExist(): Boolean {
        return keyManager.getApiKey(Keys.API_KEY_1) != null

    ***REMOVED***

    fun azureKeysExist(): Boolean {
        return keyManager.getApiKey(Keys.API_KEY_2) != null && keyManager.getApiKey(Keys.API_KEY_3) != null
    ***REMOVED***

    fun removeImageUri(index: Int) {
        progressRepository.removeImageUri(index)
    ***REMOVED***

    fun setProgress(fl: Float) {
        progressRepository.setProgress(fl)

    ***REMOVED***

    fun clearResult() {
        progressRepository.clearResult()
    ***REMOVED***

    fun setActiveTemplate(active: Template?) {
        progressRepository.setTemplate(active)

    ***REMOVED***


***REMOVED***

enum class Keys {
    API_KEY_1, API_KEY_2, API_KEY_3
***REMOVED***

