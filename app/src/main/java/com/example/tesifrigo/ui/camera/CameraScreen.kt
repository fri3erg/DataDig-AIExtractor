package com.example.tesifrigo.ui.camera

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.example.tesifrigo.Screen
import com.example.tesifrigo.models.Extraction
import com.example.tesifrigo.models.Options
import com.example.tesifrigo.services.ExtractionService
import com.example.tesifrigo.utils.DropDownGeneral
import com.example.tesifrigo.utils.FileCard
import com.example.tesifrigo.utils.HelpIconButton
import com.example.tesifrigo.utils.LabeledSwitch
import com.example.tesifrigo.utils.MyImageArea
import com.example.tesifrigo.utils.SearchBar
import com.example.tesifrigo.viewmodels.ExtractionViewModel
import com.example.tesifrigo.viewmodels.ServiceViewModel
import com.example.tesifrigo.viewmodels.SortOrder
import com.example.tesifrigo.viewmodels.TemplateViewModel
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import kotlinx.coroutines.delay
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor


@Composable
fun CameraScreen(
    templateId: String?,
    navController: NavHostController,
    serviceViewModel: ServiceViewModel,
    templateViewModel: TemplateViewModel,
    extractionViewModel: ExtractionViewModel
) {

    val sharedPrefs =
        LocalContext.current.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)

    val context = LocalContext.current
    val imageUris by serviceViewModel.imageUris.collectAsState()
    val template by templateViewModel.queryTemplate(templateId ?: "").collectAsState(initial = null)
    val activePhoto by serviceViewModel.activePhoto.collectAsState()
    val activeExtraction by serviceViewModel.activeExtraction.collectAsState()
    var openGptKeysDialog by remember { mutableStateOf(false) ***REMOVED***
    var openAzureKeysDialog by remember { mutableStateOf(false) ***REMOVED***
    var reasonTable by remember { mutableStateOf(false) ***REMOVED***
    val options by serviceViewModel.options.collectAsState()

    if (templateId != null) {
        template?.let {
            serviceViewModel.setTemplate(it)
            templateViewModel.setActiveTemplate(it)
        ***REMOVED***

    ***REMOVED***
    LaunchedEffect(template) {
        template?.let { serviceViewModel.setTemplate(it) ***REMOVED***
    ***REMOVED***


    Column(modifier = Modifier.fillMaxSize()) {
        if (activePhoto) {
                Column( modifier = Modifier.fillMaxSize()) {
                    CameraPreview(
                        modifier = Modifier.fillMaxSize(),
                        onSetUri = { newUri ->
                            serviceViewModel.addImageUri(newUri)
                            Log.d("CameraScreen", "Image URIs: $imageUris")
                        ***REMOVED***,
                        nPhotos = imageUris.size,
                        changeActivePhoto = { serviceViewModel.setActivePhoto(false) ***REMOVED***
        ***REMOVED***

                ***REMOVED***


        ***REMOVED*** else if (template == null) {
                ChooseTemplate(navController, templateViewModel)

        ***REMOVED*** else {
                ExtractionDetails(
                    template!!.title,
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

                            // If Azure keys are missing and the conditions for Azure OCR are met, show the Azure keys dialog
                            !serviceViewModel.azureKeysExist() -> {
                                when {
                                    // If there are tables, set the reason for tables and show the dialog
                                    template?.tables?.isNotEmpty() == true -> {
                                        openAzureKeysDialog = true
                                        reasonTable = true
                                    ***REMOVED***
                                    // If the Azure OCR option is enabled, show the dialog without table reasons
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
                    templateViewModel,
                    sharedPrefs
    ***REMOVED***

        ***REMOVED***
    ***REMOVED***
    if(openAzureKeysDialog) {
        AzureKeysDialog({ openAzureKeysDialog=false ***REMOVED***,  navController, reasonTable)
    ***REMOVED***
    if(openGptKeysDialog) {
        GptKeysDialog({ openGptKeysDialog=false ***REMOVED***,  navController)
    ***REMOVED***

***REMOVED***

@Composable
fun ExceptionsDialog(closeDialog: () -> Unit, extraction: Extraction) {
    AlertDialog(
        onDismissRequest = { closeDialog() ***REMOVED***,
        title = {
            Text("Errors occurred while fetching the extraction.")
        ***REMOVED***,
        text = {
            LazyColumn {
                items(extraction.exceptionsOccurred) { error ->
                    Row {
                        Text(text = error.error, modifier = Modifier.weight(1f))
                        Text(text = error.errorType)
                    ***REMOVED***
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = error.errorDescription)
                    HorizontalDivider()
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***,
        confirmButton = {
            Button(
                onClick = { closeDialog() ***REMOVED***
***REMOVED*** {
                Text("Ok")
            ***REMOVED***
        ***REMOVED***
    )


***REMOVED***


fun startExtraction(context: Context, serviceViewModel: ServiceViewModel, imageUris: List<Uri>) {
    val intent = Intent(context, ExtractionService::class.java).also {
        it.action = ExtractionService.Actions.START.toString()
        it.putParcelableArrayListExtra("imageUris", ArrayList(imageUris))
    ***REMOVED***
    serviceViewModel.setActiveExtraction(true)
    ContextCompat.startForegroundService(context, intent)
***REMOVED***

@Composable
fun AzureKeysDialog(changeOpen: () -> Unit, navController: NavHostController, reason1:Boolean) {

    var text = if (reason1) {
        "Your template contains table, Azure is required for table extraction"
    ***REMOVED*** else {
        "You cannot use Azure OCR without keys"
    ***REMOVED***
    text += "Please enter your Azure keys in the settings"
    AlertDialog(
        onDismissRequest = { changeOpen() ***REMOVED***,
        title = { Text("Azure Keys") ***REMOVED***,
        text = {
            Text(text)
        ***REMOVED***,
        confirmButton = {
            Button(
                onClick = {
                    changeOpen()
                    navController.navigate(Screen.Settings.route)
                ***REMOVED***
***REMOVED*** {
                Text("Go to settings")
            ***REMOVED***
        ***REMOVED***,
        dismissButton = {
            Button(
                onClick = { changeOpen() ***REMOVED***
***REMOVED*** {
                Text("Ok")
            ***REMOVED***
        ***REMOVED***
    )
***REMOVED***


@Composable
fun GptKeysDialog(function: () -> Unit, navController: NavHostController) {
    AlertDialog(
        onDismissRequest = { function() ***REMOVED***,
        title = { Text("OpenAi Keys") ***REMOVED***,
        text = {
            Text("You cannot extract without keys. Please enter your OpenAi keys in the settings")
        ***REMOVED***,
        confirmButton = {
            Button(
                onClick = { function()
                navController.navigate(Screen.Settings.route)
                ***REMOVED***
***REMOVED*** {
                Text("Go to settings")
            ***REMOVED***
        ***REMOVED***,
        dismissButton = {
            Button(
                onClick = { function() ***REMOVED***
***REMOVED*** {
                Text("Ok")
            ***REMOVED***
        ***REMOVED***
    )

***REMOVED***

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseTemplate(navController: NavHostController, templateViewModel: TemplateViewModel) {
    val templates by templateViewModel.sortedTemplates.collectAsState()
    val searchText by templateViewModel.searchText.collectAsState()
    var expanded by remember { mutableStateOf(false) ***REMOVED***
    val ascending by templateViewModel.ascending.collectAsState()
    val focusRequester = remember { FocusRequester() ***REMOVED***

    Scaffold(
        topBar = {
            Row {
                SearchBar(
                    text = searchText,
                    onTextChange = { templateViewModel.updateSearchText(it) ***REMOVED***,
                    onSearch = { templateViewModel.updateSearchText(it) ***REMOVED***,
                    modifier= Modifier.weight(1f)
    ***REMOVED***


                    val typeOptions = mapOf(
                        "Title" to { templateViewModel.updateSortOrder(SortOrder.BY_TITLE) ***REMOVED***,
                        "Date" to { templateViewModel.updateSortOrder(SortOrder.BY_DATE) ***REMOVED***
        ***REMOVED***
                    Spacer(modifier = Modifier.width(4.dp))
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded ***REMOVED***
        ***REMOVED*** {

                        Button(
                            onClick = { expanded = true ***REMOVED***, modifier = Modifier
                                .padding(end = 8.dp, top = 24.dp)
                                .align(Alignment.CenterVertically)
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
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ***REMOVED*** {
                        if (ascending) {
                            FaIcon(faIcon = FaIcons.ArrowUp, tint = Color.White)
                        ***REMOVED*** else {
                            FaIcon(faIcon = FaIcons.ArrowDown, tint = Color.White)
                        ***REMOVED***
                    ***REMOVED***

            ***REMOVED***
        ***REMOVED***
    ) { innerPadding ->
    LazyColumn(modifier = Modifier.padding(innerPadding)) {
        item {
            Text(text =
            "Choose a template to start extracting",
            modifier = Modifier.padding(16.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
***REMOVED***
        ***REMOVED***
        items(templates) { template ->
            Card( // Consider using a Card for visual structure
                modifier = Modifier
                    .fillMaxWidth() // Occupy full width
                    .clickable {
                        templateViewModel.setActiveTemplate(template)
                    ***REMOVED***
                    .padding(8.dp),
                border = CardDefaults.outlinedCardBorder(), // Add a border
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp) // Add elevation for better visuals

***REMOVED*** {
                Column(modifier = Modifier.padding(16.dp)) { // Inner Column for content
                    Row {

                        Text(
                            text = template.title, style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(top = 8.dp, start = 8.dp)
            ***REMOVED*** // Title
                        Spacer(modifier = Modifier.weight(1f)) // Creates space between text and button

                        FaIcon( // Add an icon to the end of the row
                            faIcon = FaIcons.Edit, // Using the right arrow icon
                            tint = Color.Black, // Set icon color
                            size = 24.dp, // Set icon size
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(Screen.EditTemplate.withArgs("templateId" to template.id.toHexString()))
                                ***REMOVED***
                                .align(Alignment.CenterVertically) // Align the icon to the center vertically
                                .padding(end = 8.dp)
            ***REMOVED***
                    ***REMOVED***

                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

***REMOVED***
    ***REMOVED***


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
    templateViewModel: TemplateViewModel,
    sharedPrefs: SharedPreferences
) {

    val defaultOptions = Options()

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
            defaultOptions.language = getString("language", defaultOptions.language) ?: "auto-detect"
            defaultOptions.azureOcr = getBoolean("azureOcr", defaultOptions.azureOcr)
            defaultOptions.format = getString("format", defaultOptions.format) ?: "json"
        ***REMOVED***
        serviceViewModel.setOptions(defaultOptions) // Set the options in the ViewModel
    ***REMOVED***
    Text(
        "Template: $templateTitle",
        modifier = modifier
            .padding(16.dp),
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
    )
    Spacer(modifier = Modifier.height(30.dp))
    MyImageArea(
        imageUris = imageUris,
    )
    if (!activeExtraction) {
    HorizontalDivider()
    ButtonBar(serviceViewModel, sharedPrefs, templateViewModel)
    HorizontalDivider()

        Button(
            modifier = Modifier
                .padding(16.dp)
                .size(100.dp, 50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(Color.Green),
            onClick = onExtractionClick
        ) {
            Text(text = "Extract!", color = Color.Black)
        ***REMOVED***
    ***REMOVED*** else {
        ExtractedBar(serviceViewModel,  templateViewModel)
        
        HorizontalDivider()

        Spacer(modifier = Modifier.height(16.dp))
        ProgressBar()
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        ShownExtraction(
            navController = navController,
            serviceViewModel = serviceViewModel,
            extractionViewModel = extractionViewModel
        )
    ***REMOVED***

***REMOVED***

@Composable
fun ExtractedBar(serviceViewModel: ServiceViewModel,  templateViewModel: TemplateViewModel) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
            // New Extraction
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        serviceViewModel.clearImageUris()
                        serviceViewModel.setActiveExtraction(false)
                        serviceViewModel.setActivePhoto(true)
                        templateViewModel.setActiveTemplate(null)
                    ***REMOVED***
                    .padding(8.dp), // Optional padding
                horizontalAlignment = Alignment.CenterHorizontally
***REMOVED*** {
                FaIcon(
                    faIcon = FaIcons.Plus, // Using the plus icon
                    tint = Color.Black, // Set icon color
                    size = 24.dp // Set icon size
    ***REMOVED***
                Spacer(modifier = Modifier.height(4.dp)) // Space between icon and text
                Text(
                    text = "New Extraction",
                    fontSize = 12.sp, // Smaller font size
                    textAlign = TextAlign.Center // Center align text
    ***REMOVED***
            ***REMOVED***

            // Use New Photos
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        serviceViewModel.clearImageUris()
                        serviceViewModel.setActiveExtraction(false)
                        serviceViewModel.setActivePhoto(true)
                    ***REMOVED***
                    .padding(8.dp), // Optional padding
                horizontalAlignment = Alignment.CenterHorizontally
***REMOVED*** {
                FaIcon(
                    faIcon = FaIcons.Camera, // Using the camera icon
                    tint = Color.Black, // Set icon color
                    size = 24.dp // Set icon size
    ***REMOVED***
                Spacer(modifier = Modifier.height(4.dp)) // Space between icon and text
                Text(
                    text = "Use New Photos",
                    fontSize = 12.sp, // Smaller font size
                    textAlign = TextAlign.Center // Center align text
    ***REMOVED***
            ***REMOVED***

            // Change Template
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        serviceViewModel.setActiveExtraction(false)
                        templateViewModel.setActiveTemplate(null)
                    ***REMOVED***
                    .padding(8.dp), // Optional padding
                horizontalAlignment = Alignment.CenterHorizontally
***REMOVED*** {
                FaIcon(
                    faIcon = FaIcons.Table, // Using the table icon
                    tint = Color.Black, // Set icon color
                    size = 24.dp // Set icon size
    ***REMOVED***
                Spacer(modifier = Modifier.height(4.dp)) // Space between icon and text
                Text(
                    text = "Change Template",
                    fontSize = 12.sp, // Smaller font size
                    textAlign = TextAlign.Center // Center align text
    ***REMOVED***
            ***REMOVED***

    ***REMOVED***
    Spacer(modifier = Modifier.height(16.dp))
***REMOVED***

@Composable
fun ButtonBar(
    serviceViewModel: ServiceViewModel,
    sharedPrefs: SharedPreferences,
    templateViewModel: TemplateViewModel
) {
    val options by serviceViewModel.options.collectAsState()


    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            serviceViewModel.setActivePhoto(true)
        ***REMOVED***) {
            FaIcon(
                faIcon = FaIcons.Plus, // Using the plus icon
                tint = Color.Black, // Set icon color
                size = 24.dp // Set icon size
***REMOVED***
        ***REMOVED***
        IconButton(onClick = {
            serviceViewModel.clearImageUris()
            serviceViewModel.setActivePhoto(true)
        ***REMOVED***) {
            FaIcon(
                faIcon = FaIcons.Redo, // Using the trash icon
                tint = Color.Black, // Set icon color
                size = 24.dp // Set icon size
***REMOVED***
        ***REMOVED***
        IconButton(onClick = {
            templateViewModel.setActiveTemplate(null)
        ***REMOVED***) {
            FaIcon(faIcon = FaIcons.Table, tint = Color.Black, size = 24.dp)
        ***REMOVED***
        Spacer(modifier = Modifier.weight(1f))
        options?.let {

            var checked by remember { mutableStateOf(it.azureOcr) ***REMOVED***
            LabeledSwitch(
                label = "Azure OCR",
                checked = checked,
                onCheckedChange = { change ->
                    serviceViewModel.changeOptions("azureOcr", change)
                    with(sharedPrefs.edit()) {
                        putBoolean("azureOcr", change)
                        apply()
                    ***REMOVED***
                    checked = change
                ***REMOVED***
***REMOVED***

            HelpIconButton("Use Azure OCR for the extraction")
        ***REMOVED***

    ***REMOVED***
    HorizontalDivider()
    Row {
        options?.let {
            val modelList = mapOf("GPT 4" to "gpt-4", "GPT 3-5" to "gpt-3.5-turbo", "Smart Mix" to "smart_mix")
            DropDownGeneral(
                items = modelList.keys.toList(),
                onItemSelected = { selectedItem ->
                    serviceViewModel.changeOptions("model", modelList[selectedItem] ?: "gpt-4")
                    with(sharedPrefs.edit()) {
                        putString("model", modelList[selectedItem] ?: "gpt-4")
                        apply()
                    ***REMOVED***
                ***REMOVED***,
                modifier = Modifier.weight(1f),
                defaultSelectedItemIndex = modelList.values.indexOf(it.model)
***REMOVED***
            HelpIconButton("Select the model to use for the extraction")
            val formatList = listOf("json", "csv", "xml")
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
            HelpIconButton("Select the format to export the extraction")
            val languageList = listOf("auto-detect", "en", "it", "es", "fr", "de")
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

            HelpIconButton("Select the language to use for the extraction")
        ***REMOVED***
    ***REMOVED***
***REMOVED***


@Composable
fun ProgressBar() {
    val serviceViewModel = hiltViewModel<ServiceViewModel>()
    val progress by serviceViewModel.progress.collectAsState()
    val result by serviceViewModel.result.collectAsState()
    Row {
        Text("Progress: ${progress * 100***REMOVED***%", modifier = Modifier.padding(start = 12.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Spinner(isActive = result == null)
        Spacer(modifier = Modifier.width(16.dp))
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, end = 16.dp),
            progress = { progress ***REMOVED***,
            color = Color.Blue
        )

    ***REMOVED***

***REMOVED***

@Composable
fun Spinner(isActive: Boolean, content: @Composable () -> Unit = { CircularProgressIndicator() ***REMOVED***) {
    if (isActive) {
        content() // Display the provided content when active
    ***REMOVED***
***REMOVED***


@Composable
fun ShownExtraction(
    navController: NavHostController,
    serviceViewModel: ServiceViewModel,
    extractionViewModel: ExtractionViewModel
) {
    val result by serviceViewModel.result.collectAsState()
    var extraction by remember { mutableStateOf<Extraction?>(null) ***REMOVED***
    var isLoading by remember { mutableStateOf(false) ***REMOVED***
    var errorOccurred by remember { mutableStateOf(false) ***REMOVED***
    LaunchedEffect(key1 = result) {
        isLoading = true // Show loading indicator
        result?.let {
            extractionViewModel.queryExtraction(it).collect { fetchedExtraction ->
                extraction = fetchedExtraction
                isLoading = false // Hide loading indicator
            ***REMOVED***
        ***REMOVED*** ?: run {
            errorOccurred = true
            isLoading = false
        ***REMOVED***
    ***REMOVED***
    if (isLoading) {
        CircularProgressIndicator() // Show loading indicator
    ***REMOVED*** else{
        extraction?.let {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
***REMOVED*** {
                Text(
                    "Extraction Result:",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
    ***REMOVED***
                Button(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(150.dp, 60.dp),

                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(Color.Red),
                    onClick = {
                        it.id.let { extractionId ->

                            navController.navigate(Screen.SingleExtraction.withArgs("extractionId" to extractionId.toHexString()))
                        ***REMOVED***
                    ***REMOVED***) {
                    Text("Go to Extraction")
                ***REMOVED***
            ***REMOVED***
            HorizontalDivider()
            if (it.fileUri != null) {
                FileCard(it)
            ***REMOVED***
            if (errorOccurred) {
                ExceptionsDialog( { errorOccurred = false ***REMOVED***, it)
            ***REMOVED***
        ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***


@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    onSetUri: (Uri) -> Unit = { ***REMOVED***,
    nPhotos: Int = 0,
    changeActivePhoto: () -> Unit = { ***REMOVED***
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris: List<Uri>? ->
            uris?.forEach { uri ->
                onSetUri(uri)
            ***REMOVED***
            changeActivePhoto()
        ***REMOVED***
    )
    val imagePickerLauncherLegacy = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris: List<Uri>? ->
            uris?.forEach { uri ->
                onSetUri(uri)
            ***REMOVED***
            changeActivePhoto()
        ***REMOVED***
    )
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if (isGranted) {
                // Launch the image picker
                imagePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            ***REMOVED***
        ***REMOVED***
    )
    val requestPermissionLauncherLegacy = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if (isGranted) {
                // Launch the image picker
                imagePickerLauncherLegacy.launch("image/*")
            ***REMOVED***
        ***REMOVED***
    )
    val onPhotoGalleryClick = {
        // Check if we're on Android 13+ (API level 33 or higher)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // On Android 13+, use the PickVisualMedia contract
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_MEDIA_IMAGES
    ***REMOVED*** == PackageManager.PERMISSION_GRANTED
***REMOVED*** {
                imagePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            ***REMOVED*** else {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            ***REMOVED***
        ***REMOVED*** else {
            // On Android versions below 13, use the ACTION_PICK intent with the old contract
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
    ***REMOVED*** == PackageManager.PERMISSION_GRANTED

***REMOVED*** {
                // Use the original imagePickerLauncher (ActivityResultContracts.StartActivityForResult())
                imagePickerLauncherLegacy.launch("image/*")
            ***REMOVED*** else {
                requestPermissionLauncherLegacy.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            ***REMOVED***

        ***REMOVED***
    ***REMOVED***

    var showFlash by remember { mutableStateOf(false) ***REMOVED***
    val lensFacing by remember { mutableIntStateOf(CameraSelector.LENS_FACING_BACK) ***REMOVED***

    LaunchedEffect(key1 = showFlash) {
        if (showFlash) {
            delay(100)
            showFlash = false
        ***REMOVED***
    ***REMOVED***

    val preview = remember { Preview.Builder().build() ***REMOVED***
    val imageCapture = remember { ImageCapture.Builder().build() ***REMOVED***
    val cameraSelector = remember { CameraSelector.Builder().requireLensFacing(lensFacing).build() ***REMOVED***

    // Function to bind camera use cases
    fun bindCameraUseCases(cameraProvider: ProcessCameraProvider) {
        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture
***REMOVED***
        ***REMOVED*** catch (exc: Exception) {
            Log.e("CameraX", "Use case binding failed", exc)
        ***REMOVED***
    ***REMOVED***

// Initialize and bind the camera use cases once
    DisposableEffect(context) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            bindCameraUseCases(cameraProvider)
        ***REMOVED***, ContextCompat.getMainExecutor(context))
        onDispose {
            cameraProviderFuture.get().unbindAll()
        ***REMOVED***
    ***REMOVED***

    Box(modifier = modifier) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { factoryContext ->
                PreviewView(factoryContext).apply {
                    this.scaleType = scaleType
                    this.implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    preview.setSurfaceProvider(this.surfaceProvider)
                ***REMOVED***
            ***REMOVED***,
            update = { previewView ->
                previewView.scaleType = scaleType
            ***REMOVED***
        )
        Button(
            modifier = Modifier
                .padding(bottom = 20.dp, start = 24.dp)
                .align(Alignment.BottomStart),

            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(Color.White),
            onClick = onPhotoGalleryClick
        ) {
            FaIcon(
                faIcon = FaIcons.Images, // Using the gallery icon
                tint = Color.Black, // Set icon color
                size = 24.dp // Set icon size
***REMOVED***
        ***REMOVED***

        // Capture Button
        Button(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .size(90.dp) // Adjust the size as needed
                .clip(CircleShape) // Clip the button to be a circle
                .align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(Color.White),

            onClick = {
                takePhoto(
                    imageCapture = imageCapture,
                    outputDirectory = context.filesDir,
                    executor = ContextCompat.getMainExecutor(context),
                    onImageCaptured = onSetUri,
                    onError = { error ->
                        Log.e("CameraX", "Image capture failed: ${error.message***REMOVED***")
                    ***REMOVED***,
                    context = context
    ***REMOVED***
                showFlash = true
            ***REMOVED***
        ) {
            Text("Capture")
        ***REMOVED***

        // Gallery Button
        if (nPhotos > 0) {
            Button(
                modifier = Modifier
                    .padding(bottom = 20.dp, end = 20.dp)
                    .align(Alignment.BottomEnd)
                    .size(65.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(Color.White),
                onClick = changeActivePhoto
***REMOVED*** {
                FaIcon(
                    faIcon = FaIcons.Check,
                    tint = Color.Black,
                    size = 24.dp,
    ***REMOVED***

            ***REMOVED***
        ***REMOVED***
        Box(
            modifier = Modifier
                .padding(20.dp)
                .size(70.dp, 35.dp)
                .background(
                    Color.White,
                    shape = RoundedCornerShape(10.dp) // Apply RoundedCornerShape to background
    ***REMOVED***
                .align(Alignment.TopStart),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "$nPhotos taken",
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontSize = 14.sp
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

// Function to capture a photo using CameraX
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
                "CameraX", "Photo capture failed: ${exc.message***REMOVED***",
                exc
***REMOVED***
            // Provide more specific error messages based on exc.imageCaptureError
            val errorMessage = when (exc.imageCaptureError) {
                ImageCapture.ERROR_FILE_IO -> "Error saving image."
                ImageCapture.ERROR_CAMERA_CLOSED -> "Camera closed unexpectedly."
                // ... handle other error cases
                else -> "An error occurred while capturing the photo."
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



