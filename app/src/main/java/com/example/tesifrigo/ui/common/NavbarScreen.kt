package com.example.tesifrigo.ui.common

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.example.tesifrigo.Screen
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons


@Composable
fun NavBar(navController: NavHostController) {

    val (selectedItem, setSelectedItem) = remember { mutableStateOf("Templates") ***REMOVED*** // Hoisted state

    NavigationBar {
        NavigationBarItem(
            icon = { FaIcon(faIcon = FaIcons.Table, tint = LocalContentColor.current) ***REMOVED***,
            label = { Text("Templates") ***REMOVED***,
            selected = selectedItem == "Templates",
            onClick = {
                setSelectedItem("Templates")
                navController.navigate(Screen.Templates.route)
            ***REMOVED***
        )
        NavigationBarItem(
            icon = { FaIcon(faIcon = FaIcons.Camera, tint = LocalContentColor.current) ***REMOVED***,
            label = { Text("Camera") ***REMOVED***,
            selected = selectedItem == "Camera",
            onClick = {
                setSelectedItem("Camera")
                navController.navigate(Screen.Camera.route)
            ***REMOVED***
        )
        NavigationBarItem(
            icon = { FaIcon(faIcon = FaIcons.CloudDownloadAlt, tint = LocalContentColor.current) ***REMOVED***,
            label = { Text("Storage") ***REMOVED***,
            selected = selectedItem == "Storage",
            onClick = {
                setSelectedItem("Storage")
                navController.navigate(Screen.Storage.route)
            ***REMOVED***
        )
    ***REMOVED***
***REMOVED***



