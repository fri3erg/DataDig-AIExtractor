package com.example.tesifrigo.ui.common

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons


@Composable
fun NavBar(navController: NavHostController) {

    val items = listOf(
        NavItem("Templates","Templates") { FaIcon(faIcon = FaIcons.Table, tint = LocalContentColor.current) ***REMOVED***,
        NavItem("Camera","Camera") { FaIcon(faIcon = FaIcons.Camera, tint = LocalContentColor.current) ***REMOVED***,
        NavItem("Storage", "Storage") { FaIcon(faIcon = FaIcons.CloudDownloadAlt, tint = LocalContentColor.current) ***REMOVED***
    )
    val (selectedItem, setSelectedItem) =  remember { mutableStateOf(0) ***REMOVED*** // Hoisted state

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon =item.icon,
                label = { Text(item.label) ***REMOVED***,
                selected = selectedItem == index,
                onClick = {
                    setSelectedItem(index)
                    navController.navigate(item.route)
                ***REMOVED***
***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***



data class NavItem(val label: String, val route: String = label, val icon: @Composable () -> Unit)
