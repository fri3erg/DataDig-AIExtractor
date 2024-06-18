package com.example.tesifrigo.services

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.tesifrigo.R
import com.example.tesifrigo.models.Extracted
import com.example.tesifrigo.repositories.ServiceRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
@AndroidEntryPoint
class ExtractionService : Service(){



    @Inject
    lateinit var serviceRepository: ServiceRepository
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

        if (! Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        ***REMOVED***
        val py = Python.getInstance()
        val module = py.getModule("main_test")
        val n = module["n"]?.toInt()
        Log.d("TestService", "n: $n")

        val progressCallback: (Float) -> Unit = { progress ->
            updateProgress(progress)
        ***REMOVED***


        serviceScope.launch {
            val pyResult = module.callAttr("process_data", n, progressCallback).toString()
            // Handle 'pyResult' on the main thread for UI updates if needed
            Log.d("TestService", "Result: $pyResult")
        ***REMOVED***

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
    private fun processImage(imageUri: Uri?) {
        Log.d("TestService", "Processing image: $imageUri")
        Thread.sleep(5000) // Sleep for 5 seconds

        val result = Extracted()
            serviceRepository.updateResult(result)
            stopSelf()
    ***REMOVED***
    enum class Actions {
        START,
        STOP
    ***REMOVED***


***REMOVED***
