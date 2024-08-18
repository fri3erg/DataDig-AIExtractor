package com.example.tesifrigo.ui.storage

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tesifrigo.Screen
import com.example.tesifrigo.models.Extraction
import com.example.tesifrigo.ui.theme.cyan_custom
import com.example.tesifrigo.ui.theme.light_gray
import com.example.tesifrigo.utils.SearchBar
import com.example.tesifrigo.viewmodels.ExtractionViewModel
import com.example.tesifrigo.viewmodels.SortOrder
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StorageScreen(navController: NavHostController, extractionViewModel: ExtractionViewModel) {


    val searchText by extractionViewModel.searchText.collectAsState()
    var expanded by remember { mutableStateOf(false) ***REMOVED***
    val ascending by extractionViewModel.ascending.collectAsState()

    val extractions by extractionViewModel.sortedExtractions.collectAsState()
    Scaffold(topBar = {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {

            SearchBar(text = searchText,
                onTextChange = { extractionViewModel.updateSearchText(it) ***REMOVED***,
                onSearch = { extractionViewModel.updateSearchText(it) ***REMOVED***)
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                val sortOrder = extractionViewModel.sortOrder.collectAsState().value

                Row {
                    val sortOptions = listOf(SortOrder.BY_TITLE, SortOrder.BY_DATE)

                    sortOptions.forEach { option ->
                        Button(colors = ButtonDefaults.buttonColors(
                            containerColor = if (sortOrder == option) cyan_custom else light_gray, // Change color based on selection
                            contentColor = if (sortOrder == option) Color.White else Color.Black, // Change text color based on selection
            ***REMOVED***,
                            border = BorderStroke(1.dp, cyan_custom),
                            modifier = Modifier
                                .height(40.dp)
                                .width(100.dp)
                                .padding(start = 10.dp), // Add padding only to the first button
                            onClick = { extractionViewModel.updateSortOrder(option) ***REMOVED***) {
                            Text(text = option.name.removePrefix("BY_").lowercase())
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***



                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { extractionViewModel.toggleAscending() ***REMOVED***,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .height(40.dp)
                        .width(60.dp)
                        .padding(end = 2.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = cyan_custom, containerColor = light_gray
        ***REMOVED***
    ***REMOVED*** {

                    FaIcon(
                        faIcon = if (ascending) FaIcons.SortUp else FaIcons.SortDown,
                        tint = cyan_custom
        ***REMOVED***
                ***REMOVED***
                Spacer(modifier = Modifier.width(20.dp))
            ***REMOVED***

        ***REMOVED***
    ***REMOVED***) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {

            Row {

                val focusRequester = remember { FocusRequester() ***REMOVED***
                val typeOptions =
                    mapOf("Title" to { extractionViewModel.updateSortOrder(SortOrder.BY_TITLE) ***REMOVED***,
                        "Date" to { extractionViewModel.updateSortOrder(SortOrder.BY_DATE) ***REMOVED***)
                Spacer(modifier = Modifier.width(20.dp))
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded ***REMOVED***) {

                    Button(
                        onClick = { expanded = true ***REMOVED***,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .focusRequester(focusRequester)
                            .menuAnchor(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ***REMOVED*** {
                        Text("Sort")
                    ***REMOVED***
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false ***REMOVED***) {
                        typeOptions.forEach { (type, onCLick) ->
                            DropdownMenuItem(text = { Text(type) ***REMOVED***, onClick = {
                                onCLick()
                                expanded = false
                            ***REMOVED***)
                        ***REMOVED***
                    ***REMOVED***

                ***REMOVED***




                Button(
                    onClick = { extractionViewModel.toggleAscending() ***REMOVED***,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
    ***REMOVED*** {
                    if (ascending) {
                        FaIcon(faIcon = FaIcons.ArrowUp, tint = Color.White)
                    ***REMOVED*** else {
                        FaIcon(faIcon = FaIcons.ArrowDown, tint = Color.White)
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
            LazyColumn {
                items(extractions) { extraction ->
                    ExtractionItem(
                        extraction = extraction,
                        viewModel = extractionViewModel,
                        navController = navController
        ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***


    ***REMOVED***
***REMOVED***

@Composable
fun ExtractionItem(
    extraction: Extraction, viewModel: ExtractionViewModel, navController: NavHostController
) {
    var showDeleteDialog by remember { mutableStateOf(false) ***REMOVED***

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate(Screen.SingleExtraction.withArgs("extractionId" to extraction.id.toHexString()))
            ***REMOVED***,
        border = CardDefaults.outlinedCardBorder(),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp) // Add elevation for better visuals

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth()
***REMOVED*** {
                Text(
                    text = extraction.title
    ***REMOVED***
                Spacer(modifier = Modifier.weight(1f)) // Creates space between text and button
                IconButton(
                    onClick = {
                        showDeleteDialog = true
                    ***REMOVED***, modifier = Modifier.align(Alignment.CenterVertically)
    ***REMOVED*** {
                    FaIcon(faIcon = FaIcons.Trash, tint = Color.Gray)
                ***REMOVED***
            ***REMOVED***
            LazyRow {
                items(extraction.tags) { field ->
                    AssistChip(onClick = {***REMOVED***, label = { Text(field) ***REMOVED***)
                    Spacer(modifier = Modifier.width(4.dp))
                ***REMOVED***

            ***REMOVED***

        ***REMOVED***


// Confirmation Dialog
        if (showDeleteDialog) {
            AlertDialog(onDismissRequest = { showDeleteDialog = false ***REMOVED***,
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
                ***REMOVED***)
        ***REMOVED***
    ***REMOVED***
***REMOVED***

