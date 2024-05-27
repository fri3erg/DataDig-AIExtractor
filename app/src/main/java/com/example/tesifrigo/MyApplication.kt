package com.example.tesifrigo  // Update with your actual package name

import android.app.Application
import com.chaquo.python.android.PyApplication
import com.example.tesifrigo.model.Extraction
import com.example.tesifrigo.model.Field
import com.example.tesifrigo.model.Template
import dagger.hilt.android.HiltAndroidApp
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration


class MyApplication : PyApplication() {
    // Add any other initialization code you might need here
***REMOVED***


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
                Template::class,
                Field::class,
***REMOVED***
        ).schemaVersion(2).build()

        realm = Realm.open(
        configuration
        )
    ***REMOVED***

***REMOVED***