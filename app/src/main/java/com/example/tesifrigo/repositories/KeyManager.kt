package com.example.tesifrigo.repositories

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import com.example.tesifrigo.viewmodels.Keys
import javax.inject.Inject

class KeyManager @Inject constructor(
    private val encryptedSharedPrefs: SharedPreferences
) {
    fun storeApiKey(apiKey: String, number:Keys) {
            encryptedSharedPrefs.edit().putString(number.toString(), apiKey).apply()
    ***REMOVED***




        fun getApiKey(number: Keys): String? {
                return encryptedSharedPrefs.getString(number.toString(), null)
        ***REMOVED***

    ***REMOVED***



