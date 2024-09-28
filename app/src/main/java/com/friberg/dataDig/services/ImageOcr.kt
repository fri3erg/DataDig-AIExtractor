package com.friberg.dataDig.services

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import java.io.File
import java.io.FileOutputStream

/**
 *  Extract text from image using OCR
 *
 * @constructor Create empty Image OCR
 */
class ImageOCR {
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    /**
     * Extract text from bitmap using TextRecognition
     *
     * @param bitmap The bitmap to extract text from
     * @return
     */
    fun extractTextFromBitmap(bitmap: Bitmap): String {
        val image = InputImage.fromBitmap(bitmap, 0)
        return try {
            val result = Tasks.await(recognizer.process(image)) // Await task completion
            val stringBuilder = StringBuilder()
            for (block in result.textBlocks) {
                for (line in block.lines) {
                    stringBuilder.append(line.text).append("\n")
                ***REMOVED***
            ***REMOVED***
            Log.d("OCR", "Extracted text: $stringBuilder")
            stringBuilder.toString()
        ***REMOVED*** catch (e: Exception) {
            "Failed to extract text from image: ${e.message***REMOVED***" // Handle errors
        ***REMOVED***
    ***REMOVED***


    fun extractTextFromPdf(context: Context, pdfUri: Uri, pageNumber: Int): String {
        PDFBoxResourceLoader.init(context)

        var extractedText: String

        // Copy PDF from Uri to a file, because PdfRenderer needs a FileDescriptor
        val file = File(context.cacheDir, "temp_pdf.pdf")
        context.contentResolver.openInputStream(pdfUri)?.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream)
            ***REMOVED***
        ***REMOVED***

        // Load the document using PdfBox
        val document = PDDocument.load(file)

        document.use { doc ->
            val stripper = PDFTextStripper()
            stripper.startPage = pageNumber + 1 // pageNumber is zero-based
            stripper.endPage = pageNumber + 1
            extractedText = stripper.getText(doc)
        ***REMOVED***

        return extractedText
    ***REMOVED***
***REMOVED***
