package com.friberg.dataDig.fileCreator

import android.content.Context
import android.net.Uri
import com.friberg.dataDig.models.Extraction
import com.friberg.dataDig.utils.encodeImageToBase64
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Text file creator
 *
 * @constructor Create empty Text creator
 */
class TextCreator {

    /**
     * Convert to text file from extraction
     *
     * @param extraction The extraction to convert
     * @param context The context for the file creation
     * @return The uri of the created file
     */
    fun convertToTextFile(extraction: Extraction, context: Context): Uri? {
        // Create a filename based on the timestamp and template title
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ITALY).format(Date())
        val templateTitle = extraction.template?.title ?: "Untitled"
        val filename = "${templateTitle***REMOVED***-$timestamp.txt"

        val outputDirectory = context.filesDir
        val outputFile = File(outputDirectory, filename)

        return try {
            // use a FileWriter to write the text to the file
            FileWriter(outputFile).use { writer ->
                // Extraction Title
                writer.write("Extraction Title: ${extraction.title***REMOVED***\n\n")

                // Extracted Fields
                if (extraction.extractedFields.isNotEmpty()) {
                    writer.write("Extracted Fields:\n")
                    for (field in extraction.extractedFields) {
                        writer.write("  ${field.templateField?.title***REMOVED***: ${field.value***REMOVED***\n")
                    ***REMOVED***
                    writer.write("\n")
                ***REMOVED***

                // Extracted Tables
                if (extraction.extractedTables.isNotEmpty()) {
                    writer.write("Extracted Tables:\n")
                    for (table in extraction.extractedTables) {
                        writer.write("  Table Title: ${table.title ?: ""***REMOVED***\n")
                        // Column Headers
                        val columnHeaders =
                            table.fields.firstOrNull()?.fields?.mapNotNull { it.templateField?.title ***REMOVED***
                        if (columnHeaders != null) {
                            writer.write("    ${columnHeaders.joinToString(", ")***REMOVED***\n")
                        ***REMOVED***
                        // Data Rows
                        for (row in table.fields) {
                            val rowValues = row.fields.map { it.value ***REMOVED***
                            writer.write("    ${rowValues.joinToString(", ")***REMOVED***\n")
                        ***REMOVED***
                        writer.write("\n")
                    ***REMOVED***
                ***REMOVED***

                // Extraction Costs
                writer.write("Extraction Costs:\n")
                for (cost in extraction.extractionCosts) {
                    writer.write("  ${cost.name***REMOVED***: ${cost.tokens***REMOVED*** tokens, Cost: ${cost.cost***REMOVED*** ${cost.currency***REMOVED***\n")
                ***REMOVED***
                writer.write("\n")

                // Exceptions Occurred
                if (extraction.exceptionsOccurred.isNotEmpty()) {
                    writer.write("Exceptions Occurred:\n")
                    for (exception in extraction.exceptionsOccurred) {
                        writer.write("  Error: ${exception.error***REMOVED***, Type: ${exception.errorType***REMOVED***, Description: ${exception.errorDescription***REMOVED***\n")
                    ***REMOVED***
                ***REMOVED*** else {
                    writer.write("No exceptions occurred.\n")
                ***REMOVED***
                writer.write("extraImages: \n")
                for (image in extraction.extraImages) {
                    writer.write("  ${encodeImageToBase64(context, Uri.parse(image))***REMOVED***\n")
                ***REMOVED***
            ***REMOVED***
            Uri.fromFile(outputFile)
        ***REMOVED*** catch (e: IOException) {
            e.printStackTrace()
            null
        ***REMOVED***
    ***REMOVED***
***REMOVED***