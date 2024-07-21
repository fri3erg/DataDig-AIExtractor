package com.example.tesifrigo  // Update with your actual package name

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.tesifrigo.models.ExceptionOccurred
import com.example.tesifrigo.models.Extraction
import com.example.tesifrigo.models.ExtractionField
import com.example.tesifrigo.models.ExtractionTable
import com.example.tesifrigo.models.TemplateField
import com.example.tesifrigo.models.Template
import com.example.tesifrigo.models.TemplateTable
import dagger.hilt.android.HiltAndroidApp
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration



@HiltAndroidApp
class MyApp: Application(){
    companion object{
        lateinit var realm: Realm
    ***REMOVED***


    override fun onCreate(){
        super.onCreate()


        val configuration = RealmConfiguration.Builder(
            schema = setOf(
                Extraction::class,
                ExtractionField::class,
                ExtractionTable::class,
                Template::class,
                TemplateField::class,
                TemplateTable::class,
                ExceptionOccurred::class
***REMOVED***
        ).schemaVersion(4).build()

        realm = Realm.open(
        configuration
        )


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel= NotificationChannel(
                "extracting_data",
                "Extracting Data",
                NotificationManager.IMPORTANCE_HIGH
***REMOVED***
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        ***REMOVED***
    ***REMOVED***



***REMOVED***