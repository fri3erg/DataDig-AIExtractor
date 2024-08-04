package com.example.tesifrigo.ui.template

import android.widget.NumberPicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tesifrigo.models.Template
import com.example.tesifrigo.models.TemplateTable
import com.example.tesifrigo.viewmodels.TemplateViewModel
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTemplateScreen(
    navController: NavHostController,
    templateId: String
) {
    val viewModel = viewModel<TemplateViewModel>()
    val template by viewModel.queryTemplate(templateId).collectAsState(initial = null)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = template?.title ?: "Edit Template") ***REMOVED***,
                navigationIcon = {
                    FaIcon(faIcon = FaIcons.ArrowLeft)
                ***REMOVED***
***REMOVED***
        ***REMOVED***,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.addField(template!!) ***REMOVED***,
                containerColor = MaterialTheme.colorScheme.primary
***REMOVED*** {
                FaIcon(faIcon = FaIcons.Plus)
            ***REMOVED***
        ***REMOVED***
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (template != null) {
                // Template Title Section
                item {
                    OutlinedTextField(
                        value = template?.title ?: "",
                        onValueChange = { viewModel.updateTemplateTitle(template!!, it) ***REMOVED***,
                        label = { Text("Template Title") ***REMOVED***,
                        modifier = Modifier.fillMaxWidth()
        ***REMOVED***
                ***REMOVED***

                item {
                    TemplateTagsSection(template!!, viewModel)
                ***REMOVED***

                // Template Fields Section
                itemsIndexed(template?.fields ?: emptyList()) { index, _ ->
                    TemplateField(template, index, viewModel)
                ***REMOVED***
                item {
                    TemplateTablesSection(template!!, viewModel)
                ***REMOVED***


            ***REMOVED*** else {
                // ... (handle null template - same as before)
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TemplateTagsSection(template: Template, viewModel: TemplateViewModel) {
    Column {
        Text("Template Tags", style = MaterialTheme.typography.titleMedium)
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)

        ) {
        for (tag in template.tags) {
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
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = "Remove Tag",
                            modifier = Modifier.clickable {
                                viewModel.removeTag(template, tag)
                            ***REMOVED***
            ***REMOVED***
                    ***REMOVED***
                ***REMOVED***,
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                showDismissIcon = true
                                tryAwaitRelease()
                                showDismissIcon = false
                            ***REMOVED***
            ***REMOVED***
                    ***REMOVED***
***REMOVED***
        ***REMOVED***
    ***REMOVED***

        // Add New Tag Section
        var newTag by remember { mutableStateOf("") ***REMOVED***
        OutlinedTextField(
            value = newTag,
            onValueChange = { newTag = it ***REMOVED***,
            label = { Text("Add Tag") ***REMOVED***,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                FaIcon(faIcon = FaIcons.Plus, tint = Color.Gray)
            ***REMOVED***
        )

        // Call changeTags when clicking outside the chips
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    viewModel.changeTags(template, newTag)
                ***REMOVED***
        )
    ***REMOVED***
***REMOVED***@Composable
fun TemplateField(
    template: Template?,
    index: Int,
    viewModel: TemplateViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            template?.fields?.get(index)?.let { field ->
                // Title with Help Icon
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = field.title,
                        onValueChange = { newText ->
                            viewModel.updateTemplateItem(template, "title" to newText, index)
                        ***REMOVED***,
                        label = { Text("Field Title") ***REMOVED***,
                        modifier = Modifier.weight(1f) // Occupy remaining space
        ***REMOVED***
                    Spacer(modifier = Modifier.width(4.dp)) // Reduced spacing
                    HelpIconButton(helpText = "This is the title of the field.")
                ***REMOVED***

                Spacer(modifier = Modifier.height(8.dp))

                // Description with Help Icon
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = field.description,
                        onValueChange = { newText ->
                            viewModel.updateTemplateItem(template, "description" to newText, index)
                        ***REMOVED***,
                        label = { Text("Field Description") ***REMOVED***,
                        modifier = Modifier.weight(1f)
        ***REMOVED***
                    Spacer(modifier = Modifier.width(4.dp)) // Reduced spacing
                    HelpIconButton(helpText = "This is a detailed description of the field.")
                ***REMOVED***

                Spacer(modifier = Modifier.height(8.dp))

                // Type with Help Icon
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = field.type,
                        onValueChange = { newText ->
                            viewModel.updateTemplateItem(template, "type" to newText, index)
                        ***REMOVED***,
                        label = { Text("Field Type") ***REMOVED***,
                        modifier = Modifier.weight(1f)
        ***REMOVED***
                    Spacer(modifier = Modifier.width(4.dp)) // Reduced spacing
                    HelpIconButton(helpText = "Specify the type of data expected for this field (e.g., 'string', 'number', 'date').")
                ***REMOVED***
                Spacer(modifier = Modifier.height(8.dp))

                // Required & Intelligent Extraction (separate composables with equal weight)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
    ***REMOVED*** {
                    BooleanFieldWithLabel(
                        label = "Required",
                        value = field.required,
                        onValueChange = { newValue ->
                            viewModel.updateTemplateItem(template, "required" to newValue, index)
                        ***REMOVED***,
                        modifier = Modifier.weight(1f)
        ***REMOVED***

                    BooleanFieldWithLabel(
                        label = "Interpretative",
                        value = field.intelligentExtraction,
                        onValueChange = { newValue ->
                            viewModel.updateTemplateItem(template, "intelligentExtraction" to newValue, index)
                        ***REMOVED***,
                        modifier = Modifier.weight(1f)
        ***REMOVED***
                ***REMOVED***
            ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***


@Composable
fun TemplateTablesSection(template: Template, viewModel: TemplateViewModel) {
    Column {
        Text("Template Tables", style = MaterialTheme.typography.titleMedium)

        // Display existing tables
        for ((tableIndex, table) in template.tables.withIndex()) {
            TemplateTableCard(table, tableIndex, viewModel, template)
            Spacer(modifier = Modifier.height(8.dp))
        ***REMOVED***

        // Add Table Button
        Button(onClick = { viewModel.addTable(template) ***REMOVED***) {
            Text("Add Table")
        ***REMOVED***
    ***REMOVED***
***REMOVED***@Composable
fun TemplateTableCard(table: TemplateTable, tableIndex: Int, viewModel: TemplateViewModel, template: Template) {
    var numRows by remember { mutableStateOf(maxOf(2, table.rows.size)) ***REMOVED*** // Start with at least 2 rows
    var numCols by remember { mutableStateOf(maxOf(2, table.columns.size)) ***REMOVED*** // Start with at least 2 columns

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = table.title,
                onValueChange = { newText ->
                    viewModel.updateTableItem(template, "title" to newText, tableIndex)
                ***REMOVED***,
                label = { Text("Table Title") ***REMOVED***,
                modifier = Modifier.fillMaxWidth()
***REMOVED***

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = table.description,
                onValueChange = { newText ->
                    viewModel.updateTableItem(template, "description" to newText, tableIndex)
                ***REMOVED***,
                label = { Text("Table Description") ***REMOVED***,
                modifier = Modifier.fillMaxWidth()
***REMOVED***

            Spacer(modifier = Modifier.height(16.dp))

            // Table Grid
            TableGrid(table, viewModel, tableIndex, numRows, numCols, template)

            Spacer(modifier = Modifier.height(16.dp))

            // Delete Table Button
            Button(
                onClick = { viewModel.deleteTable(template, tableIndex) ***REMOVED***,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
***REMOVED*** {
                Text("Delete Table")
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***

@Composable
fun TableGrid(table: TemplateTable, viewModel: TemplateViewModel, tableIndex: Int, numRows: Int, numCols: Int, template: Template) {
    Column {
        // Header Row (Column Headers)
        Row {
            // Empty cell for row index
            TableCellTemplate(text = "", isHeader = true, isAddButton = true, onClick = {
                viewModel.addColumnToTable(template, tableIndex, "")
            ***REMOVED***, modifier = Modifier.weight(1f))

            for ((columnIndex, columnField) in table.columns.withIndex()) {
                TableCellTemplate(
                    text = columnField.title,
                    isHeader = true,
                    onValueChange = { newText ->
                        viewModel.updateTableColumnHeader(template, tableIndex, columnIndex, newText)
                    ***REMOVED***, modifier = Modifier.weight(1f)
    ***REMOVED***
            ***REMOVED***
        ***REMOVED***

        // Data Rows (with row indexes and editable cells)
        for ((rowIndex, row) in table.rows.withIndex()) {
            Row {
                // Row index cell (editable)
                TableCellTemplate(
                    text = row.title,
                    isHeader = true,
                    onValueChange = { newText ->
                        viewModel.updateTableRowHeader(template, tableIndex, rowIndex, newText)
                    ***REMOVED***, modifier = Modifier.weight(1f)
    ***REMOVED***

                for ((columnIndex, _) in table.columns.withIndex()) { // Iterate over columns to create cells
                    TableCellTemplate(text = "", modifier = Modifier.weight(1f)) // Initially empty cells
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***

        // Add Row Button (fake row)
        Row {
            TableCellTemplate(text = "", isHeader = true, modifier = Modifier.weight(1f)) // Empty cell for row index

            for (colIndex in 0 until numCols) {
                if (colIndex == numCols - 1) {
                    TableCellTemplate(text = "+", isAddButton = true, modifier = Modifier.weight(1f), onClick = {
                        viewModel.addRowToTable(template, tableIndex, "")
                    ***REMOVED***)
                ***REMOVED*** else {
                    TableCellTemplate(text = "", modifier = Modifier.weight(1f))
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***

@Composable
fun TableCellTemplate(text: String, modifier: Modifier = Modifier, isHeader: Boolean = false, isAddButton: Boolean = false, onValueChange: (String) -> Unit = {***REMOVED***, onClick: () -> Unit = {***REMOVED***) {

    var showDialog by remember { mutableStateOf(false) ***REMOVED***
    var editedText by remember { mutableStateOf(text) ***REMOVED***
    Box(
        modifier = modifier
            .padding(8.dp)
            .border(1.dp, Color.LightGray), // Add a border to each cell
                contentAlignment = Alignment.Center
    ) {
        if (isHeader && !isAddButton) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    showDialog = true
                    editedText = text // Initialize with current text
                ***REMOVED***
***REMOVED***

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false ***REMOVED***,
                    title = { Text("Edit Header") ***REMOVED***,
                    text = {
                        OutlinedTextField(
                            value = editedText,
                            onValueChange = { editedText = it ***REMOVED***,
                            label = { Text("Enter Header") ***REMOVED***
            ***REMOVED***
                    ***REMOVED***,
                    confirmButton = {
                        TextButton(onClick = {
                            onValueChange(editedText)
                            showDialog = false
                        ***REMOVED***) {
                            Text("OK")
                        ***REMOVED***
                    ***REMOVED***,
                    dismissButton = {
                        TextButton(onClick = { showDialog = false ***REMOVED***) {
                            Text("Cancel")
                        ***REMOVED***
                    ***REMOVED***
    ***REMOVED***
            ***REMOVED***
        ***REMOVED*** else if (isAddButton) {
            Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
                Text(text)
            ***REMOVED***
        ***REMOVED*** else {
            Text(
                text = text,
                textAlign = TextAlign.Center
***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***


@Composable
fun BooleanFieldWithLabel(
    label: String,
    value: Boolean,
    onValueChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier

    ) {
        Text(label)
        Spacer(modifier = Modifier.width(8.dp))
        Switch(
            checked = value,
            onCheckedChange = onValueChange
        )
    ***REMOVED***
***REMOVED***

// Help Icon Button Composable (with modal tooltip)
@Composable
fun HelpIconButton(helpText: String) {
    var showDialog by remember { mutableStateOf(false) ***REMOVED***

    IconButton(
        onClick = { showDialog = true ***REMOVED***,
        modifier = Modifier.size(20.dp) // Make the icon smaller
    ) {
        Icon(Icons.Filled.Build, contentDescription = "Help", modifier = Modifier.offset(y = (-4).dp)) // Adjust position slightly up
    ***REMOVED***

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false ***REMOVED***,
            title = { Text("Help") ***REMOVED***,
            text = { Text(helpText) ***REMOVED***,
            confirmButton = {
                TextButton(onClick = { showDialog = false ***REMOVED***) {
                    Text("OK")
                ***REMOVED***
            ***REMOVED***
        )
    ***REMOVED***
***REMOVED***

