package com.example.tesifrigo.ui.extraction

import androidx.compose.foundation.background
import androidx.compose .foundation.background
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
import com.example.tesifrigo.utils.TextWithTitle
import com.example.tesifrigo.viewmodels.ExtractionViewModel



@Composable
fun SingleExtractionScreen(
    navController: NavHostController,
    templateId: String
) {
    val viewModel = viewModel<ExtractionViewModel>()

    val extraction by  viewModel.queryTemplate(templateId).collectAsState(initial = null)

    if (extraction != null) {
        extraction!!.template?.let { Text(text = it.title) ***REMOVED***
    LazyColumn {
            items(extraction!!.extractedFields.size) { index ->  // Iterate over fields directly
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
    extraction.extractedFields[index].templateField?.let {
        extraction.extractedFields[index].templateField?.let { it1 ->
            TextWithTitle(
            title = it.title,
            text = it1.description,
            modifier= Modifier.padding(6.dp),

    ***REMOVED***
        ***REMOVED***
    ***REMOVED***
    if(extraction.extractedFields[index].templateField?.extraDescription !=""){
        extraction.extractedFields[index].templateField?.let {
            TextWithTitle(
                title = "extra description",
                text = it.extraDescription,
                modifier= Modifier.padding(0.dp),

***REMOVED***
        ***REMOVED***
    ***REMOVED***
    EditableTextWithTitle(
        title = "Extracted text",
        text = extraction.extractedFields[index].value,
        modifier= Modifier.padding(6.dp),
        onTextChange = { newText ->
            viewModel.updateExtraction(extraction, newText , index)
        ***REMOVED***
    )
    extraction.extractedFields[index].templateField?.let {
        TextWithTitle(
        title = "Type",
        text = it.type,
        modifier= Modifier.padding(6.dp),

***REMOVED***
    ***REMOVED***
    LazyRow {
            extraction.extractedFields[index].templateField?.let {
                items(it.tags) { field ->
                    Box(modifier = Modifier.padding(8.dp)) {
                        Text(text = field, modifier = Modifier
                            .padding(8.dp)
                            .background(Color.Blue))
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
            // Add other template details here if needed

    ***REMOVED***
***REMOVED***
