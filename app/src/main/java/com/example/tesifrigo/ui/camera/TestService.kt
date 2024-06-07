package com.example.tesifrigo.ui.camera

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tesifrigo.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class TestService : Service() {
    private val _serviceResponse = MutableLiveData<String>()
    val serviceResponse : LiveData<String> = _serviceResponse

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
                processImage(imageUri)
            ***REMOVED***
        ***REMOVED***

    ***REMOVED***

    private fun createNotification(): Notification {
    return NotificationCompat.Builder(this,"extracting_data")
        .setContentTitle("Service")
        .setContentText("Service is running")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .build()
    ***REMOVED***

    // Simulate image processing for now
    private fun processImage(imageUri: Uri?) {
        Log.d("TestService", "Processing image: $imageUri")
        Thread.sleep(5000) // Sleep for 5 seconds

        CoroutineScope(Dispatchers.IO).launch {
            val response = "Hello World"
            _serviceResponse.postValue(response)
            Log.d("TestService", "Service response: $serviceResponse $response")
            stopSelf()
        ***REMOVED***
    ***REMOVED***
    enum class Actions {
        START,
        STOP
    ***REMOVED***
***REMOVED***
