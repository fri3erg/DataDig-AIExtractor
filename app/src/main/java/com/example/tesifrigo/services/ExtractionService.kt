package com.example.tesifrigo.services

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.IBinder
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
import com.example.tesifrigo.models.ExtractionCosts
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
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@AndroidEntryPoint
class ExtractionService : Service() {


    @Inject
    lateinit var serviceRepository: ServiceRepository

    @Inject
    lateinit var keyManager: KeyManager


    @Inject
    lateinit var apiKeyManager: KeyManager
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)


    override fun onBind(intent: Intent?): IBinder? {
        Log.d("TestService", "Service bound")
        return null // We'll use a started service, not a bound service
    ***REMOVED***

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.STOP.toString() -> stopSelf()
            Actions.START.toString() -> start(intent)
        ***REMOVED***
        return START_NOT_STICKY // Service won't restart automatically if stopped
    ***REMOVED***

    @RequiresApi(Build.VERSION_CODES.P)
    private fun start(intent: Intent?) {
        startForeground(1, createNotification())

        Log.d("TestService", "Service started")
        val imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableArrayListExtra("imageUris", Uri::class.java)
        ***REMOVED*** else {
            @Suppress("DEPRECATION") intent?.getParcelableArrayListExtra("imageUris")
        ***REMOVED***
        if (imageUri != null) {
            val template = serviceRepository.getTemplate() ?: return
            val options = serviceRepository.getOptions()
            // Process the image using a Coroutine
            CoroutineScope(Dispatchers.IO).launch {
                processImage(imageUri, serviceRepository, keyManager, template, options)
            ***REMOVED***
        ***REMOVED***

    ***REMOVED***

    private fun updateProgress(progress: Float) {
        serviceRepository.updateProgress(progress)
    ***REMOVED***


    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, "extracting_data").setContentTitle("Service")
            .setContentText("Service is running").setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
    ***REMOVED***

    // Simulate image processing for now
    @RequiresApi(Build.VERSION_CODES.P)
    private fun processImage(
        imageUris: List<Uri>,
        serviceRepository: ServiceRepository,
        keyManager: KeyManager,
        template: Template,
        options: Options?
    ) {
        val localContext = this

        val onResult: (PyObject) -> Unit = { result ->
            serviceRepository.updateResult(extractResult(result, template, imageUris), localContext)
        ***REMOVED***

        serviceScope.launch {
            Log.d("TestService", "Processing image: $imageUris")
            val progressCallback: (Float) -> Unit = { progress ->
                serviceScope.launch {
                    updateProgress(progress)
                ***REMOVED***
            ***REMOVED***


            if (!Python.isStarted()) {
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
                    pyListImages.callAttr(
                        "append", base64Image
        ***REMOVED***  // Add Base64 image to the Python list
                ***REMOVED***
            ***REMOVED***
            progressCallback(0.15f)
            val imageUriOcr = ImageOCR()
            val pyListText = builtinsModule.callAttr("list") // Create an empty Python list
            imageUris.map { imageUri ->
                val source = ImageDecoder.createSource(localContext.contentResolver, imageUri)
                val bitmap = ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
                    decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE // Choose an allocator
                    decoder.isMutableRequired = true // Set if the image needs to be mutable
                ***REMOVED***
                pyListText.callAttr(
                    "append", imageUriOcr.extractTextFromBitmap(bitmap)
    ***REMOVED***  // Add Base64 image to the Python list
            ***REMOVED***


            val classesOptionModule = py.getModule("extractor.classes.Options") // Get the module

            val getApiKey = { key: String ->
                when (key) {
                    "API_KEY_1" -> keyManager.getApiKey(Keys.API_KEY_1)
                    "API_KEY_2" -> keyManager.getApiKey(Keys.API_KEY_2)
                    "API_KEY_3" -> keyManager.getApiKey(Keys.API_KEY_3)
                    else -> null
                ***REMOVED***
            ***REMOVED***

            val chosenOption = classesOptionModule.callAttr(
                "Options",  // Directly call the constructor
                PyObject.fromJava(options?.language),
                PyObject.fromJava(options?.model),
                PyObject.fromJava(options?.azureOcr),
                PyObject.fromJava(getApiKey),
                PyObject.fromJava(options?.format)
***REMOVED***

            progressCallback(0.2f)

            val classesModule = py.getModule("extractor.classes.Template") // Get the module

            val pythonTemplate = getTemplate(classesModule, builtinsModule, template)


            val deferredPyResult = async(Dispatchers.Default) { // Use async for Python call
                module.callAttr(
                    "main_kotlin", pyListImages, pyListText, pythonTemplate, chosenOption
    ***REMOVED***
            ***REMOVED***

            // Monitor progress in parallel
            while (isActive && !deferredPyResult.isCompleted) {  // Check if Python is still running
                val currentProgress = module["global_progress"]?.toFloat()
                if (currentProgress != null) {
                    progressCallback(currentProgress)
                ***REMOVED***
                delay(10)
            ***REMOVED***

            // Python call is complete, handle results
            val pyResult = deferredPyResult.await()  // Wait for the result
            launch(Dispatchers.Main) {
                onResult(pyResult)
                progressCallback(1f)
                stopSelf()
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***


    enum class Actions {
        START, STOP
    ***REMOVED***


***REMOVED***


private fun extractResult(
    pyResult: PyObject, templateOg: Template, imageUris: List<Uri>
): Extraction {

    val extracted = Extraction().apply {
        image.addAll(imageUris.map { it.toString() ***REMOVED***)
        format = pyResult["format"].toString()
        pyResult["tags"]?.let { pyTags ->
            val tagsList = pyTags.asList()
            tags.addAll(tagsList.map { it.toString() ***REMOVED***)
        ***REMOVED***

        pyResult["extraction_costs"]?.asList()?.let {
            extractionCosts.addAll(it.map { pyCost ->
                ExtractionCosts().apply {
                    name = pyCost["name"].toString()
                    tokens = pyCost["tokens"]?.toInt() ?: 0
                    cost = pyCost["cost"]?.toFloat() ?: 0f
                    currency = pyCost["currency"].toString()
                ***REMOVED***
            ***REMOVED***)
        ***REMOVED***

        title = pyResult["title"].toString()
        pyResult["exceptions_occurred"]?.asList()?.let {
            exceptionsOccurred.addAll(it.map { pyException ->
                val errorFiltered = filterErrors(pyException["error"].toString())
                ExceptionOccurred().apply {
                    error = errorFiltered
                    errorType = pyException["error_type"].toString()
                    errorDescription = pyException["error_description"].toString()
                ***REMOVED***
            ***REMOVED***)
        ***REMOVED***
        pyResult["extracted_fields"]?.asList()?.let { pyObjects ->
            extractedFields.addAll(pyObjects.map { pyExtractedField ->
                ExtractionField().apply {
                    templateField = templateOg.fields.first {
                        it.id.toHexString() == pyExtractedField["template_field"]?.get("id")
                            .toString()
                    ***REMOVED***
                    value = pyExtractedField["value"].toString()
                ***REMOVED***
            ***REMOVED***)
        ***REMOVED***
        pyResult["extracted_tables"]?.asList()?.forEach { pyExtractedTable ->
            extractedTables.add(extractTable(pyExtractedTable, templateOg))
        ***REMOVED***
        template = templateOg
    ***REMOVED***

    // Convert to JSON and save to file


    return extracted
***REMOVED***

fun filterErrors(error: String): String {
    //if error string contains certain recognized words it rewords it for the user
    return when {
        error.contains("API key") -> "API key is invalid"
        error.contains("Model") -> "Model is invalid"
        error.contains("Language") -> "Language is invalid"
        error.contains("Azure") -> "Azure OCR is invalid"
        else -> error
    ***REMOVED***

***REMOVED***


private fun extractTable(pyExtractedTable: PyObject, template: Template): ExtractionTable {


    val realmExtractionTable = ExtractionTable().apply {
        templateTable = template.tables.first {
            it.id.toHexString() == pyExtractedTable["template_table"]?.get("id").toString()
        ***REMOVED***
        dataframe = pyExtractedTable["dataframe"].toString()
        // Handle fields (nested dictionaries)
        val rows = mutableListOf<ExtractionTableRow>()
        val pyFields = pyExtractedTable["fields"]?.asMap()
        if (pyFields != null) {
            for ((rowIndex, rowData) in pyFields) {
                val fields = rowData.values.map { extractedField: PyObject ->
                    ExtractionField().apply {
                        templateTable
                        value = extractedField["value"].toString()
                    ***REMOVED***
                ***REMOVED***
                rows.add(ExtractionTableRow().apply {
                    this.rowName = rowIndex.toString()
                    this.fields.addAll(fields)
                ***REMOVED***)
            ***REMOVED***
            this.fields.addAll(rows)
        ***REMOVED***
    ***REMOVED***
    return realmExtractionTable
***REMOVED***


private fun getTemplate(
    classesModule: PyObject, builtinsModule: PyObject, template: Template
): PyObject? {

    val pyFields = builtinsModule.callAttr("list") // Create an empty Python list

    template.fields.map { realmField ->

        pyFields.callAttr(
            "append", classesModule.callAttr(
                "TemplateField",
                PyObject.fromJava(realmField.id.toHexString()),
                PyObject.fromJava(realmField.title),
                PyObject.fromJava(realmField.description),
                PyObject.fromJava(realmField.extraDescription),
                PyObject.fromJava(realmField.type),
                PyObject.fromJava(realmField.list),
                PyObject.fromJava(realmField.required),
                PyObject.fromJava(realmField.intelligentExtraction),
                PyObject.fromJava(realmField.default)
***REMOVED***
        )  // Add Base64 image to the Python list

    ***REMOVED***
    val pyTables = builtinsModule.callAttr("list") // Create an empty Python list
// Create Python TemplateTable objects (with their fields)
    template.tables.map { realmTable ->
        val pyTableRows = builtinsModule.callAttr("list") // Create an empty Python list
        val pyTableColumns = builtinsModule.callAttr("list") // Create an empty Python list
        realmTable.rows.map { tableField ->
            pyTableRows.callAttr(
                "append", classesModule.callAttr(
                    "TemplateField",
                    PyObject.fromJava(tableField.id.toHexString()),
                    PyObject.fromJava(tableField.title),
                    PyObject.fromJava(tableField.description),
                    PyObject.fromJava(tableField.extraDescription),
                    PyObject.fromJava(tableField.type),
                    PyObject.fromJava(tableField.required),
                    PyObject.fromJava(tableField.intelligentExtraction)
    ***REMOVED***
***REMOVED***  // Add Base64 image to the Python list
        ***REMOVED***
        realmTable.columns.map { tableField ->
            pyTableColumns.callAttr(
                "append", classesModule.callAttr(
                    "TemplateField",
                    PyObject.fromJava(tableField.id.toHexString()),
                    PyObject.fromJava(tableField.title),
                    PyObject.fromJava(tableField.description),
                    PyObject.fromJava(tableField.extraDescription),
                    PyObject.fromJava(tableField.type),
                    PyObject.fromJava(tableField.required),
                    PyObject.fromJava(tableField.intelligentExtraction)
    ***REMOVED***
***REMOVED***  // Add Base64 image to the Python list
        ***REMOVED***
        pyTables.callAttr(
            "append", classesModule.callAttr(
                "TemplateTable",
                PyObject.fromJava(realmTable.id.toHexString()),
                PyObject.fromJava(realmTable.title),
                PyObject.fromJava(realmTable.keywords.toList()),
                PyObject.fromJava(realmTable.description),
                pyTableRows,
                pyTableColumns
***REMOVED***
        )  // Add Base64 image to the Python list
    ***REMOVED***

// Create the Python Template object
    val pythonTemplate = classesModule.callAttr(
        "Template",
        PyObject.fromJava(template.id.toHexString()),
        PyObject.fromJava(template.title),
        PyObject.fromJava(template.description),
        pyFields, // Pass the created Python TemplateField list
        pyTables, // Pass the created Python TemplateTable list
        PyObject.fromJava(template.tags.toList())
    )
    return pythonTemplate
***REMOVED***