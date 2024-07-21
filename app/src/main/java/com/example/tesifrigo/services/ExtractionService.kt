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
import com.example.tesifrigo.models.Extracted
import com.example.tesifrigo.models.Template
import com.example.tesifrigo.models.TemplateField
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
import com.googlecode.tesseract.android.TessBaseAPI
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
            // Process the image using a Coroutine
            CoroutineScope(Dispatchers.IO).launch {
                processImage(imageUri, serviceRepository, keyManager)
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
    private fun processImage(imageUri: Uri?, serviceRepository: ServiceRepository, keyManager: KeyManager) {
        val localContext = this

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

            builtinsModule["API_KEY_1"] = PyObject.fromJava(keyManager.getApiKey1())
            builtinsModule["API_KEY_2"] = PyObject.fromJava(keyManager.getApiKey2())
            builtinsModule["TESSDATA_PREFIX"] = PyObject.fromJava(tessdataDir.absolutePath)


            val template= Template().apply {
                title = "Sample Template"
                description = "This is a sample template"
            ***REMOVED***


            val templateFields = template.fields.map { realmField ->
                TemplateField().apply {
                    realmField.id.toHexString()  // Convert ObjectId to hex string
                    realmField.title
                    realmField.description
                    realmField.extraDescription
                    realmField.tags.toList()
                ***REMOVED***
            ***REMOVED***

            val pythonTemplate = PyObject.fromJava(
                Template().apply {
                    template.id.toHexString()
                    template.title
                    template.description
                    templateFields
                    template.tags.toList()

                ***REMOVED***
***REMOVED***

            val pyResult = module.callAttr("process_data", base64Image,template, progressCallback).toString()
            // Handle 'pyResult' on the main thread for UI updates if needed
            Log.d("TestService", "Result: $pyResult")
        ***REMOVED***
        val result = Extracted()
        this.serviceRepository.updateResult(result)
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

