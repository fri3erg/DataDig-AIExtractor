package com.example.tesifrigo.fileCreator

import android.content.Context
import android.net.Uri
import com.example.tesifrigo.models.Extraction
import com.google.gson.*
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.lang.reflect.Type
import java.lang.reflect.ParameterizedType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class JsonCreator {


    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(RealmList::class.java, RealmListAdapter()) // Generic Adapter
        .excludeFieldsWithoutExposeAnnotation() // Exclude fields without @Expose
        .setPrettyPrinting()
        .create()


    fun convertToJsonFile(extraction: Extraction, context: Context): Uri? {
        // Create a filename based on the timestamp and template title
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ITALY).format(Date())
        val templateTitle = extraction.template?.title ?: "Untitled"
        val filename = "$templateTitle-$timestamp.json"

        // Get the output directory (replace with actual directory if needed)
        val outputDirectory = context.filesDir
        val outputFile = File(outputDirectory, filename)


        // Convert the modified Extraction object to JSON and save to the file
        return try {
            val json = gson.toJson(extraction)
            FileWriter(outputFile).use { writer ->
                writer.write(json)
            ***REMOVED***
            // Return the URI of the created file
            Uri.fromFile(outputFile)
        ***REMOVED*** catch (e: IOException) {
            // Handle the exception (log, throw, etc.)
            e.printStackTrace() // Placeholder for error handling
            null
        ***REMOVED***
    ***REMOVED***

    // Adapter for RealmList
    class RealmListAdapter : JsonSerializer<RealmList<*>>, JsonDeserializer<RealmList<*>> {

        override fun serialize(
            src: RealmList<*>,
            typeOfSrc: Type,
            context: JsonSerializationContext
        ): JsonElement {
            return context.serialize(src.toList())
        ***REMOVED***

        override fun deserialize(
            json: JsonElement,
            typeOfT: Type,
            context: JsonDeserializationContext
        ): RealmList<*> {
            // Get the parameterized type of the RealmList
            val parameterizedType = typeOfT as? ParameterizedType
            val itemType = parameterizedType?.actualTypeArguments?.get(0) ?: Any::class.java

            val list = context.deserialize<List<*>>(json, itemType)
            return realmListOf(*list.toTypedArray())
        ***REMOVED***
    ***REMOVED***
***REMOVED***
