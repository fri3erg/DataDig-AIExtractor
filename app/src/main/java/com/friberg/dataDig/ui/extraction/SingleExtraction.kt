package com.friberg.dataDig.ui.extraction

import android.app.Activity
import android.content.Context
import com.friberg.dataDig.R
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
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.friberg.dataDig.Screen
import com.friberg.dataDig.models.ExceptionOccurred
import com.friberg.dataDig.models.Extraction
import com.friberg.dataDig.models.ExtractionCosts
import com.friberg.dataDig.models.ExtractionField
import com.friberg.dataDig.models.ExtractionTable
import com.friberg.dataDig.models.Review
import com.friberg.dataDig.ui.theme.dark_green
import com.friberg.dataDig.ui.theme.base_card_color
import com.friberg.dataDig.utils.BooleanFieldWithLabel
import com.friberg.dataDig.utils.FileCard
import com.friberg.dataDig.utils.HelpIconButton
import com.friberg.dataDig.utils.MyImageArea
import com.friberg.dataDig.utils.ExtractionTableCell
import com.friberg.dataDig.viewmodels.ExtractionViewModel
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


/**
 * Displaying a past extraction in detail
 *
 * @param navController Navigation controller
 * @param extractionId ID of the extraction to display
 * @param extractionViewModel ViewModel for extraction data
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleExtractionScreen(
    navController: NavHostController, extractionId: String, extractionViewModel: ExtractionViewModel
) {
    val extraction by extractionViewModel.queryExtraction(extractionId)
        .collectAsState(initial = null)
    var showRatingModal by remember { mutableStateOf(false) ***REMOVED***
    var pendingDestinationRoute by remember { mutableStateOf<String?>(null) ***REMOVED***



    DisposableEffect(navController) { //block navigation and show rating modal
        val listener = NavController.OnDestinationChangedListener { _, destination, arguments ->
            val currentRoute = destination.route ?: return@OnDestinationChangedListener

            if (!currentRoute.startsWith(Screen.SingleExtraction.route) && !showRatingModal && extraction != null && extraction?.review != true) {
                // Capture the intended destination route
                val templateId = arguments?.getString("templateId")

                if (templateId != null && currentRoute.startsWith(Screen.EditTemplate.route)) {
                    pendingDestinationRoute =
                        Screen.EditTemplate.withArgs("templateId" to templateId) // Construct the route manually
                    showRatingModal = true
                ***REMOVED*** else {
                    pendingDestinationRoute = currentRoute
                    // Block the navigation by showing the modal
                    showRatingModal = true
                ***REMOVED***

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
        TopAppBar(title = {
            Text(
                text = extraction?.template?.title ?: stringResource(R.string.extraction_details)
***REMOVED***
        ***REMOVED***, navigationIcon = {
            IconButton(onClick = {
                val lastDestination =
                    navController.previousBackStackEntry?.destination?.route ?: Screen.Storage.route
                navController.navigate(lastDestination)
            ***REMOVED***) {
                FaIcon(faIcon = FaIcons.ArrowLeft)
            ***REMOVED***
        ***REMOVED***)
    ***REMOVED***) { innerPadding ->
        if (extraction == null) {
            Text(text = stringResource(R.string.no_extraction_found))
        ***REMOVED*** else {
            extraction?.let { extraction ->
                LazyColumn(
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
                                    text = stringResource(R.string.extracted_tables),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    modifier = Modifier.padding(8.dp)
                    ***REMOVED***
                            ***REMOVED***
                            items(tables) { table ->
                                if (table.fields.isNotEmpty()) {
                                    ExtractionTableDisplay(table, extractionViewModel)
                                ***REMOVED***
                            ***REMOVED***
                        ***REMOVED***
                    ***REMOVED***

                    // Extracted Fields Section
                    if (extraction.extractedFields.isNotEmpty()) {
                        item {
                            Text(
                                text = stringResource(R.string.extracted_fields),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(8.dp)
                ***REMOVED***
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = base_card_color, contentColor = Color.Black
                    ***REMOVED***,
                                shape = RoundedCornerShape(8.dp)

                ***REMOVED*** {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    for (field in extraction.extractedFields) {
                                        ExtractionFieldComposable(
                                            field = field, viewModel = extractionViewModel
                            ***REMOVED***
                                        HorizontalDivider()
                                    ***REMOVED***

                                ***REMOVED***
                            ***REMOVED***
                        ***REMOVED***

                    ***REMOVED***

                    // Other Attributes (extractionCosts, exceptionsOccurred)
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = base_card_color, contentColor = Color.Black
                ***REMOVED***,
                            shape = RoundedCornerShape(8.dp)
            ***REMOVED*** {
                            Column(modifier = Modifier.padding(16.dp)) {
                                // Extraction Tags Section
                                TagsSection(extraction, extractionViewModel)
                                Spacer(modifier = Modifier.height(16.dp))

                                ExtractionCostsComposable(extraction.extractionCosts)
                                Spacer(modifier = Modifier.height(8.dp))

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

/**
 * Rating modal for the extraction that appears after clicking away from the extraction
 *
 * @param onDismiss
 * @param viewModel
 * @param extraction
 */
@Composable
fun RatingModal(onDismiss: () -> Unit, viewModel: ExtractionViewModel, extraction: Extraction) {
    var review by remember { mutableStateOf(Review(10, "")) ***REMOVED***
    AlertDialog(onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.rate_the_extraction)) ***REMOVED***,
        text = {
            Column {
                // Satisfaction Slider
                Text(
                    stringResource(R.string.satisfaction_rating), fontSize = 14.sp
    ***REMOVED***
                Row {
                    Text("0")
                    Slider(
                        modifier = Modifier.weight(1f),
                        value = review.rating.toFloat(),
                        onValueChange = { newRating ->
                            review = review.copy(rating = newRating.toInt())
                        ***REMOVED***,
                        valueRange = 0f..10f,
                        steps = 10
        ***REMOVED***
                    Text("10")
                ***REMOVED***
                Spacer(modifier = Modifier.height(16.dp))

                // Optional Comment Textbox
                OutlinedTextField(
                    value = review.comment,
                    onValueChange = { newComment ->
                        review = review.copy(comment = newComment)
                    ***REMOVED***,
                    label = { Text(stringResource(R.string.optional_comments)) ***REMOVED***,
                    modifier = Modifier.fillMaxWidth()
    ***REMOVED***
            ***REMOVED***
        ***REMOVED***,
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.changeReview(extraction)
                    viewModel.saveReview(review)
                    onDismiss()
                ***REMOVED***, colors = ButtonDefaults.textButtonColors(
                    containerColor = dark_green, contentColor = Color.Black
    ***REMOVED***
***REMOVED*** {
                Text(stringResource(R.string.send))
            ***REMOVED***
        ***REMOVED***,
        dismissButton = {
            TextButton(
                onClick = {
                    viewModel.changeReview(extraction)
                    onDismiss()
                ***REMOVED***, colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.LightGray, contentColor = Color.Gray
    ***REMOVED***
***REMOVED*** {
                Text(stringResource(R.string.skip))
            ***REMOVED***
        ***REMOVED***)
***REMOVED***

/**
 * Extraction exceptions section,   lists all exceptions that occurred during extraction
 *
 * @param exceptionsOccurred List of exceptions that occurred during extraction
 */
@Composable
fun ExtractionExceptions(exceptionsOccurred: List<ExceptionOccurred>) {


    Text(
        stringResource(R.string.exceptions_occurred), style = MaterialTheme.typography.titleMedium
    )
    if (exceptionsOccurred.isEmpty()) {
        Text(
            stringResource(R.string.no_exceptions_occurred),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
    ***REMOVED*** else {
        Column {
            for (exception in exceptionsOccurred) {
                HorizontalDivider()
                Row(
                    modifier = Modifier.padding(8.dp)
    ***REMOVED*** {
                    Text(
                        text = exception.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
        ***REMOVED***
                    Text(
                        text = exception.errorType,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f),
        ***REMOVED***
                ***REMOVED***
                Text(
                    text = exception.errorDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(8.dp)
    ***REMOVED***

            ***REMOVED***
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))

        ***REMOVED***
    ***REMOVED***

    Spacer(modifier = Modifier.height(16.dp))

***REMOVED***

/**
 * Extraction costs composable that lists all the costs associated with the extraction
 *
 * @param extractionCosts
 */
@Composable
fun ExtractionCostsComposable(extractionCosts: List<ExtractionCosts>) {

    Text(stringResource(R.string.extraction_costs), style = MaterialTheme.typography.titleMedium)
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
                    modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart
    ***REMOVED*** {
                    Text(
                        text = "${cost.name***REMOVED***: ",
                        style = MaterialTheme.typography.bodyMedium,

            ***REMOVED***
                ***REMOVED***
                Box(
                    modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart
    ***REMOVED*** {
                    Text(
                        text = "${cost.tokens***REMOVED*** tokens",
                        style = MaterialTheme.typography.bodyMedium,
        ***REMOVED***
                ***REMOVED***
                Box(
                    modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd
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


/**
 * Template info section that displays the title and images of the extraction
 *
 * @param extraction
 * @param navController
 * @param viewModel
 */
@Composable
fun TemplateInfo(
    extraction: Extraction, navController: NavHostController, viewModel: ExtractionViewModel
) {
    val context = LocalContext.current


    // Launcher for cropping the image for the extra images
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
    // legacy image picker launcher
    val imagePickerLauncherLegacy = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val imageUri = result.data?.data
            imageUri?.let { cropImageLauncher.launch(createCropIntent(it, context)) ***REMOVED***
        ***REMOVED***
    ***REMOVED***


    val requestPermissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted: Boolean ->
                if (isGranted) {

                    pickImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

                ***REMOVED*** else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.permission_denied_cannot_access_images),
                        Toast.LENGTH_SHORT
        ***REMOVED***.show()
                ***REMOVED***
            ***REMOVED***)
    // Legacy image picker launcher
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
                    Toast.makeText(
                        context,
                        context.getString(R.string.permission_denied_cannot_access_images),
                        Toast.LENGTH_SHORT
        ***REMOVED***.show()
                ***REMOVED***
            ***REMOVED***)

    val onPhotoGalleryClick = {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
            if (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.READ_MEDIA_IMAGES
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
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = base_card_color, contentColor = Color.Black
        )
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
                    val id = extraction.template?.id?.toHexString()
                    if (id != null) {
                        navController.navigate(Screen.EditTemplate.withArgs("templateId" to id))
                    ***REMOVED*** else {
                        Toast
                            .makeText(
                                context,
                                context.getString(R.string.no_connected_template_found),
                                Toast.LENGTH_SHORT
                ***REMOVED***
                            .show()
                    ***REMOVED***
                ***REMOVED***
                .padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                FaIcon(faIcon = FaIcons.Table, size = 24.dp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = extraction.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
    ***REMOVED***
            ***REMOVED***
            Spacer(modifier = Modifier.height(20.dp))

            // Image Carousel
            if (extraction.image.isNotEmpty()) {
                MyImageArea(
                    imageUris = extraction.image.map { Uri.parse(it) ***REMOVED***,
                    modifier = Modifier.fillMaxWidth(),
                    onDelete = null

    ***REMOVED***
            ***REMOVED***

            if (extraction.extraImages.isNotEmpty()) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(R.string.added_images),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
    ***REMOVED***
                MyImageArea(imageUris = extraction.extraImages.map { Uri.parse(it) ***REMOVED***,
                    modifier = Modifier.fillMaxWidth(),
                    onDelete = { viewModel.removeExtraImage(extraction, it) ***REMOVED***)
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
                    Text(stringResource(R.string.add_an_extra_image))
                ***REMOVED***

                HelpIconButton(
                    title = stringResource(R.string.extra_images), helpText = stringResource(
                        R.string.add_an_extra_image_to_the_extraction_these_are_separate_from_the_extraction_and_are_only_for_your_commodity_use_these_for_logos_or_other_images_that_you_want_attached_to_the_extraction
        ***REMOVED***
    ***REMOVED***

            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***


/**
 * Tags section that displays all the tags associated with the extraction
 *
 * @param extraction
 * @param viewModel
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagsSection(
    extraction: Extraction, viewModel: ExtractionViewModel
) {
    val context = LocalContext.current
    Column {
        Text(stringResource(R.string.extraction_tags), style = MaterialTheme.typography.titleMedium)
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
                                contentDescription = stringResource(id = R.string.remove_keyword),
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
            label = { Text(stringResource(R.string.add_tag), color = Color.Black) ***REMOVED***,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                FaIcon(faIcon = FaIcons.Plus, tint = Color.Black, modifier = Modifier.clickable {
                    when {
                        newKey.isBlank() -> {
                            Toast.makeText(
                                context,
                                context.getString(R.string.tag_cannot_be_empty),
                                Toast.LENGTH_SHORT
                ***REMOVED***.show()
                        ***REMOVED***

                        extraction.tags.size >= 10 -> {
                            Toast.makeText(
                                context,
                                context.getString(R.string.max_number_of_keywords_is_10),
                                Toast.LENGTH_SHORT
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


/**
 * Extraction table display that displays the extraction table in a tabular format
 *
 * @param extractionTable
 * @param viewModel
 */
@Composable
fun ExtractionTableDisplay(extractionTable: ExtractionTable, viewModel: ExtractionViewModel) {
    val columnHeaders =
        extractionTable.fields.firstOrNull()?.fields?.mapNotNull { it.templateField?.title ***REMOVED***
    if (columnHeaders == null && extractionTable.fields.isEmpty()) {
        Text(
            stringResource(R.string.no_table_found),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(8.dp),
            color = Color.Gray
        )
    ***REMOVED***
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = base_card_color, contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
        ) {
            Text(
                text = extractionTable.title ?: stringResource(id = R.string.table),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
***REMOVED***
            // Header Row
            if (columnHeaders != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
    ***REMOVED*** {
                    // Add an empty cell for the row index header
                    ExtractionTableCell(
                        text = "",
                        modifier = Modifier.weight(1f),
                        onValueChange = {***REMOVED***,
                        onDelete = {***REMOVED***,
                        invisible = true
        ***REMOVED***

                    for (header in columnHeaders) {
                        ExtractionTableCell(
                            text = header,
                            modifier = Modifier.weight(1f),
                            isHeader = true,
                            onDelete = { viewModel.removeColumn(extractionTable, header) ***REMOVED***,


                ***REMOVED***
                    ***REMOVED***

                    ExtractionTableCell(
                        text = "",
                        modifier = Modifier.weight(1f),
                        buttonClick = {
                            viewModel.addColumn(extractionTable, it)
                        ***REMOVED***,
                        isButton = true,
        ***REMOVED***
                ***REMOVED***
            ***REMOVED***

            // Data Rows
            for (row in extractionTable.fields) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
    ***REMOVED*** {
                    // Add row index cell

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),

                        contentAlignment = Alignment.CenterStart
        ***REMOVED*** {
                        ExtractionTableCell(text = row.rowName,
                            modifier = Modifier.align(Alignment.CenterStart),
                            isHeader = true,
                            onDelete = { viewModel.removeRow(extractionTable, row) ***REMOVED***)
                    ***REMOVED***
                    for (field in row.fields) {
                        ExtractionTableCell(text = field.value,
                            modifier = Modifier.weight(1f),
                            onValueChange = { newValue ->
                                viewModel.updateField(field, newValue)
                            ***REMOVED***)

                    ***REMOVED***
                    ExtractionTableCell(
                        text = "",
                        modifier = Modifier.weight(1f),
                        invisible = true,
        ***REMOVED***
                ***REMOVED***
            ***REMOVED***
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
***REMOVED*** { //last row with buttons
                ExtractionTableCell(
                    text = "",
                    modifier = Modifier.weight(1f),
                    buttonClick = {
                        viewModel.addRow(extractionTable, it)
                    ***REMOVED***,
                    isButton = true,
    ***REMOVED***
                for (field in extractionTable.fields[0].fields) {
                    ExtractionTableCell(
                        text = "",
                        modifier = Modifier.weight(1f),
                        invisible = true,
        ***REMOVED***

                ***REMOVED***
                ExtractionTableCell(
                    text = "",
                    modifier = Modifier.weight(1f),
                    invisible = true,
    ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***


/**
 * Format section for choosing the format of the extraction
 *
 * @param extraction
 * @param viewModel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormatSection(extraction: Extraction, viewModel: ExtractionViewModel) {

    Text(
        stringResource(R.string.extraction_format),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(8.dp)
    )

    var expanded by remember { mutableStateOf(false) ***REMOVED***
    var selectedFormat by remember { mutableStateOf(extraction.format) ***REMOVED***
    val formatOptions = listOf("json", "csv", "txt", "xml")
    val context = LocalContext.current

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded ***REMOVED***) {
        TextField(
            value = selectedFormat,
            onValueChange = {***REMOVED***,
            readOnly = true,
            label = { Text("Format") ***REMOVED***,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
    ***REMOVED***
            ***REMOVED***,
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
***REMOVED***,
            modifier = Modifier.menuAnchor()

        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false ***REMOVED***,
            modifier = Modifier.background(Color.White)
        ) {
            formatOptions.forEach { format ->
                DropdownMenuItem(text = { Text(format) ***REMOVED***, onClick = {
                    selectedFormat = format
                    for (f in formatOptions) {
                        viewModel.removeTag(extraction, f)
                    ***REMOVED***
                    viewModel.addTag(extraction, format)
                    viewModel.updateFile(extraction, format, context)
                    Toast.makeText(
                        context,
                        context.getString(R.string.file_recreated_to_fit_format, format),
                        Toast.LENGTH_SHORT
        ***REMOVED***.show()
                    expanded = false
                ***REMOVED***)
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***


    // File Card Section
    Spacer(modifier = Modifier.height(16.dp))
    FileCard(
        extraction = extraction, modifier = Modifier.fillMaxWidth(), extractionViewModel = viewModel
    )


***REMOVED***


/**
 * single Extraction field composable
 *
 * @param field field to display
 * @param viewModel extration view model
 * @param modifier modifier for the column
 */
@Composable
fun ExtractionFieldComposable(
    field: ExtractionField, viewModel: ExtractionViewModel, modifier: Modifier = Modifier
) {
    val value by remember { mutableStateOf(field.value) ***REMOVED***
    field.templateField?.let { templateField ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val elements = if (templateField.list) value.split("|") else listOf(value)
            Text(text = templateField.title, style = MaterialTheme.typography.titleMedium)

            var editableElements by remember { mutableStateOf(elements) ***REMOVED***

            for ((index, element) in editableElements.withIndex()) {
                Picker(type = templateField.type, text = element, changeText = { newText ->
                    editableElements = editableElements.toMutableList().also { it[index] = newText ***REMOVED***
                    val updatedValue =
                        if (templateField.list) editableElements.joinToString("|") else newText
                    viewModel.updateField(field, updatedValue)
                ***REMOVED***)
            ***REMOVED***

        ***REMOVED***
    ***REMOVED***
***REMOVED***

/**
 * Picker for each field type to respect the type of the field
 *
 * @param type
 * @param text
 * @param changeText
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Picker(
    type: String,
    text: String,
    changeText: (String) -> Unit,
) {
    val context = LocalContext.current
    var editableText by remember { mutableStateOf(text) ***REMOVED***
    val clipboardManager = LocalClipboardManager.current
    when (type) {
        "Text" -> { //normal text field
            OutlinedTextField(value = editableText, onValueChange = { newText ->
                editableText = newText
                changeText(newText)
            ***REMOVED***, colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Black,
                unfocusedLabelColor = Color.Black,

    ***REMOVED***, trailingIcon = {
                IconButton(onClick = {
                    clipboardManager.setText(AnnotatedString(editableText))
                    Toast.makeText(
                        context,
                        context.getString(R.string.copied_to_clipboard),
                        Toast.LENGTH_SHORT
        ***REMOVED***.show()
                ***REMOVED***) {
                    FaIcon(
                        FaIcons.CopyRegular,
                        tint = Color.Black,
                        modifier = Modifier.semantics {
                            contentDescription = context.getString(R.string.copy_value)
                        ***REMOVED***)
                ***REMOVED***
            ***REMOVED***, label = { Text(stringResource(R.string.field_value)) ***REMOVED***)
        ***REMOVED***

        "Number" -> { //keyboard type number
            OutlinedTextField(
                value = text,
                onValueChange = { newText ->
                    editableText = newText
                    changeText(newText)
                ***REMOVED***,
                trailingIcon = {
                    IconButton(onClick = {
                        clipboardManager.setText(AnnotatedString(editableText))
                        Toast.makeText(
                            context,
                            context.getString(R.string.copied_to_clipboard),
                            Toast.LENGTH_SHORT
            ***REMOVED***.show()
                    ***REMOVED***) {
                        FaIcon(
                            FaIcons.CopyRegular,
                            tint = Color.Black,
                            modifier = Modifier.semantics {
                                contentDescription = context.getString(R.string.copy_value)
                            ***REMOVED***)
                    ***REMOVED***
                ***REMOVED***,
                label = { Text(stringResource(R.string.field_value_number)) ***REMOVED***,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

    ***REMOVED***
        ***REMOVED***

        "Date" -> { //date picker

            var parsedDateMillis: Long? = null
            var showError by remember { mutableStateOf(false) ***REMOVED***
            // Try to parse the date from the text
            try {
                parsedDateMillis = text.let {

                    val possibleDateFormats = listOf(
                        // List of possible date formats, the more the merrier
                        "yyyy-MM-dd",
                        "dd/MM/yyyy",
                        "MM/dd/yyyy",
                        "ddMMyyyy",
                        "MMddyyyy",
                        "yyyyMMdd",
                        "MMMM d, yyyy",
                        "d MMMM yyyy",

                        // Additional YYYY-MM-DD formats
                        "yyyy.MM.dd",
                        "yyyy/MM/dd",
                        "dd.MM.yyyy",
                        "dd-MM-yyyy",
                        "MM-dd-yyyy",

                        // Other variations with separators
                        "dd MMM yyyy",  // e.g., "12 Sep 2023"
                        "dd MMMM, yyyy", // e.g., "12 September, 2023"
                        "MMMM dd yyyy",  // e.g., "September 12 2023"
        ***REMOVED***

                    for (formatString in possibleDateFormats) {
                        try {

                            val dateFormat = SimpleDateFormat(formatString, Locale.getDefault())
                            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
                            val parsedDate = dateFormat.parse(it)
                            if (parsedDate != null) {
                                val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                                calendar.time = parsedDate
                                val year = calendar.get(Calendar.YEAR)

                                if (year in 1900..2100) { // Check if year is within the allowed range
                                    return@let parsedDate.time
                                ***REMOVED*** else {
                                    showError =
                                        true // Set showError to true if year is out of range
                                ***REMOVED***
                            ***REMOVED***
                        ***REMOVED*** catch (e: ParseException) {
                            // Ignore parsing errors and try the next format
                        ***REMOVED*** catch (e: Exception) {
                            // Ignore parsing errors and try the next format
                        ***REMOVED***

                    ***REMOVED***


                    return@let null // Return null if no format matches
                ***REMOVED***
            ***REMOVED*** catch (e: Exception) {
                showError = true
            ***REMOVED***


            // Show the DatePicker if the date was parsed successfully
            if (parsedDateMillis != null && !showError) {
                val datePickerState =
                    rememberDatePickerState(initialSelectedDateMillis = parsedDateMillis)

                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                        .padding(5.dp)
    ***REMOVED*** {
                    DatePicker(state = datePickerState, title = {
                        Text(
                            text = stringResource(R.string.date), color = Color.Black
            ***REMOVED***
                    ***REMOVED***)
                ***REMOVED***
            ***REMOVED*** else {
                // Fallback to an OutlinedTextField if parsing fails
                var localText by remember { mutableStateOf(text) ***REMOVED***
                var lastKnownText by remember { mutableStateOf(text) ***REMOVED***

                OutlinedTextField(value = localText, onValueChange = { newText ->
                    localText = newText
                ***REMOVED***, label = {
                    val labelText = if (showError) {
                        stringResource(R.string.enter_date_manually)
                    ***REMOVED*** else {
                        stringResource(id = R.string.date)
                    ***REMOVED***
                    Text(text = labelText)
                ***REMOVED***, modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), trailingIcon = {
                    IconButton(
                        onClick = {
                            changeText(localText)
                            lastKnownText = localText
                            showError = false
                        ***REMOVED***, enabled = localText != lastKnownText
        ***REMOVED*** {
                        FaIcon(faIcon = FaIcons.Check, tint = Color.Black)
                    ***REMOVED***
                ***REMOVED***, singleLine = true, isError = showError
    ***REMOVED***
            ***REMOVED***
        ***REMOVED***

        // Boolean field checkbox
        "Boolean" -> {
            BooleanFieldWithLabel(
                label = stringResource(R.string.field_value_boolean),
                value = text.toBoolean(),
                onValueChange = { newText: Boolean ->
                    editableText = newText.toString()
                    changeText(newText.toString())
                ***REMOVED***,
***REMOVED***
        ***REMOVED***
        // Float field
        "Float" -> {
            OutlinedTextField(
                value = text,
                onValueChange = { newText: String ->
                    editableText = newText
                    changeText(newText)
                ***REMOVED***,
                trailingIcon = {
                    IconButton(onClick = {
                        clipboardManager.setText(AnnotatedString(editableText))
                        Toast.makeText(
                            context,
                            context.getString(R.string.copied_to_clipboard),
                            Toast.LENGTH_SHORT
            ***REMOVED***.show()
                    ***REMOVED***) {
                        FaIcon(
                            FaIcons.CopyRegular,
                            tint = Color.Black,
                            modifier = Modifier.semantics {
                                contentDescription = context.getString(R.string.copy_value)
                            ***REMOVED***)
                    ***REMOVED***
                ***REMOVED***,
                label = { Text(stringResource(R.string.field_value_float)) ***REMOVED***,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

***REMOVED***

        ***REMOVED***
    ***REMOVED***
***REMOVED***
