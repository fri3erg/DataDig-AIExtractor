package com.example.tesifrigo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tesifrigo.ui.camera.CameraOpenScreen
import com.example.tesifrigo.ui.extraction.SingleExtractionScreen
import com.example.tesifrigo.ui.settings.SettingsScreen
import com.example.tesifrigo.ui.storage.StorageScreen
import com.example.tesifrigo.ui.template.EditTemplateScreen
import com.example.tesifrigo.ui.template.TemplateScreen


@Composable
fun AppNavigation( navController: NavHostController, modifier: Modifier = Modifier) {

    NavHost(navController = navController, startDestination = Screen.Templates.route, modifier = modifier) {
        composable(route= Screen.Camera.route + "?templateId={templateId***REMOVED***",
            arguments = listOf(
                navArgument("templateId") { type = NavType.StringType
                    defaultValue = null
                    nullable = true
                ***REMOVED***
***REMOVED***
        ) {
            CameraOpenScreen(it.arguments?.getString("templateId")) ***REMOVED***
        composable(Screen.Storage.route) { StorageScreen(navController) ***REMOVED***
        composable(Screen.Settings.route) { SettingsScreen() ***REMOVED***
        composable(Screen.Templates.route + "?photos={photos***REMOVED***",
            arguments = listOf(
                navArgument("photos") { type = NavType.StringType
                    defaultValue = null
                    nullable = true
                ***REMOVED***
***REMOVED***
        ) {
            TemplateScreen(navController, it.arguments?.getString("photos"))
        ***REMOVED***
        composable(Screen.EditTemplate.route + "/templateId={templateId***REMOVED***",
            arguments = listOf(
                navArgument("templateId") { type = NavType.StringType
                    defaultValue = ""
                    nullable = false***REMOVED***
***REMOVED***
        ) {
            EditTemplateScreen(navController, it.arguments?.getString("templateId")!!)
        ***REMOVED***
        composable(Screen.SingleExtraction.route + "/extractionId={extractionId***REMOVED***",
            arguments = listOf(
                navArgument("extractionId") { type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                ***REMOVED***
***REMOVED***
        ) {
            SingleExtractionScreen(navController, it.arguments?.getString("extractionId")!!)
        ***REMOVED***

***REMOVED***
***REMOVED***

