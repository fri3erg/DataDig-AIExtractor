package com.friberg.dataDig.fileCreator

import android.content.Context
import android.net.Uri
import com.friberg.dataDig.models.Extraction
import com.friberg.dataDig.utils.encodeImageToBase64
import com.google.gson.*
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


/**
 * Json creator
 *
 * @constructor Create empty Json creator
 */
class JsonCreator {


    private val gson: Gson =
        GsonBuilder().excludeFieldsWithoutExposeAnnotation() // Exclude fields without @Expose
            .setPrettyPrinting().create()


    /**
     * Convert to json file
     *
     * @param extraction The extraction to convert
     * @param context The context for the file creation
     * @return The uri of the created file
     */
    fun convertToJsonFile(extraction: Extraction, context: Context): Uri? {
        // Create a filename based on the timestamp and template title
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ITALY).format(Date())
        val templateTitle = extraction.template?.title ?: "Untitled"
        val filename = "$templateTitle-$timestamp.json"

        val outputDirectory = context.filesDir
        val outputFile = File(outputDirectory, filename)


        return try {
            val directory = outputFile.parentFile
            if (directory != null && !directory.exists()) {
                directory.mkdirs()
            ***REMOVED***

            // Write the JSON to the file
            val json = gson.toJson(extraction)

            val jsonObject = JsonParser.parseString(json).asJsonObject
            // Add extraImagesBase64 to the JSON
            val extraImagesBase64 = extraction.extraImages.mapNotNull {
                encodeImageToBase64(context, Uri.parse(it))
            ***REMOVED***
            // Add extraImagesBase64 to the JSON
            jsonObject.add("extraImages", JsonArray().apply {
                extraImagesBase64.forEach { add(it) ***REMOVED***
            ***REMOVED***)


            FileWriter(outputFile).use { writer ->
                writer.write(jsonObject.toString())
            ***REMOVED***

            return Uri.fromFile(outputFile)
        ***REMOVED*** catch (e: IOException) {
            e.printStackTrace()
            null
        ***REMOVED***
    ***REMOVED***

***REMOVED***