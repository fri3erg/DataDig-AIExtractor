package com.example.tesifrigo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tesifrigo.ui.camera.CameraOpenScreen
import com.example.tesifrigo.ui.camera.CameraScreen
import com.example.tesifrigo.ui.settings.SettingsScreen
import com.example.tesifrigo.ui.storage.StorageScreen
import com.example.tesifrigo.ui.template.EditTemplateScreen
import com.example.tesifrigo.ui.template.TemplateScreen
import com.example.tesifrigo.viewmodels.TemplateViewModel


@Composable
fun AppNavigation( navController: NavHostController, modifier: Modifier = Modifier) {

    NavHost(navController = navController, startDestination = "Templates", modifier = modifier) {
        composable("Camera") { CameraOpenScreen() ***REMOVED***
        composable("Storage") { StorageScreen() ***REMOVED***
        composable("Settings") { SettingsScreen() ***REMOVED***
        composable("Templates") {
            TemplateScreen(navController)
        ***REMOVED***
        composable(
            "editTemplate/{templateId***REMOVED***",
            arguments = listOf(navArgument("templateId") { type = NavType.IntType ***REMOVED***)
        ) { backStackEntry ->
            val templateId = backStackEntry.arguments?.getInt("templateId")
                ?: throw IllegalArgumentException("Missing templateId argument")
            EditTemplateScreen(navController, templateId)
        ***REMOVED***

***REMOVED***
***REMOVED***

