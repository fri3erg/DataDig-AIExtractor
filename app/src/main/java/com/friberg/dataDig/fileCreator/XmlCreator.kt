package com.friberg.dataDig.fileCreator

import android.content.Context
import android.net.Uri
import android.util.Xml
import com.friberg.dataDig.models.Extraction
import com.friberg.dataDig.utils.encodeImageToBase64
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Xml creator
 *
 * @constructor Create empty Xml creator
 */
class XmlCreator {
    /**
     * Convert to xml file
     *
     * @param extraction The extraction to convert
     * @param context The context for the file creation
     * @return The uri of the created file
     */
    fun convertToXmlFile(extraction: Extraction, context: Context): Uri? {
        // Create a filename based on the timestamp and template title
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ITALY).format(Date())
        val templateTitle = extraction.template?.title ?: "Untitled"
        val filename = "${templateTitle***REMOVED***-$timestamp.xml"

        val outputDirectory = context.filesDir
        val outputFile = File(outputDirectory, filename)
        return try {
            // use a FileWriter to write the text to the file
            FileWriter(outputFile).use { writer ->
                val serializer = Xml.newSerializer()
                serializer.setOutput(writer)
                serializer.startDocument("UTF-8", true)
                serializer.setFeature(
                    "http://xmlpull.org/v1/doc/features.html#indent-output", true
    ***REMOVED*** // Enable indentation

                // Start 'extraction' tag
                serializer.startTag(null, "extraction")
                serializer.text("\n")

                // Serialize Extraction attributes
                serializer.startTag(null, "title")
                serializer.text(extraction.title)
                serializer.endTag(null, "title")
                serializer.text("\n")

                // Serialize extractionCosts
                serializer.startTag(null, "extractionCosts")
                serializer.text("\n")
                for (cost in extraction.extractionCosts) {
                    serializer.startTag(null, "cost")
                    serializer.text("\n")

                    serializer.startTag(null, "name")
                    serializer.text(cost.name)
                    serializer.endTag(null, "name")
                    serializer.text("\n")

                    serializer.startTag(null, "tokens")
                    serializer.text(cost.tokens.toString())
                    serializer.endTag(null, "tokens")
                    serializer.text("\n")

                    serializer.startTag(null, "costValue")
                    serializer.text(cost.cost.toString())
                    serializer.endTag(null, "costValue")
                    serializer.text("\n")

                    serializer.startTag(null, "currency")
                    serializer.text(cost.currency)
                    serializer.endTag(null, "currency")
                    serializer.text("\n")

                    serializer.endTag(null, "cost")
                    serializer.text("\n")
                ***REMOVED***
                serializer.endTag(null, "extractionCosts")
                serializer.text("\n")

                // Serialize exceptionsOccurred
                serializer.startTag(null, "exceptionsOccurred")
                serializer.text("\n")
                for (exception in extraction.exceptionsOccurred) {
                    serializer.startTag(null, "exception")
                    serializer.text("\n")

                    serializer.startTag(null, "error")
                    serializer.text(exception.error)
                    serializer.endTag(null, "error")
                    serializer.text("\n")

                    serializer.startTag(null, "errorType")
                    serializer.text(exception.errorType)
                    serializer.endTag(null, "errorType")
                    serializer.text("\n")

                    serializer.startTag(null, "errorDescription")
                    serializer.text(exception.errorDescription)
                    serializer.endTag(null, "errorDescription")
                    serializer.text("\n")

                    serializer.endTag(null, "exception")
                    serializer.text("\n")
                ***REMOVED***
                serializer.endTag(null, "exceptionsOccurred")
                serializer.text("\n")

                // Serialize language and model
                serializer.startTag(null, "language")
                serializer.text(extraction.language ?: "")
                serializer.endTag(null, "language")
                serializer.text("\n")

                serializer.startTag(null, "model")
                serializer.text(extraction.model ?: "")
                serializer.endTag(null, "model")
                serializer.text("\n")


                // Serialize extractedFields
                serializer.startTag(null, "extractedFields")
                serializer.text("\n")
                for (field in extraction.extractedFields) {
                    serializer.startTag(null, "field")
                    serializer.text("\n")

                    serializer.startTag(null, "template_field_title")
                    serializer.text(field.templateField?.title ?: "")
                    serializer.endTag(null, "template_field_title")
                    serializer.text("\n")

                    serializer.startTag(null, "value")
                    serializer.text(field.value)
                    serializer.endTag(null, "value")
                    serializer.text("\n")

                    serializer.endTag(null, "field")
                    serializer.text("\n")
                ***REMOVED***
                serializer.endTag(null, "extractedFields")
                serializer.text("\n")

                // Serialize extractedTables
                serializer.startTag(null, "extractedTables")
                serializer.text("\n")
                for (table in extraction.extractedTables) {
                    serializer.startTag(null, "table")
                    serializer.text("\n")

                    serializer.startTag(null, "title")
                    serializer.text(table.title ?: "")
                    serializer.endTag(null, "title")
                    serializer.text("\n")

                    serializer.startTag(null, "template_table_title")
                    serializer.text(table.templateTable?.title ?: "")
                    serializer.endTag(null, "template_table_title")
                    serializer.text("\n")

                    serializer.startTag(null, "dataframe")
                    serializer.text(table.dataframe)
                    serializer.endTag(null, "dataframe")
                    serializer.text("\n")

                    // Serialize table fields (rows and columns)
                    serializer.startTag(null, "fields")
                    serializer.text("\n")
                    for (row in table.fields) {
                        serializer.startTag(null, "row")
                        serializer.text("\n")

                        serializer.startTag(null, "rowName")
                        serializer.text(row.rowName)
                        serializer.endTag(null, "rowName")
                        serializer.text("\n")

                        serializer.startTag(null, "rowFields")
                        serializer.text("\n")
                        for (field in row.fields) {
                            serializer.startTag(null, "field")
                            serializer.text("\n")

                            serializer.startTag(null, "template_field_title")
                            serializer.text(field.templateField?.title ?: "")
                            serializer.endTag(null, "template_field_title")
                            serializer.text("\n")

                            serializer.startTag(null, "value")
                            serializer.text(field.value)
                            serializer.endTag(null, "value")
                            serializer.text("\n")

                            serializer.endTag(null, "field")
                            serializer.text("\n")
                        ***REMOVED***
                        serializer.endTag(null, "rowFields")
                        serializer.text("\n")

                        serializer.endTag(null, "row")
                        serializer.text("\n")
                    ***REMOVED***
                    serializer.endTag(null, "fields")
                    serializer.text("\n")

                    serializer.endTag(null, "table")
                    serializer.text("\n")
                ***REMOVED***
                serializer.endTag(null, "extractedTables")
                serializer.text("\n")

                serializer.startTag(null, "extraImages")
                serializer.text("\n")
                for (image in extraction.extraImages) {
                    serializer.startTag(null, "image")
                    serializer.text("\n")
                    encodeImageToBase64(context, Uri.parse(image))?.let {
                        serializer.text(it)
                    ***REMOVED***
                    serializer.text("\n")

                    serializer.endTag(null, "image")
                    serializer.text("\n")
                ***REMOVED***
                serializer.endTag(null, "extraImages")

                // End 'extraction' tag
                serializer.endTag(null, "extraction")
                serializer.endDocument()
            ***REMOVED***
            Uri.fromFile(outputFile)
        ***REMOVED*** catch (e: IOException) {
            e.printStackTrace()
            null
        ***REMOVED***
    ***REMOVED***

***REMOVED***
