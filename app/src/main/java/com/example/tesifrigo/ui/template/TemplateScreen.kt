package com.example.tesifrigo.ui.template

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import  androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tesifrigo.Screen
import com.example.tesifrigo.models.Template
import com.example.tesifrigo.ui.theme.cyan_custom
import com.example.tesifrigo.ui.theme.dark_blue
import com.example.tesifrigo.ui.theme.light_gray
import com.example.tesifrigo.ui.theme.vale
import com.example.tesifrigo.utils.DropdownWithNavigation
import com.example.tesifrigo.utils.SearchBar
import com.example.tesifrigo.viewmodels.SortOrder
import com.example.tesifrigo.viewmodels.TemplateViewModel
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons


@Composable
fun TemplateScreen(
    navController: NavHostController,
    templateViewModel: TemplateViewModel
) {
    val searchText by templateViewModel.searchText.collectAsState()
    val ascending by templateViewModel.ascending.collectAsState()
    val templates by templateViewModel.sortedTemplates.collectAsState()

    Scaffold(
        topBar = {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)) {

                SearchBar(
                    text = searchText,
                    onTextChange = { templateViewModel.updateSearchText(it) ***REMOVED***,
                    onSearch = { templateViewModel.updateSearchText(it) ***REMOVED***
    ***REMOVED***
                Spacer(modifier = Modifier.height(12.dp))
                Row {
                    val sortOrder = templateViewModel.sortOrder.collectAsState().value

                    Row {
                        val sortOptions = listOf(SortOrder.BY_TITLE, SortOrder.BY_DATE)

                        sortOptions.forEach { option ->
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (sortOrder == option) cyan_custom else light_gray, // Change color based on selection
                                    contentColor = if (sortOrder == option) Color.White else Color.Black, // Change text color based on selection
                    ***REMOVED***,
                                border= BorderStroke(1.dp, cyan_custom),
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(100.dp)
                                    .padding(start = 10.dp)
                                , // Add padding only to the first button
                                onClick = { templateViewModel.updateSortOrder(option) ***REMOVED***
                ***REMOVED*** {
                                Text(text = option.name.removePrefix("BY_").lowercase())
                            ***REMOVED***
                        ***REMOVED***
                    ***REMOVED***



                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = { templateViewModel.toggleAscending() ***REMOVED***,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .height(40.dp)
                            .width(60.dp)
                            .padding(end=2.dp),
                        colors = ButtonDefaults.buttonColors(contentColor = cyan_custom, containerColor = light_gray)
        ***REMOVED*** {

                            FaIcon(faIcon = if (ascending) FaIcons.SortUp else FaIcons.SortDown, tint = cyan_custom)
                        ***REMOVED***
                    Spacer(modifier = Modifier.width(20.dp))
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


            LazyColumn {
                items(templates) { template ->  // Iterate directly over templates
                    TemplateItem(template, navController, templateViewModel)
                ***REMOVED***
            ***REMOVED***

        ***REMOVED***
    ***REMOVED***

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
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = vale,
            contentColor = Color.Black
        ),
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
        ***REMOVED***
    ***REMOVED***
***REMOVED***

