package com.example.tesifrigo.viewmodels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.tesifrigo.MyApp
import com.example.tesifrigo.models.Extracted
import com.example.tesifrigo.models.Extraction
import com.example.tesifrigo.models.Options
import com.example.tesifrigo.models.Template
import com.example.tesifrigo.repositories.KeyManager
import com.example.tesifrigo.repositories.ServiceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class ServiceViewModel @Inject constructor(
    private val progressRepository: ServiceRepository,
    private val keyManager: KeyManager
) : ViewModel(){

    val progress: StateFlow<Float> = progressRepository.progress
    val result: StateFlow<Extraction?> = progressRepository.result
    val template: StateFlow<Template?> = progressRepository.template
    val options : StateFlow<Options?> = progressRepository.options

    fun setTemplate(template: Template) {
        progressRepository.setTemplate(template)

    ***REMOVED***

    fun setOptions(options: Options) {
        progressRepository.setOptions(options)
    ***REMOVED***


    fun storeApiKey(key: Keys, apiKey: String) {
        when(key){
            Keys.API_KEY_1 -> keyManager.storeApiKey(apiKey, Keys.API_KEY_1)
            Keys.API_KEY_2 -> keyManager.storeApiKey(apiKey, Keys.API_KEY_2)
            Keys.API_KEY_3 -> keyManager.storeApiKey(apiKey, Keys.API_KEY_3)
        ***REMOVED***
    ***REMOVED***


    fun getApiKey(key: Keys): String? {
        return when(key){
            Keys.API_KEY_1 -> keyManager.getApiKey( Keys.API_KEY_1)
            Keys.API_KEY_2 -> keyManager.getApiKey( Keys.API_KEY_2)
            Keys.API_KEY_3 -> keyManager.getApiKey( Keys.API_KEY_3)

        ***REMOVED***
    ***REMOVED***


    fun keyExists(key: Keys): Boolean {
        return when(key){
            Keys.API_KEY_1 -> keyManager.getApiKey( Keys.API_KEY_1) != null
            Keys.API_KEY_2 -> keyManager.getApiKey( Keys.API_KEY_2) != null
            Keys.API_KEY_3 -> keyManager.getApiKey( Keys.API_KEY_3) != null
        ***REMOVED***
    ***REMOVED***
***REMOVED***

enum class Keys{
    API_KEY_1,
    API_KEY_2,
    API_KEY_3
***REMOVED***

