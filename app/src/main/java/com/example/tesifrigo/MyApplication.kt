package com.example.tesifrigo  // Update with your actual package name

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.tesifrigo.models.ExceptionOccurred
import com.example.tesifrigo.models.Extraction
import com.example.tesifrigo.models.ExtractionCosts
import com.example.tesifrigo.models.ExtractionField
import com.example.tesifrigo.models.ExtractionTable
import com.example.tesifrigo.models.ExtractionTableRow
import com.example.tesifrigo.models.TemplateField
import com.example.tesifrigo.models.Template
import com.example.tesifrigo.models.TemplateTable
import dagger.hilt.android.HiltAndroidApp
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class AppLifecycleObserver : LifecycleEventObserver {
    var isInForeground = false
        private set

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> {
                isInForeground = true
            ***REMOVED***

            Lifecycle.Event.ON_STOP -> {
                isInForeground = false
            ***REMOVED***

            else -> {***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***

/**
 * Main application class
 *
 * @constructor Create empty My app
 */
@HiltAndroidApp
class MyApp : Application() {
    lateinit var lifecycleObserver: AppLifecycleObserver

    companion object { // Attach the Realm instance to the application class
        lateinit var realm: Realm
    ***REMOVED***


    override fun onCreate() {
        super.onCreate()
        lifecycleObserver = AppLifecycleObserver()
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleObserver)

        val configuration = RealmConfiguration.Builder( //realm configuration
            schema = setOf(
                Extraction::class,
                ExtractionField::class,
                ExtractionTable::class,
                ExtractionTableRow::class,
                Template::class,
                TemplateField::class,
                TemplateTable::class,
                ExceptionOccurred::class,
                ExtractionCosts::class
***REMOVED***
        ).schemaVersion(16).build()

        realm = Realm.open(
            configuration
        )


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "extracting_data", "Extracting Data", NotificationManager.IMPORTANCE_HIGH
***REMOVED***
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        ***REMOVED***
    ***REMOVED***


***REMOVED***


