package com.example.tesifrigo.ui.template

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tesifrigo.utils.EditableTextWithTitle
import com.example.tesifrigo.viewmodels.TemplateViewModel


@Composable
fun EditTemplateScreen(
    navController: NavHostController,
    templateId: String
) {
    val viewModel = viewModel<TemplateViewModel>()

    val template by  viewModel.queryTemplate(templateId).collectAsState(initial = null)
    Text(text = "${template?.title***REMOVED***")
    LazyColumn {
        if (template != null) {
            items(template?.fields?.size?:0) { index ->  // Iterate over fields directly
                template?.fields?.get(index)?.let {
                    EditableTextWithTitle(
                        title = it.title,
                        text = template?.fields!![index].description,
                        modifier= Modifier.padding(6.dp),
                        onTextChange = { newText ->
                            viewModel.updateTemplateItem(template!!, "description" to newText , index)
                        ***REMOVED***
        ***REMOVED***
                ***REMOVED***
                    if(template!!.fields[index].extraDescription!=""){
                        EditableTextWithTitle(
                            title = "extra description",
                            text = template!!.fields[index].extraDescription,
                            modifier= Modifier.padding(0.dp),
                            onTextChange = { newText ->
                                viewModel.updateTemplateItem(template!!, "extra" to newText , index)
                ***REMOVED***
            ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        ***REMOVED***
    ***REMOVED***
    Button(onClick = { viewModel.addField(template!!) ***REMOVED***) {
        Text("Add Field")
    ***REMOVED***
    // ... code to edit the template ...
***REMOVED***
