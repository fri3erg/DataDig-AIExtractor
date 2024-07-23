package com.example.tesifrigo.services

import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.util.Base64
import android.util.Log
import androidx.core.app.NotificationCompat
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.tesifrigo.R
import com.example.tesifrigo.models.ExceptionOccurred
import com.example.tesifrigo.models.Extracted
import com.example.tesifrigo.models.Extraction
import com.example.tesifrigo.models.ExtractionField
import com.example.tesifrigo.models.ExtractionTable
import com.example.tesifrigo.models.ExtractionTableRow
import com.example.tesifrigo.models.Template
import com.example.tesifrigo.models.TemplateField
import com.example.tesifrigo.models.TemplateTable
import com.example.tesifrigo.repositories.KeyManager
import com.example.tesifrigo.repositories.ServiceRepository
import com.example.tesifrigo.ui.extraction.ExtractionField
import com.example.tesifrigo.viewmodels.Keys
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import com.googlecode.tesseract.android.TessBaseAPI
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.types.RealmList
import kotlinx.coroutines.async
import java.io.File
import java.io.FileOutputStream

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
            // Process the image using a Coroutine
            CoroutineScope(Dispatchers.IO).launch {
                processImage(imageUri, serviceRepository, keyManager, template)
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
    private fun processImage(imageUri: Uri?, serviceRepository: ServiceRepository, keyManager: KeyManager, template: Template) {
        val localContext = this

        val onResult: (PyObject) -> Unit = { result ->

            serviceRepository.updateResult(extractResult(result, template))
        ***REMOVED***

        serviceScope.launch {
            Log.d("TestService", "Processing image: $imageUri")
            Thread.sleep(5000) // Sleep for 5 seconds
            val progressCallback: (Float) -> Unit = { progress ->
                updateProgress(progress)
            ***REMOVED***
            val bitmap = imageUri?.let {
                contentResolver.openInputStream(it)?.use { inputStream ->
                    BitmapFactory.decodeStream(inputStream)
                ***REMOVED***
            ***REMOVED***
            val tessdataDir = File(filesDir, "tesseract/tessdata") // Store in app's filesDir
            if (!tessdataDir.exists()) {
                tessdataDir.mkdirs()
            ***REMOVED***
            if (tessdataDir.listFiles()?.isEmpty() == true) {
                // If tessdata is empty, copy from assets to filesDir
                copyAssetsToStorage(applicationContext, "tessdata", tessdataDir.absolutePath)
            ***REMOVED***

            val base64Image = bitmap?.let {
                val byteArrayOutputStream = ByteArrayOutputStream()
                it.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                val byteArray = byteArrayOutputStream.toByteArray()
                Base64.encodeToString(byteArray, Base64.DEFAULT)
            ***REMOVED***


            if (! Python.isStarted()) {
                Python.start(AndroidPlatform(localContext))
            ***REMOVED***
            val py = Python.getInstance()
            val module = py.getModule("main_test")

            val builtinsModule = py.getModule("builtins")

            builtinsModule["OPENAI_API_KEY"] = PyObject.fromJava(keyManager.getApiKey(Keys.API_KEY_1))
            builtinsModule["AZURE_FORM_RECOGNIZER_KEY"] = PyObject.fromJava(keyManager.getApiKey(Keys.API_KEY_2))
            builtinsModule["AZURE_FORM_RECOGNIZER_ENPOINT"] = PyObject.fromJava(keyManager.getApiKey(Keys.API_KEY_3))
            builtinsModule["TESSDATA_PREFIX"] = PyObject.fromJava(tessdataDir.absolutePath)



            val templateFields = template.fields.map { realmField ->
                TemplateField().apply {
                    realmField.id.toHexString()  // Convert ObjectId to hex string
                    realmField.title
                    realmField.description
                    realmField.extraDescription
                    realmField.tags.toList()
                ***REMOVED***
            ***REMOVED***
            val templateTables = template.tables.map { realmTable ->
                TemplateTable().apply {
                    realmTable.id.toHexString()  // Convert ObjectId to hex string
                    realmTable.title
                    realmTable.description
                    realmTable.keywords.toList()
                    realmTable.rows
                    realmTable.columns
                ***REMOVED***
            ***REMOVED***

            val pythonTemplate = PyObject.fromJava(
                Template().apply {
                    template.id.toHexString()
                    template.title
                    template.description
                    templateFields
                    templateTables
                    template.tags.toList()

                ***REMOVED***
***REMOVED***


            val deferredResult = async {
                module.callAttr("main", base64Image, pythonTemplate, progressCallback)
            ***REMOVED***

            val pyResult = deferredResult.await()  // Wait for the result from Python            // Handle 'pyResult' on the main thread for UI updates if needed
            Log.d("TestService", "Result: $pyResult")
            onResult(pyResult)
            progressCallback(1f)
        ***REMOVED***
        stopSelf()
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

