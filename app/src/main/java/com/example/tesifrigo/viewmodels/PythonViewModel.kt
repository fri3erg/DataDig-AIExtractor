package com.example.tesifrigo.viewmodels

import android.content.SharedPreferences
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class PythonViewModel(private val sharedPreferences: SharedPreferences): ViewModel() {
    val progressState = mutableIntStateOf(sharedPreferences.getInt("progress", 0))

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener { _, key ->
            if (key == "progress") {
                progressState.intValue = sharedPreferences.getInt("progress", 0)
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    override fun onCleared() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener { _, _ -> ***REMOVED***
        super.onCleared()
    ***REMOVED***

***REMOVED***