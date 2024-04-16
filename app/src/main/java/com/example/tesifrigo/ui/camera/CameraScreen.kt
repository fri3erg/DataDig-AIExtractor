package com.example.tesifrigo.ui.camera

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Environment
import android.util.Log
import android.view.TextureView
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.Executors
@Composable
fun CameraScreen() {
    val context = LocalContext.current
    var imageFilePath by remember { mutableStateOf<String?>(null) ***REMOVED***
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() ***REMOVED***
    val cameraPermissionState = remember { mutableStateOf(false) ***REMOVED***

    // Check camera permission
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
        cameraPermissionState.value = true
    ***REMOVED***

    if (cameraPermissionState.value) {
        // Initialize CameraX
        val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) ***REMOVED***
        LaunchedEffect(cameraProviderFuture) {
            cameraProviderFuture.addListener(
                {
                    val cameraProvider = cameraProviderFuture.get()
                    bindCameraPreview(context, cameraProvider, textureView = TextureView(context))
                ***REMOVED***,
                ContextCompat.getMainExecutor(context)
***REMOVED***
        ***REMOVED***

        // Composable for Camera Preview
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                TextureView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
        ***REMOVED***
                ***REMOVED***
            ***REMOVED***,
            update = { view ->
                imageFilePath?.let { filePath ->
                    // Display captured image
                    // Example: load image into ImageView
                    // view.setImageBitmap(BitmapFactory.decodeFile(filePath))
                    imageFilePath = null
                ***REMOVED***
            ***REMOVED***
        )

        // Capture Button
        Button(
            onClick = {
                bindCameraPreview(context, cameraProviderFuture.get(), textureView = TextureView(context))

            ***REMOVED***,
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Capture Image")
        ***REMOVED***
    ***REMOVED*** else {
        // Request camera permission
        Button(
            onClick = {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_REQUEST_CODE
    ***REMOVED***
            ***REMOVED***,
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Request Camera Permission")
        ***REMOVED***
    ***REMOVED***
***REMOVED***
fun bindCameraPreview(
    context: Context,
    cameraProvider: ProcessCameraProvider,
    textureView: TextureView
) {
    val lifecycleOwner = context as LifecycleOwner // Assume context is Activity or Fragment
    val preview = Preview.Builder().build()

    // ... Your surface provider setup ...

    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()

    val imageCapture = ImageCapture.Builder()
        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
        .build() // For image capture

    try {
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture // Bind image capture
        )
    ***REMOVED*** catch (exc: Exception) {
        // Handle exceptions
    ***REMOVED***

    imageCapture.takePicture(
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageCapturedCallback() {
            @OptIn(ExperimentalGetImage::class) override fun onCaptureSuccess(image: ImageProxy) {
                // Handle captured image
                val imageFile = createImageFile(context)
                val savedUri = imageFile?.let { file ->
                    try {
                        FileOutputStream(file).use { outputStream ->
                            image.image?.let {
                                it.planes[0].buffer.apply {
                                    rewind()
                                    val data = ByteArray(remaining())
                                    get(data)
                                    outputStream.write(data)
                                ***REMOVED***
                            ***REMOVED***
                        file.absolutePath
                        ***REMOVED***
                    ***REMOVED*** catch (ex: IOException) {
                        // Handle IOException
                        null
                    ***REMOVED***
                ***REMOVED***
                image.close()

                savedUri?.let {
                    // Use the captured image path here
                    Log.d("CameraCapture", "Image saved: $it")
                ***REMOVED***
            ***REMOVED***

            override fun onError(exc: ImageCaptureException) {
                // Handle errors
            ***REMOVED***
        ***REMOVED***
    )
***REMOVED***

private fun createImageFile(context: Context): File? {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "JPEG_${timeStamp***REMOVED***_", /* prefix */
        ".jpg", /* suffix */
        storageDir /* directory */
    )
***REMOVED***



private const val CAMERA_PERMISSION_REQUEST_CODE = 1001

private fun startPythonService(context: Context, imagePath: String) {
    // Start the Python service with the captured image path
    val intent = Intent(context, ExtractionService::class.java).apply {
        putExtra("imagePath", imagePath)
    ***REMOVED***
    ContextCompat.startForegroundService(context, intent)
***REMOVED***
@Composable
fun ButtonandBar() {
    val context = LocalContext.current
    val progress = remember { mutableStateOf(0) ***REMOVED***

    Button(onClick = { // ... inside your onClick handler in Compose
    val intent = Intent(context, ExtractionService::class.java).apply {
        putExtra("param1", "value1")
        putExtra("param2", 42)
        putExtra("file_path", 42) // Pass the file path
    ***REMOVED***
    context.startService(intent)
***REMOVED***) {

***REMOVED***
    LinearProgressIndicator(
        progress = progress.value / 100f, // Convert to a float value between 0 and 1
        modifier = Modifier.padding(16.dp)
    )
    


***REMOVED***
