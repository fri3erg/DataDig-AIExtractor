package com.example.tesifrigo.fileCreator

import android.content.Context
import android.net.Uri
import com.example.tesifrigo.models.Extraction
import com.google.gson.*
import io.realm.kotlin.types.RealmList
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class JsonCreator {


    private val gson: Gson = GsonBuilder()
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
            val directory = outputFile.parentFile
            if (directory != null && !directory.exists()) {
                directory.mkdirs()
            ***REMOVED***

            // Write the JSON to the file
            val json = gson.toJson(extraction)
            FileWriter(outputFile).use { writer ->
                writer.write(json)
            ***REMOVED***

            // Return the URI of the created file
            return Uri.fromFile(outputFile)
        ***REMOVED*** catch (e: IOException) {
            // Handle the exception (log, throw, etc.)
            e.printStackTrace() // Placeholder for error handling
            null
        ***REMOVED***
    ***REMOVED***
***REMOVED***