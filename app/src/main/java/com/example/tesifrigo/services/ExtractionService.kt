package com.example.tesifrigo.services

import ImageOCR
import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import android.net.ProxyInfo
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.tesifrigo.R
import com.example.tesifrigo.models.ExceptionOccurred
import com.example.tesifrigo.models.Extraction
import com.example.tesifrigo.models.ExtractionField
import com.example.tesifrigo.models.ExtractionTable
import com.example.tesifrigo.models.ExtractionTableRow
import com.example.tesifrigo.models.Options
import com.example.tesifrigo.models.Template
import com.example.tesifrigo.repositories.KeyManager
import com.example.tesifrigo.repositories.ServiceRepository
import com.example.tesifrigo.viewmodels.Keys
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import io.realm.kotlin.types.RealmList
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@AndroidEntryPoint
class ExtractionService : Service(){



    @Inject
    lateinit var serviceRepository: ServiceRepository

    @Inject lateinit var keyManager: KeyManager


    @Inject
    lateinit var apiKeyManager: KeyManager
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)


    override fun onBind(intent: Intent?): IBinder? {
        Log.d("TestService", "Service bound")
        return null // We'll use a started service, not a bound service
    ***REMOVED***

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            Actions.STOP.toString() -> stopSelf()
            Actions.START.toString() -> start(intent)
        ***REMOVED***
        return START_NOT_STICKY // Service won't restart automatically if stopped
    ***REMOVED***
    private fun start(intent: Intent?) {
        startForeground(1,createNotification())

        Log.d("TestService", "Service started")
        val imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra("imageUri", Uri::class.java)
        ***REMOVED*** else {
            @Suppress("DEPRECATION")
            intent?.getParcelableExtra<Uri>("imageUri")
        ***REMOVED***
        if (imageUri != null) {
            val template = serviceRepository.getTemplate() ?: return
            val options = serviceRepository.getOptions()
            // Process the image using a Coroutine
            CoroutineScope(Dispatchers.IO).launch {
                processImage(listOf(imageUri), serviceRepository, keyManager, template, options)
            ***REMOVED***
        ***REMOVED***

    ***REMOVED***

    private fun updateProgress(progress: Float) {
        serviceRepository.updateProgress(progress)
    ***REMOVED***


    private fun createNotification(): Notification {
    return NotificationCompat.Builder(this,"extracting_data")
        .setContentTitle("Service")
        .setContentText("Service is running")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .build()
    ***REMOVED***

    // Simulate image processing for now
    private fun processImage(imageUris: List<Uri>, serviceRepository: ServiceRepository, keyManager: KeyManager, template: Template, options: Options?) {
        val localContext = this

        val onResult: (PyObject) -> Unit = { result ->
            serviceRepository.updateResult(extractResult(result, template))
        ***REMOVED***

        serviceScope.launch {
            Log.d("TestService", "Processing image: $imageUris")
            Thread.sleep(5000) // Sleep for 5 seconds
            val progressCallback: (Float) -> Unit = { progress ->
                updateProgress(progress)
            ***REMOVED***

            // Find Tesseract data directory (assuming it's in the tessdata directory)

            if (! Python.isStarted()) {
                Python.start(AndroidPlatform(localContext))
            ***REMOVED***
            val py = Python.getInstance()
            val module = py.getModule("main")


            val builtinsModule = py.getModule("builtins")

            val pyListImages = builtinsModule.callAttr("list") // Create an empty Python list

            for (imageUri in imageUris) {
                val bitmap = imageUri.let {
                    contentResolver.openInputStream(it)?.use { inputStream ->
                        BitmapFactory.decodeStream(inputStream)
                    ***REMOVED***
                ***REMOVED***
                val base64Image = bitmap?.let {
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    it.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                    val byteArray = byteArrayOutputStream.toByteArray()
                    Base64.encodeToString(byteArray, Base64.DEFAULT)
                ***REMOVED***
                if (base64Image != null) {
                    pyListImages.callAttr("append", base64Image)  // Add Base64 image to the Python list
                ***REMOVED***
            ***REMOVED***
            val imageUriOcr = ImageOCR()
            val pyListText = builtinsModule.callAttr("list") // Create an empty Python list
            imageUris.map { imageUri ->
                val bitmap = MediaStore.Images.Media.getBitmap(localContext.contentResolver, imageUri)
                pyListText.callAttr("append",imageUriOcr.extractTextFromBitmap(bitmap) )  // Add Base64 image to the Python list
            ***REMOVED***

            val classesOptionModule = py.getModule("extractor.classes.Options") // Get the module


            val getApiKey = { key: String ->
                    when(key){
                        "API_KEY_1" -> keyManager.getApiKey(Keys.API_KEY_1)
                        "API_KEY_2" -> keyManager.getApiKey(Keys.API_KEY_2)
                        "API_KEY_3" -> keyManager.getApiKey(Keys.API_KEY_3)
                        else -> null
                    ***REMOVED***
            ***REMOVED***


// Create a map of keyword arguments
            val chosenOption = classesOptionModule.callAttr(
                "Options",  // Directly call the constructor
                PyObject.fromJava("gpt-4"),
                PyObject.fromJava("eng"),
                PyObject.fromJava(false),
                PyObject.fromJava(getApiKey)
***REMOVED***

            val classesModule = py.getModule("extractor.classes.Template") // Get the module

// Create Python TemplateField objects
            val pyTemplateFields = template.fields.map { realmField ->
                classesModule.callAttr(
                    "TemplateField",
                    PyObject.fromJava(realmField.title),
                    PyObject.fromJava(realmField.description),
                    PyObject.fromJava(realmField.extraDescription),
                    PyObject.fromJava(realmField.type),
                    PyObject.fromJava(realmField.required),
                    PyObject.fromJava(realmField.tags.toList()),
                    PyObject.fromJava(realmField.intelligent_extraction)
    ***REMOVED***
            ***REMOVED***

// Create Python TemplateTable objects (with their fields)
            val pyTemplateTables = template.tables.map { realmTable ->
                val pyTableRows = realmTable.rows.map { tableField ->
                    classesModule.callAttr(
                        "TemplateField",
                        PyObject.fromJava(tableField.title),
                        PyObject.fromJava(tableField.description),
                        PyObject.fromJava(tableField.extraDescription),
                        PyObject.fromJava(tableField.type),
                        PyObject.fromJava(tableField.required),
                        PyObject.fromJava(tableField.tags.toList()),
                        PyObject.fromJava(tableField.intelligent_extraction)
        ***REMOVED***
                ***REMOVED***
                val pyTableColumns = realmTable.columns.map { tableField ->
                    classesModule.callAttr(
                        "TemplateField",
                        PyObject.fromJava(tableField.title),
                        PyObject.fromJava(tableField.description),
                        PyObject.fromJava(tableField.extraDescription),
                        PyObject.fromJava(tableField.type),
                        PyObject.fromJava(tableField.required),
                        PyObject.fromJava(tableField.tags.toList()),
                        PyObject.fromJava(tableField.intelligent_extraction)
        ***REMOVED***
                ***REMOVED***

                classesModule.callAttr(
                    "TemplateTable",
                    PyObject.fromJava(realmTable.title),
                    PyObject.fromJava(realmTable.keywords.toList()),
                    PyObject.fromJava(realmTable.description),
                    pyTableRows,
                    pyTableColumns
    ***REMOVED***
            ***REMOVED***

// Create the Python Template object
            val pythonTemplate = classesModule.callAttr(
                "Template",
                PyObject.fromJava(template.title),
                PyObject.fromJava(template.description),
                pyTemplateFields, // Pass the created Python TemplateField list
                pyTemplateTables, // Pass the created Python TemplateTable list
                PyObject.fromJava(template.tags.toList())
***REMOVED***



            val pyResult =module.callAttr("main_kotlin", pyListImages,pyListText, pythonTemplate, progressCallback, chosenOption)

            launch(Dispatchers.Main) {        // Handle 'pyResult' on the main thread for UI updates if needed

                onResult(pyResult)
                progressCallback(1f)

                // Stop the service only after processing the result
                stopSelf()
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***



    enum class Actions {
        START,
        STOP
    ***REMOVED***


***REMOVED***

private fun copyAssetsToStorage(context: Context, assetPath: String, targetDirectoryPath: String) {
    val assetManager = context.assets

    assetManager.list(assetPath)?.forEach { fileName ->
        val inputStream = assetManager.open("$assetPath/$fileName")
        val targetFile = File(targetDirectoryPath, fileName)

        targetFile.parentFile?.mkdirs()
        FileOutputStream(targetFile).use { outputStream ->
            inputStream.copyTo(outputStream)
        ***REMOVED***
    ***REMOVED***
***REMOVED***

private fun extractResult(pyResult:PyObject, template: Template): Extraction{
    val extracted = Extraction().apply {
        image = pyResult.get("image").toString()
        format = pyResult.get("format").toString()
        pyResult.get("tags")?.asList()?.let { tags.addAll(it.map { it.toString() ***REMOVED***) ***REMOVED***
        extractionCosts = pyResult.get("extraction_costs").toString() // Adjust conversion as needed
        pyResult.get("exceptions_occurred")?.asList()?.let {
            exceptionsOccurred.addAll(
                it.map { pyException ->
                    ExceptionOccurred().apply {
                        error = pyException.get("error").toString() // Assuming error is a string
                        errorType = pyException.get("error_type").toString()
                        errorDescription = pyException.get("error_description").toString()
                    ***REMOVED***
                ***REMOVED***
***REMOVED***
        ***REMOVED***
        pyResult.get("extracted_fields")?.asList()?.let {
            extractedFields.addAll(
                it.map { pyExtractedField ->
                    ExtractionField().apply {
                        templateField = template.fields.first { it.id.toHexString() == pyExtractedField.get("template_field")
                            ?.get("id")
                            .toString() ***REMOVED***
                        value = pyExtractedField.get("value").toString()
                    ***REMOVED***
                ***REMOVED***
***REMOVED***
        ***REMOVED***
        extractedTables = pyResult.get("extracted_tables")?.asList()?.map { pyExtractedTable ->
            extractTable(pyExtractedTable, template)
        ***REMOVED*** as RealmList<ExtractionTable>

    ***REMOVED***
    return extracted
***REMOVED***


private fun extractTable(pyExtractedTable: PyObject, template: Template): ExtractionTable {


    val realmExtractionTable = ExtractionTable().apply {
        templateTable = template.tables.first { it.id.toHexString() == pyExtractedTable.get("template_table")
            ?.get("id")
            .toString() ***REMOVED***
        dataframe= pyExtractedTable.get("dataframe").toString()
        // Handle fields (nested dictionaries)
        val rows = mutableListOf<ExtractionTableRow>()
        val pyFields = pyExtractedTable.get("fields")?.asMap()
        if (pyFields != null) {
            for ((rowIndex, rowData) in pyFields) {
                val fields = rowData.values.map { extractedField: PyObject ->
                    ExtractionField().apply {
                        templateTable
                        value = extractedField.get("value").toString()
                    ***REMOVED***
                ***REMOVED***
                rows.add(ExtractionTableRow().apply {
                    this.rowIndex = rowIndex.toString()
                    this.fields.addAll(fields)
                ***REMOVED***)
            ***REMOVED***
            this.fields.addAll(rows)
        ***REMOVED***
    ***REMOVED***
    return realmExtractionTable
***REMOVED***
