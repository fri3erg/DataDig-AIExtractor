package com.example.tesifrigo.fileCreator

import android.content.Context
import android.net.Uri
import com.example.tesifrigo.models.Extraction
import com.example.tesifrigo.utils.encodeImageToBase64
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CsvCreator {
    fun convertToCsvFile(extraction: Extraction, context: Context): Uri? {
        // Create a filename based on the timestamp and template title
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ITALY).format(Date())
        val templateTitle = extraction.template?.title ?: "Untitled"
        val filename = "${templateTitle***REMOVED***-$timestamp.csv"

        val outputDirectory = context.filesDir
        val outputFile = File(outputDirectory, filename)

        return try {
            FileWriter(outputFile).use { writer ->
                writer.write("type,title,extraction_costs,exception_occurred_error,exception_occurred_type,exception_occurred_description,template_field_title,value,row_index,column_index\n")

                // Write Extraction row
                val extractionRow = listOf(
                    "Extraction",
                    extraction.title,
                    extraction.extractionCosts,
                    extraction.exceptionsOccurred.firstOrNull()?.error,
                    extraction.exceptionsOccurred.firstOrNull()?.errorType,
                    extraction.exceptionsOccurred.firstOrNull()?.errorDescription,
                    "", "", "", "", // Empty fields for non-Extraction attributes
                    extraction.extraImages.joinToString("|") { encodeImageToBase64(context, Uri.parse(it)) ?: "" ***REMOVED*** // Add extraImagesBase64

    ***REMOVED***.joinToString(",")
                writer.write("$extractionRow\n")

                // Write ExtractionField rows
                for (field in extraction.extractedFields) {
                    val fieldRow = listOf(
                        "extracted_field",
                        "", "", "", "", // Empty fields for non-field attributes
                        field.templateField?.title,
                        field.value, "", ""

        ***REMOVED***.joinToString(",")
                    writer.write("$fieldRow\n")
                ***REMOVED***

                // Write ExtractionTable rows
                for (table in extraction.extractedTables) {
                    for (i in table.fields.indices) {
                        for (j in table.fields[i].fields.indices) {
                            val tableRow = listOf(
                                "extracted_table",
                                "", "", "", "", // Empty fields for non-table attributes
                                table.fields[i].fields[j].templateField?.title,
                                table.fields[i].fields[j].value,
                                i.toString(),
                                j.toString()
                ***REMOVED***.joinToString(",")
                            writer.write("$tableRow\n")
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
            Uri.fromFile(outputFile)
        ***REMOVED*** catch (e: IOException) {
            e.printStackTrace()
            null
        ***REMOVED***
    ***REMOVED***
***REMOVED***