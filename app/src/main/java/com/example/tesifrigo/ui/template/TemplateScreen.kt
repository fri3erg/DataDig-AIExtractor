package com.example.tesifrigo.ui.template

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.tesifrigo.viewmodels.TemplateViewModel


@Composable
fun TemplateScreen(navController: NavHostController, viewModel: TemplateViewModel) {
    Column {
        // ... code to display the list of Templates ...
        viewModel.templates.forEach { template ->
            Button(onClick = { navController.navigate("editTemplate/${template.id***REMOVED***") ***REMOVED***) {
                Text("Edit ${template.title***REMOVED***")
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***

@Composable
fun EditTemplateScreen(navController: NavHostController, viewModel: TemplateViewModel, templateId: Int) {
    val template = viewModel.templates.find { it.id == templateId ***REMOVED***
        ?: throw IllegalArgumentException("Template not found")

    // ... code to edit the template ...
***REMOVED***
