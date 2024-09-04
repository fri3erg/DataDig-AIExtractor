package com.example.tesifrigo.services

import android.graphics.Bitmap
import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

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
***REMOVED***
