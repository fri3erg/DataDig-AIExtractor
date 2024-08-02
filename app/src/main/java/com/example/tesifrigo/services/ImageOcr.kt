package com.example.tesifrigo.services

import android.graphics.Bitmap
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class ImageOCR {
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

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
            stringBuilder.toString()
        ***REMOVED*** catch (e: Exception) {
            "Failed to extract text from image: ${e.message***REMOVED***" // Handle errors
        ***REMOVED***
    ***REMOVED***
***REMOVED***
