package com.example.tesifrigo.ui.extraction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tesifrigo.models.Extraction
import com.example.tesifrigo.models.ExtractionField
import com.example.tesifrigo.utils.EditableTextWithTitle
import com.example.tesifrigo.viewmodels.ExtractionViewModel



@Composable
fun SingleExtractionScreen(
    navController: NavHostController,
    templateId: String
) {
    val viewModel = viewModel<ExtractionViewModel>()

    val extraction by  viewModel.queryTemplate(templateId).collectAsState(initial = null)

    if (extraction != null) {
    Text(text = extraction!!.title)
    LazyColumn {
            items(extraction!!.fields.size) { index ->  // Iterate over fields directly
                ExtractionField(extraction!!, index, viewModel)
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***    else{
        Text(text = "No extraction found")
    ***REMOVED***

    // ... code to edit the template ...
***REMOVED***


@Composable
fun ExtractionField (
    extraction: Extraction,
    index: Int,
    viewModel: ExtractionViewModel
) {
    EditableTextWithTitle(
        title = extraction.fields[index].title,
        text = extraction.fields[index].description,
        modifier= Modifier.padding(6.dp),
        onTextChange = { newText ->
            viewModel.updateExtraction(extraction, "description" to newText , index)
        ***REMOVED***
    )
    if(extraction.fields[index].extraDescription!=""){
        EditableTextWithTitle(
            title = "extra description",
            text = extraction.fields[index].extraDescription,
            modifier= Modifier.padding(0.dp),
            onTextChange = { newText ->
                viewModel.updateExtraction(extraction, "extra" to newText , index)
            ***REMOVED***
        )
    ***REMOVED***
    EditableTextWithTitle(
        title = "Extracted text",
        text = extraction.fields[index].extracted,
        modifier= Modifier.padding(6.dp),
        onTextChange = { newText ->
            viewModel.updateExtraction(extraction, "extracted" to newText , index)
        ***REMOVED***
    )
    EditableTextWithTitle(
        title = "Type",
        text = extraction.fields[index].type,
        modifier= Modifier.padding(6.dp),
        onTextChange = { newText ->
            viewModel.updateExtraction(extraction, "type" to newText , index)
        ***REMOVED***
    )
    LazyRow {
            items(extraction.fields[index].tags) { field ->
                Box(modifier = Modifier.padding(8.dp)) {
                    Text(text = field, modifier = Modifier
                        .padding(8.dp)
                        .background(Color.Blue))
                ***REMOVED***
            ***REMOVED***
            // Add other template details here if needed

    ***REMOVED***
***REMOVED***
