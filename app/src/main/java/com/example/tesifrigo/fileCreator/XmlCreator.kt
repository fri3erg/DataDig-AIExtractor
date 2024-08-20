package com.example.tesifrigo.fileCreator

import android.content.Context
import android.net.Uri
import android.util.Xml
import com.example.tesifrigo.models.Extraction
import org.xmlpull.v1.XmlSerializer
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class XmlCreator {
    fun convertToXmlFile(extraction: Extraction, context: Context): Uri? {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ITALY).format(Date())
        val templateTitle = extraction.template?.title ?: "Untitled"
        val filename = "${templateTitle***REMOVED***-$timestamp.xml"

        val outputDirectory = context.filesDir
        val outputFile = File(outputDirectory, filename)

        return try {
            FileWriter(outputFile).use { writer ->
                val serializer = Xml.newSerializer()
                serializer.setOutput(writer)
                serializer.startDocument("UTF-8", true)
                serializer.startTag(null, "extraction")

                // Serialize Extraction attributes
                serializer.startTag(null, "title")
                serializer.text(extraction.title)
                serializer.endTag(null, "title")

                serializer.startTag(null, "extractionCosts")
                for (cost in extraction.extractionCosts) {
                    serializer.startTag(null, "cost")
                    serializer.startTag(null, "name")
                    serializer.text(cost.name)
                    serializer.endTag(null, "name")
                    serializer.startTag(null, "tokens")
                    serializer.text(cost.tokens.toString())
                    serializer.endTag(null, "tokens")
                    serializer.startTag(null, "costValue")
                    serializer.text(cost.cost.toString())
                    serializer.endTag(null, "costValue")
                    serializer.startTag(null, "currency")
                    serializer.text(cost.currency)
                    serializer.endTag(null, "currency")
                    serializer.endTag(null, "cost")
                ***REMOVED***
                serializer.endTag(null, "extractionCosts")

                serializer.startTag(null, "exceptionsOccurred")
                for (exception in extraction.exceptionsOccurred) {
                    serializer.startTag(null, "exception")
                    serializer.startTag(null, "error")
                    serializer.text(exception.error)
                    serializer.endTag(null, "error")
                    serializer.startTag(null, "errorType")
                    serializer.text(exception.errorType)
                    serializer.endTag(null, "errorType")
                    serializer.startTag(null, "errorDescription")
                    serializer.text(exception.errorDescription)
                    serializer.endTag(null, "errorDescription")
                    serializer.endTag(null, "exception")
                ***REMOVED***
                serializer.endTag(null, "exceptionsOccurred")

                serializer.startTag(null, "language")
                serializer.text(extraction.language ?: "")
                serializer.endTag(null, "language")

                serializer.startTag(null, "model")
                serializer.text(extraction.model ?: "")
                serializer.endTag(null, "model")


                // Serialize extractedFields
                serializer.startTag(null, "extractedFields")
                for (field in extraction.extractedFields) {
                    serializer.startTag(null, "field")
                    serializer.startTag(null, "template_field_title")
                    serializer.text(field.templateField?.title ?: "")
                    serializer.endTag(null, "template_field_title")
                    serializer.startTag(null, "value")
                    serializer.text(field.value)
                    serializer.endTag(null, "value")
                    serializer.endTag(null, "field")
                ***REMOVED***
                serializer.endTag(null, "extractedFields")

                // Serialize extractedTables
                serializer.startTag(null, "extractedTables")
                for (table in extraction.extractedTables) {
                    serializer.startTag(null, "table")

                    serializer.startTag(null, "title")
                    serializer.text(table.title ?: "")
                    serializer.endTag(null, "title")

                    serializer.startTag(null, "template_table_title")
                    serializer.text(table.templateTable?.title ?: "")
                    serializer.endTag(null, "template_table_title")

                    serializer.startTag(null, "dataframe")
                    serializer.text(table.dataframe)
                    serializer.endTag(null, "dataframe")

                    // Serialize table fields (rows and columns)
                    serializer.startTag(null, "fields")
                    for (row in table.fields) {
                        serializer.startTag(null, "row")
                        serializer.startTag(null, "rowName")
                        serializer.text(row.rowName)
                        serializer.endTag(null, "rowName")

                        serializer.startTag(null, "rowFields")
                        for (field in row.fields) {
                            serializer.startTag(null, "field")
                            serializer.startTag(null, "template_field_title")
                            serializer.text(field.templateField?.title ?: "")
                            serializer.endTag(null, "template_field_title")
                            serializer.startTag(null, "value")
                            serializer.text(field.value)
                            serializer.endTag(null, "value")
                            serializer.endTag(null, "field")
                        ***REMOVED***
                        serializer.endTag(null, "rowFields")

                        serializer.endTag(null, "row")
                    ***REMOVED***
                    serializer.endTag(null, "fields")

                    serializer.endTag(null, "table")
                ***REMOVED***
                serializer.endTag(null, "extractedTables")


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