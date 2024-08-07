package com.example.tesifrigo.ui.extraction

import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
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
import androidx.compose.material3.TextFieldDefaults
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tesifrigo.Screen
import com.example.tesifrigo.models.Extraction
import com.example.tesifrigo.models.ExtractionTable
import com.example.tesifrigo.utils.FileCard
import com.example.tesifrigo.utils.MyImageArea
import com.example.tesifrigo.utils.TableCell
import com.example.tesifrigo.viewmodels.ExtractionViewModel
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleExtractionScreen(
    navController: NavHostController,
    templateId: String
) {
    val viewModel = viewModel<ExtractionViewModel>()

    val extraction by viewModel.queryExtraction(templateId).collectAsState(initial = null)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = extraction?.template?.title ?: "Extraction Details") ***REMOVED***,
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() ***REMOVED***) {
                        FaIcon(faIcon = FaIcons.ArrowLeft)
                    ***REMOVED***
                ***REMOVED***
***REMOVED***
        ***REMOVED***
    ) { innerPadding ->
        if (extraction != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
***REMOVED*** {
                item {
                        TemplateInfo(extraction!!, navController)
                    ***REMOVED***
                // Extracted Tables Section
                if (extraction!!.extractedTables.isNotEmpty()) {
                    item {
                        Text(
                            text = "Extracted Tables",
                            style = MaterialTheme.typography.titleLarge
            ***REMOVED***
                    ***REMOVED***
                    items(extraction!!.extractedTables) { table ->

                            ExtractionTableDisplay(table, viewModel)

                    ***REMOVED***
                ***REMOVED***

                // Extracted Fields Section
                if (extraction!!.extractedFields.isNotEmpty()) {
                    item {
                        Text(
                            text = "Extracted Fields",
                            style = MaterialTheme.typography.titleLarge
            ***REMOVED***
                    ***REMOVED***
                    itemsIndexed(extraction!!.extractedFields) { index, field ->
                        ExtractionFieldComposable(extraction!!, index, viewModel)
                    ***REMOVED***
                ***REMOVED***

                // Other Attributes (extractionCosts, exceptionsOccurred)
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ***REMOVED*** {
                        Column(modifier = Modifier.padding(16.dp))
                        {


                            // Extraction Tags Section
                            if (extraction!!.tags.isNotEmpty()) {
                                ExtractionTags(extraction!!, viewModel)
                            ***REMOVED***

                            ExtractionExceptions(extraction!!)
                            // Extraction Format Section
                            FormatSection(extraction!!, viewModel)
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED*** else {
            // Handle the case where extraction is null
            Text(text = "No extraction found")
        ***REMOVED***
    ***REMOVED***
***REMOVED***

@Composable
fun ExtractionExceptions(extraction: Extraction) {

    Text("Extraction Costs", style = MaterialTheme.typography.titleMedium)
    Text(
        extraction.extractionCosts,
        style = MaterialTheme.typography.bodyMedium
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        "Exceptions Occurred",
        style = MaterialTheme.typography.titleMedium
    )
    if (extraction.exceptionsOccurred.isEmpty()) {
        Text(
            "No exceptions occurred.",
            style = MaterialTheme.typography.bodyMedium
        )
    ***REMOVED*** else {
        for (exception in extraction.exceptionsOccurred) {
            Text(
                "Error: ${exception.error***REMOVED***, Type: ${exception.errorType***REMOVED***, Description: ${exception.errorDescription***REMOVED***",
                style = MaterialTheme.typography.bodyMedium
***REMOVED***
        ***REMOVED***
    ***REMOVED***

    Spacer(modifier = Modifier.height(16.dp))

***REMOVED***

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExtractionTags(extraction: Extraction, viewModel: ExtractionViewModel) {

    Text(
        "Extraction Tags",
        style = MaterialTheme.typography.titleMedium
    )
    FlowRow( // Use FlowRow for wrapping chips
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (tag in extraction.tags) {
            var showDismissIcon by remember { mutableStateOf(false) ***REMOVED***

            AssistChip(
                onClick = { /* Handle tag click (if needed) */ ***REMOVED***,
                label = { Text(tag) ***REMOVED***,
                trailingIcon = {
                    AnimatedVisibility(
                        visible = showDismissIcon,
                        enter = fadeIn() + scaleIn(),
                        exit = fadeOut() + scaleOut()
        ***REMOVED*** {
                        FaIcon(faIcon = FaIcons.Times,
                            modifier = Modifier.clickable {
                                // Handle tag removal
                                viewModel.removeTag(extraction, tag)
                            ***REMOVED***
            ***REMOVED***
                    ***REMOVED***
                ***REMOVED***,
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                showDismissIcon = !showDismissIcon
                            ***REMOVED***
            ***REMOVED***
                    ***REMOVED***
***REMOVED***
        ***REMOVED***
    ***REMOVED***

    Spacer(modifier = Modifier.height(16.dp))
***REMOVED***


@Composable
fun TemplateInfo(extraction: Extraction, navController: NavHostController) {

    val context = LocalContext.current
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val templateId = extraction.template?.id
                        if(templateId!=null){
                            navController.navigate(Screen.EditTemplate.routeWithOptionalArgs("templateId" to templateId.toString()))
                        ***REMOVED***
                        else{
                              Toast.makeText(context, "No connected template found", Toast.LENGTH_SHORT).show()
                            ***REMOVED***
                    ***REMOVED***
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
***REMOVED*** {
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
        ***REMOVED***
    ***REMOVED***
***REMOVED***


@Composable
fun ExtractionTableDisplay(extractionTable: ExtractionTable, viewModel: ExtractionViewModel) {
    val columnHeaders = extractionTable.fields.firstOrNull()?.fields?.mapNotNull { it.templateField?.title ***REMOVED***

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
                    TableCell(text = "", modifier = Modifier.weight(1f), isHeader = true, onTextChange = {***REMOVED***)

                    for (header in columnHeaders) {
                        TableCell(text = header, modifier = Modifier.weight(1f), isHeader = true, onTextChange = {***REMOVED***)
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
                        TableCell(
                            text = (rowIndex + 1).toString(),
                            modifier = Modifier.align(Alignment.CenterStart),
                            isHeader = true,
                            onTextChange = {***REMOVED***)
                    ***REMOVED***
                    for (field in row.fields) {
                        TableCell(text = field.value, modifier = Modifier.weight(1f), onTextChange = {viewModel.updateField(field, it) ***REMOVED***)
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
    val formatOptions =
        listOf("json", "csv", "txt") // Add more formats if needed
    val context= LocalContext.current

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded ***REMOVED***
    ) {
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
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor()

        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest
        = { expanded = false ***REMOVED***) {
            formatOptions.forEach { format ->
                DropdownMenuItem(
                    text = { Text(format) ***REMOVED***,
                    onClick = {
                        selectedFormat = format
                        viewModel.changeFormat(extraction, format, context)
                        expanded = false
                    ***REMOVED***
    ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***


    // File Card Section (display only if fileUri is available)
    Spacer(modifier = Modifier.height(16.dp))
    FileCard(
        extraction = extraction,
        modifier = Modifier.fillMaxWidth()
    )


***REMOVED***


@OptIn(ExperimentalMaterial3Api::class)
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
                text = templateField.title,
                style = MaterialTheme.typography.titleMedium
***REMOVED***
            OutlinedTextField(
                value = value,
                onValueChange = { newText ->
                    value = newText
                    viewModel.updateField(extraction.extractedFields[index], newText)
                ***REMOVED***,
                label = { Text(text = "Value") ***REMOVED***,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor
                    = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline

    ***REMOVED***
***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***
