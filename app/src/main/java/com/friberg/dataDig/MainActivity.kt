package com.friberg.dataDig

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController

import com.friberg.dataDig.ui.common.NavBar
import com.friberg.dataDig.ui.theme.TesiFrigoTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity for the app
 *
 * @constructor Create empty Main activity
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ***REMOVED*** {
                    MainAppScreen()

                ***REMOVED***
            ***REMOVED***
        ***REMOVED***

    ***REMOVED***


    /**
     * Main app screen composable
     *
     */
    @Composable
    fun MainAppScreen() {

        val navController = rememberNavController()

        Scaffold(bottomBar = { NavBar(navController) ***REMOVED***,
            modifier = Modifier.fillMaxSize(),
            content = { innerPadding ->
                AppNavigation(navController, modifier = Modifier.padding(innerPadding))
            ***REMOVED***)
    ***REMOVED***

***REMOVED***