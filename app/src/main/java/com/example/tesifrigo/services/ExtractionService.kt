package com.example.tesifrigo.services

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.ParcelFileDescriptor
import android.util.Base64
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.tesifrigo.MainActivity
import com.example.tesifrigo.MyApp
import com.example.tesifrigo.R
import com.example.tesifrigo.models.ExceptionOccurred
import com.example.tesifrigo.models.Extraction
import com.example.tesifrigo.models.ExtractionCosts
import com.example.tesifrigo.models.ExtractionField
import com.example.tesifrigo.models.ExtractionTable
import com.example.tesifrigo.models.ExtractionTableRow
import com.example.tesifrigo.models.Options
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
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.io.FileNotFoundException
import java.io.IOException

/**
 * Extraction service to call the chaquopy python code
 *
 * @constructor Create empty Extraction service
 */
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

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.STOP.toString() -> stopSelf()
            Actions.START.toString() -> start(intent)
        ***REMOVED***
        return START_NOT_STICKY // Service won't restart automatically if stopped
    ***REMOVED***


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

    /**
     * Process image
     *
     * @param imageUris The image uris to process
     * @param serviceRepository The service repository
     * @param keyManager The key manager to get the api key
     * @param template The template to use for extraction
     * @param options The options to use for extraction
     */// Simulate image processing for now
    private fun processImage(
        imageUris: List<Uri>,
        serviceRepository: ServiceRepository,
        keyManager: KeyManager,
        template: Template,
        options: Options?
    ) {
        val localContext = this

        // Callback to update the result in the repository
        val onResult: (PyObject) -> Unit = { result ->
            serviceRepository.updateResult(extractResult(result, template, imageUris), localContext)
            val isAppInForeground =
                (localContext.applicationContext as MyApp).lifecycleObserver.isInForeground

            if (!isAppInForeground) {
                // Send a notification to the user
                sendNotification(
                    localContext,
                    getString(R.string.extraction_complete),
                    getString(R.string.your_extraction_has_finished_processing)
    ***REMOVED***

            ***REMOVED***
        ***REMOVED***
        // Callback to send an error
        val sendError: () -> Unit = {
            serviceRepository.setActiveExtraction(false)
            serviceRepository.setProgress(0f)

        ***REMOVED***

        serviceScope.launch {
            try {
                Log.d("TestService", "Processing image: $imageUris")
                // Callback to update the progress in the repository
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
                val builtinModule = py.getModule("builtins")
                val pyListImages = builtinModule.callAttr("list") // Create an empty Python list
                val bitmapList = mutableListOf<Bitmap>()

                // Process each image and add to the list
                imageUris.forEach { imageUri ->
                    val bitmaps = processUri(localContext.contentResolver, imageUri, pyListImages)
                    bitmapList.addAll(bitmaps)
                ***REMOVED***

                progressCallback(0.15f)
                val imageUriOcr = ImageOCR()
                val pyListText = builtinModule.callAttr("list") // Create an empty Python list

                // Extract text from each bitmap and add to the list
                bitmapList.forEach { bitmap ->
                    pyListText.callAttr(
                        "append", imageUriOcr.extractTextFromBitmap(bitmap)
        ***REMOVED***
                ***REMOVED***


                val classesOptionModule =
                    py.getModule("extractor.classes.Options") // Get the module

                // Callback to get the api key
                val getApiKey = { key: String ->
                    when (key) {
                        "API_KEY_1" -> keyManager.getApiKey(Keys.API_KEY_1)
                        "API_KEY_2" -> keyManager.getApiKey(Keys.API_KEY_2)
                        "API_KEY_3" -> keyManager.getApiKey(Keys.API_KEY_3)
                        else -> null
                    ***REMOVED***
                ***REMOVED***

                // Create the options python object
                val chosenOption = classesOptionModule.callAttr(
                    "Options",  // Directly call the constructor
                    PyObject.fromJava(options?.model),
                    PyObject.fromJava(options?.language),
                    PyObject.fromJava(options?.azureOcr),
                    PyObject.fromJava(getApiKey),
                    PyObject.fromJava(options?.format),
                    PyObject.fromJava(options?.resize)
    ***REMOVED***

                progressCallback(0.2f)

                val classesModule = py.getModule("extractor.classes.Template") // Get the module

                val pythonTemplate = getTemplate(classesModule, builtinModule, template)

                // Call the main function in the Python code to process the images
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
                    delay(100)
                ***REMOVED***

                // Python call is complete, handle results
                val pyResult = deferredPyResult.await()  // Wait for the result
                launch(Dispatchers.Main) {
                    onResult(pyResult)
                    progressCallback(1f)
                    stopSelf()
                ***REMOVED***
            ***REMOVED*** catch (e: IllegalArgumentException) {
                Log.e("TestService", "Unsupported file format", e)
                Handler(Looper.getMainLooper()).post { // Post to the main thread
                    Toast.makeText(localContext, "Unsupported file format", Toast.LENGTH_SHORT)
                        .show()
                ***REMOVED***
                sendError()
                stopSelf()
            ***REMOVED*** catch (e: FileNotFoundException) {
                Log.e("TestService", "File not found", e)
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(localContext, "File not found", Toast.LENGTH_SHORT).show()
                ***REMOVED***
                sendError()
                stopSelf()
            ***REMOVED*** catch (e: IOException) {
                Log.e("TestService", "Error processing image", e)
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(localContext, "Error processing image", Toast.LENGTH_SHORT)
                        .show()
                ***REMOVED***
                sendError()
                stopSelf()
            ***REMOVED*** catch (e: Exception) {
                Log.e("TestService", "Error processing image", e)
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(localContext, "Error processing image", Toast.LENGTH_SHORT)
                        .show()
                ***REMOVED***
                sendError()
                stopSelf()


            ***REMOVED***
        ***REMOVED***
    ***REMOVED***


    enum class Actions {
        START, STOP
    ***REMOVED***


***REMOVED***


/**
 * creates the extraction object from the python result
 *
 * @param pyResult The python result extracted
 * @param templateOg The template to use
 * @param imageUris The image uris used
 * @return Extraction
 */
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
    return extracted
***REMOVED***


/**
 * Filter errors, shortens the error message for the user
 *
 * @param error
 * @return
 */
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


/**
 * creates the extraction table object from the python result
 *
 * @param pyExtractedTable The python extracted table
 * @param template The template to use
 * @return ExtractionTable
 */
private fun extractTable(pyExtractedTable: PyObject, template: Template): ExtractionTable {

    val realmExtractionTable = ExtractionTable().apply {
        title = pyExtractedTable["title"].toString()
        templateTable = template.tables.first {
            it.id.toHexString() == pyExtractedTable["template_table"]?.get("id").toString()
        ***REMOVED***
        dataframe = pyExtractedTable["dataframe"].toString()
        // Handle fields, nested dictionaries :(((((((((((((
        val rows = mutableListOf<ExtractionTableRow>()
        val pyFields = pyExtractedTable["fields"]?.asMap()
        if (pyFields != null) {
            templateTable?.let { table ->
                for ((rowIndex, rowData) in pyFields) {
                    val templateRow = table.rows.firstOrNull { it.title == rowIndex.toString() ***REMOVED***
                    val foundFields = mutableListOf<ExtractionField>()

                    for ((columnIndex, columnData) in rowData.asMap()) {
                        var templateColumn =
                            table.columns.firstOrNull { it.title == columnIndex.toString() ***REMOVED***
                        if (templateColumn == null) { //case where the column is not in the template and has been invented
                            templateColumn =
                                TemplateField().apply { title = columnIndex.toString() ***REMOVED***
                        ***REMOVED***
                        val foundField = ExtractionField().apply {
                            templateField = templateColumn
                            value = columnData["value"].toString()
                        ***REMOVED***
                        foundFields.add(foundField)
                    ***REMOVED***
                    val newRow = ExtractionTableRow().apply {
                        rowName = templateRow?.title ?: rowIndex.toString()
                        fields.addAll(foundFields)
                    ***REMOVED***
                    rows.add(newRow)
                ***REMOVED***


            ***REMOVED***

        ***REMOVED***
        fields.addAll(rows)
    ***REMOVED***
    return realmExtractionTable
***REMOVED***


/**
 * Get template as a python object
 *
 * @param classesModule The classes module to use
 * @param builtinModule The builtin module to use
 * @param template The template to use
 * @return
 */
private fun getTemplate(
    classesModule: PyObject, builtinModule: PyObject, template: Template
): PyObject? {

    val pyFields = builtinModule.callAttr("list") // Create an empty Python list

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
        )

    ***REMOVED***
    val pyTables = builtinModule.callAttr("list")
// Create Python TemplateTable objects (with their fields)
    template.tables.map { realmTable ->
        val pyTableRows = builtinModule.callAttr("list")
        val pyTableColumns = builtinModule.callAttr("list")
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
***REMOVED***
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
***REMOVED***
        ***REMOVED***

        val keywords = builtinModule.callAttr("list") // Create an empty Python list
        for (keyword in realmTable.keywords) {
            keywords.callAttr("append", PyObject.fromJava(keyword))
        ***REMOVED***
        pyTables.callAttr(
            "append", classesModule.callAttr(
                "TemplateTable",
                PyObject.fromJava(realmTable.id.toHexString()),
                PyObject.fromJava(realmTable.title),
                keywords,
                PyObject.fromJava(realmTable.description),
                pyTableRows,
                pyTableColumns,
                PyObject.fromJava(realmTable.all)
***REMOVED***
        )
    ***REMOVED***

// Create the Python Template object
    val pythonTemplate = classesModule.callAttr(
        "Template",
        PyObject.fromJava(template.id.toHexString()),
        PyObject.fromJava(template.title),
        PyObject.fromJava(template.description),
        pyFields, // Pass the created Python TemplateField list
        pyTables, // Pass the created Python TemplateTable list
    )
    return pythonTemplate
***REMOVED***

/**
 * Send notification if the user is not in the app
 *
 * @param context The context to use
 * @param title The title of the notification
 * @param content The content of the notification
 */
fun sendNotification(context: Context, title: String, content: String) {
    CoroutineScope(Dispatchers.Main).launch {
        val channelId = "extraction_channel"
        val notificationId = 2  // Ensuring this ID doesn't conflict with your foreground service

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        ***REMOVED***

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Extraction Completed"
            val descriptionText = "Your extraction was completed successfully"
            val importance = NotificationManager.IMPORTANCE_HIGH // Set to HIGH for more visibility
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            ***REMOVED***
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        ***REMOVED***

        // Create the notification
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.logo_vale) // Replace with your own icon
            .setContentTitle(title).setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Set to HIGH for more visibility
            .setAutoCancel(true) // Dismiss the notification when clicked
            .setContentIntent(pendingIntent)  // Set the intent that will fire when the user taps the notification

        // Show the notification
        CoroutineScope(Dispatchers.Main).launch {

            with(NotificationManagerCompat.from(context)) {
                if (ActivityCompat.checkSelfPermission(
                        context, Manifest.permission.POST_NOTIFICATIONS
        ***REMOVED*** == PackageManager.PERMISSION_GRANTED
    ***REMOVED*** {
                    notify(notificationId, builder.build())
                ***REMOVED*** else {
                    Log.e("Notification", "POST_NOTIFICATIONS permission not granted")
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***


/**
 * Process uri transforms the uri into a base64 image
 *
 * @param contentResolver The content resolver to use
 * @param uri The uri to process
 * @param pyListImages The python list to append the base64 image to
 * @return List<Bitmap>
 */
fun processUri(contentResolver: ContentResolver, uri: Uri, pyListImages: PyObject): List<Bitmap> {
    val bitmaps = mutableListOf<Bitmap>()

    when (val mimeType = getMimeType(uri, contentResolver)) {
        "application/pdf" -> {
            val base64Images = pdfToBase64Images(contentResolver, uri)
            base64Images.forEach { base64Image ->
                pyListImages.callAttr("append", base64Image)
                val bitmap = base64ToBitmap(base64Image)
                bitmaps.add(bitmap)
            ***REMOVED***
        ***REMOVED***

        "image/png", "image/jpeg", "image/jpg" -> {
            val bitmap = uri.let {
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
                pyListImages.callAttr("append", base64Image)
                bitmap.let { bitmaps.add(it) ***REMOVED***
            ***REMOVED***
        ***REMOVED***

        else -> throw IllegalArgumentException("Unsupported file format: $mimeType")
    ***REMOVED***

    return bitmaps
***REMOVED***

fun getMimeType(uri: Uri, contentResolver: ContentResolver): String? {
    var mimeType: String? = contentResolver.getType(uri)

    if (mimeType == null) {
        val extension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
        if (extension != null) {
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.lowercase())
        ***REMOVED***
    ***REMOVED***
    return mimeType
***REMOVED***

fun pdfToBase64Images(contentResolver: ContentResolver, uri: Uri): List<String> {
    val base64Images = mutableListOf<String>()

    try {
        val parcelFileDescriptor: ParcelFileDescriptor? =
            contentResolver.openFileDescriptor(uri, "r")
        parcelFileDescriptor?.let {
            val pdfRenderer = PdfRenderer(it)

            for (pageIndex in 0 until pdfRenderer.pageCount) {
                val page = pdfRenderer.openPage(pageIndex)
                // Create a Bitmap object
                val bitmap = Bitmap.createBitmap(
                    page.width, page.height, Bitmap.Config.ARGB_8888
    ***REMOVED***
                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

                // Convert Bitmap to Base64
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                val base64Image =
                    Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)

                base64Images.add(base64Image)

                page.close()
            ***REMOVED***

            pdfRenderer.close()
        ***REMOVED***
    ***REMOVED*** catch (e: FileNotFoundException) {
        e.printStackTrace()
    ***REMOVED*** catch (e: IOException) {
        e.printStackTrace()
    ***REMOVED***

    return base64Images
***REMOVED***

fun base64ToBitmap(base64Str: String): Bitmap {
    val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
***REMOVED***
