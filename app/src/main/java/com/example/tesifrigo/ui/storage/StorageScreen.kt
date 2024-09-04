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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tesifrigo.R
import com.example.tesifrigo.Screen
import com.example.tesifrigo.models.Extraction
import com.example.tesifrigo.ui.theme.cyan_custom
import com.example.tesifrigo.ui.theme.light_gray
import com.example.tesifrigo.ui.theme.base_card_color
import com.example.tesifrigo.utils.SearchBar
import com.example.tesifrigo.utils.isFirstTimeVisit
import com.example.tesifrigo.viewmodels.ExtractionViewModel
import com.example.tesifrigo.viewmodels.SortOrder
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons

/**
 * Storage screen for managing extractions
 *
 * @param navController
 * @param extractionViewModel
 */
@Composable
fun StorageScreen(navController: NavHostController, extractionViewModel: ExtractionViewModel) {


    val searchText by extractionViewModel.searchText.collectAsState()
    val ascending by extractionViewModel.ascending.collectAsState()

    val extractions by extractionViewModel.sortedExtractions.collectAsState()

    val context = LocalContext.current
    var firstTimeModal by remember { mutableStateOf(false) ***REMOVED***
    val composableKey = "StorageScreen"
    if (isFirstTimeVisit(context, composableKey)) {
        firstTimeModal = true
    ***REMOVED***
    Scaffold(topBar = {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            // Search bar and sort options
            SearchBar(text = searchText,
                onTextChange = { extractionViewModel.updateSearchText(it) ***REMOVED***,
                onSearch = { extractionViewModel.updateSearchText(it) ***REMOVED***)
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                val sortOrder = extractionViewModel.sortOrder.collectAsState().value

                Row {
                    val sortOptions = listOf(SortOrder.BY_DATE, SortOrder.BY_TITLE)
                    Spacer(modifier = Modifier.width(10.dp))
                    sortOptions.forEach { option ->
                        Button(colors = ButtonDefaults.buttonColors(
                            containerColor = if (sortOrder == option) cyan_custom else light_gray,
                            contentColor = if (sortOrder == option) Color.White else Color.Black,
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
                Spacer(modifier = Modifier.width(10.dp))
            ***REMOVED***

        ***REMOVED***
    ***REMOVED***) { innerPadding -> //list of extractions
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            items(extractions) { extraction ->
                ExtractionItem(
                    extraction = extraction,
                    viewModel = extractionViewModel,
                    navController = navController
    ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        if (firstTimeModal) { // First time visit modal
            AlertDialog(title = {
                Text(stringResource(R.string.extractions))
            ***REMOVED***,
                text = {
                    Text(stringResource(R.string.here_you_can_manage_your_extractions_change_whatever_you_like_and_manage_the_extracted_files))
                ***REMOVED***,
                shape = RoundedCornerShape(8.dp),
                onDismissRequest = { firstTimeModal = false ***REMOVED***,
                confirmButton = {
                    Button(onClick = { firstTimeModal = false ***REMOVED***) {
                        Text("OK")
                    ***REMOVED***
                ***REMOVED***)
        ***REMOVED***
    ***REMOVED***
***REMOVED***

/**
 * Single Extraction item
 *
 * @param extraction
 * @param viewModel
 * @param navController
 */
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
        colors = CardDefaults.cardColors(
            containerColor = base_card_color, contentColor = Color.Black
        ),
        border = CardDefaults.outlinedCardBorder(),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)

    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth()
***REMOVED*** {
                Text(
                    text = extraction.title,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 20.sp
    ***REMOVED***
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {
                        showDeleteDialog = true
                    ***REMOVED***,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(top = 4.dp, end = 4.dp)
    ***REMOVED*** {
                    FaIcon(faIcon = FaIcons.Trash, tint = Color.Black)
                ***REMOVED***
            ***REMOVED***
            LazyRow(
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
***REMOVED*** {
                items(extraction.tags) { field ->
                    AssistChip(onClick = {***REMOVED***,
                        label = { Text(field) ***REMOVED***,
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = Color.White, labelColor = Color.Black
            ***REMOVED***

        ***REMOVED***
                    Spacer(modifier = Modifier.width(4.dp))
                ***REMOVED***

            ***REMOVED***

        ***REMOVED***


// Confirmation Dialog
        if (showDeleteDialog) {
            AlertDialog(onDismissRequest = { showDeleteDialog = false ***REMOVED***,
                title = { Text(stringResource(R.string.confirm_delete)) ***REMOVED***,
                text = { Text(stringResource(R.string.are_you_sure_you_want_to_delete_this_extraction)) ***REMOVED***,
                confirmButton = {
                    Button(onClick = {
                        viewModel.deleteExtraction(extraction.id.toHexString())
                        showDeleteDialog = false
                    ***REMOVED***) {
                        Text(stringResource(R.string.delete))
                    ***REMOVED***
                ***REMOVED***,
                dismissButton = {
                    Button(onClick = { showDeleteDialog = false ***REMOVED***) {
                        Text(stringResource(R.string.cancel))
                    ***REMOVED***
                ***REMOVED***)
        ***REMOVED***
    ***REMOVED***
***REMOVED***

