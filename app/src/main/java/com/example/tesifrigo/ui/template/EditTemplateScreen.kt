package com.example.tesifrigo.ui.template

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tesifrigo.utils.EditableTextWithTitle
import com.example.tesifrigo.viewmodels.TemplateViewModel


@Composable
fun EditTemplateScreen(
    navController: NavHostController,
    templateId: Int
) {
    val viewModel = viewModel<TemplateViewModel>()

    val template = viewModel.templates.find { it.id == templateId ***REMOVED***
        ?: throw IllegalArgumentException("Template not found")
    Text(text = "${template.title***REMOVED***")
    LazyColumn {
        items(template.fields.size) { index ->  // Iterate over fields directly
            EditableTextWithTitle(
                title = template.fields[index].title,
                text = template.fields[index].description,
                modifier= Modifier.padding(6.dp),
                onTextChange = { newText ->
                    viewModel.updateTemplateItem(templateId, template.fields[index].id, newText)
                ***REMOVED***
***REMOVED***
            if(template.fields[index].extraDescription!=""){
                EditableTextWithTitle(
                    title = "extra description",
                    text = template.fields[index].extraDescription,
                    modifier= Modifier.padding(0.dp),
                    onTextChange = { newText ->
                        viewModel.updateTemplateItem(templateId, template.fields[index].id, newText)
                    ***REMOVED***
    ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
    // ... code to edit the template ...
***REMOVED***
