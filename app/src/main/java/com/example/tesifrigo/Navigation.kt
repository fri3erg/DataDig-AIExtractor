package com.example.tesifrigo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tesifrigo.ui.camera.CameraScreen
import com.example.tesifrigo.ui.extraction.SingleExtractionScreen
import com.example.tesifrigo.ui.settings.SettingsScreen
import com.example.tesifrigo.ui.storage.StorageScreen
import com.example.tesifrigo.ui.template.EditTemplateScreen
import com.example.tesifrigo.ui.template.TemplateScreen
import com.example.tesifrigo.viewmodels.ExtractionViewModel
import com.example.tesifrigo.viewmodels.ServiceViewModel
import com.example.tesifrigo.viewmodels.TemplateViewModel


@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    val extractionViewModel: ExtractionViewModel = hiltViewModel()
    val templateViewModel: TemplateViewModel = hiltViewModel()
    val serviceViewModel = hiltViewModel<ServiceViewModel>()

    NavHost(
        navController = navController,
        startDestination = Screen.Templates.route,
        modifier = modifier
    ) {
        composable(route = Screen.Camera.route + "?templateId={templateId***REMOVED***",
            arguments = listOf(
                navArgument("templateId") {
                    type = NavType.StringType
                    defaultValue = null
                    nullable = true
                ***REMOVED***
***REMOVED***
        ) {
            CameraScreen(
                it.arguments?.getString("templateId"),
                navController,
                serviceViewModel,
                templateViewModel,
                extractionViewModel
***REMOVED***
        ***REMOVED***
        composable(Screen.Storage.route) { StorageScreen(navController, extractionViewModel) ***REMOVED***
        composable(Screen.Settings.route) { SettingsScreen(serviceViewModel) ***REMOVED***
        composable(Screen.Templates.route + "?photos={photos***REMOVED***",
            arguments = listOf(
                navArgument("photos") {
                    type = NavType.StringType
                    defaultValue = null
                    nullable = true
                ***REMOVED***
***REMOVED***
        ) {
            TemplateScreen(navController, it.arguments?.getString("photos"), templateViewModel)
        ***REMOVED***
        composable(Screen.EditTemplate.route + "/templateId={templateId***REMOVED***",
            arguments = listOf(
                navArgument("templateId") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                ***REMOVED***
***REMOVED***
        ) {
            EditTemplateScreen(
                navController,
                it.arguments?.getString("templateId")!!,
                templateViewModel
***REMOVED***
        ***REMOVED***
        composable(Screen.SingleExtraction.route + "/extractionId={extractionId***REMOVED***",
            arguments = listOf(
                navArgument("extractionId") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                ***REMOVED***
***REMOVED***
        ) {
            SingleExtractionScreen(
                navController,
                it.arguments?.getString("extractionId")!!,
                extractionViewModel
***REMOVED***
        ***REMOVED***

    ***REMOVED***
***REMOVED***

