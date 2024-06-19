package com.example.tesifrigo.repositories

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import javax.inject.Inject

class KeyManager @Inject constructor(
    private val encryptedSharedPrefs: SharedPreferences
) {
    fun storeApiKey1(apiKey: String) {
        encryptedSharedPrefs.edit().putString("api_key_1", apiKey).apply()
    ***REMOVED***

    fun storeApiKey2(apiKey: String) {
        encryptedSharedPrefs.edit().putString("api_key_2", apiKey).apply()
    ***REMOVED***

    fun getApiKey1(): String? {
        return encryptedSharedPrefs.getString("api_key_1", null)
    ***REMOVED***

    fun getApiKey2(): String? {
        return encryptedSharedPrefs.getString("api_key_2", null)
    ***REMOVED***
***REMOVED***

