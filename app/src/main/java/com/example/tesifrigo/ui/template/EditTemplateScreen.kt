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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tesifrigo.models.Template
import com.example.tesifrigo.models.TemplateField
import com.example.tesifrigo.models.TemplateTable
import com.example.tesifrigo.ui.theme.dark_blue
import com.example.tesifrigo.ui.theme.dark_red
import com.example.tesifrigo.ui.theme.vale
import com.example.tesifrigo.utils.AddButton
import com.example.tesifrigo.utils.DeleteButton
import com.example.tesifrigo.utils.HelpIconButton
import com.example.tesifrigo.viewmodels.TemplateViewModel
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTemplateScreen(
    navController: NavHostController, templateId: String, templateViewModel: TemplateViewModel
) {
    if (templateId.isEmpty()) {
        navController.navigateUp()
    ***REMOVED***
    val template by templateViewModel.queryTemplate(templateId).collectAsState(initial = null)
    val focusRequesterIndex by templateViewModel.focusRequesterIndex.collectAsState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var title by remember { mutableStateOf(template?.title) ***REMOVED***

    LaunchedEffect(template) {
        if (template != null && title == null) {
            title = template!!.title

        ***REMOVED***
    ***REMOVED***



    Scaffold(topBar = {
        TopAppBar(title = {
            title?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 16.dp)
    ***REMOVED***
            ***REMOVED***
        ***REMOVED***, navigationIcon = {
            FaIcon(faIcon = FaIcons.ArrowLeft, modifier = Modifier.clickable {
                navController.navigateUp()
            ***REMOVED***)
        ***REMOVED***)
    ***REMOVED***) { innerPadding ->
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
                    title?.let {
                        OutlinedTextField(value = it,
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Black,
                                unfocusedLabelColor = Color.Black,
                ***REMOVED***,
                            onValueChange = { newValue ->
                                templateViewModel.updateTemplateTitle(template!!, newValue)
                                title = newValue
                            ***REMOVED***,
                            label = { Text("Template Title", color = Color.Black) ***REMOVED***,
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = {
                                HelpIconButton(helpText = "This is the title of the template.")
                            ***REMOVED***)
                    ***REMOVED***
                ***REMOVED***


                item {
                    Text("Template Fields", style = MaterialTheme.typography.titleMedium)
                ***REMOVED***

                // Template Fields Section
                itemsIndexed(template?.fields ?: emptyList()) { index, _ ->
                    val listStateChange: () -> Unit = {
                        scope.launch {
                            listState.scrollToItem(index + 3)
                        ***REMOVED***
                    ***REMOVED***

                    TemplateFieldComposable(
                        template, index, templateViewModel, focusRequesterIndex, listStateChange
        ***REMOVED***
                ***REMOVED***

                item {
                    AddButton(text = "Field", onClick = { templateViewModel.addField(template!!) ***REMOVED***)
                ***REMOVED***

                item {
                    Text("Template Tables", style = MaterialTheme.typography.titleMedium)
                ***REMOVED***
                itemsIndexed(template?.tables ?: emptyList()) { index, _ ->
                    val listStateChange: () -> Unit = {
                        scope.launch {
                            listState.animateScrollToItem(listState.layoutInfo.totalItemsCount)
                        ***REMOVED***
                    ***REMOVED***

                    TemplateTableCard(
                        index, templateViewModel, template!!, focusRequesterIndex, listStateChange
        ***REMOVED***
                ***REMOVED***

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    AddButton(text = "Table", onClick = { templateViewModel.addTable(template!!) ***REMOVED***)
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
fun TemplateKeyWordsSection(
    template: Template, table: TemplateTable, viewModel: TemplateViewModel
) {
    val context = LocalContext.current
    Column {
        Row {
            Text("Table Keywords", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.width(8.dp))
            HelpIconButton(helpText = "These are the keywords that the LLM will look for in the text to extract the table.",
                title = "Table Keywords")
            Spacer(modifier = Modifier.weight(1f))
        ***REMOVED***
        FlowRow(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)

        ) {
            for (keyword in table.keywords) {
                var showDismissIcon by remember { mutableStateOf(false) ***REMOVED***

                AssistChip(onClick = { showDismissIcon = !showDismissIcon ***REMOVED***,
                    label = { Text(keyword) ***REMOVED***,
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
                                    viewModel.removeKeyword(table, table.keywords.indexOf(keyword))
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
            label = { Text("Add Keyword", color = Color.Black) ***REMOVED***,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                FaIcon(faIcon = FaIcons.Plus, tint = Color.Black, modifier = Modifier.clickable {
                    when {
                        newKey.isBlank() -> {
                            Toast.makeText(context, "Keyword cannot be empty", Toast.LENGTH_SHORT)
                                .show()
                        ***REMOVED***

                        template.tags.size >= 10 -> {
                            Toast.makeText(
                                context, "Max number of keywords is 10", Toast.LENGTH_SHORT
                ***REMOVED***.show()
                        ***REMOVED***

                        else -> {
                            viewModel.addKeyword(table, newKey)
                            newKey = ""
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***)
            ***REMOVED***)

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
    val typeOptions = listOf("Any", "Text", "Date", "Number", "Boolean", "Float")
    var expanded by remember { mutableStateOf(false) ***REMOVED***
    val foundField by remember { mutableStateOf(template?.fields?.get(index)) ***REMOVED***
    var title by remember { mutableStateOf(foundField?.title) ***REMOVED***
    var description by remember { mutableStateOf(foundField?.description) ***REMOVED***
    var type by remember { mutableStateOf(foundField?.type) ***REMOVED***
    var required by remember { mutableStateOf(foundField?.required) ***REMOVED***
    var intelligentExtraction by remember { mutableStateOf(foundField?.intelligentExtraction) ***REMOVED***
    var default by remember { mutableStateOf(foundField?.default) ***REMOVED***
    var list by remember { mutableStateOf(foundField?.list) ***REMOVED***


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
            .focusRequester(focusRequester),
        colors = CardDefaults.cardColors(
            containerColor = vale
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            foundField.let { field ->
                if (field != null && template != null) {
                    // Title with Help Icon
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        title?.let {
                            OutlinedTextField(value = it,
                                onValueChange = { newText ->
                                    title = newText
                                    viewModel.updateTemplateItem(
                                        template, "title" to newText, index
                        ***REMOVED***
                                ***REMOVED***,
                                label = { Text("Field Title") ***REMOVED***,
                                modifier = Modifier.weight(1f), // Occupy remaining space
                                trailingIcon = {
                                    HelpIconButton(helpText = "This is the title of the field.")
                                ***REMOVED***)

                        ***REMOVED***
                    ***REMOVED***

                    Spacer(modifier = Modifier.height(8.dp))

                    // Description with Help Icon
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        description?.let {
                            OutlinedTextField(value = it,
                                onValueChange = { newText ->
                                    description = newText
                                    viewModel.updateTemplateItem(
                                        template, "description" to newText, index
                        ***REMOVED***
                                ***REMOVED***,
                                label = { Text("Field Description") ***REMOVED***,
                                modifier = Modifier.weight(1f),
                                trailingIcon = {
                                    HelpIconButton(helpText = "This is the description of the field.")
                                ***REMOVED***)
                        ***REMOVED***
                    ***REMOVED***

                    Spacer(modifier = Modifier.height(8.dp))

                    // Type with Help Icon
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded ***REMOVED***) {
                            type?.let {
                                TextField(value = it,
                                    onValueChange = {***REMOVED***,
                                    readOnly = true,
                                    label = { Text("Type") ***REMOVED***,
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = expanded
                            ***REMOVED***
                                    ***REMOVED***,
                                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                                        focusedLabelColor = Color.Black,
                                        unfocusedLabelColor = Color.Black,
                                        focusedIndicatorColor = Color.Black,
                                        unfocusedIndicatorColor = Color.Black,
                                        unfocusedContainerColor = vale,
                                        focusedContainerColor = vale
                        ***REMOVED***,
                                    modifier = Modifier
                                        .menuAnchor()
                                        .focusRequester(focusRequester)
                    ***REMOVED***
                            ***REMOVED***

                            ExposedDropdownMenu(modifier = Modifier.background(Color.White),
                                expanded = expanded,
                                onDismissRequest = { expanded = false ***REMOVED***) {
                                typeOptions.forEach { newType ->
                                    DropdownMenuItem(text = { Text(newType) ***REMOVED***, onClick = {
                                        type = newType
                                        when (newType) {
                                            "Text" -> {
                                                default = "N/A"
                                            ***REMOVED***

                                            "Number" -> {
                                                default = "-1"
                                            ***REMOVED***

                                            "Date" -> {
                                                default = SimpleDateFormat(
                                                    "yyyy-MM-dd", Locale.getDefault()
                                    ***REMOVED***.format(Date())
                                            ***REMOVED***

                                            "Any" -> {
                                                default = ""
                                            ***REMOVED***

                                            "Float" -> {
                                                default = "-1.0"
                                            ***REMOVED***

                                        ***REMOVED***
                                        viewModel.updateTemplateItem(
                                            template, "default" to (default ?: ""), index
                            ***REMOVED***
                                        viewModel.updateTemplateItem(
                                            template, "type" to newType, index
                            ***REMOVED***
                                        expanded = false

                                    ***REMOVED***)
                                ***REMOVED***
                            ***REMOVED***
                        ***REMOVED***
                        Spacer(modifier = Modifier.width(4.dp)) // Reduced spacing
                        HelpIconButton(helpText = "Specify the type of data expected for this field (e.g., 'string', 'number', 'date').")
                    ***REMOVED***
                    Spacer(modifier = Modifier.height(8.dp))
                    list?.let {
                        BooleanFieldWithLabel(
                            label = "List",
                            value = it,
                            help = "if the value is a list of the type submitted",
                            onValueChange = { newValue ->
                                list = newValue
                                viewModel.updateTemplateItem(
                                    template, "list" to newValue, index
                    ***REMOVED***
                                default=""
                                viewModel.updateTemplateItem(
                                    template, "default" to "", index
                    ***REMOVED***

                                ***REMOVED***,
            ***REMOVED***
                    ***REMOVED***

                    Spacer(modifier = Modifier.height(8.dp))
                    DefaultPicker(
                        default = default,
                        changeDefault = { newDefault ->
                            default = newDefault
                        ***REMOVED***,
                        isList = list ?: true,
                        type = type ?: "Any",
                        viewModel = viewModel,
                        template = template,
                        index = index
        ***REMOVED***

                    Spacer(modifier = Modifier.height(8.dp))


                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(8.dp))

                    // Required & Intelligent Extraction (separate composables with equal weight)

                    required?.let {
                        BooleanFieldWithLabel(
                            label = "Required",
                            value = it,
                            help = "if the field is required",
                            onValueChange = { newValue ->
                                required = newValue
                                viewModel.updateTemplateItem(
                                    template, "required" to newValue, index
                    ***REMOVED***
                            ***REMOVED***,
            ***REMOVED***
                    ***REMOVED***
                    Spacer(modifier = Modifier.height(8.dp))
                    intelligentExtraction?.let {
                        BooleanFieldWithLabel(
                            label = "Interpretative",
                            value = it,
                            help = "the LLM will try to interpret the instruction given and return a more loose response, recommended if you want some processed information from the text instead of extracted information that is in the text",
                            onValueChange = { newValue ->
                                intelligentExtraction = newValue
                                viewModel.updateTemplateItem(
                                    template, "intelligentExtraction" to newValue, index
                    ***REMOVED***
                            ***REMOVED***,
            ***REMOVED***


                    ***REMOVED***
                    Spacer(modifier = Modifier.height(12.dp))
                    DeleteButton(
                        text = "Field",
                        onClick = { viewModel.deleteField(template, index) ***REMOVED***)
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultPicker(
    default: String?,
    changeDefault: (String) -> Unit,
    isList: Boolean = false,
    type: String,
    viewModel: TemplateViewModel,
    template: Template,
    index: Int
) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        when (type) {
            "Text", "Any" -> {
                default?.let {
                    OutlinedTextField(value = it,
                        enabled = !isList,
                        onValueChange = { newText ->
                            changeDefault(newText)
                            viewModel.updateTemplateItem(
                                template, "default" to newText, index
                ***REMOVED***
                        ***REMOVED***,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Black,
                            unfocusedLabelColor = Color.Black,

                ***REMOVED***,
                        label = { Text("Field Default") ***REMOVED***,
                        modifier = Modifier.weight(1f),
                        trailingIcon = {
                            HelpIconButton(helpText = " This is the default value for the field.")
                        ***REMOVED***)
                ***REMOVED***
            ***REMOVED***

            "Number" -> {
                default?.let {

                    OutlinedTextField(value = it,
                        onValueChange = { newValue ->
                            changeDefault(newValue)
                            viewModel.updateTemplateItem(
                                template, "default" to newValue, index
                ***REMOVED***
                        ***REMOVED***,
                        enabled = !isList,
                        label = { Text("Field Default (Number)") ***REMOVED***,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        trailingIcon = {
                            HelpIconButton(helpText = " This is the default value for the field.")
                        ***REMOVED***

        ***REMOVED***

                ***REMOVED***
            ***REMOVED***

            "Date" -> {
                if (!isList) {
                    val datePickerState =
                        rememberDatePickerState(initialSelectedDateMillis = default?.let {
                            try {
                                SimpleDateFormat(
                                    "yyyy-MM-dd", Locale.getDefault()
                    ***REMOVED***.apply {
                                    timeZone = TimeZone.getTimeZone("UTC")
                                ***REMOVED***.parse(it)?.time
                            ***REMOVED*** catch (e: ParseException) {
                                null
                            ***REMOVED***
                        ***REMOVED***) // State for DatePicker
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                            .padding(5.dp)
        ***REMOVED*** {

                        HelpIconButton(
                            helpText = " This is the default value for the field.",
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(6.dp)
            ***REMOVED***
                        DatePicker( // Use DatePicker for "Date" type
                            state = datePickerState
            ***REMOVED***
                    ***REMOVED***

                    // Update the default value when the selected date changes
                    LaunchedEffect(datePickerState.selectedDateMillis) {
                        val selectedDate = datePickerState.selectedDateMillis?.let {
                            // Use UTC time zone for formatting
                            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
                                timeZone = TimeZone.getTimeZone("UTC")
                            ***REMOVED***.format(Date(it))
                        ***REMOVED*** ?: ""
                        changeDefault(selectedDate)
                        viewModel.updateTemplateItem(
                            template, "default" to selectedDate, index
            ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***

            "Boolean" -> {
                default?.let {
                    BooleanFieldWithLabel(
                        label = "Field Default",
                        value = it.toBoolean(),
                        onValueChange = { newValue ->
                            changeDefault(newValue.toString())
                            viewModel.updateTemplateItem(
                                template, "default" to newValue.toString(), index
                ***REMOVED***
                        ***REMOVED***,
                        help = " This is the default value for the field.",
                        enabled = !isList,
                        modifier = Modifier.weight(1f)
        ***REMOVED***
                ***REMOVED***
            ***REMOVED***

            "Float" -> {
                default?.let {
                    OutlinedTextField(value = it,
                        onValueChange = { newValue ->
                            changeDefault(newValue)
                            if(newValue=="") {
                                viewModel.updateTemplateItem(
                                    template, "default" to "", index
                    ***REMOVED***
                            ***REMOVED***
                            newValue.toFloatOrNull()?.let {
                                viewModel.updateTemplateItem(
                                    template, "default" to newValue, index
                    ***REMOVED***
                            ***REMOVED***

                        ***REMOVED***,
                        enabled = !isList,
                        label = { Text("Field Default (Float)") ***REMOVED***,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        trailingIcon = {
                            HelpIconButton(helpText = " This is the default value for the field.")
                        ***REMOVED***

        ***REMOVED***


                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***

@Composable
fun TemplateTableCard(
    tableIndex: Int,
    viewModel: TemplateViewModel,
    template: Template,
    focusRequesterIndex: Int? = null,
    changelistState: () -> Unit
) {
    val table = template.tables[tableIndex]
    val focusRequester = remember { FocusRequester() ***REMOVED***
    var tableTitle by remember { mutableStateOf(table.title) ***REMOVED***
    var tableDescription by remember { mutableStateOf(table.description) ***REMOVED***

    LaunchedEffect(key1 = focusRequesterIndex) {
        if (focusRequesterIndex == tableIndex + template.fields.size - 1) {
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
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = vale
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(value = tableTitle,
                onValueChange = { newText ->
                    viewModel.updateTableItem(template, "title" to newText, tableIndex)
                    tableTitle = newText
                ***REMOVED***,
                label = { Text("Table Title") ***REMOVED***,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
    ***REMOVED***,
                trailingIcon = {
                    HelpIconButton(helpText = "This is the title of the table.")
                ***REMOVED***)

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(value = tableDescription, colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Black,
                unfocusedLabelColor = Color.Black,

    ***REMOVED***, trailingIcon = {
                HelpIconButton(helpText = "This is the description of the table.")
            ***REMOVED***, onValueChange = { newText ->
                viewModel.updateTableItem(template, "description" to newText, tableIndex)
                tableDescription = newText
            ***REMOVED***, label = {
                Text(
                    "Table Description",
                    color = Color.Black,
    ***REMOVED***
            ***REMOVED***, modifier = Modifier.fillMaxWidth()
***REMOVED***

            Spacer(modifier = Modifier.height(8.dp))

            TemplateKeyWordsSection(template, table, viewModel)

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


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp)
    ) {
        // Column Headers
        Row(
            modifier = Modifier
                .padding(1.dp)
                .fillMaxWidth()
                .horizontalScroll(scrollState)
        ) { // Apply horizontal scrolling){ // Make the header row horizontally scrollable) { // Wrap column headers in a Row with weight
            TableCellTemplate(
                modifier = Modifier.weight(1f), invisible = true
***REMOVED*** // Add a blank cell for the button column
            for ((columnIndex, columnField) in table.columns.withIndex()) {
                TableCellTemplate(field = columnField, isHeader = true, onValueChange = { field ->
                    viewModel.updateTableColumnHeader(
                        template, tableIndex, columnIndex, field
        ***REMOVED***
                ***REMOVED***, onDelete = {
                    viewModel.deleteColumnFromTable(table, columnIndex)
                ***REMOVED***, modifier = Modifier.weight(1f)
    ***REMOVED***
            ***REMOVED***
            // Add Column Button (top right, outside the table)

            TableCellTemplate(modifier = Modifier.weight(1f), isButton = true, buttonClick = {
                showDialog = true; isColumn = true
            ***REMOVED***) // Add a blank cell for the button column
        ***REMOVED***

        for ((rowIndex, rowField) in table.rows.withIndex()) {
            Row(modifier = Modifier.padding(1.dp)) {
                TableCellTemplate(field = rowField, isHeader = true, onValueChange = { newText ->
                    viewModel.updateTableRowHeader(template, tableIndex, rowIndex, newText)
                ***REMOVED***, onDelete = {
                    viewModel.deleteRowFromTable(table, rowIndex)
                ***REMOVED***, modifier = Modifier.weight(1f)
    ***REMOVED***
                for (templateField in table.columns) {
                    TableCellTemplate(modifier = Modifier.weight(1f))
                ***REMOVED***
                TableCellTemplate(
                    modifier = Modifier.weight(1f), invisible = true
    ***REMOVED*** // Add a blank cell for the button column
            ***REMOVED***
        ***REMOVED***
        Row(modifier = Modifier.padding(1.dp)) {
            TableCellTemplate(modifier = Modifier.weight(1f), isButton = true, buttonClick = {
                showDialog = true; isColumn = false
            ***REMOVED***) // Add a blank cell for the button column

            for (templateField in table.columns) {
                TableCellTemplate(modifier = Modifier.weight(1f), invisible = true)
            ***REMOVED***
            TableCellTemplate(modifier = Modifier.weight(1f), invisible = true)

        ***REMOVED***
    ***REMOVED***

    if (showDialog) {
        val nMax = if (isColumn) {
            table.columns.size
        ***REMOVED*** else {
            table.rows.size
        ***REMOVED***
        val context = LocalContext.current
        if (nMax > 15) {
            Toast.makeText(context, "Max number of rows and columns is 15", Toast.LENGTH_SHORT)
                .show()
            showDialog = false
        ***REMOVED*** else {
            AlertTable(
                field = null,
                onValueChange = {
                    if (isColumn) {
                        viewModel.addColumnToTable(template, tableIndex, it)
                    ***REMOVED*** else {
                        viewModel.addRowToTable(template, tableIndex, it)
                    ***REMOVED***
                    showDialog = false
                    newText = ""
                ***REMOVED***,
                onDelete = {
                    showDialog = false
                ***REMOVED***,
                changeShowDialog = { showDialog = it ***REMOVED***,
***REMOVED***

        ***REMOVED***
    ***REMOVED***
***REMOVED***

@Composable
fun TableCellTemplate(
    modifier: Modifier = Modifier,
    field: TemplateField? = null,
    isHeader: Boolean = false,
    onValueChange: (TemplateField) -> Unit = {***REMOVED***,
    invisible: Boolean = false,
    isButton: Boolean = false,
    buttonClick: () -> Unit = {***REMOVED***,
    onDelete: () -> Unit = {***REMOVED***
) {

    var modifierPadded = modifier
        .padding(1.dp)
        .defaultMinSize(minWidth = 24.dp, minHeight = 24.dp) // Ensure a minimum width for cells
    var boxSize by remember { mutableStateOf(IntSize.Zero) ***REMOVED***
    val text = field?.title ?: ""

    if (!invisible) {
        modifierPadded = modifierPadded.border(1.dp, Color.Gray)
    ***REMOVED***
    if (isHeader) {
        modifierPadded = modifierPadded.background(Color.LightGray)
    ***REMOVED***
    if (isButton) {//pls round the button
        modifierPadded = modifierPadded
            .background(dark_blue)
            .clip(shape = RoundedCornerShape(4.dp))
    ***REMOVED***
    var showDialog by remember { mutableStateOf(false) ***REMOVED***
    var editedText by remember { mutableStateOf(text) ***REMOVED***
    Box(
        modifier = modifierPadded.onSizeChanged { size ->
            boxSize = size
        ***REMOVED***, contentAlignment = Alignment.Center

    ) {
        if (isHeader) {
            Text(text = text,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1, // Limit to a single line
                modifier = Modifier.clickable {
                    showDialog = true
                    editedText = text // Initialize with current text
                ***REMOVED***)
            if (showDialog) {
                AlertTable(
                    field = field,
                    onValueChange = { onValueChange(it) ***REMOVED***,
                    onDelete = onDelete,
                    changeShowDialog = { showDialog = it ***REMOVED***,
    ***REMOVED***
            ***REMOVED***
        ***REMOVED*** else if (isButton) {
            val iconSize = if (boxSize != IntSize.Zero) {
                LocalDensity.current.run { minOf(boxSize.width, boxSize.height).toDp() / 2 ***REMOVED***
            ***REMOVED*** else {
                16.dp
            ***REMOVED***

            FaIcon(faIcon = FaIcons.Plus,
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
fun AlertTable(
    field: TemplateField?,
    onValueChange: (TemplateField) -> Unit,
    changeShowDialog: (Boolean) -> Unit,
    onDelete: () -> Unit = {***REMOVED***
) {
    val focusRequester = remember { FocusRequester() ***REMOVED***
    var title by remember { mutableStateOf(field?.title ?: "") ***REMOVED***
    var selectedType by remember { mutableStateOf(field?.type ?: "Any") ***REMOVED*** // Default type
    var selectedRequired by remember { mutableStateOf(field?.required ?: false) ***REMOVED***
    AlertDialog(containerColor = Color.White,
        onDismissRequest = { changeShowDialog(false) ***REMOVED***,
        title = { Text("Edit Header") ***REMOVED***,
        text = {
            Column {
                OutlinedTextField(value = title,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Black,
                        unfocusedLabelColor = Color.Black,
        ***REMOVED***,
                    onValueChange = { title = it ***REMOVED***,
                    label = { Text("Enter Header", color = Color.Black) ***REMOVED***)

                Spacer(modifier = Modifier.height(8.dp))

                // Type Dropdown
                var expanded by remember { mutableStateOf(false) ***REMOVED***
                val typeOptions = listOf("Any", "String", "Date", "Number")

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded ***REMOVED***) {
                    TextField(
                        value = selectedType,
                        onValueChange = { selectedType = it ***REMOVED***,
                        readOnly = true,
                        label = { Text("Type") ***REMOVED***,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) ***REMOVED***,
                        colors = ExposedDropdownMenuDefaults.textFieldColors(
                            focusedLabelColor = Color.Black,
                            unfocusedLabelColor = Color.Black,
                            focusedIndicatorColor = Color.Black,
                            unfocusedIndicatorColor = Color.Black,
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
            ***REMOVED***,
                        modifier = Modifier
                            .menuAnchor()
                            .focusRequester(focusRequester)
        ***REMOVED***

                    ExposedDropdownMenu(modifier = Modifier.background(Color.White),
                        expanded = expanded,
                        onDismissRequest = { expanded = false ***REMOVED***) {
                        typeOptions.forEach { type ->
                            DropdownMenuItem(text = { Text(type) ***REMOVED***, onClick = {
                                selectedType = type
                                expanded = false
                            ***REMOVED***)
                        ***REMOVED***
                    ***REMOVED***

                ***REMOVED***
                Spacer(modifier = Modifier.height(8.dp))
                // Required Checkbox
                BooleanFieldWithLabel(
                    label = "Required",
                    value = selectedRequired,
                    onValueChange = { newValue -> selectedRequired = newValue ***REMOVED***,
                    help = "if the field is required"
    ***REMOVED***
            ***REMOVED***
        ***REMOVED***,
        confirmButton = {
            Row {
                TextButton(
                    onClick = {
                        onDelete()
                        changeShowDialog(false)
                    ***REMOVED***, modifier = Modifier.padding(end = 8.dp)
    ***REMOVED*** {
                    Text("Delete", color = dark_red)
                ***REMOVED***
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = { changeShowDialog(false) ***REMOVED***) {
                    Text("Cancel")
                ***REMOVED***
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(onClick = {
                    val newField = TemplateField().apply {
                        this.title = title
                        this.type = selectedType
                        this.required = selectedRequired
                    ***REMOVED***
                    onValueChange(newField) // Pass the edited text
                    // You'll likely want to pass the selectedType to your ViewModel as well
                    changeShowDialog(false)
                ***REMOVED***) {
                    Text("OK")
                ***REMOVED***

            ***REMOVED***

        ***REMOVED***)
***REMOVED***


@Composable
fun AlertTableExtraction(
    text: String,
    onValueChange: (String) -> Unit,
    changeShowDialog: (Boolean) -> Unit,
    onDelete: (() -> Unit)? = null
) {
    var editableText by remember { mutableStateOf(text) ***REMOVED***
    AlertDialog(containerColor = Color.White,
        onDismissRequest = { changeShowDialog(false) ***REMOVED***,
        title = { Text("Edit Value") ***REMOVED***,
        text = {
            Column {
                OutlinedTextField(value = editableText,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Black,
                        unfocusedLabelColor = Color.Black,
        ***REMOVED***,
                    onValueChange = { editableText = it ***REMOVED***,
                    label = { Text("Enter Value", color = Color.Black) ***REMOVED***)
            ***REMOVED***
        ***REMOVED***,
        confirmButton = {
            Row {
                if(onDelete != null) {
                    TextButton(
                        onClick = {
                            onDelete()
                            changeShowDialog(false)
                        ***REMOVED***, modifier = Modifier.padding(end = 8.dp)
        ***REMOVED*** {
                        Text("Delete", color = dark_red)
                    ***REMOVED***
                    Spacer(modifier = Modifier.weight(1f))
                ***REMOVED***
                else{
                    Spacer(modifier = Modifier.weight(1f))
                ***REMOVED***
                TextButton(onClick = { changeShowDialog(false) ***REMOVED***) {
                    Text("Cancel")
                ***REMOVED***
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(onClick = {
                    onValueChange(editableText) // Pass the edited text
                    // You'll likely want to pass the selectedType to your ViewModel as well
                    changeShowDialog(false)
                ***REMOVED***) {
                    Text("OK")
                ***REMOVED***

            ***REMOVED***

        ***REMOVED***)
***REMOVED***


@Composable
fun BooleanFieldWithLabel(
    label: String,
    value: Boolean,
    onValueChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    help: String = "",
    enabled: Boolean = true
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = modifier

    ) {
        Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        if (help.isNotEmpty()) {
            Spacer(modifier = Modifier.width(4.dp))
            HelpIconButton(helpText = help)
        ***REMOVED***
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            enabled = enabled, checked = value, onCheckedChange = onValueChange
        )
    ***REMOVED***
***REMOVED***

// Help Icon Button Composable (with modal tooltip)



