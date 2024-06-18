package com.example.tesifrigo.repositories

import com.example.tesifrigo.models.Extracted
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

    private val _result = MutableStateFlow<Extracted?>(null)
    val result: StateFlow<Extracted?> = _result.asStateFlow()

    private val _progressChannel = Channel<Float>()
    val progressChannel: Flow<Float> = _progressChannel.receiveAsFlow()

    fun updateProgress(newProgress: Float) {
        _progress.value = newProgress
        _progressChannel.trySend(newProgress) // Send to Channel for UI update
    ***REMOVED***

    fun updateResult(newResult: Extracted) {
        _result.value = newResult
    ***REMOVED***
***REMOVED***
