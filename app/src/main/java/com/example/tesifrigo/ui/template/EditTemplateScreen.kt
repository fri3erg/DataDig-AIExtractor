package com.example.tesifrigo.ui.template

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tesifrigo.models.Template
import com.example.tesifrigo.models.TemplateField
import com.example.tesifrigo.utils.AddButton
import com.example.tesifrigo.utils.DeleteButton
import com.example.tesifrigo.utils.HelpIconButton
import com.example.tesifrigo.viewmodels.TemplateViewModel
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTemplateScreen(
    navController: NavHostController,
    templateId: String
) {
    val viewModel = viewModel<TemplateViewModel>()
    val template by viewModel.queryTemplate(templateId).collectAsState(initial = null)
    val focusRequesterIndex by viewModel.focusRequesterIndex.collectAsState()
    val listState = rememberLazyListState() // Remember the LazyListState
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = template?.title ?: "Edit Template", fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 16.dp)) ***REMOVED***,
                navigationIcon = {
                    FaIcon(faIcon = FaIcons.ArrowLeft, modifier = Modifier.clickable {
                        navController.navigateUp()
                    ***REMOVED***)

                ***REMOVED***
***REMOVED***
        ***REMOVED***
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            state = listState
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
                item{
                    Text("Template Fields", style = MaterialTheme.typography.titleMedium)
                ***REMOVED***

                // Template Fields Section
                itemsIndexed(template?.fields ?: emptyList()) { index, _ ->
                    val listStateChange: () -> Unit ={
                        scope.launch {
                            listState.scrollToItem(index+3)
                        ***REMOVED***
                    ***REMOVED***

                    TemplateFieldComposable(template, index, viewModel, focusRequesterIndex,listStateChange)
                ***REMOVED***
                item{
                    AddButton(text = "Field", onClick = { viewModel.addField(template!!) ***REMOVED***)

                ***REMOVED***
                item{
                    Text("Template Tables", style = MaterialTheme.typography.titleMedium)
                ***REMOVED***
                itemsIndexed(template?.tables ?: emptyList()) { index, _ ->
                    val listStateChange: () -> Unit ={
                        scope.launch {
                            listState.animateScrollToItem(listState.layoutInfo.totalItemsCount)
                        ***REMOVED***
                    ***REMOVED***

                    TemplateTableCard(index, viewModel, template!!, focusRequesterIndex, listStateChange)
                ***REMOVED***
                item {
                    // Add Table Button
                    Spacer(modifier = Modifier.height(8.dp))
                    AddButton(text = "Table", onClick = { viewModel.addTable(template!!) ***REMOVED***)
                ***REMOVED***

            ***REMOVED*** else {

                item {
                    Text("Template not found")
                ***REMOVED***
                item {
                    Button(onClick = {
                        navController.navigateUp()
                    ***REMOVED***) {
                        Text("Back")
                    ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***

    ***REMOVED***


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TemplateTagsSection(template: Template, viewModel: TemplateViewModel) {
    val context = LocalContext.current
    Column {
        Text("Template Tags", style = MaterialTheme.typography.titleMedium)
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)

        ) {
        for (tag in template.tags) {
            var showDismissIcon by remember { mutableStateOf(false) ***REMOVED***

            AssistChip(
                onClick = { showDismissIcon = !showDismissIcon ***REMOVED***,
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
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                FaIcon(faIcon = FaIcons.Plus, tint = Color.Gray, modifier = Modifier.clickable {
                    when {
                        newTag.isBlank() -> {
                            Toast.makeText(context, "Tag cannot be empty", Toast.LENGTH_SHORT).show()
                        ***REMOVED***
                        template.tags.size >= 5 -> {
                            Toast.makeText(context, "Max number of tags is 5", Toast.LENGTH_SHORT).show()
                        ***REMOVED***
                        else -> {
                            viewModel.changeTags(template, newTag)
                            newTag = ""
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
        ***REMOVED***
                ***REMOVED***
        )

    ***REMOVED***
***REMOVED***

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateFieldComposable(
    template: Template?,
    index: Int,
    viewModel: TemplateViewModel,
    focusRequesterIndex: Int? = null,
    changelistState: () -> Unit,

    ) {

    val focusRequester = remember { FocusRequester() ***REMOVED*** // Create FocusRequester for field
    val typeOptions = listOf("Any", "String", "Date", "Number")
    var selectedType by remember { mutableStateOf(typeOptions[0]) ***REMOVED***
    var expanded by remember { mutableStateOf(false) ***REMOVED***

    LaunchedEffect(key1 = focusRequesterIndex) {
        if (focusRequesterIndex == index) {
            focusRequester.requestFocus()
            changelistState()
            viewModel._focusRequesterIndex.value = null // Reset after focusing
        ***REMOVED***
    ***REMOVED***

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .focusRequester(focusRequester)
            ,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
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

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded ***REMOVED***
        ***REMOVED*** {
                        TextField(
                            value = selectedType,
                            onValueChange = {***REMOVED***,
                            readOnly = true,
                            label = { Text("Type") ***REMOVED***,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) ***REMOVED***,
                            colors = ExposedDropdownMenuDefaults.textFieldColors(),
                            modifier = Modifier
                                .menuAnchor()
                                .focusRequester(focusRequester)
            ***REMOVED***

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false ***REMOVED***) {
                            typeOptions.forEach { type ->
                                DropdownMenuItem(
                                    text = { Text(type) ***REMOVED***,
                                    onClick = {
                                        selectedType = type
                                        viewModel.updateTemplateItem(
                                            template,
                                            "type" to type,
                                            index
                            ***REMOVED***
                                        expanded = false

                                    ***REMOVED***
                    ***REMOVED***
                            ***REMOVED***
                        ***REMOVED***
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
                Spacer(modifier = Modifier.height(8.dp))
                DeleteButton(text = "Field", onClick = {  viewModel.deleteField(template, index)  ***REMOVED***)
            ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***




@Composable
fun TemplateTableCard(tableIndex: Int, viewModel: TemplateViewModel, template: Template, focusRequesterIndex: Int? = null, changelistState: () -> Unit) {
    val table = template.tables[tableIndex]
    val focusRequester = remember { FocusRequester() ***REMOVED***

    LaunchedEffect(key1 = focusRequesterIndex) {
        if (focusRequesterIndex == tableIndex+template.fields.size-1) {
            focusRequester.requestFocus()
            changelistState()
            viewModel._focusRequesterIndex.value = -1 // Reset after focusing
        ***REMOVED***
    ***REMOVED***
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .focusRequester(focusRequester),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
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
            TableGrid(viewModel, tableIndex, template)

            Spacer(modifier = Modifier.height(16.dp))

            // Delete Table Button
            DeleteButton(text = "Table", onClick = { viewModel.deleteTable(template, tableIndex) ***REMOVED***)
        ***REMOVED***
    ***REMOVED***
***REMOVED***
@Composable
fun TableGrid(viewModel: TemplateViewModel, tableIndex: Int, template: Template) {

    var showDialog by remember { mutableStateOf(false) ***REMOVED***
    var newText by remember { mutableStateOf("") ***REMOVED***
    var isColumn by remember { mutableStateOf(false) ***REMOVED***
    val table = template.tables[tableIndex]
    val scrollState = rememberScrollState()


    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(1.dp)) {
        // Column Headers
        Row(modifier = Modifier
            .padding(1.dp)
            .fillMaxWidth()
            .horizontalScroll(scrollState)){ // Apply horizontal scrolling){ // Make the header row horizontally scrollable) { // Wrap column headers in a Row with weight
            TableCellTemplate(
                text = "",
                modifier = Modifier.weight(1f),
                invisible = true
***REMOVED*** // Add a blank cell for the button column
            for ((columnIndex, columnField) in table.columns.withIndex()) {
                TableCellTemplate(
                    text = columnField.title,
                    isHeader = true,
                    onValueChange = { newText ->
                        viewModel.updateTableColumnHeader(
                            template,
                            tableIndex,
                            columnIndex,
                            newText
            ***REMOVED***
                    ***REMOVED***,
                    modifier = Modifier.weight(1f)
    ***REMOVED***
            ***REMOVED***
            // Add Column Button (top right, outside the table)

            TableCellTemplate(
                text = "",
                modifier = Modifier.weight(1f),
                isButton = true,
                buttonClick = {
                    showDialog = true; isColumn = true
                ***REMOVED***) // Add a blank cell for the button column
        ***REMOVED***

        for ((rowIndex, rowField) in table.rows.withIndex()) {
            Row(modifier = Modifier.padding(1.dp)) {
                TableCellTemplate(
                    text = rowField.title,
                    isHeader = true,
                    onValueChange = { newText ->
                        viewModel.updateTableRowHeader(template, tableIndex, rowIndex, newText)
                    ***REMOVED***,
                    modifier = Modifier.weight(1f)
    ***REMOVED***
                for (templateField in table.columns) {
                    TableCellTemplate(text = "", modifier = Modifier.weight(1f))
                ***REMOVED***
                TableCellTemplate(
                    text = "",
                    modifier = Modifier.weight(1f),
                    invisible = true
    ***REMOVED*** // Add a blank cell for the button column
            ***REMOVED***
        ***REMOVED***
        Row(modifier = Modifier.padding(1.dp)) {
            TableCellTemplate(text = "", modifier = Modifier.weight(1f), isButton = true, buttonClick = { showDialog=true; isColumn=false ***REMOVED***) // Add a blank cell for the button column

            for (templateField in table.columns) {
                TableCellTemplate(text = "", modifier = Modifier.weight(1f), invisible = true)
            ***REMOVED***
            TableCellTemplate(text = "", modifier = Modifier.weight(1f), invisible = true)

        ***REMOVED***
    ***REMOVED***

    if (showDialog) {
        val nMax= if (isColumn) {table.columns.size***REMOVED*** else {table.rows.size***REMOVED***
        val context = LocalContext.current
        if (nMax>15){
            Toast.makeText(context, "Max number of rows and columns is 15", Toast.LENGTH_SHORT).show()
            showDialog=false
        ***REMOVED***
        else {
            AlertTable(
                editedText = newText,
                changeText = { newText = it ***REMOVED***,
                onValueChange = {it->
                    if (isColumn) {
                        viewModel.addColumnToTable(template, tableIndex, it)
                    ***REMOVED*** else {
                        viewModel.addRowToTable(template, tableIndex, it)
                    ***REMOVED***
                    showDialog = false
                    newText = ""
                ***REMOVED***,
                changeShowDialog = { showDialog = it ***REMOVED***,
***REMOVED***

        ***REMOVED***
    ***REMOVED***
***REMOVED***

@Composable
fun TableCellTemplate(text: String, modifier: Modifier = Modifier, isHeader: Boolean = false, onValueChange: (TemplateField) -> Unit = {***REMOVED***, invisible: Boolean = false, isButton: Boolean = false, buttonClick: () -> Unit = {***REMOVED***) {

    var modifierPadded =
        modifier
            .padding(1.dp)
            .defaultMinSize(minWidth = 24.dp, minHeight = 24.dp) // Ensure a minimum width for cells
    var boxSize by remember { mutableStateOf(IntSize.Zero) ***REMOVED***


    if (!invisible) {
        modifierPadded = modifierPadded.border(1.dp, Color.Gray)
    ***REMOVED***
    if (isHeader) {
        modifierPadded = modifierPadded.background(Color.LightGray)
    ***REMOVED***
    if (isButton) {//pls round the button
        modifierPadded =
            modifierPadded
                .background(Color.Blue)
                .clip(shape = RoundedCornerShape(4.dp))
    ***REMOVED***
    var showDialog by remember { mutableStateOf(false) ***REMOVED***
    var editedText by remember { mutableStateOf(text) ***REMOVED***
    Box(
        modifier = modifierPadded.onSizeChanged { size ->
            boxSize = size
        ***REMOVED***,
        contentAlignment = Alignment.Center

    ) {
        if (isHeader) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1, // Limit to a single line
                modifier = Modifier.clickable {
                    showDialog = true
                    editedText = text // Initialize with current text
                ***REMOVED***
***REMOVED***
            if (showDialog) {
                AlertTable(
                    editedText = editedText,
                    changeText = { editedText = it ***REMOVED***,
                    onValueChange = { onValueChange(it) ***REMOVED***,
                    changeShowDialog = { showDialog = it ***REMOVED***,
    ***REMOVED***
            ***REMOVED***
        ***REMOVED*** else if (isButton) {
            val iconSize = if (boxSize != IntSize.Zero) {
                LocalDensity.current.run { minOf(boxSize.width, boxSize.height).toDp() / 2 ***REMOVED***
            ***REMOVED*** else {
                16.dp
            ***REMOVED***

                FaIcon(
                    faIcon = FaIcons.Plus,
                    tint = Color.White,
                    size = iconSize,
                    modifier = Modifier.clickable {
                        buttonClick()
                    ***REMOVED***

    ***REMOVED***

            ***REMOVED*** else {
                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1 // Limit to a single line

    ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertTable(editedText: String, changeText: (String) -> Unit, onValueChange: (TemplateField) -> Unit,changeShowDialog: (Boolean) -> Unit) {
    val focusRequester = remember { FocusRequester() ***REMOVED***
    var selectedType by remember { mutableStateOf("Any") ***REMOVED*** // Default type
    var selectedRequired by remember { mutableStateOf(false) ***REMOVED***
    AlertDialog(
        onDismissRequest = { changeShowDialog(false) ***REMOVED***,
        title = { Text("Edit Header") ***REMOVED***,
        text = {
            Column {
                OutlinedTextField(
                    value = editedText,
                    onValueChange = { changeText(it) ***REMOVED***,
                    label = { Text("Enter Header") ***REMOVED***
    ***REMOVED***

                Spacer(modifier = Modifier.height(8.dp))

                // Type Dropdown
                var expanded by remember { mutableStateOf(false) ***REMOVED***
                val typeOptions = listOf("Any", "String", "Date", "Number")

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded ***REMOVED***
    ***REMOVED*** {
                    TextField(
                        value = selectedType,
                        onValueChange = {***REMOVED***,
                        readOnly = true,
                        label = { Text("Type") ***REMOVED***,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) ***REMOVED***,
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier
                            .menuAnchor()
                            .focusRequester(focusRequester)
        ***REMOVED***

                    ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false ***REMOVED***) {
                        typeOptions.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) ***REMOVED***,
                                onClick = {
                                    selectedType = type
                                    expanded = false
                                ***REMOVED***
                ***REMOVED***
                        ***REMOVED***
                    ***REMOVED***

                ***REMOVED***
                Spacer(modifier = Modifier.height(8.dp))
                // Required Checkbox
                BooleanFieldWithLabel(
                    label = "Required",
                    value = selectedRequired,
                    onValueChange = { newValue -> selectedRequired = newValue ***REMOVED***
    ***REMOVED***
            ***REMOVED***
        ***REMOVED***,
        confirmButton = {
            TextButton(onClick = {
                val newField= TemplateField().apply {
                    this.title=editedText
                    this.type=selectedType
                    this.required=selectedRequired
                ***REMOVED***
                onValueChange(newField) // Pass the edited text
                // You'll likely want to pass the selectedType to your ViewModel as well
                changeShowDialog(false)
            ***REMOVED***) {
                Text("OK")
            ***REMOVED***
        ***REMOVED***,
        dismissButton = {
            TextButton(onClick = { changeShowDialog(false) ***REMOVED***) {
                Text("Cancel")
            ***REMOVED***
        ***REMOVED***
    )***REMOVED***


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
***REMOVED***
        ***REMOVED***
    ***REMOVED***

    // Help Icon Button Composable (with modal tooltip)



