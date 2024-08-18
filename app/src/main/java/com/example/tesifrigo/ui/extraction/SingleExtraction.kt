package com.example.tesifrigo.ui.extraction

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import android.Manifest
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.TextButton
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.example.tesifrigo.Screen
import com.example.tesifrigo.models.ExceptionOccurred
import com.example.tesifrigo.models.Extraction
import com.example.tesifrigo.models.ExtractionCosts
import com.example.tesifrigo.models.ExtractionTable
import com.example.tesifrigo.models.Review
import com.example.tesifrigo.utils.FileCard
import com.example.tesifrigo.utils.MyImageArea
import com.example.tesifrigo.utils.TableCell
import com.example.tesifrigo.viewmodels.ExtractionViewModel
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleExtractionScreen(
    navController: NavHostController, extractionId: String, extractionViewModel: ExtractionViewModel
) {
    val extraction by extractionViewModel.queryExtraction(extractionId)
        .collectAsState(initial = null)
    var showRatingModal by remember { mutableStateOf(false) ***REMOVED***
    var pendingDestinationRoute by remember { mutableStateOf<String?>(null) ***REMOVED***



    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            val currentRoute = destination.route ?: return@OnDestinationChangedListener
            if (!currentRoute.startsWith("singleExtraction") && !showRatingModal && extraction != null && extraction?.review != true) {
                // Capture the intended destination route
                pendingDestinationRoute = currentRoute
                // Block the navigation by showing the modal
                showRatingModal = true

                // Reset to previous destination
                navController.popBackStack()
            ***REMOVED***
        ***REMOVED***
        navController.addOnDestinationChangedListener(listener)

        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        ***REMOVED***
    ***REMOVED***
    BackHandler {
        val lastDestination =
            navController.previousBackStackEntry?.destination?.route ?: Screen.Storage.route
        navController.navigate(lastDestination)
    ***REMOVED***

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = extraction?.template?.title ?: "Extraction Details") ***REMOVED***,
            navigationIcon = {
                IconButton(onClick = {
                    val lastDestination = navController.previousBackStackEntry?.destination?.route
                        ?: Screen.Storage.route
                    navController.navigate(lastDestination)
                ***REMOVED***) {
                    FaIcon(faIcon = FaIcons.ArrowLeft)
                ***REMOVED***
            ***REMOVED***)
    ***REMOVED***) { innerPadding ->
        if (extraction == null) {
            Text(text = "No extraction found") // Handle null case
        ***REMOVED*** else {
            extraction?.let { extraction ->
                LazyColumn( // Use extraction directly without null assertion
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
    ***REMOVED*** {
                    item {
                        TemplateInfo(extraction, navController, extractionViewModel)
                    ***REMOVED***

                    // Extracted Tables Section
                    extraction.extractedTables.let { tables ->
                        if (tables.isNotEmpty()) {
                            item {
                                Text(
                                    text = "Extracted Tables",
                                    style = MaterialTheme.typography.titleLarge
                    ***REMOVED***
                            ***REMOVED***
                            items(tables) { table ->
                                ExtractionTableDisplay(table, extractionViewModel)
                            ***REMOVED***
                        ***REMOVED***
                    ***REMOVED***

                    // Extracted Fields Section
                    if (extraction.extractedFields.isNotEmpty()) {
                        item {
                            Text(
                                text = "Extracted Fields",
                                style = MaterialTheme.typography.titleLarge
                ***REMOVED***
                        ***REMOVED***
                        itemsIndexed(extraction.extractedFields) { index, _ ->
                            ExtractionFieldComposable(extraction, index, extractionViewModel)

                        ***REMOVED***

                    ***REMOVED***

                    // Other Attributes (extractionCosts, exceptionsOccurred)
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ***REMOVED*** {
                            Column(modifier = Modifier.padding(16.dp)) {
                                // Extraction Tags Section
                                TagsSection(extraction, extractionViewModel)

                                ExtractionCostsComposable(extraction.extractionCosts)

                                ExtractionExceptions(extraction.exceptionsOccurred)

                                // Extraction Format Section
                                FormatSection(extraction, extractionViewModel)
                            ***REMOVED***
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***

        if (showRatingModal) {
            extraction?.let { ex ->
                RatingModal(
                    onDismiss = {
                        pendingDestinationRoute?.let {
                            navController.navigate(it)
                            pendingDestinationRoute = null
                        ***REMOVED***

                        showRatingModal = false
                    ***REMOVED***, viewModel = extractionViewModel, extraction = ex
    ***REMOVED***
            ***REMOVED***
        ***REMOVED***

    ***REMOVED***
***REMOVED***

@Composable
fun RatingModal(onDismiss: () -> Unit, viewModel: ExtractionViewModel, extraction: Extraction) {
    var review by remember { mutableStateOf(Review(5, "")) ***REMOVED***
    AlertDialog(onDismissRequest = onDismiss, title = { Text("Rate the Extraction") ***REMOVED***, text = {
        Column {
            // Satisfaction Slider
            Text("Satisfaction Rating:")
            Row {
                Text("0")
                Slider(
                    value = review.rating.toFloat(), onValueChange = { newRating ->
                        review = review.copy(rating = newRating.toInt()) // Update using copy
                    ***REMOVED***, valueRange = 0f..10f, steps = 10
    ***REMOVED***
                Text("10")
            ***REMOVED***
            Spacer(modifier = Modifier.height(16.dp))

            // Optional Comment Textbox
            OutlinedTextField(value = review.comment, onValueChange = { newComment ->
                review = review.copy(comment = newComment) // Update using copy
            ***REMOVED***, label = { Text("Optional Comments") ***REMOVED***, modifier = Modifier.fillMaxWidth()
***REMOVED***
        ***REMOVED***
    ***REMOVED***, confirmButton = {
        TextButton(
            onClick = {
                // Handle rating submission logic here (pass satisfactionRating and comment)
                viewModel.changeReview(extraction)
                viewModel.saveReview(review)
                onDismiss()
            ***REMOVED***, colors = ButtonDefaults.textButtonColors(
                containerColor = Color.Green, contentColor = Color.Black
***REMOVED***
        ) {
            Text("Send")
        ***REMOVED***
    ***REMOVED***, dismissButton = {
        TextButton(
            onClick = onDismiss, colors = ButtonDefaults.textButtonColors(
                containerColor = Color.LightGray, contentColor = Color.Gray
***REMOVED***
        ) {
            Text("Skip")
        ***REMOVED***
    ***REMOVED***)
***REMOVED***

@Composable
fun ExtractionExceptions(exceptionsOccurred: List<ExceptionOccurred>) {


    Text(
        "Exceptions Occurred", style = MaterialTheme.typography.titleMedium
    )
    if (exceptionsOccurred.isEmpty()) {
        Text(
            "No exceptions occurred.", style = MaterialTheme.typography.bodyMedium
        )
    ***REMOVED*** else {
        Column {
            for (exception in exceptionsOccurred) {
                HorizontalDivider()
                Row {
                    Text(
                        text = exception.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
        ***REMOVED***
                    Text(
                        text = exception.errorType, style = MaterialTheme.typography.bodyMedium
        ***REMOVED***
                ***REMOVED***
                Text(
                    text = exception.errorDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
    ***REMOVED***

            ***REMOVED***
            HorizontalDivider()

        ***REMOVED***
    ***REMOVED***

    Spacer(modifier = Modifier.height(16.dp))

***REMOVED***

@Composable
fun ExtractionCostsComposable(extractionCosts: List<ExtractionCosts>) {

    Text("Extraction Costs", style = MaterialTheme.typography.titleMedium)
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        for (cost in extractionCosts) {
            HorizontalDivider(color = Color.LightGray)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
***REMOVED*** {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart // Align to start
    ***REMOVED*** {
                    Text(
                        text = "${cost.name***REMOVED***: ",
                        style = MaterialTheme.typography.bodyMedium,

            ***REMOVED***
                ***REMOVED***
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart // Align to start
    ***REMOVED*** {
                    Text(
                        text = "${cost.tokens***REMOVED*** tokens",
                        style = MaterialTheme.typography.bodyMedium,
        ***REMOVED***
                ***REMOVED***
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterEnd // Align to start
    ***REMOVED*** {
                    Text(
                        text = "${cost.cost***REMOVED*** ${cost.currency***REMOVED***",
                        style = MaterialTheme.typography.bodyMedium,
        ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        HorizontalDivider()
    ***REMOVED***

    Spacer(modifier = Modifier.height(16.dp))
***REMOVED***


@Composable
fun TemplateInfo(
    extraction: Extraction, navController: NavHostController, viewModel: ExtractionViewModel
) {
    val context = LocalContext.current


    // Launcher for cropping the image
    val cropImageLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult(),
            onResult = { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val resultUri =
                        result.data?.getParcelableExtra<CropImage.ActivityResult>(CropImage.CROP_IMAGE_EXTRA_RESULT)?.uriContent
                    if (resultUri != null) {
                        viewModel.addExtraImage(extraction, resultUri)
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***)

    // Function to create the crop intent using CropImageContract
    fun createCropIntent(imageUri: Uri, context: Context): Intent {
        return CropImageContract().createIntent(
            context as Activity, CropImageContractOptions(imageUri, CropImageOptions())
        )
    ***REMOVED***


    val pickImageLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                if (uri != null) {
                    cropImageLauncher.launch(createCropIntent(uri, context))
                ***REMOVED*** else {
                    Log.d("PhotoPicker", "No media selected")
                ***REMOVED***
            ***REMOVED***)

    val imagePickerLauncherLegacy = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val imageUri = result.data?.data
            imageUri?.let { cropImageLauncher.launch(createCropIntent(it, context)) ***REMOVED***
        ***REMOVED***
    ***REMOVED***


    // Permission request launcher
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted: Boolean ->
                if (isGranted) {

                    pickImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

                ***REMOVED*** else {
                    // Permission is denied, handle accordingly (e.g., show a message to the user)
                    Toast.makeText(
                        context, "Permission denied. Cannot access images.", Toast.LENGTH_SHORT
        ***REMOVED***.show()
                ***REMOVED***
            ***REMOVED***)

    val requestPermissionLauncherLegacy =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted: Boolean ->
                if (isGranted) {
                    // Launch the image picker using the legacy intent
                    val pickIntent = Intent(Intent.ACTION_PICK).apply {
                        setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                    ***REMOVED***
                    imagePickerLauncherLegacy.launch(pickIntent)
                ***REMOVED*** else {
                    // Permission is denied, handle accordingly
                    Toast.makeText(
                        context, "Permission denied. Cannot access images.", Toast.LENGTH_SHORT
        ***REMOVED***.show()
                ***REMOVED***
            ***REMOVED***)

    val onPhotoGalleryClick = {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
            if (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.READ_MEDIA_IMAGES // Use READ_MEDIA_IMAGES
    ***REMOVED*** == PackageManager.PERMISSION_GRANTED
***REMOVED*** {
                // Launch the image picker

                pickImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            ***REMOVED*** else {
                // Request permission
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES) // Request READ_MEDIA_IMAGES
            ***REMOVED***
        ***REMOVED*** else // Android 7 to 12 (API 24-32)
            if (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.READ_EXTERNAL_STORAGE
    ***REMOVED*** == PackageManager.PERMISSION_GRANTED
***REMOVED*** {
                // Launch
                val pickIntent = Intent(Intent.ACTION_PICK).apply {
                    setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                ***REMOVED***
                imagePickerLauncherLegacy.launch(pickIntent)
            ***REMOVED*** else {
                requestPermissionLauncherLegacy.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            ***REMOVED***
    ***REMOVED***
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Template Title Section (Clickable)
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    val templateId = extraction.template?.id
                    if (templateId != null) {
                        navController.navigate(Screen.EditTemplate.routeWithOptionalArgs("templateId" to templateId.toString()))
                    ***REMOVED*** else {
                        Toast
                            .makeText(
                                context, "No connected template found", Toast.LENGTH_SHORT
                ***REMOVED***
                            .show()
                    ***REMOVED***
                ***REMOVED***
                .padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                FaIcon(faIcon = FaIcons.File, size = 24.dp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = extraction.template?.title ?: "No title",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
    ***REMOVED***
            ***REMOVED***
            Spacer(modifier = Modifier.height(20.dp))

            // Image Carousel (if available)
            if (extraction.image.isNotEmpty()) {
                MyImageArea(
                    imageUris = extraction.image.map { Uri.parse(it) ***REMOVED***,
                    modifier = Modifier.fillMaxWidth()

    ***REMOVED***
            ***REMOVED***
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Added Images:", style = MaterialTheme.typography.titleMedium
***REMOVED***
            Spacer(modifier = Modifier.height(20.dp))

            if (extraction.extraImages.isNotEmpty()) {
                MyImageArea(
                    imageUris = extraction.extraImages.map { Uri.parse(it) ***REMOVED***,
                    modifier = Modifier.fillMaxWidth()
    ***REMOVED***
            ***REMOVED***
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
***REMOVED*** {
                Button(onClick = {
                    onPhotoGalleryClick()
                ***REMOVED***) {
                    Text("Add Images")
                ***REMOVED***

            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagsSection(
    extraction: Extraction, viewModel: ExtractionViewModel
) {
    val context = LocalContext.current
    Column {
        Text("Extraction Tags", style = MaterialTheme.typography.titleMedium)
        FlowRow(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)

        ) {
            for (tag in extraction.tags) {
                var showDismissIcon by remember { mutableStateOf(false) ***REMOVED***

                AssistChip(onClick = { showDismissIcon = !showDismissIcon ***REMOVED***,
                    label = { Text(tag) ***REMOVED***,
                    trailingIcon = {
                        AnimatedVisibility(
                            visible = showDismissIcon,
                            enter = fadeIn() + scaleIn(),
                            exit = fadeOut() + scaleOut()
            ***REMOVED*** {
                            Icon(Icons.Filled.Close,
                                tint = Color.Black,
                                contentDescription = "Remove Keyword",
                                modifier = Modifier.clickable {
                                    viewModel.removeTag(extraction, tag)
                                ***REMOVED***)
                        ***REMOVED***
                    ***REMOVED***,
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures(onPress = {
                            showDismissIcon = true
                        ***REMOVED***)
                    ***REMOVED***)
            ***REMOVED***
        ***REMOVED***

        // Add New Tag Section
        var newKey by remember { mutableStateOf("") ***REMOVED***
        OutlinedTextField(value = newKey,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Black,
                unfocusedLabelColor = Color.Black,
***REMOVED***,
            onValueChange = { newKey = it ***REMOVED***,
            label = { Text("Add Tag", color = Color.Black) ***REMOVED***,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                FaIcon(faIcon = FaIcons.Plus, tint = Color.Black, modifier = Modifier.clickable {
                    when {
                        newKey.isBlank() -> {
                            Toast.makeText(context, "tag cannot be empty", Toast.LENGTH_SHORT)
                                .show()
                        ***REMOVED***

                        extraction.tags.size >= 10 -> {
                            Toast.makeText(
                                context, "Max number of keywords is 10", Toast.LENGTH_SHORT
                ***REMOVED***.show()
                        ***REMOVED***

                        else -> {
                            viewModel.addTag(extraction, newKey)
                            newKey = ""
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***)
            ***REMOVED***)

    ***REMOVED***
***REMOVED***


@Composable
fun ExtractionTableDisplay(extractionTable: ExtractionTable, viewModel: ExtractionViewModel) {
    val columnHeaders =
        extractionTable.fields.firstOrNull()?.fields?.mapNotNull { it.templateField?.title ***REMOVED***

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header Row
            if (columnHeaders != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
    ***REMOVED*** {
                    // Add an empty cell for the row index header
                    TableCell(text = "",
                        modifier = Modifier.weight(1f),
                        isHeader = true,
                        onTextChange = {***REMOVED***)

                    for (header in columnHeaders) {
                        TableCell(text = header,
                            modifier = Modifier.weight(1f),
                            isHeader = true,
                            onTextChange = {***REMOVED***)
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***

            // Data Rows
            for ((rowIndex, row) in extractionTable.fields.withIndex()) {
                HorizontalDivider(color = Color.LightGray)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
    ***REMOVED*** {
                    // Add row index cell

                    Box( // Wrap the TableCell in a Box
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(), // Make the Box fill the entire height of the Row

                        contentAlignment = Alignment.CenterStart // Center the content vertically within the Box
        ***REMOVED*** {
                        TableCell(text = (rowIndex + 1).toString(),
                            modifier = Modifier.align(Alignment.CenterStart),
                            isHeader = true,
                            onTextChange = {***REMOVED***)
                    ***REMOVED***
                    for (field in row.fields) {
                        TableCell(text = field.value,
                            modifier = Modifier.weight(1f),
                            onTextChange = { viewModel.updateField(field, it) ***REMOVED***)
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormatSection(extraction: Extraction, viewModel: ExtractionViewModel) {

    Text("Extraction Format", style = MaterialTheme.typography.titleMedium)

    var expanded by remember { mutableStateOf(false) ***REMOVED***
    var selectedFormat by remember { mutableStateOf(extraction.format) ***REMOVED***
    val formatOptions = listOf("json", "csv", "txt") // Add more formats if needed
    val context = LocalContext.current

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded ***REMOVED***) {
        TextField(value = selectedFormat,
            onValueChange = {***REMOVED***,
            readOnly = true,
            label = { Text("Format") ***REMOVED***,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
    ***REMOVED***
            ***REMOVED***,
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor()

        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false ***REMOVED***) {
            formatOptions.forEach { format ->
                DropdownMenuItem(text = { Text(format) ***REMOVED***, onClick = {
                    selectedFormat = format
                    viewModel.changeFormat(extraction, format, context)
                    expanded = false
                ***REMOVED***)
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***


    // File Card Section (display only if fileUri is available)
    Spacer(modifier = Modifier.height(16.dp))
    FileCard(
        extraction = extraction, modifier = Modifier.fillMaxWidth()
    )


***REMOVED***


@Composable
fun ExtractionFieldComposable(
    extraction: Extraction,
    index: Int,
    viewModel: ExtractionViewModel,
    modifier: Modifier = Modifier
) {
    var value by remember { mutableStateOf(extraction.extractedFields[index].value) ***REMOVED***
    extraction.extractedFields[index].templateField?.let { templateField ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = templateField.title, style = MaterialTheme.typography.titleMedium
***REMOVED***
            OutlinedTextField(value = value,
                onValueChange = { newText ->
                    value = newText
                    viewModel.updateField(extraction.extractedFields[index], newText)
                ***REMOVED***,
                label = { Text(text = "Value") ***REMOVED***,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline

    ***REMOVED***
***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***
