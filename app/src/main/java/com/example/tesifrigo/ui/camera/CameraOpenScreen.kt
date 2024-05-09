package com.example.tesifrigo.ui.camera

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraOpenScreen() {

    var serviceResponse by remember { mutableStateOf("placeholder") ***REMOVED***

    val context = LocalContext.current

    val permissionsState = rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
***REMOVED***
        )

    var imageUri by remember { mutableStateOf<Uri?>(null) ***REMOVED***

    var showImage by remember { mutableStateOf(false) ***REMOVED***

    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Get the image URI from Intent data (if available)
            var photoUri = result.data?.data

            // If no URI directly available, try to get it from Media Store
            if (photoUri == null) {
                val intent = result.data
                if (intent != null && intent.hasExtra("data")) {
                    val thumbnail = intent.getParcelableExtra<Bitmap>("data")
                    photoUri = thumbnail?.let { getUriFromBitmap(context, it) ***REMOVED***
                ***REMOVED***
            ***REMOVED***

            photoUri?.let {
                imageUri = it
                showImage = true
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
    val testService = TestService() // Assuming you create/access the service somehow

// Observe LiveData
    LaunchedEffect(testService) {
        snapshotFlow { testService.serviceResponse.value ***REMOVED***
            .collect { response ->
                Log.d("CameraOpenScreen", "Service response: $response")
                if (response != null) {
                    serviceResponse = response
                    Log.d("CameraOpenScreen", "Service response: $serviceResponse")
                ***REMOVED***
            ***REMOVED***
    ***REMOVED***

    val selectImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
        showImage = true
    ***REMOVED***

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showImage) {
            imageUri?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
    ***REMOVED***
            ***REMOVED***
        ***REMOVED***

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            permissionsState.launchMultiplePermissionRequest()
        ***REMOVED***) {
            Text("Request Permissions")
        ***REMOVED***
        if (imageUri != null) {
        Button(onClick = {
            Log.d("CameraOpenScreen", "Sending image to service ${imageUri***REMOVED***")
                val intent = Intent(context, TestService::class.java)
                intent.putExtra("imageUri", imageUri)
                context.startService(intent)
        ***REMOVED***) {
            Text("Send Image to Service")
        ***REMOVED***
            Text(text = serviceResponse)
    ***REMOVED***


        // Handle permission results
        permissionsState.permissions.forEach { perm ->
            when (perm.permission) {
                Manifest.permission.CAMERA -> {
                    when {
                        perm.status.isGranted -> {
                            Button(onClick = {
                                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                takePhotoLauncher.launch(intent)
                            ***REMOVED***) {
                                Text("Take Photo")
                            ***REMOVED***
                        ***REMOVED***
                        else -> {
                            Text("Camera permission denied.")
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
                Manifest.permission.READ_EXTERNAL_STORAGE -> {
                    when {
                        perm.status.isGranted -> {
                            Button(onClick = {
                                selectImageLauncher.launch("image/*")
                            ***REMOVED***) {
                                Text("Choose from Gallery")
                            ***REMOVED***
                        ***REMOVED***
                        else -> {
                            Text("Gallery permission denied.")
                                    ***REMOVED***
                                ***REMOVED***
                            ***REMOVED***
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
***REMOVED***


private fun getUriFromBitmap(context: Context, bitmap: Bitmap): Uri? {
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, "temp_image.jpg")
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
    ***REMOVED***
    val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    return uri?.let {
        context.contentResolver.openOutputStream(it)?.use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
        ***REMOVED***
        it
    ***REMOVED***
***REMOVED***
// Placeholder for your camera activity launch function
private fun startCameraActivity() {
    // Implement your logic to launch the camera activity using an Intent
    // or CameraX depending on your preference
***REMOVED***
