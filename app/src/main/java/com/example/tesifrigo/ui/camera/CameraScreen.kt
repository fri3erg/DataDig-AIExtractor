package com.example.tesifrigo.ui.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.tesifrigo.models.Template
import com.example.tesifrigo.services.ExtractionService
import com.example.tesifrigo.viewmodels.ServiceViewModel
import com.example.tesifrigo.viewmodels.TemplateViewModel
import java.io.File


@Composable
fun CameraScreen(templateId: String?) {


    val context = LocalContext.current
    val serviceViewModel = hiltViewModel<ServiceViewModel>()
    var imageUri by remember { mutableStateOf<Uri?>(null) ***REMOVED***
    val templateViewModel = hiltViewModel<TemplateViewModel>()
    var template by remember { mutableStateOf<Template?>(null) ***REMOVED***
    if (templateId != null) {
        template = templateViewModel.queryTemplate(templateId).collectAsState().value
        if (template != null){
            serviceViewModel.setTemplate(template!!)
        ***REMOVED***
    ***REMOVED***

    Column (modifier = Modifier.fillMaxSize()){

        MyImageArea(
            uri = imageUri,
            directory = context.getExternalFilesDir("images")
        ) { uri ->
            imageUri = uri
        ***REMOVED***
        if (imageUri != null) {
            Button(onClick = {
                val intent = Intent(context, ExtractionService::class.java).also {
                    it.action = ExtractionService.Actions.START.toString()
                    it.putExtra("imageUri", imageUri) // Pass the image URI to the service
                ***REMOVED***

                ContextCompat.startForegroundService(context, intent)
            ***REMOVED***) {
            Text(text = "Start Service")
            ***REMOVED***

            ProgressBar()
        ***REMOVED***


    ***REMOVED***


***REMOVED***



@Composable
fun ProgressBar() {
    val serviceViewModel = hiltViewModel<ServiceViewModel>()
    val progress by serviceViewModel.progress.collectAsState()
    val result by serviceViewModel.result.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LinearProgressIndicator(
            progress = { progress ***REMOVED***,
        ) // Use state
        Text("Progress: ${progress*100***REMOVED***%")
    ***REMOVED***

***REMOVED***

@Composable
fun MyImageArea(
    uri: Uri? = null, //target url to preview
    directory: File? = null, // stored directory
    onSetUri : (Uri) -> Unit = {***REMOVED***, // selected / taken uri
) {
    val context = LocalContext.current
    val tempUri = remember { mutableStateOf<Uri?>(null) ***REMOVED***
    val authority = "com.example.tesifrigo.fileprovider"


    // for takePhotoLauncher used
    fun getTempUri(): Uri? {
        val imagesFolder = File(context.cacheDir, "images") // Create the "images" subdirectory if it doesn't exist
        imagesFolder.mkdirs()

        val file = File(
            imagesFolder,
            "image_" + System.currentTimeMillis().toString() + ".jpg",
***REMOVED***

        return FileProvider.getUriForFile(
                context,
                authority,
                file
***REMOVED***
    ***REMOVED***

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            it?.let {
                onSetUri.invoke(it)
            ***REMOVED***
        ***REMOVED***
    )

    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = {_ ->
            tempUri.value?.let {
                onSetUri.invoke(it)
            ***REMOVED***
        ***REMOVED***
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted, launch takePhotoLauncher
            val tmpUri = getTempUri()
            tempUri.value = tmpUri
            takePhotoLauncher.launch(tempUri.value)
        ***REMOVED*** else {
            // Permission is denied, handle it accordingly
        ***REMOVED***
    ***REMOVED***

    var showBottomSheet by remember { mutableStateOf(false) ***REMOVED***
    if (showBottomSheet){
        MyModalBottomSheet(
            onDismiss = {
                showBottomSheet = false
            ***REMOVED***,
            onTakePhotoClick = {
                showBottomSheet = false

                val permission = Manifest.permission.CAMERA
                if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    ***REMOVED*** {
                    // Permission is already granted, proceed to step 2
                    val tmpUri = getTempUri()
                    tempUri.value = tmpUri
                    takePhotoLauncher.launch(tempUri.value)
                ***REMOVED*** else {
                    // Permission is not granted, request it
                    cameraPermissionLauncher.launch(permission)
                ***REMOVED***
            ***REMOVED***,
            onPhotoGalleryClick = {
                showBottomSheet = false
                imagePicker.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
        ***REMOVED***
    ***REMOVED***
            ***REMOVED***,
        )
    ***REMOVED***

    Column (
        modifier = Modifier.fillMaxWidth()
    ) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    showBottomSheet = true
                ***REMOVED***
***REMOVED*** {
                Text(text = "Select / Take")
            ***REMOVED***
        ***REMOVED***

        //preview selfie
        uri?.let {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
***REMOVED*** {
                AsyncImage(
                    model = it,
                    modifier = Modifier
                        .height(200.dp)
                        .width(200.dp),
                    contentDescription = null,
    ***REMOVED***
            ***REMOVED***
            Spacer(modifier = Modifier.height(16.dp))
        ***REMOVED***

    ***REMOVED***
***REMOVED***

@Composable
fun MyModalBottomSheet(
    onDismiss: () -> Unit,
    onTakePhotoClick: () -> Unit,
    onPhotoGalleryClick: () -> Unit
) {
    MyModalBottomSheetContent(
        header = "Choose Option",
        onDismiss = {
            onDismiss.invoke()
        ***REMOVED***,
        items = listOf(
            BottomSheetItem(
                title = "Take Photo",
                icon = Icons.Default.AccountBox,
                onClick = {
                    onTakePhotoClick.invoke()
                ***REMOVED***
***REMOVED***,
            BottomSheetItem(
                title = "select image",
                icon = Icons.Default.Place,
                onClick = {
                    onPhotoGalleryClick.invoke()
                ***REMOVED***
***REMOVED***,
        )
    )
***REMOVED***

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyModalBottomSheetContent(
    onDismiss: () -> Unit,
    //header
    header: String = "Choose Option",

    items: List<BottomSheetItem> = listOf(),
) {
    val skipPartiallyExpanded by remember { mutableStateOf(false) ***REMOVED***
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )
    val edgeToEdgeEnabled by remember { mutableStateOf(false) ***REMOVED***
    val windowInsets = if (edgeToEdgeEnabled)
        WindowInsets(0) else BottomSheetDefaults.windowInsets

    ModalBottomSheet(
        shape = MaterialTheme.shapes.medium.copy(
            bottomStart = CornerSize(0),
            bottomEnd = CornerSize(0)
        ),
        onDismissRequest = { onDismiss.invoke() ***REMOVED***,
        sheetState = bottomSheetState,
        windowInsets = windowInsets
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                text = header,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
***REMOVED***
            items.forEach {item ->
                androidx.compose.material3.ListItem(
                    modifier = Modifier.clickable {
                        item.onClick.invoke()
                    ***REMOVED***,
                    headlineContent = {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleMedium,
            ***REMOVED***
                    ***REMOVED***,
                    leadingContent = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
            ***REMOVED***
                    ***REMOVED***,
    ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***

data class BottomSheetItem(
    val title: String = "",
    val icon: ImageVector,
    val onClick: () -> Unit
)