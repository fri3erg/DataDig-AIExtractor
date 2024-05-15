package com.example.tesifrigo.ui.storage

import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tesifrigo.model.Extraction
import com.example.tesifrigo.viewmodels.ExtractionViewModel
import com.example.tesifrigo.viewmodels.TemplateViewModel

@Composable
fun StorageScreen(extractionViewModel: ExtractionViewModel) {

    val extractionViewModel = viewModel<ExtractionViewModel>()

    val extractions by extractionViewModel.extractions.collectAsState()

    LazyColumn (modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)){
        items(extractions) { extraction ->
            ExtractionItem(
                extraction = extraction,
                onEdit = extractionViewModel::updateExtraction,
                onDelete = extractionViewModel::deleteExtraction
***REMOVED***
        ***REMOVED***
    ***REMOVED***

    // LaunchedEffect to add initial extractions when the ViewModel is first created
    LaunchedEffect(Unit) {
        if (extractions.isEmpty()) {
            //extractionViewModel.addInitialExtractions()
        ***REMOVED***
    ***REMOVED***
***REMOVED***

@Composable
fun ExtractionItem(extraction: Extraction, onEdit: (Extraction) -> Unit, onDelete: (Extraction) -> Unit) {
    var showMenu by remember { mutableStateOf(false) ***REMOVED***

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp) // Add elevation for better visuals

    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(extraction.title, style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(4.dp))
            Text(extraction.shortDescription, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
***REMOVED*** {
                IconButton(onClick = { showMenu = true ***REMOVED***) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                ***REMOVED***
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false ***REMOVED***
    ***REMOVED*** {
                    DropdownMenuItem(onClick = { onEdit(extraction); showMenu = false ***REMOVED***,
                    text= { Text("Edit") ***REMOVED***
        ***REMOVED***
                    DropdownMenuItem(onClick = { onDelete(extraction); showMenu = false ***REMOVED***,
                        text ={Text("Delete") ***REMOVED***)
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***

