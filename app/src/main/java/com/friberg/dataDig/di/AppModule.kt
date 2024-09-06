package com.friberg.dataDig.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.friberg.dataDig.repositories.KeyManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

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

    /**
     * Provide encrypted shared preferences for the app, using the MasterKey
     *
     * @param context The application context
     * @return The encrypted shared preferences
     */
    @Provides
    @Singleton
    fun provideEncryptedSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        val masterKeyAlias =
            MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

        return EncryptedSharedPreferences.create(
            context,
            "encrypted_shared_prefs",
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    ***REMOVED***

    @Provides
    @Singleton
    fun provideKeyManager(encryptedSharedPrefs: SharedPreferences): KeyManager {
        return KeyManager(encryptedSharedPrefs)
    ***REMOVED***

    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(
        SupervisorJob() + Dispatchers.IO
    )

***REMOVED***

