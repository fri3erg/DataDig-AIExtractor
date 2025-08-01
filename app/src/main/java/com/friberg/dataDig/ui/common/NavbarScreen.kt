package com.friberg.dataDig.ui.common

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.friberg.dataDig.R
import com.friberg.dataDig.Screen
import com.friberg.dataDig.ui.theme.cyan_custom
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons


/**
 * Main Navbar for the app
 *
 * @param navController
 */
@Composable
fun NavBar(navController: NavHostController) {

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route ?: "unmatchable"
    NavigationBar(
        containerColor = cyan_custom
    ) {
        val templateSelected =
            currentDestination.startsWith(Screen.EditTemplate.route) || currentDestination.startsWith(
                Screen.Templates.route
***REMOVED***
        //Templates
        NavigationBarItem(colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.Black,
            selectedTextColor = Color.White,
            unselectedIconColor = Color.White,
            unselectedTextColor = Color.Black
        ),
            icon = {
                FaIcon(
                    faIcon = FaIcons.Table,
                    //tint = if(templateSelected) Color.Black else Color.White
    ***REMOVED***
            ***REMOVED***,
            label = { Text(stringResource(R.string.templates)) ***REMOVED***,
            selected = templateSelected,
            onClick = {
                navController.navigate(Screen.Templates.route)
            ***REMOVED***)
        val cameraSelected = currentDestination.startsWith(Screen.Camera.route)
        //Camera
        NavigationBarItem(colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.Black,
            selectedTextColor = Color.White,
            unselectedIconColor = Color.White,
            unselectedTextColor = Color.Black
        ),
            icon = {
                FaIcon(
                    faIcon = FaIcons.Camera,
                    //tint = if(cameraSelected) Color.Black else Color.White
    ***REMOVED***
            ***REMOVED***,
            label = { Text(stringResource(R.string.camera)) ***REMOVED***,
            selected = cameraSelected,
            onClick = {
                navController.navigate(Screen.Camera.route)
            ***REMOVED***)
        val storageSelected =
            currentDestination.startsWith(Screen.Storage.route) || currentDestination.startsWith(
                Screen.SingleExtraction.route
***REMOVED***
        //Storage
        NavigationBarItem(colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.Black,
            selectedTextColor = Color.White,
            unselectedIconColor = Color.White,
            unselectedTextColor = Color.Black
        ),
            icon = {
                FaIcon(
                    faIcon = FaIcons.CloudDownloadAlt,
                    //tint =  if(storageSelected) Color.Black else Color.White
    ***REMOVED***
            ***REMOVED***,
            label = { Text(stringResource(R.string.storage)) ***REMOVED***,
            selected = storageSelected,
            onClick = {
                navController.navigate(Screen.Storage.route)
            ***REMOVED***)
        val settingsSelected = currentDestination.startsWith(Screen.Settings.route)
        //Settings
        NavigationBarItem(colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.Black,
            selectedTextColor = Color.White,
            unselectedIconColor = Color.White,
            unselectedTextColor = Color.Black
        ),
            icon = {
                FaIcon(
                    faIcon = FaIcons.Cog,
                    //tint = if(settingsSelected) Color.Black else Color.White
    ***REMOVED***
            ***REMOVED***,
            label = { Text(stringResource(R.string.settings),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis) ***REMOVED***,
            selected = settingsSelected,
            onClick = {
                navController.navigate(Screen.Settings.route)
            ***REMOVED***)
    ***REMOVED***
***REMOVED***



