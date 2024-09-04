package com.example.tesifrigo.ui.template

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tesifrigo.R
import com.example.tesifrigo.Screen
import com.example.tesifrigo.models.Template
import com.example.tesifrigo.ui.theme.cyan_custom
import com.example.tesifrigo.ui.theme.light_gray
import com.example.tesifrigo.ui.theme.base_card_color
import com.example.tesifrigo.utils.DropdownWithNavigation
import com.example.tesifrigo.utils.SearchBar
import com.example.tesifrigo.utils.isFirstTimeVisit
import com.example.tesifrigo.viewmodels.ServiceViewModel
import com.example.tesifrigo.viewmodels.SortOrder
import com.example.tesifrigo.viewmodels.TemplateViewModel
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons


/**
 * Template screen composable, which displays a list of templates
 *
 * @param navController The navigation controller
 * @param templateViewModel The view model for templates
 * @param serviceViewModel The view model for services
 */
@Composable
fun TemplateScreen(
    navController: NavHostController,
    templateViewModel: TemplateViewModel,
    serviceViewModel: ServiceViewModel
) {
    val searchText by templateViewModel.searchText.collectAsState()
    val ascending by templateViewModel.ascending.collectAsState()
    val templates by templateViewModel.sortedTemplates.collectAsState()
    val context = LocalContext.current
    var firstTimeModal by remember { mutableStateOf(false) ***REMOVED***
    val composableKey = "TemplateScreen"
    if (isFirstTimeVisit(context, composableKey)) {
        templateViewModel.createSampleTemplates(context)
        firstTimeModal = true
    ***REMOVED***

    Scaffold(
        topBar = {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)) { //Search bar and sort options

                SearchBar(
                    text = searchText,
                    onTextChange = { templateViewModel.updateSearchText(it) ***REMOVED***,
                    onSearch = { templateViewModel.updateSearchText(it) ***REMOVED***
    ***REMOVED***
                Spacer(modifier = Modifier.height(12.dp))
                Row {
                    val sortOrder = templateViewModel.sortOrder.collectAsState().value

                    Row {
                        val sortOptions = listOf( SortOrder.BY_DATE,SortOrder.BY_TITLE)
                        Spacer(modifier = Modifier.width(10.dp))
                        sortOptions.forEach { option ->
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (sortOrder == option) cyan_custom else light_gray,
                                    contentColor = if (sortOrder == option) Color.White else Color.Black,
                    ***REMOVED***,
                                border= BorderStroke(1.dp, cyan_custom),
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(100.dp)
                                    .padding(start = 10.dp),
                                onClick = {
                                    templateViewModel.updateSortOrder(option) ***REMOVED***
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
                            .padding(end = 2.dp),
                        colors = ButtonDefaults.buttonColors(contentColor = cyan_custom, containerColor = light_gray)
        ***REMOVED*** {

                            FaIcon(faIcon = if (ascending) FaIcons.SortUp else FaIcons.SortDown, tint = cyan_custom)
                        ***REMOVED***
                    Spacer(modifier = Modifier.width(10.dp))
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
                items(templates) { template ->  // Display the list of templates
                    TemplateItem(template, navController, templateViewModel, serviceViewModel)
                ***REMOVED***
            ***REMOVED***

        ***REMOVED***
        if(firstTimeModal){ // Show the first time modal
            AlertDialog(
                title={
                    Text(stringResource(R.string.welcome_to_data_dig))
                ***REMOVED***,
                text = {
                    Text(stringResource(R.string.you_can_create_your_own_templates_or_use_the_sample_templates_to_extract_data_from_images_and_pdfs_click_on_the_camera_icon_to_take_a_photo_of_or_select_your_document_then_choose_the_desired_template_your_options_and_start_the_extraction_you_can_then_open_download_or_send_a_file_containing_the_extracted_values_and_use_them_however_you_want_but_one_thing_before_all_of_this_we_are_transparent_about_ai_usage_costs_and_our_app_must_use_external_keys_to_operate_please_head_to_the_settings_to_insert_your_own_openai_key_to_use_for_the_extraction))
                ***REMOVED***,
                shape = RoundedCornerShape(8.dp),
                onDismissRequest = { firstTimeModal=false ***REMOVED***,
                confirmButton = {
                Button(onClick = { firstTimeModal = false ***REMOVED***) {
                    Text("OK")
                ***REMOVED***
            ***REMOVED***)
        ***REMOVED***
    ***REMOVED***

***REMOVED***

/**
 * single template item composable
 *
 * @param template The template to display
 * @param navController The navigation controller
 * @param viewmodel The template view model
 * @param serviceViewModel The service view model
 */
@Composable
fun TemplateItem(
    template: Template,
    navController: NavHostController,
    viewmodel: TemplateViewModel,
    serviceViewModel: ServiceViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(Screen.EditTemplate.withArgs("templateId" to template.id.toHexString()))
            ***REMOVED***
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = base_card_color,
            contentColor = Color.Black
        ),
        border = CardDefaults.outlinedCardBorder(),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {

                Text(
                    text = template.title, style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(top = 8.dp, start = 8.dp)
    ***REMOVED***
                Spacer(modifier = Modifier.weight(1f))
                val id = template.id.toHexString()
                val onUse = {

                    serviceViewModel.clearImageUris()
                    serviceViewModel.setActiveExtraction(false)
                    serviceViewModel.setActivePhoto(true)
                    serviceViewModel.setActiveTemplate(null)
                    serviceViewModel.setProgress(0f)
                    serviceViewModel.clearResult()
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

