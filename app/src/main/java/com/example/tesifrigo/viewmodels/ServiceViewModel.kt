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
import com.example.tesifrigo.models.Extracted
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
    progressRepository: ServiceRepository,
    private val keyManager: KeyManager
) : ViewModel(){
    val progress: StateFlow<Float> = progressRepository.progress
    val result: StateFlow<Extracted?> = progressRepository.result


    fun storeApiKey(key: Keys, apiKey: String) {
        when(key){
            Keys.API_KEY_1 -> keyManager.storeApiKey1(apiKey)
            Keys.API_KEY_2 -> keyManager.storeApiKey2(apiKey)
        ***REMOVED***
    ***REMOVED***


    fun getApiKey(key: Keys): String? {
        return when(key){
            Keys.API_KEY_1 -> keyManager.getApiKey1()
            Keys.API_KEY_2 -> keyManager.getApiKey2()
        ***REMOVED***
    ***REMOVED***


    fun keyExists(key: Keys): Boolean {
        return when(key){
            Keys.API_KEY_1 -> keyManager.getApiKey1() != null
            Keys.API_KEY_2 -> keyManager.getApiKey2() != null
        ***REMOVED***
    ***REMOVED***
***REMOVED***

enum class Keys{
    API_KEY_1,
    API_KEY_2
***REMOVED***

