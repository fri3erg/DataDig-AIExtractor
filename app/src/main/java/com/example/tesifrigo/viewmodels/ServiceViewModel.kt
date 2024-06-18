package com.example.tesifrigo.viewmodels

import android.app.Application
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.tesifrigo.models.Extracted
import com.example.tesifrigo.repositories.ServiceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor( progressRepository: ServiceRepository
) : ViewModel(){
    val progress: StateFlow<Float> = progressRepository.progress
    val result: StateFlow<Extracted?> = progressRepository.result
***REMOVED***

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Provides
    fun provideViewModelFactory(
        application: Application, // Pass the application here
        savedStateRegistryOwner: SavedStateRegistryOwner // Pass a SavedStateRegistryOwner
    ): ViewModelProvider.Factory {
        return SavedStateViewModelFactory(application, savedStateRegistryOwner)
    ***REMOVED***
***REMOVED***
