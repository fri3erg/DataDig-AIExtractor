package com.example.tesifrigo.fileCreator

import android.content.Context
import android.net.Uri
import com.example.tesifrigo.models.Extraction
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

            // Get the output directory
            val outputDirectory = context.filesDir
            val outputFile = File(outputDirectory, filename)

            return try {
                FileWriter(outputFile).use { writer ->
                    // Write CSV header (field names)
                    writer.write("extractedFields,extractedTables,extractionCosts,exceptionsOccurred,template\n")

                    // Write data rows
                    val extractedFieldsCsv = extraction.extractedFields.joinToString(",") { it.templateField?.title ?: "" ***REMOVED***
                    val extractedTablesCsv = extraction.extractedTables.joinToString(",") { it.templateTable?.title ?: "" ***REMOVED***
                    val exceptionsOccurredCsv = extraction.exceptionsOccurred.joinToString(",") { "${it.error***REMOVED***-${it.errorType***REMOVED***" ***REMOVED***
                    val templateTitleCsv = extraction.template?.title ?: ""

                    writer.write("$extractedFieldsCsv,$extractedTablesCsv,${extraction.extractionCosts***REMOVED***,$exceptionsOccurredCsv,$templateTitleCsv\n")
                ***REMOVED***
                Uri.fromFile(outputFile)
            ***REMOVED*** catch (e: IOException) {
                e.printStackTrace()
                null
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***