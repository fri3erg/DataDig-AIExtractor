package com.friberg.dataDig.ui.camera

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import com.friberg.dataDig.R
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.friberg.dataDig.Screen
import com.friberg.dataDig.models.ExceptionOccurred
import com.friberg.dataDig.models.Extraction
import com.friberg.dataDig.models.Options
import com.friberg.dataDig.models.Template
import com.friberg.dataDig.services.ExtractionService
import com.friberg.dataDig.ui.theme.cyan_custom
import com.friberg.dataDig.ui.theme.light_gray
import com.friberg.dataDig.ui.theme.dark_green
import com.friberg.dataDig.ui.theme.base_card_color
import com.friberg.dataDig.ui.theme.white_transparent
import com.friberg.dataDig.utils.DropDownGeneral
import com.friberg.dataDig.utils.FileCard
import com.friberg.dataDig.utils.HelpIconButton
import com.friberg.dataDig.utils.LabeledSwitch
import com.friberg.dataDig.utils.MyImageArea
import com.friberg.dataDig.utils.SearchBar
import com.friberg.dataDig.utils.translateType
import com.friberg.dataDig.viewmodels.ExtractionViewModel
import com.friberg.dataDig.viewmodels.ServiceViewModel
import com.friberg.dataDig.viewmodels.SortOrder
import com.friberg.dataDig.viewmodels.TemplateViewModel
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import io.realm.kotlin.types.RealmList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor


/**
 * Camera screen, where the user can take photos and extract data
 *
 * @param templateId The ID of the template to use if one is already selected
 * @param navController The navigation controller
 * @param serviceViewModel The service view model for the extraction service and access to the repository
 * @param templateViewModel The template view model to access the templates
 * @param extractionViewModel The extraction view model to access the extractions
 */
@Composable
fun CameraScreen(
    templateId: String?,
    navController: NavHostController,
    serviceViewModel: ServiceViewModel,
    templateViewModel: TemplateViewModel,
    extractionViewModel: ExtractionViewModel
) {

    var id = templateId
    val context = LocalContext.current
    val imageUris by serviceViewModel.imageUris.collectAsState()
    val combinedTemplate: State<Template?> = combine(
        templateViewModel.queryTemplate(id ?: ""), serviceViewModel.template
    ) { templateFromQuery, serviceTemplate ->
        templateFromQuery
            ?: serviceTemplate // If templateFromQuery is null, fallback to serviceTemplate
    ***REMOVED***.collectAsState(initial = null)
    val template by combinedTemplate

    val activePhoto by serviceViewModel.activePhoto.collectAsState()
    val activeExtraction by serviceViewModel.activeExtraction.collectAsState()
    var openGptKeysDialog by remember { mutableStateOf(false) ***REMOVED***
    var openAzureKeysDialog by remember { mutableStateOf(false) ***REMOVED***
    var reasonTable by remember { mutableStateOf(false) ***REMOVED*** //one of the 2 reasons to open the azure keys dialog
    val options by serviceViewModel.options.collectAsState()

    // If the template ID is not null, set the template in the view model
    if (id != null) {
        template?.let {
            serviceViewModel.setTemplate(it)
        ***REMOVED***

    ***REMOVED***
    LaunchedEffect(template) {
        template?.let { serviceViewModel.setTemplate(it) ***REMOVED***
    ***REMOVED***

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            //if photos need to be taken, show the camera preview
            if (activePhoto || imageUris.isEmpty()) {
                Column(modifier = Modifier.fillMaxSize()) {
                    CameraPreview(modifier = Modifier.fillMaxSize(),
                        onSetUri = { newUri ->
                            serviceViewModel.addImageUri(newUri)
                            Log.d("CameraScreen", "Image URIs: $imageUris")
                        ***REMOVED***,
                        nPhotos = imageUris.size,
                        changeActivePhoto = { serviceViewModel.setActivePhoto(false) ***REMOVED***)

                ***REMOVED***

                //after that, if the template is not selected, show the template selection
            ***REMOVED*** else if (template == null) {
                ChooseTemplate(navController, templateViewModel, serviceViewModel)
                // if the template is selected, show the extraction details, with the option to extract and the progress bar and result
            ***REMOVED*** else {
                ExtractionDetails(template?.title ?: "",
                    modifier = Modifier,
                    navController,
                    imageUris,
                    activeExtraction,
                    onExtractionClick = {
                        when {
                            // If GPT keys are missing, show the GPT keys dialog
                            !serviceViewModel.gptKeysExist() -> {
                                openGptKeysDialog = true
                            ***REMOVED***

                            // If Azure keys are missing and the conditions for Azure  are met, show the Azure keys dialog
                            !serviceViewModel.azureKeysExist() -> {
                                when {
                                    // If there are tables Azure is required
                                    template?.tables?.isNotEmpty() == true -> {
                                        openAzureKeysDialog = true
                                        reasonTable = true
                                    ***REMOVED***
                                    // If the Azure OCR option is enabled, Azure is required
                                    options?.azureOcr == true -> {
                                        openAzureKeysDialog = true
                                        reasonTable = false
                                    ***REMOVED***
                                    // If neither tables nor Azure OCR are required, proceed with extraction
                                    else -> {
                                        startExtraction(context, serviceViewModel, imageUris)
                                    ***REMOVED***
                                ***REMOVED***
                            ***REMOVED***

                            // If all necessary keys exist, proceed with extraction
                            else -> {
                                startExtraction(context, serviceViewModel, imageUris)
                            ***REMOVED***
                        ***REMOVED***
                    ***REMOVED***,
                    serviceViewModel,
                    extractionViewModel,
                    changeId = { id = null ***REMOVED***)

            ***REMOVED***
        ***REMOVED***
        if (openAzureKeysDialog) {
            AzureKeysDialog({ openAzureKeysDialog = false ***REMOVED***, navController, reasonTable)
        ***REMOVED***
        if (openGptKeysDialog) {
            GptKeysDialog({ openGptKeysDialog = false ***REMOVED***, navController)
        ***REMOVED***

    ***REMOVED***
***REMOVED***

/**
 * Exceptions dialog when errors occur during extraction
 *
 * @param closeDialog Function to close the dialog
 * @param exceptionsOccurred List of exceptions that occurred
 */
@Composable
fun ExceptionsDialog(closeDialog: () -> Unit, exceptionsOccurred: RealmList<ExceptionOccurred>) {
    AlertDialog(onDismissRequest = { closeDialog() ***REMOVED***, title = {
        Text(stringResource(R.string.errors_occurred_while_fetching_the_extraction))
    ***REMOVED***, text = {
        LazyColumn {
            items(exceptionsOccurred) { error ->
                Row {
                    Text(text = error.error, modifier = Modifier.weight(1f))
                    Text(text = error.errorType)
                ***REMOVED***
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = error.errorDescription)
                HorizontalDivider()
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***, confirmButton = {
        Button(onClick = { closeDialog() ***REMOVED***) {
            Text(stringResource(id = R.string.ok))
        ***REMOVED***
    ***REMOVED***)


***REMOVED***


/**
 * Start extraction service
 *
 * @param context The context to start the service
 * @param serviceViewModel The service view model to update the progress
 * @param imageUris The list of image URIs to extract
 */
fun startExtraction(context: Context, serviceViewModel: ServiceViewModel, imageUris: List<Uri>) {
    val intent = Intent(context, ExtractionService::class.java).also {
        it.action = ExtractionService.Actions.START.toString()
        it.putParcelableArrayListExtra("imageUris", ArrayList(imageUris))
    ***REMOVED***
    serviceViewModel.setActiveExtraction(true)
    serviceViewModel.setProgress(0f)
    ContextCompat.startForegroundService(context, intent)
***REMOVED***

/**
 * Azure keys dialog when Azure keys are missing
 *
 * @param changeOpen Function to close the dialog
 * @param navController The navigation controller to navigate to the settings
 * @param reason1 Boolean to indicate if the template contains tables or is azure OCR fault
 */
@Composable
fun AzureKeysDialog(changeOpen: () -> Unit, navController: NavHostController, reason1: Boolean) {

    var text = if (reason1) {
        stringResource(R.string.your_template_contains_table_azure_is_required_for_table_extraction)
    ***REMOVED*** else {
        stringResource(R.string.you_cannot_use_azure_ocr_without_keys)
    ***REMOVED***
    text += stringResource(R.string.please_enter_your_azure_keys_in_the_settings)
    AlertDialog(onDismissRequest = { changeOpen() ***REMOVED***,
        title = { Text(stringResource(R.string.azure_keys)) ***REMOVED***,
        text = {
            Text(text)
        ***REMOVED***,
        confirmButton = {
            Button(onClick = {
                changeOpen()
                navController.navigate(Screen.Settings.route)
            ***REMOVED***) {
                Text(stringResource(id = R.string.go_to_settings))
            ***REMOVED***
        ***REMOVED***,
        dismissButton = {
            Button(onClick = { changeOpen() ***REMOVED***) {
                Text(stringResource(id = R.string.ok))
            ***REMOVED***
        ***REMOVED***)
***REMOVED***


/**
 * Gpt keys dialog when GPT keys are missing
 *
 * @param function Function to close the dialog
 * @param navController The navigation controller to navigate to the settings
 */
@Composable
fun GptKeysDialog(function: () -> Unit, navController: NavHostController) {
    AlertDialog(onDismissRequest = { function() ***REMOVED***,
        title = { Text(stringResource(R.string.openai_keys)) ***REMOVED***,
        text = {
            Text(stringResource(R.string.you_cannot_extract_without_keys_please_enter_your_openai_keys_in_the_settings))
        ***REMOVED***,
        confirmButton = {
            Button(onClick = {
                function()
                navController.navigate(Screen.Settings.route)
            ***REMOVED***) {
                Text(stringResource(R.string.go_to_settings))
            ***REMOVED***
        ***REMOVED***,
        dismissButton = {
            Button(onClick = { function() ***REMOVED***) {
                Text(stringResource(R.string.ok))
            ***REMOVED***
        ***REMOVED***)

***REMOVED***

/**
 * Screen to choose a template to start use in the extraction
 *
 * @param navController  The navigation controller
 * @param templateViewModel The template view model to access the templates
 * @param serviceViewModel The service view model to set the template
 */
@Composable
fun ChooseTemplate(
    navController: NavHostController,
    templateViewModel: TemplateViewModel,
    serviceViewModel: ServiceViewModel
) {
    val templates by templateViewModel.sortedTemplates.collectAsState()
    val searchText by templateViewModel.searchText.collectAsState()
    val ascending by templateViewModel.ascending.collectAsState()

    Scaffold(topBar = {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            //search bar and others

            SearchBar(text = searchText,
                onTextChange = { templateViewModel.updateSearchText(it) ***REMOVED***,
                onSearch = { templateViewModel.updateSearchText(it) ***REMOVED***)
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                val sortOrder = templateViewModel.sortOrder.collectAsState().value

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
                                .padding(start = 10.dp),
                            onClick = { templateViewModel.updateSortOrder(option) ***REMOVED***) {
                            Text(
                                text = translateType(
                                    option.name.removePrefix("BY_").lowercase(),
                                    LocalContext.current
                    ***REMOVED***
                ***REMOVED***
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
    ***REMOVED***) { innerPadding -> //list of templates
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            item {
                Text(
                    text = stringResource(R.string.choose_a_template_to_start_extracting),
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
    ***REMOVED***
            ***REMOVED***
            items(templates) { template ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        serviceViewModel.setTemplate(template)
                    ***REMOVED***
                    .padding(8.dp),
                    border = CardDefaults.outlinedCardBorder(),
                    colors = CardDefaults.cardColors(
                        containerColor = base_card_color, contentColor = Color.Black
        ***REMOVED***,
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)

    ***REMOVED*** {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row {

                            Text(
                                text = template.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(top = 8.dp, start = 8.dp, bottom = 8.dp)
                ***REMOVED***
                            Spacer(modifier = Modifier.weight(1f))
                        ***REMOVED***

                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***

    ***REMOVED***
***REMOVED***


/**
 * Extraction details, including the images, options, loading bar, and result
 *
 * @param templateTitle The title of the template
 * @param modifier The modifier for the column
 * @param navController The navigation controller
 * @param imageUris The list of image URIs to extract
 * @param activeExtraction Whether the extraction is active
 * @param onExtractionClick The function to call when the extract! button is clicked
 * @param serviceViewModel The service view model
 * @param extractionViewModel The extraction view model
 * @param changeId The function to change the template ID to null to reset the template
 */
@Composable
fun ExtractionDetails(
    templateTitle: String?,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    imageUris: List<Uri> = emptyList(),
    activeExtraction: Boolean = false,
    onExtractionClick: () -> Unit = {***REMOVED***,
    serviceViewModel: ServiceViewModel,
    extractionViewModel: ExtractionViewModel,
    changeId: () -> Unit = {***REMOVED***
) {

    val defaultOptions = Options() // Default options for the extraction if not set
    val sharedPrefs =
        LocalContext.current.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
    LaunchedEffect(Unit) { // Execute only once when the composable is first created
        with(sharedPrefs) {
            with(edit()) {
                if (!contains("model")) {
                    putString("model", "gpt-4")
                ***REMOVED***
                if (!contains("language")) {
                    putString("language", "auto-detect")
                ***REMOVED***
                if (!contains("azureOcr")) {
                    putBoolean("azureOcr", false)
                ***REMOVED***
                if (!contains("format")) {
                    putString("format", "json")
                ***REMOVED***
                apply()
            ***REMOVED***

            defaultOptions.model = getString("model", defaultOptions.model) ?: "gpt-4"
            defaultOptions.language =
                getString("language", defaultOptions.language) ?: "auto-detect"
            defaultOptions.azureOcr = getBoolean("azureOcr", defaultOptions.azureOcr)
            defaultOptions.format = getString("format", defaultOptions.format) ?: "json"
        ***REMOVED***
        serviceViewModel.setOptions(defaultOptions) // Set the options in the ViewModel
    ***REMOVED***
    Column {
        Text(
            templateTitle.toString(),
            modifier = modifier.padding(16.dp),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
        ) {
            MyImageArea(imageUris = imageUris, onDelete = { uri ->

                serviceViewModel.removeImageUri(uri)
                if (imageUris.size == 1) {
                    serviceViewModel.setActivePhoto(true)
                ***REMOVED***
            ***REMOVED***)
        ***REMOVED***
        if (!activeExtraction) { //choosing what goes under the images
            ButtonBar(serviceViewModel, sharedPrefs)
            HorizontalDivider()

            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(dark_green),
                onClick = onExtractionClick
***REMOVED*** {
                Text(
                    text = stringResource(R.string.extract), fontSize = 15.sp, color = Color.White
    ***REMOVED***
            ***REMOVED***
        ***REMOVED*** else { //extraction ongoing or complete
            ExtractedBar(serviceViewModel, changeId)

            ShownExtraction(
                navController = navController,
                serviceViewModel = serviceViewModel,
                extractionViewModel = extractionViewModel
***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***

/**
 * Extracted bar for options on what to do after extraction
 *
 * @param serviceViewModel The service view model
 * @param changeId the function to change the template ID to null to reset the template
 */
@Composable
fun ExtractedBar(serviceViewModel: ServiceViewModel, changeId: () -> Unit) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // New Extraction
        Column(modifier = Modifier
            .weight(1f)
            .clickable {
                serviceViewModel.clearImageUris()
                serviceViewModel.setActiveExtraction(false)
                serviceViewModel.setActivePhoto(true)
                serviceViewModel.setActiveTemplate(null)
                serviceViewModel.setProgress(0f)
                serviceViewModel.clearResult()
            ***REMOVED***
            .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            FaIcon(
                faIcon = FaIcons.Plus, tint = Color.Black, size = 24.dp
***REMOVED***
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.new_extraction),
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
***REMOVED***
        ***REMOVED***

        // Use New Photos
        Column(modifier = Modifier
            .weight(1f)
            .clickable {
                serviceViewModel.clearImageUris()
                serviceViewModel.setActiveExtraction(false)
                serviceViewModel.setActivePhoto(true)
                serviceViewModel.setProgress(0f)
                serviceViewModel.clearResult()
            ***REMOVED***
            .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            FaIcon(
                faIcon = FaIcons.Camera, tint = Color.Black, size = 24.dp
***REMOVED***
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.use_new_photos),
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
***REMOVED***
        ***REMOVED***

        // Change Template
        Column(modifier = Modifier
            .weight(1f)
            .clickable {
                changeId()
                serviceViewModel.setActiveExtraction(false)
                serviceViewModel.setActiveTemplate(null)
                serviceViewModel.setProgress(0f)
                serviceViewModel.clearResult()
            ***REMOVED***
            .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            FaIcon(
                faIcon = FaIcons.Table, tint = Color.Black, size = 24.dp
***REMOVED***
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.change_template),
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
***REMOVED***
        ***REMOVED***

    ***REMOVED***
    Spacer(modifier = Modifier.height(8.dp))
***REMOVED***

/**
 * Button bar for options to manipulate the extraction before it starts
 *
 * @param serviceViewModel The service view model
 * @param sharedPrefs The shared preferences to save option changes
 */
@Composable
fun ButtonBar(
    serviceViewModel: ServiceViewModel, sharedPrefs: SharedPreferences
) {
    val options by serviceViewModel.options.collectAsState()


    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Add Photo
        IconButton(onClick = {
            serviceViewModel.setActivePhoto(true)
        ***REMOVED***) {
            FaIcon(
                faIcon = FaIcons.Plus, tint = Color.Black, size = 24.dp
***REMOVED***
        ***REMOVED***
        // Clear Photos
        IconButton(onClick = {
            serviceViewModel.clearImageUris()
            serviceViewModel.setActivePhoto(true)
        ***REMOVED***) {
            FaIcon(
                faIcon = FaIcons.Redo, tint = Color.Black, size = 24.dp
***REMOVED***
        ***REMOVED***
        // Clear Template
        IconButton(onClick = {
            serviceViewModel.setActiveTemplate(null)
            serviceViewModel.setActiveExtraction(false)

        ***REMOVED***) {
            FaIcon(faIcon = FaIcons.Table, tint = Color.Black, size = 24.dp)
        ***REMOVED***
        Spacer(modifier = Modifier.weight(1f))
        options?.let {
            // Azure OCR
            var checked by remember { mutableStateOf(it.azureOcr) ***REMOVED***
            LabeledSwitch(label = stringResource(
                R.string.azure_ocr
***REMOVED***, checked = checked, onCheckedChange = { change ->
                serviceViewModel.changeOptions("azureOcr", change)
                with(sharedPrefs.edit()) {
                    putBoolean("azureOcr", change)
                    apply()
                ***REMOVED***
                checked = change
            ***REMOVED***)
            Spacer(modifier = Modifier.width(8.dp))
            HelpIconButton(
                stringResource(R.string.use_azure_ocr_for_the_extraction), title = stringResource(
                    R.string.azure_ocr
    ***REMOVED***
***REMOVED***
        ***REMOVED***
        Spacer(modifier = Modifier.width(8.dp))

    ***REMOVED***
    HorizontalDivider()
    Spacer(modifier = Modifier.height(8.dp))
    Row {
        // Model Dropdown
        options?.let {
            val modelList =
                mapOf("GPT 4" to "gpt-4", "GPT 3-5" to "gpt-3.5-turbo", "Smart Mix" to "smart_mix")
            var defaultIndex = modelList.values.indexOf(it.model)
            if (defaultIndex == -1) {
                defaultIndex = 0
            ***REMOVED***
            DropDownGeneral(
                items = modelList.keys.toList(), onItemSelected = { selectedItem ->
                    serviceViewModel.changeOptions("model", modelList[selectedItem] ?: "gpt-4")
                    with(sharedPrefs.edit()) {
                        putString("model", modelList[selectedItem] ?: "gpt-4")
                        apply()
                    ***REMOVED***
                ***REMOVED***, modifier = Modifier.weight(1f), defaultSelectedItemIndex = defaultIndex
***REMOVED***
            HelpIconButton(
                stringResource(R.string.select_the_model_to_use_for_the_extraction),
                title = stringResource(R.string.model)
***REMOVED***
            val formatList = listOf("json", "csv", "txt", "xml")
            // Format Dropdown
            DropDownGeneral(
                items = formatList,
                onItemSelected = { selectedItem ->
                    serviceViewModel.changeOptions("format", selectedItem)
                    with(sharedPrefs.edit()) {
                        putString("format", selectedItem)
                        apply()
                    ***REMOVED***
                ***REMOVED***,
                modifier = Modifier.weight(1f),
                defaultSelectedItemIndex = formatList.indexOf(it.format)
***REMOVED***
            HelpIconButton(
                stringResource(R.string.select_the_format_to_export_the_extraction),
                title = stringResource(R.string.format)
***REMOVED***
            val languageList = listOf("auto-detect", "en", "it", "es", "fr", "de")
            // Language Dropdown
            DropDownGeneral(
                items = languageList,
                onItemSelected = { selectedItem ->
                    serviceViewModel.changeOptions("language", selectedItem)
                    with(sharedPrefs.edit()) {
                        putString("language", selectedItem)
                        apply()
                    ***REMOVED***
                ***REMOVED***,
                modifier = Modifier.weight(1f),
                defaultSelectedItemIndex = languageList.indexOf(it.language)
***REMOVED***

            HelpIconButton(
                stringResource(R.string.select_the_language_to_use_for_the_extraction),
                title = stringResource(
                    R.string.language
    ***REMOVED***
***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***


/**
 * Progress bar displayed during extraction
 *
 */
@Composable
fun ProgressBar() {
    val serviceViewModel = hiltViewModel<ServiceViewModel>()
    val progress by serviceViewModel.progress.collectAsState()
    val result by serviceViewModel.result.collectAsState()
    LaunchedEffect(progress == 1f) {
        if (progress == 1f) {
            serviceViewModel.setProgress(0f)
        ***REMOVED***
    ***REMOVED***
    Spacer(modifier = Modifier.height(16.dp))
    Row {
        Text(
            stringResource(R.string.progress, (progress * 100).toInt()),
            modifier = Modifier.padding(start = 12.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        LinearProgressIndicator(
            modifier = Modifier
                .weight(1f)
                .padding(top = 10.dp, end = 16.dp),
            progress = { progress ***REMOVED***,
            color = Color.Blue
        )
        Spacer(modifier = Modifier.width(16.dp))
        Spinner(isActive = result == null)
        Spacer(modifier = Modifier.width(16.dp))

    ***REMOVED***

    Spacer(modifier = Modifier.height(16.dp))
    HorizontalDivider()

***REMOVED***

@Composable
fun Spinner(isActive: Boolean, content: @Composable () -> Unit = { CircularProgressIndicator() ***REMOVED***) {
    if (isActive) {
        content()
    ***REMOVED***
***REMOVED***


/**
 * Shown extraction details after extraction is complete
 *
 * @param navController The navigation controller
 * @param serviceViewModel The service view model
 * @param extractionViewModel The extraction view model
 */
@Composable
fun ShownExtraction(
    navController: NavHostController,
    serviceViewModel: ServiceViewModel,
    extractionViewModel: ExtractionViewModel
) {
    val result by serviceViewModel.result.collectAsState()
    var extraction by remember { mutableStateOf<Extraction?>(null) ***REMOVED***
    var errorOccurred by remember { mutableStateOf(false) ***REMOVED***
    LaunchedEffect(key1 = result) { //check if exception occurred
        result?.let {
            extractionViewModel.queryExtraction(it).collect { fetchedExtraction ->
                extraction = fetchedExtraction
                if (extraction?.exceptionsOccurred?.isNotEmpty() == true) {
                    errorOccurred = true
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
    extraction?.let {

        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
***REMOVED*** {
                Text(
                    stringResource(R.string.extraction_result),
                    modifier = Modifier.padding(16.dp),
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis
    ***REMOVED***
                Button(modifier = Modifier
                    .padding(16.dp)
                    .height(70.dp),

                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(dark_green),
                    onClick = {
                        it.id.let { extractionId ->
                            navController.navigate(Screen.SingleExtraction.withArgs("extractionId" to extractionId.toHexString()))
                        ***REMOVED***
                    ***REMOVED***) {
                    Text(
                        stringResource(R.string.go_to_extraction),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
        ***REMOVED***
                ***REMOVED***
            ***REMOVED***
            // Show the extraction result file
            if (it.fileUri != null) {
                FileCard(it, extractionViewModel = extractionViewModel)
            ***REMOVED***
            if (errorOccurred) {
                ExceptionsDialog({ errorOccurred = false ***REMOVED***, it.exceptionsOccurred)
            ***REMOVED***
        ***REMOVED***
    ***REMOVED*** ?: run {

        HorizontalDivider()

        ProgressBar()
    ***REMOVED***

***REMOVED***


/**
 * Camera preview
 *
 * @param modifier The modifier for the preview
 * @param onSetUri The function to set the URI of the image when one is taken
 * @param nPhotos The number of photos taken
 * @param changeActivePhoto The function to change the active photo and exit out

 */
@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    onSetUri: (Uri) -> Unit = { ***REMOVED***,
    nPhotos: Int = 0,
    changeActivePhoto: () -> Unit = { ***REMOVED***
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val imagePickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickMultipleVisualMedia(),
            onResult = { uris: List<Uri>? ->
                uris?.forEach { uri ->
                    onSetUri(uri)
                ***REMOVED***
                uris?.let {
                    if (it.isEmpty()) {
                        changeActivePhoto()
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***)
    val imagePickerLauncherLegacy = //for android versions below 13
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetMultipleContents(),
            onResult = { uris: List<Uri>? ->
                uris?.forEach { uri ->
                    onSetUri(uri)
                ***REMOVED***
                uris?.let {
                    if (it.isEmpty()) {
                        changeActivePhoto()
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***)
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted: Boolean ->
                if (isGranted) {
                    // Launch the image picker
                    imagePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                ***REMOVED***
            ***REMOVED***)
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            val data: Intent? = result.data
            val clipData = data?.clipData
            if (clipData != null) {
                for (i in 0 until clipData.itemCount) {
                    val uri: Uri = clipData.getItemAt(i).uri
                    onSetUri(uri)
                ***REMOVED***
                changeActivePhoto()
            ***REMOVED*** else {
                data?.data?.let { uri ->
                    onSetUri(uri)
                    changeActivePhoto()
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***)
    val onFilePickerClick = {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "*/*"
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*", "application/pdf"))
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        ***REMOVED***
        filePickerLauncher.launch(intent)
    ***REMOVED***
    val requestPermissionLauncherLegacy = //for android versions below 13
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted: Boolean ->
                if (isGranted) {
                    // Launch the image picker
                    imagePickerLauncherLegacy.launch("image/*")
                ***REMOVED***
            ***REMOVED***)
    val onPhotoGalleryClick = {
        // Check if we're on Android 13+ (API level 33 or higher)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // On Android 13+, use the PickVisualMedia contract
            if (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.READ_MEDIA_IMAGES
    ***REMOVED*** == PackageManager.PERMISSION_GRANTED
***REMOVED*** {
                imagePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            ***REMOVED*** else {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            ***REMOVED***
        ***REMOVED*** else { //legacy
            if (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.READ_EXTERNAL_STORAGE
    ***REMOVED*** == PackageManager.PERMISSION_GRANTED

***REMOVED*** {
                imagePickerLauncherLegacy.launch("image/*")
            ***REMOVED*** else {
                requestPermissionLauncherLegacy.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            ***REMOVED***

        ***REMOVED***
    ***REMOVED***

    var showFlash by remember { mutableStateOf(false) ***REMOVED***
    val lensFacing by remember { mutableIntStateOf(CameraSelector.LENS_FACING_BACK) ***REMOVED***

    LaunchedEffect(key1 = showFlash) { // Flash effect
        if (showFlash) {
            delay(100)
            showFlash = false
        ***REMOVED***
    ***REMOVED***
    val cameraSelector = remember { CameraSelector.Builder().requireLensFacing(lensFacing).build() ***REMOVED***

    val preview = remember { Preview.Builder().build() ***REMOVED***
    val imageCapture = remember { ImageCapture.Builder().build() ***REMOVED***
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) ***REMOVED***

    // Track if the surface provider has been set
    var isSurfaceProviderSet by remember { mutableStateOf(false) ***REMOVED***

    LaunchedEffect(lifecycleOwner) {
        Log.d("CameraX", "DisposableEffect called")
        val cameraProvider = cameraProviderFuture.get()

        cameraProviderFuture.addListener({
            try {
                cameraProvider.unbindAll() //this gives so many problems
                cameraProvider.bindToLifecycle(
                    lifecycleOwner, cameraSelector, preview, imageCapture
    ***REMOVED***
                Log.d("CameraX", "Use case binding succeeded")
            ***REMOVED*** catch (exc: Exception) {
                Log.e("CameraX", "Use case binding failed", exc)
            ***REMOVED***
        ***REMOVED***, ContextCompat.getMainExecutor(context))

    ***REMOVED***


    Box(modifier = modifier) {
        AndroidView( //Camera View

            modifier = modifier.fillMaxSize(), factory = { factoryContext ->
                Log.d("CameraPreview", "Creating PreviewView")
                PreviewView(factoryContext).apply {
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                ***REMOVED***
            ***REMOVED***, update = { previewView ->
                if (!isSurfaceProviderSet) {
                    Log.d("CameraPreview", "Setting SurfaceProvider")
                    previewView.post {
                        preview.setSurfaceProvider(previewView.surfaceProvider)
                    ***REMOVED***
                    isSurfaceProviderSet = true
                ***REMOVED***
            ***REMOVED***)
        Column(
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            // File Picker Button
            Button(
                modifier = Modifier
                    .padding(bottom = 20.dp, start = 24.dp)
                    .height(50.dp)
                    .width(75.dp),

                shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(
                    white_transparent
    ***REMOVED***, onClick = onFilePickerClick
***REMOVED*** {
                FaIcon(
                    faIcon = FaIcons.FilePdf,
                    tint = Color.Black,
                    modifier = Modifier.background(Color.Transparent),
                    size = 24.dp
    ***REMOVED***
            ***REMOVED***
            // Gallery Button
            Button(
                modifier = Modifier
                    .padding(bottom = 20.dp, start = 24.dp)
                    .height(50.dp)
                    .width(75.dp),

                shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(
                    white_transparent
    ***REMOVED***, onClick = onPhotoGalleryClick
***REMOVED*** {
                FaIcon(
                    faIcon = FaIcons.Images,
                    tint = Color.Black,
                    modifier = Modifier.background(Color.Transparent),
                    size = 24.dp
    ***REMOVED***
            ***REMOVED***


        ***REMOVED***
        // Capture Button
        Button(modifier = Modifier
            .semantics {
                contentDescription = context.getString(R.string.capture_photo_button)
            ***REMOVED***
            .padding(bottom = 20.dp)
            .size(80.dp)
            .clip(CircleShape)
            .align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            border = BorderStroke(5.dp, Color.White),
            onClick = {
                takePhoto(
                    imageCapture = imageCapture,
                    outputDirectory = context.filesDir,
                    executor = ContextCompat.getMainExecutor(context),
                    onImageCaptured = onSetUri,
                    onError = { error ->
                        Log.e(
                            "CameraX",
                            context.getString(R.string.image_capture_failed, error.message)
            ***REMOVED***
                    ***REMOVED***,
                    context = context
    ***REMOVED***
                showFlash = true
            ***REMOVED***) {***REMOVED***

        // confirm button
        if (nPhotos > 0) {
            Button(
                modifier = Modifier
                    .padding(bottom = 20.dp, end = 20.dp)
                    .align(Alignment.BottomEnd)
                    .size(65.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(white_transparent),
                onClick = changeActivePhoto
***REMOVED*** {
                FaIcon(
                    faIcon = FaIcons.Check,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .background(Color.Transparent),
                    tint = Color.Black,
                    size = 24.dp,
    ***REMOVED***

            ***REMOVED***
        ***REMOVED***
        // Number of photos taken
        Box(
            modifier = Modifier
                .padding(20.dp)
                .width(70.dp)
                .align(Alignment.TopStart)
                .background(
                    shape = RoundedCornerShape(10.dp), color = white_transparent
    ***REMOVED***,
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.taken, nPhotos),
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                modifier = Modifier.background(Color.Transparent)
***REMOVED***
        ***REMOVED***
        // Flash effect
        AnimatedVisibility(
            visible = showFlash,
            enter = fadeIn(animationSpec = tween(durationMillis = 100)),
            exit = fadeOut(animationSpec = tween(durationMillis = 200))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***


/**
 * Take photo with CameraX
 *
 * @param imageCapture The image capture use case
 * @param outputDirectory The directory to save the photo
 * @param executor The executor to run the image capture
 * @param onImageCaptured The function to call when the image is captured
 * @param onError The function to call when an error occurs
 * @param context The context to show the error message
 */
private fun takePhoto(
    imageCapture: ImageCapture,
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit,
    context: Context
) {
    val filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS"
    val photoFile = File(
        outputDirectory,
        SimpleDateFormat(filenameFormat, Locale.US).format(System.currentTimeMillis()) + ".jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback {

        override fun onError(exc: ImageCaptureException) {
            Log.e(
                "CameraX", context.getString(R.string.photo_capture_failed, exc.message), exc
***REMOVED***
            // Provide more specific error messages based on exc.imageCaptureError
            val errorMessage = when (exc.imageCaptureError) {
                ImageCapture.ERROR_FILE_IO -> context.getString(R.string.error_saving_image)
                ImageCapture.ERROR_CAMERA_CLOSED -> context.getString(R.string.camera_closed_unexpectedly)
                else -> context.getString(R.string.an_error_occurred_while_capturing_the_photo)
            ***REMOVED***
            onError(exc)
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        ***REMOVED***

        override fun onImageSaved(output: ImageCapture.OutputFileResults) {
            val savedUri = Uri.fromFile(photoFile)
            onImageCaptured(savedUri)
        ***REMOVED***
    ***REMOVED***)
***REMOVED***



