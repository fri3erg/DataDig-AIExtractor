package com.friberg.dataDig

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.friberg.dataDig.ui.camera.CameraScreen
import com.friberg.dataDig.ui.extraction.SingleExtractionScreen
import com.friberg.dataDig.ui.settings.SettingsScreen
import com.friberg.dataDig.ui.extraction.StorageScreen
import com.friberg.dataDig.ui.template.EditTemplateScreen
import com.friberg.dataDig.ui.template.TemplateScreen
import com.friberg.dataDig.viewmodels.ExtractionViewModel
import com.friberg.dataDig.viewmodels.ServiceViewModel
import com.friberg.dataDig.viewmodels.TemplateViewModel


/**
 * App navigation with the nav controller
 *
 * @param navController The navigation controller
 * @param modifier The modifier
 */
@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    val extractionViewModel: ExtractionViewModel = hiltViewModel() // Get the view models
    val templateViewModel: TemplateViewModel = hiltViewModel()
    val serviceViewModel = hiltViewModel<ServiceViewModel>()

    //all routes
    NavHost(
        navController = navController,
        startDestination = Screen.Templates.route,
        modifier = modifier
    ) {
        composable(
            route = Screen.Camera.route + "?templateId={templateId***REMOVED***",
            arguments = listOf(navArgument("templateId") {
                type = NavType.StringType
                defaultValue = null
                nullable = true
            ***REMOVED***)
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
        composable(Screen.Templates.route) {
            TemplateScreen(
                navController, templateViewModel, serviceViewModel
***REMOVED***
        ***REMOVED***
        composable(
            Screen.EditTemplate.route + "/templateId={templateId***REMOVED***",
            arguments = listOf(navArgument("templateId") {
                type = NavType.StringType
                defaultValue = ""
                nullable = false
            ***REMOVED***)
        ) {
            EditTemplateScreen(
                navController,
                it.arguments?.getString("templateId") ?: "",
                templateViewModel,
                serviceViewModel
***REMOVED***
        ***REMOVED***
        composable(
            Screen.SingleExtraction.route + "/extractionId={extractionId***REMOVED***",
            arguments = listOf(navArgument("extractionId") {
                type = NavType.StringType
                defaultValue = ""
                nullable = false
            ***REMOVED***)
        ) {
            SingleExtractionScreen(
                navController, it.arguments?.getString("extractionId")!!, extractionViewModel
***REMOVED***
        ***REMOVED***

    ***REMOVED***
***REMOVED***

