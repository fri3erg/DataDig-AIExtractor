package com.example.tesifrigo.ui.camera

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.compose.runtime.mutableStateOf
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.chaquo.python.Python

class ExtractionService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Execute Python Logic (using WorkManager)
        val workRequest = OneTimeWorkRequestBuilder<PythonWorker>()
            .setInputData(createInputData(intent))
            .build()
        WorkManager.getInstance(this).enqueue(workRequest)

        return START_NOT_STICKY
    ***REMOVED***

    // --- Helper Functions ---

    // --- Helper Functions ---
    private fun createInputData(intent: Intent?): Data {
        val param1 = intent?.getStringExtra("param1") ?: ""
        val param2 = intent?.getIntExtra("param2", 0) ?: 0

        return Data.Builder()
            .putString("param1", param1)
            .putInt("param2", param2)
            .build()
    ***REMOVED***
    class PythonWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
        override fun doWork(): Result {
            // Load your Python module using Chaquopy
            val python = Python.getInstance()
            val module = python.getModule("main_test") // Assuming 'python_logic.py'

            // Execute your Python function
            val progressCallback: (Int) -> Unit = { progress ->
                // Update the progress state
                PythonExecutionService.progress.value = progress
            ***REMOVED***
            val result = module.callAttr("process_data",
                inputData.getString("param1"),
                progressCallback)

            // Handle result
            val resultString = result.toString()
            // Do something with the result, like logging or sending it back to UI

            return Result.success()
        ***REMOVED***
    ***REMOVED***

    // --- State for Progress ---
    object PythonExecutionService {
        val progress = mutableStateOf(0)
    ***REMOVED***

    companion object {
        const val ACTION_PROGRESS_UPDATE = "com.example.tesifrigo.ACTION_PROGRESS_UPDATE"
        const val EXTRA_PROGRESS_MESSAGE = "com.example.tesifrigo.EXTRA_PROGRESS_MESSAGE"

        fun sendProgressUpdate(context: Context, message: String) {
            val intent = Intent(ACTION_PROGRESS_UPDATE)
            intent.putExtra(EXTRA_PROGRESS_MESSAGE, message)
            context.sendBroadcast(intent)
        ***REMOVED***
    ***REMOVED***
***REMOVED***

