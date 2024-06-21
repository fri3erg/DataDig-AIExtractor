package com.example.tesifrigo.ui.storage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tesifrigo.Screen
import com.example.tesifrigo.models.Extraction
import com.example.tesifrigo.ui.template.SearchBar
import com.example.tesifrigo.viewmodels.ExtractionViewModel
import com.example.tesifrigo.viewmodels.SortOrder

@Composable
fun StorageScreen(navController: NavHostController) {


    val extractionViewModel = viewModel<ExtractionViewModel>()
    val searchText by extractionViewModel.searchText.collectAsState()
    var expanded by remember { mutableStateOf(true) ***REMOVED***
    val ascending by extractionViewModel.ascending.collectAsState()
    val sortOrder : SortOrder by extractionViewModel.sortOrder.collectAsState()

    val extractions by extractionViewModel.sortedExtractions.collectAsState()
    Column {
        Row {
            SearchBar(
                text = searchText,
                onTextChange = { extractionViewModel.updateSearchText(it)***REMOVED***,
                onSearch = {  extractionViewModel.updateSearchText(it) ***REMOVED***
***REMOVED***


            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = {
                navController.navigate(Screen.Settings.route)
            ***REMOVED***) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
            ***REMOVED***
        ***REMOVED***
        Button(onClick = { expanded = true ***REMOVED***) {
            Text("Sort")
        ***REMOVED***
        DropdownMenu(
            modifier = Modifier.padding(start = 16.dp),

            expanded = expanded,
            onDismissRequest = { expanded = false ***REMOVED***
        ) {

            DropdownMenuItem(
                text = { Text("Title") ***REMOVED***,
                onClick = {
                    extractionViewModel.updateSortOrder(SortOrder.BY_TITLE)
                    expanded = false
                ***REMOVED***)
            DropdownMenuItem(
                text = { Text("Date") ***REMOVED***,
                onClick = {
                    extractionViewModel.updateSortOrder(SortOrder.BY_DATE)
                    expanded = false
                ***REMOVED***)
        ***REMOVED***
        Button(onClick = { extractionViewModel.toggleAscending()***REMOVED***) {
            Text(if (ascending) "A" else "V")
        ***REMOVED***
    LazyColumn (modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)){
        items(extractions) { extraction ->
            ExtractionItem(
                extraction = extraction,
                viewModel =extractionViewModel,
                navController = navController
***REMOVED***
        ***REMOVED***
    ***REMOVED***
        ***REMOVED***


***REMOVED***

@Composable
fun ExtractionItem(extraction: Extraction, viewModel:ExtractionViewModel, navController: NavHostController) {
    var showMenu by remember { mutableStateOf(false) ***REMOVED***
    var showDeleteDialog by remember { mutableStateOf(false) ***REMOVED***

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
            Text(extraction.description, style = MaterialTheme.typography.bodyMedium)
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
                    DropdownMenuItem(onClick = {
                        navController.navigate(Screen.SingleExtraction.withArgs("extractionId" to extraction.id.toHexString()))
                        showMenu = false
                    ***REMOVED***,
                        text = { Text("Edit") ***REMOVED***
        ***REMOVED***
                    DropdownMenuItem(onClick = { showDeleteDialog = true; showMenu = false ***REMOVED***,
                        text = { Text("Delete") ***REMOVED***)
                ***REMOVED***
            ***REMOVED***
            Row {
                val template = extraction.template // Create a local copy

                if (template != null) {
                    Box(modifier = Modifier.padding(8.dp).background(color = Color.Blue)) {
                        Text(text = template.title, modifier = Modifier.padding(8.dp))
                    ***REMOVED***
                ***REMOVED***
                Box(modifier = Modifier.padding(8.dp).background(color = Color.Yellow)) {
                    Text(text = extraction.format, modifier = Modifier.padding(8.dp))
                ***REMOVED***
                LazyRow {
                    items(extraction.tags) { field ->
                        Box(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = field, modifier = Modifier
                                    .padding(8.dp)
                                    .background(Color.Blue)
                ***REMOVED***
                        ***REMOVED***
                    ***REMOVED***

                ***REMOVED***
            ***REMOVED***
        ***REMOVED***


// Confirmation Dialog
        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false ***REMOVED***,
                title = { Text("Confirm Delete") ***REMOVED***, // Confirm the action
                text = { Text("Are you sure you want to delete this extraction?") ***REMOVED***,
                confirmButton = {
                    Button(onClick = {
                        viewModel.deleteExtraction(extraction.id.toHexString()) // Delete the template
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
***REMOVED***
        ***REMOVED*** ***REMOVED***
***REMOVED***

