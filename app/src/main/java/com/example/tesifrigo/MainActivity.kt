package com.example.tesifrigo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController

import com.example.tesifrigo.ui.common.NavBar
import com.example.tesifrigo.ui.theme.TesiFrigoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    android.Manifest.permission.POST_NOTIFICATIONS,
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
    ***REMOVED***, 1
***REMOVED***
        ***REMOVED*** else {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
    ***REMOVED***, 1
***REMOVED***

        ***REMOVED***


        setContent {

                TesiFrigoTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
        ***REMOVED*** {
                        MainAppScreen()
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***

        ***REMOVED***


    @Composable
    fun MainAppScreen() {

        val navController = rememberNavController()

        Scaffold(bottomBar = { NavBar(navController) ***REMOVED***,
            modifier = Modifier.fillMaxSize(),
            content = { innerPadding -> // Important for content overlap
                AppNavigation(navController, modifier = Modifier.padding(innerPadding))
            ***REMOVED***)
    ***REMOVED***
***REMOVED***