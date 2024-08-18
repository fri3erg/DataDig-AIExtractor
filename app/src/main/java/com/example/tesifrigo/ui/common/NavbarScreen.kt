package com.example.tesifrigo.ui.common

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tesifrigo.Screen
import com.example.tesifrigo.ui.theme.cyan_custom
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons


@Composable
fun NavBar(navController: NavHostController) {

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route ?: "unmatchable"
    NavigationBar(
        containerColor = cyan_custom
    ) {
        NavigationBarItem(colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.Black,
            selectedTextColor = Color.White,
            unselectedIconColor = Color.White,
            unselectedTextColor = Color.Black
        ),
            icon = { FaIcon(faIcon = FaIcons.Table) ***REMOVED***,
            label = { Text("Templates") ***REMOVED***,
            selected = currentDestination.startsWith(Screen.EditTemplate.route) || currentDestination.startsWith(
                Screen.Templates.route
***REMOVED***,
            onClick = {
                navController.navigate(Screen.Templates.route)
            ***REMOVED***)
        NavigationBarItem(colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.Black,
            selectedTextColor = Color.White,
            unselectedIconColor = Color.White,
            unselectedTextColor = Color.Black
        ),
            icon = { FaIcon(faIcon = FaIcons.Camera) ***REMOVED***,
            label = { Text("Camera") ***REMOVED***,
            selected = currentDestination.startsWith(Screen.Camera.route),
            onClick = {
                navController.navigate(Screen.Camera.route)
            ***REMOVED***)
        NavigationBarItem(colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.Black,
            selectedTextColor = Color.White,
            unselectedIconColor = Color.White,
            unselectedTextColor = Color.Black
        ),
            icon = { FaIcon(faIcon = FaIcons.CloudDownloadAlt) ***REMOVED***,
            label = { Text("Storage") ***REMOVED***,
            selected = currentDestination.startsWith(Screen.Storage.route) || currentDestination.startsWith(
                Screen.SingleExtraction.route
***REMOVED***,
            onClick = {
                navController.navigate(Screen.Storage.route)
            ***REMOVED***)
        NavigationBarItem(colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.Black,
            selectedTextColor = Color.White,
            unselectedIconColor = Color.White,
            unselectedTextColor = Color.Black
        ),
            icon = { FaIcon(faIcon = FaIcons.Cog) ***REMOVED***,
            label = { Text("Settings") ***REMOVED***,
            selected = currentDestination.startsWith(Screen.Settings.route),
            onClick = {
                navController.navigate(Screen.Settings.route)
            ***REMOVED***)
    ***REMOVED***
***REMOVED***



