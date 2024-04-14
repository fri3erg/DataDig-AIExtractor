package com.example.tesifrigo.ui.template

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tesifrigo.viewmodels.TemplateViewModel


@Composable
fun EditTemplateScreen(
    navController: NavHostController,
    templateId: Int
) {
    val viewModel = viewModel<TemplateViewModel>()

    val template = viewModel.templates.find { it.id == templateId ***REMOVED***
        ?: throw IllegalArgumentException("Template not found")
    Text(text = "Editing template: ${template.title***REMOVED***")
    // ... code to edit the template ...
***REMOVED***
