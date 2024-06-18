package com.example.tesifrigo.ui.template

import androidx.compose.foundation.background
import  androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tesifrigo.Screen
import com.example.tesifrigo.models.Template
import com.example.tesifrigo.viewmodels.TemplateViewModel


@Composable
fun TemplateScreen(navController: NavHostController, photos: String?) {
    val viewModel = viewModel<TemplateViewModel>()
    val templates by viewModel.templates.collectAsState()
    LazyColumn {
        items(templates) { template ->  // Iterate directly over templates
            TemplateItem(template = template,navController)
        ***REMOVED***
    ***REMOVED***
    Button(onClick = {
        val newId= viewModel.addTemplate()
        navController.navigate(Screen.EditTemplate.withArgs("templateId" to newId))
    ***REMOVED***) {
        Text("Add Template")
    ***REMOVED***
***REMOVED***

@Composable
fun TemplateItem(template: Template,navController: NavHostController) {
    Card( // Consider using a Card for visual structure
        modifier = Modifier
            .fillMaxWidth() // Occupy full width
            .clickable {
                navController.navigate(Screen.EditTemplate.withArgs("templateId" to template.id.toHexString()))
            ***REMOVED*** // Make the entire item clickable
            .padding(16.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) { // Inner Column for content
            Row {

                Text(text = template.title)
                Spacer(modifier = Modifier.weight(1f)) // Creates space between text and button
                DropdownWithNavigation(navController, template.id.toHexString())
            ***REMOVED***
            LazyRow {
                items(template.tags) { field ->
                    Box(modifier = Modifier.padding(8.dp)) {
                        Text(text = field, modifier = Modifier.padding(8.dp).background(Color.Blue))
                    ***REMOVED***
                ***REMOVED***
                // Add other template details here if needed
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***

@Composable
fun DropdownWithNavigation(navController: NavHostController, id: String) {
    var expanded by remember { mutableStateOf(false) ***REMOVED***
    var showDeleteDialog by remember { mutableStateOf(false) ***REMOVED***

    Button(onClick = { expanded = true ***REMOVED***, modifier=Modifier
        .padding(start = 16.dp)


    ) {
        Text("Options")
    ***REMOVED***

    DropdownMenu(
        expanded = expanded,
        offset = DpOffset(x = 300.dp, y = (-50).dp), // Example offset values
        modifier = Modifier.padding(horizontal =16.dp),
        onDismissRequest = { expanded = false ***REMOVED***
    ) {

        DropdownMenuItem(onClick = {navController.navigate(Screen.Camera.routeWithOptionalArgs( "templateId" to id) ); expanded = false ***REMOVED***,
            text= { Text("Use") ***REMOVED***
        )
        DropdownMenuItem(onClick = {navController.navigate(Screen.EditTemplate.withArgs("templateId" to id) ); expanded = false ***REMOVED***,
            text ={Text("Edit") ***REMOVED***)
        DropdownMenuItem(onClick = {showDeleteDialog=true; expanded = false ***REMOVED***,
            text ={Text("Delete") ***REMOVED***)
    ***REMOVED***


    // Confirmation Dialog
    if (showDeleteDialog) {
        val viewModel = viewModel<TemplateViewModel>()
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false ***REMOVED***,
            title = { Text("Confirm Delete") ***REMOVED***, // Confirm the action
            text = { Text("Are you sure you want to delete this template?") ***REMOVED***,
            confirmButton = {
                Button(onClick = {
                    viewModel.deleteTemplateById(id) // Delete the template
                    showDeleteDialog = false // Close dialog
                ***REMOVED***) {
                    Text("Delete")
                ***REMOVED***
            ***REMOVED***,
            dismissButton = {
                Button(onClick = { showDeleteDialog = false ***REMOVED***) {
                    Text("Cancel")
                ***REMOVED***
            ***REMOVED***
        )
    ***REMOVED***
***REMOVED***

