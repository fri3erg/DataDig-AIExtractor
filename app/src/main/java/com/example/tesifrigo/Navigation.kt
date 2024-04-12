package com.example.tesifrigo

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tesifrigo.ui.camera.CameraScreen
import com.example.tesifrigo.ui.settings.SettingsScreen
import com.example.tesifrigo.ui.storage.StorageScreen
import com.example.tesifrigo.ui.template.EditTemplateScreen
import com.example.tesifrigo.ui.template.TemplateScreen
import com.example.tesifrigo.viewmodels.TemplateViewModel
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons


@Composable
fun AppNavigation(viewModel: TemplateViewModel, navController: NavHostController) {
    NavHost(navController = navController, startDestination = "Templates") {
        composable("Camera") { CameraScreen() ***REMOVED***
        composable("Storage") { StorageScreen() ***REMOVED***
        composable("Settings") { SettingsScreen() ***REMOVED***
        composable("Templates") {
            TemplateScreen(navController, viewModel)
        ***REMOVED***
        composable(
            "editTemplate/{templateId***REMOVED***",
            arguments = listOf(navArgument("templateId") { type = NavType.IntType ***REMOVED***)
        ) { backStackEntry ->
            val templateId = backStackEntry.arguments?.getInt("templateId")
                ?: throw IllegalArgumentException("Missing templateId argument")
            EditTemplateScreen(navController, viewModel, templateId)
        ***REMOVED***

***REMOVED***
***REMOVED***

