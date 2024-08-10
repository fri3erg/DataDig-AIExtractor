package com.example.tesifrigo.ui.template

import  androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tesifrigo.Screen
import com.example.tesifrigo.models.Template
import com.example.tesifrigo.utils.DropdownWithNavigation
import com.example.tesifrigo.viewmodels.SortOrder
import com.example.tesifrigo.viewmodels.TemplateViewModel
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateScreen(
    navController: NavHostController,
    photos: String?,
    templateViewModel: TemplateViewModel
) {
    val searchText by templateViewModel.searchText.collectAsState()
    var expanded by remember { mutableStateOf(false) ***REMOVED***
    val ascending by templateViewModel.ascending.collectAsState()
    val sortOrder: SortOrder by templateViewModel.sortOrder.collectAsState()
    val templates by templateViewModel.sortedTemplates.collectAsState()

    Scaffold(
        topBar = {
            Row {
                SearchBar(
                    text = searchText,
                    onTextChange = { templateViewModel.updateSearchText(it) ***REMOVED***,
                    onSearch = { templateViewModel.updateSearchText(it) ***REMOVED***
    ***REMOVED***


                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {
                        navController.navigate(Screen.Settings.route)
                    ***REMOVED***,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 15.dp, top = 2.dp)
    ***REMOVED*** {
                    FaIcon(faIcon = FaIcons.Cog, tint = Color.Gray, size = 45.dp)
                ***REMOVED***

            ***REMOVED***
        ***REMOVED***,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val newId = templateViewModel.addTemplate()
                    navController.navigate(Screen.EditTemplate.withArgs("templateId" to newId))
                ***REMOVED***,
                containerColor = MaterialTheme.colorScheme.primary,
***REMOVED*** {
                FaIcon(faIcon = FaIcons.Plus, tint = Color.White, size = 30.dp)
            ***REMOVED***
        ***REMOVED***,

        ) { innerPadding ->


        Column(
            modifier = Modifier.padding(innerPadding)
        ) {

            Row {

                val focusRequester = remember { FocusRequester() ***REMOVED***
                val typeOptions = mapOf(
                    "Title" to { templateViewModel.updateSortOrder(SortOrder.BY_TITLE) ***REMOVED***,
                    "Date" to { templateViewModel.updateSortOrder(SortOrder.BY_DATE) ***REMOVED***
    ***REMOVED***
                Spacer(modifier = Modifier.width(20.dp))
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded ***REMOVED***
    ***REMOVED*** {

                    Button(
                        onClick = { expanded = true ***REMOVED***, modifier = Modifier
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
                            DropdownMenuItem(
                                text = { Text(type) ***REMOVED***,
                                onClick = {
                                    onCLick()
                                    expanded = false
                                ***REMOVED***
                ***REMOVED***
                        ***REMOVED***
                    ***REMOVED***

                ***REMOVED***




                Button(
                    onClick = { templateViewModel.toggleAscending() ***REMOVED***,
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
                items(templates) { template ->  // Iterate directly over templates
                    TemplateItem(template, navController, templateViewModel)
                ***REMOVED***
            ***REMOVED***

        ***REMOVED***
    ***REMOVED***

***REMOVED***

@Composable
fun SearchBar(text: String, onTextChange: (String) -> Unit, onSearch: (String) -> Unit) {
    OutlinedTextField(
        value = text,
        onValueChange = {
            onTextChange(it) // Update the ViewModel's searchText state
            onSearch(it) // Trigger the search function (optional)
        ***REMOVED***,
        label = { Text("Search") ***REMOVED***,
        modifier = Modifier
            .padding(16.dp),
        singleLine = true, // Ensure single-line input
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
***REMOVED***
        ***REMOVED***
    )
***REMOVED***

@Composable
fun TemplateItem(
    template: Template,
    navController: NavHostController,
    viewmodel: TemplateViewModel
) {
    Card( // Consider using a Card for visual structure
        modifier = Modifier
            .fillMaxWidth() // Occupy full width
            .clickable {
                navController.navigate(Screen.EditTemplate.withArgs("templateId" to template.id.toHexString()))
            ***REMOVED*** // Make the entire item clickable
            .padding(16.dp),
        border = CardDefaults.outlinedCardBorder(), // Add a border
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp) // Add elevation for better visuals

    ) {
        Column(modifier = Modifier.padding(16.dp)) { // Inner Column for content
            Row {

                Text(
                    text = template.title, style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(top = 8.dp, start = 8.dp)
    ***REMOVED*** // Title
                Spacer(modifier = Modifier.weight(1f)) // Creates space between text and button
                val id = template.id.toHexString()
                val onUse = {
                    navController.navigate(Screen.Camera.routeWithOptionalArgs("templateId" to id))
                ***REMOVED***
                val onEdit =
                    { navController.navigate(Screen.EditTemplate.withArgs("templateId" to id)) ***REMOVED***
                DropdownWithNavigation(
                    onUse = onUse,
                    onEdit = onEdit,
                    onDelete = { viewmodel.deleteTemplateById(id) ***REMOVED***)
            ***REMOVED***
            LazyRow {
                items(template.tags) { field ->
                    AssistChip(
                        onClick = {***REMOVED***,
                        label = { Text(field) ***REMOVED***
        ***REMOVED***
                    Spacer(modifier = Modifier.width(4.dp))
                ***REMOVED***

                // Add other template details here if needed
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***

