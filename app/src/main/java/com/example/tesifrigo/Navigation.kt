package com.example.tesifrigo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.tesifrigo.ui.camera.CameraOpenScreen
import com.example.tesifrigo.ui.settings.SettingsScreen
import com.example.tesifrigo.ui.storage.StorageScreen
import com.example.tesifrigo.ui.template.EditTemplateScreen
import com.example.tesifrigo.ui.template.TemplateScreen
import kotlinx.serialization.Serializable


@Composable
fun AppNavigation( navController: NavHostController, modifier: Modifier = Modifier) {

    NavHost(navController = navController, startDestination = Templates, modifier = modifier) {
        composable<Camera> {
            val args= it.toRoute<EditTemplate>()
            CameraOpenScreen(args.templateId) ***REMOVED***
        composable<Storage> { StorageScreen(navController) ***REMOVED***
        composable<Settings> { SettingsScreen() ***REMOVED***
        composable<Templates> {
            val args= it.toRoute<Templates>()
            TemplateScreen(navController, args.photos)
        ***REMOVED***
        composable<EditTemplate> {
            val args= it.toRoute<EditTemplate>()
            EditTemplateScreen(navController, args.templateId)
        ***REMOVED***
        composable<SingleExtraction>{
            val args= it.toRoute<SingleExtraction>()

        ***REMOVED***

***REMOVED***
***REMOVED***

@Serializable
data class Templates(val photos: String?)

@Serializable
data class EditTemplate(val templateId: String)

@Serializable
object Storage

@Serializable
data class SingleExtraction(val extractionId: String)

@Serializable
data class Camera(val templateId: String?)

@Serializable
object Settings
