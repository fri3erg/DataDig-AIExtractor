package com.example.tesifrigo.ui.camera

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.tesifrigo.models.Extraction
import com.example.tesifrigo.models.Template
import com.example.tesifrigo.services.ExtractionService
import com.example.tesifrigo.utils.FileCard
import com.example.tesifrigo.utils.MyImageArea
import com.example.tesifrigo.viewmodels.ExtractionViewModel
import com.example.tesifrigo.viewmodels.ServiceViewModel
import com.example.tesifrigo.viewmodels.TemplateViewModel
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import kotlinx.coroutines.delay
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor


@Composable
fun CameraScreen(templateId: String?, navController: NavHostController) {


    val context = LocalContext.current
    val serviceViewModel = hiltViewModel<ServiceViewModel>()
    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) ***REMOVED*** // Store multiple URIs
    val templateViewModel = hiltViewModel<TemplateViewModel>()
    var template by remember { mutableStateOf<Template?>(null) ***REMOVED***
    if (templateId != null) {
        template = templateViewModel.queryTemplate(templateId).collectAsState().value
        if (template != null){
            serviceViewModel.setTemplate(template!!)
        ***REMOVED***
    ***REMOVED***
    var imageCapture: ImageCapture? by remember { mutableStateOf(null) ***REMOVED*** // For capturing images
    var lensFacing by remember { mutableIntStateOf(CameraSelector.LENS_FACING_BACK) ***REMOVED***



    Column (modifier = Modifier.fillMaxSize()){
        var activeExtraction by remember { mutableStateOf(false) ***REMOVED***
        var activePhoto by remember { mutableStateOf(true) ***REMOVED***
        if(activePhoto){
            CameraPreview(
                modifier = Modifier.fillMaxSize(),
                onSetUri = { newUris ->
                    imageUris = imageUris + listOf(newUris)  // Concatenate the lists
                    Log.d("CameraScreen", "Image URIs: $imageUris")
                ***REMOVED***,
                nPhotos = imageUris.size,
                changeActivePhoto = {
                    activePhoto = false
                ***REMOVED***
***REMOVED***
        ***REMOVED***
        else {
            MyImageArea(
                imageUris = imageUris,
***REMOVED***
            Spacer(modifier = Modifier.height(16.dp))
            if(!activeExtraction) {
                Button(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(100.dp, 50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(Color.Green),
                    onClick = {
                        val intent = Intent(context, ExtractionService::class.java).also {
                            it.action = ExtractionService.Actions.START.toString()
                            it.putParcelableArrayListExtra("imageUris", ArrayList(imageUris))
                        ***REMOVED***
                        activeExtraction = true
                        ContextCompat.startForegroundService(context, intent)
                    ***REMOVED***) {
                    Text(text = "Extract!", color = Color.White)
                ***REMOVED***
            ***REMOVED***
            else {
                ProgressBar()
                ShownExtraction(navController = navController)
        ***REMOVED***
            ***REMOVED***



    ***REMOVED***
***REMOVED***



@Composable
fun ProgressBar() {
    val serviceViewModel = hiltViewModel<ServiceViewModel>()
    val progress by serviceViewModel.progress.collectAsState()
    val result by serviceViewModel.result.collectAsState()
    Row {
        Text("Progress: ")
        Spacer(modifier = Modifier.width(8.dp))
        LinearProgressIndicator(
            progress = { progress ***REMOVED***,
            color = Color.Blue
        )
        Spacer(modifier = Modifier.width(16.dp))
        Spinner(isActive = result == null)

    ***REMOVED***

***REMOVED***

@Composable
fun Spinner(isActive: Boolean) {
    if (isActive) {
        CircularProgressIndicator(
            modifier = Modifier.size(36.dp), // Adjust size as needed
            strokeWidth = 4.dp // Adjust stroke width as needed
        )
    ***REMOVED***
    // If not active, you can either show nothing or a placeholder
***REMOVED***




@Composable
fun ShownExtraction(navController: NavHostController) {
    val serviceViewModel = hiltViewModel<ServiceViewModel>()
    val extractionViewModel = hiltViewModel<ExtractionViewModel>()
    val result by serviceViewModel.result.collectAsState()
    var extraction by remember { mutableStateOf<Extraction?>(null) ***REMOVED***
    LaunchedEffect(key1 = result) {
        result?.let {
            extractionViewModel.queryExtraction(it).collect { fetchedExtraction ->
                extraction = fetchedExtraction
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
    if (extraction != null) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
            Text("Extraction Result:")
                Button(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(100.dp, 50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(Color.Red),
                    onClick = {
                    navController.navigate("singleExtraction/extractionId=${result***REMOVED***")
                ***REMOVED***) {
                    Text("Go to Extraction")
                ***REMOVED***
            ***REMOVED***
            if (extraction!!.fileUri != null) {
                FileCard(extraction!!)
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***




@Composable
fun ImageFromUri(imageUriString: String) {
    val imageUri = Uri.parse(imageUriString)
    val filesDir = LocalContext.current.filesDir // Get the app's files directory
    val errorImagePath = File(filesDir, "error_image.png").absolutePath
    val placeholderImagePath = File(filesDir, "placeholder_image.png").absolutePath
    val errorPainter =
        rememberAsyncImagePainter(model = errorImagePath)  // Create a painter from the file path
    val placeholderPainter = rememberAsyncImagePainter(model = placeholderImagePath)


    AsyncImage(
        model = imageUri, // Use the Uri parsed from the string
        contentDescription = "Image from URI", // Add a content description (important for accessibility)
        modifier = Modifier.fillMaxSize(), // Or any other modifier you want
        error = errorPainter,         // Use the errorPainter you created
        placeholder = placeholderPainter // Use the placeholderPainter you created
    )
***REMOVED***

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    onSetUri: (Uri) -> Unit = { ***REMOVED***,
    nPhotos: Int = 0,
    changeActivePhoto: () -> Unit = { ***REMOVED***
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris: List<Uri>? ->
            uris?.forEach { uri ->
                onSetUri(uri)
            ***REMOVED***
            changeActivePhoto()
        ***REMOVED***
    )
    val onPhotoGalleryClick = {
        imagePickerLauncher.launch("image/*")
    ***REMOVED***

    var showFlash by remember { mutableStateOf(false) ***REMOVED***
    val lensFacing by remember { mutableIntStateOf(CameraSelector.LENS_FACING_BACK) ***REMOVED***

    LaunchedEffect(key1 = showFlash) {
        if (showFlash) {
            delay(100)
            showFlash = false
        ***REMOVED***
    ***REMOVED***

    val preview = remember { Preview.Builder().build() ***REMOVED***
    val imageCapture = remember { ImageCapture.Builder().build() ***REMOVED***
    val cameraSelector = remember { CameraSelector.Builder().requireLensFacing(lensFacing).build() ***REMOVED***

    // Function to bind camera use cases
    fun bindCameraUseCases(cameraProvider: ProcessCameraProvider) {
        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture
***REMOVED***
        ***REMOVED*** catch (exc: Exception) {
            Log.e("CameraX", "Use case binding failed", exc)
        ***REMOVED***
    ***REMOVED***

    // Initialize and bind the camera use cases once
    DisposableEffect(context) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            bindCameraUseCases(cameraProvider)
        ***REMOVED***, ContextCompat.getMainExecutor(context))
        onDispose {
            cameraProviderFuture.get().unbindAll()
        ***REMOVED***
    ***REMOVED***

    Box(modifier = modifier) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { factoryContext ->
                PreviewView(factoryContext).apply {
                    this.scaleType = scaleType
                    this.implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    preview.setSurfaceProvider(this.surfaceProvider)
                ***REMOVED***
            ***REMOVED***,
            update = { previewView ->
                previewView.scaleType = scaleType
            ***REMOVED***
        )
        Button(
            modifier = Modifier
                .padding(bottom = 20.dp, start = 24.dp)
                .align(Alignment.BottomStart),

            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(Color.White),
            onClick = onPhotoGalleryClick
        ) {
            FaIcon(
                faIcon =   FaIcons.Images, // Using the gallery icon
                tint = Color.Black, // Set icon color
                size = 24.dp // Set icon size
***REMOVED***
        ***REMOVED***

        // Capture Button
        Button(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .size(90.dp) // Adjust the size as needed
                .clip(CircleShape) // Clip the button to be a circle
                .align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(Color.White),

            onClick = {
                takePhoto(
                    imageCapture = imageCapture,
                    outputDirectory = context.filesDir,
                    executor = ContextCompat.getMainExecutor(context),
                    onImageCaptured = onSetUri,
                    onError = { error ->
                        Log.e("CameraX", "Image capture failed: ${error.message***REMOVED***")
                    ***REMOVED***
    ***REMOVED***
                showFlash = true
            ***REMOVED***
        ) {
            Text("Capture")
        ***REMOVED***

        // Gallery Button
        if (nPhotos > 0) {
            Button(
                modifier = Modifier
                    .padding(bottom = 20.dp, end = 20.dp)
                    .align(Alignment.BottomEnd)
                    .size(65.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(Color.White),
                onClick = changeActivePhoto
***REMOVED*** {
                    FaIcon(
                        faIcon = FaIcons.Check,
                        tint = Color.Black,
                        size = 24.dp,
        ***REMOVED***

            ***REMOVED***
        ***REMOVED***
        Box(modifier = Modifier
            .padding(20.dp)
            .size(70.dp, 35.dp)
            .background(
                Color.White,
                shape = RoundedCornerShape(10.dp) // Apply RoundedCornerShape to background
***REMOVED***
            .align(Alignment.TopStart),
            contentAlignment = Alignment.Center,
***REMOVED*** {
            Text(text = "$nPhotos taken", color = Color.Black, textAlign = TextAlign.Center, fontSize = 14.sp)
        ***REMOVED***
        // Flash effect
        AnimatedVisibility(
            visible = showFlash,
            enter = fadeIn(animationSpec = tween(durationMillis = 100)),
            exit = fadeOut(animationSpec = tween(durationMillis = 200))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***

// Function to capture a photo using CameraX
private fun takePhoto(
    imageCapture: ImageCapture,
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    val filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS"
    val photoFile = File(
        outputDirectory,
        SimpleDateFormat(filenameFormat, Locale.US).format(System.currentTimeMillis()) + ".jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
        override fun onError(exc: ImageCaptureException) {
            Log.e("CameraX", "Photo capture failed: ${exc.message***REMOVED***", exc)
            onError(exc)
        ***REMOVED***

        override fun onImageSaved(output: ImageCapture.OutputFileResults) {
            val savedUri = Uri.fromFile(photoFile)
            onImageCaptured(savedUri)
        ***REMOVED***
    ***REMOVED***)
***REMOVED***



