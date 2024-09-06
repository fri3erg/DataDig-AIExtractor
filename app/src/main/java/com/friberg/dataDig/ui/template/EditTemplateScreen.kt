package com.friberg.dataDig.ui.template

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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.friberg.dataDig.R
import com.friberg.dataDig.Screen
import com.friberg.dataDig.models.Template
import com.friberg.dataDig.models.TemplateField
import com.friberg.dataDig.models.TemplateTable
import com.friberg.dataDig.ui.theme.dark_blue
import com.friberg.dataDig.ui.theme.dark_red
import com.friberg.dataDig.ui.theme.light_gray
import com.friberg.dataDig.ui.theme.base_card_color
import com.friberg.dataDig.utils.AddButton
import com.friberg.dataDig.utils.BooleanFieldWithLabel
import com.friberg.dataDig.utils.DeleteButton
import com.friberg.dataDig.utils.HelpIconButton
import com.friberg.dataDig.viewmodels.ServiceViewModel
import com.friberg.dataDig.viewmodels.TemplateViewModel
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * Single template screen
 *
 * @param navController
 * @param templateId The id of the template to be displayed
 * @param templateViewModel
 * @param serviceViewModel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTemplateScreen(
    navController: NavHostController,
    templateId: String,
    templateViewModel: TemplateViewModel,
    serviceViewModel: ServiceViewModel
) {
    if (templateId.isEmpty()) {
        navController.navigateUp()
    ***REMOVED***
    val template by templateViewModel.queryTemplate(templateId).collectAsState(initial = null)
    val focusRequesterIndex by templateViewModel.focusRequesterIndex.collectAsState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var title by remember { mutableStateOf(template?.title) ***REMOVED***
    var description by remember { mutableStateOf(template?.description) ***REMOVED***

    LaunchedEffect(template) {
        if (template != null && title == null) {
            title = template!!.title
            description = template!!.description

        ***REMOVED***
    ***REMOVED***



    Scaffold(topBar = {
        TopAppBar(title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
***REMOVED*** {
                title?.let {
                    Text(
                        text = it,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
        ***REMOVED***
                ***REMOVED***
                template?.let { //Use template button
                    Button(modifier = Modifier.align(Alignment.CenterVertically),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = light_gray),
                        onClick = {
                            template?.id?.let { templateId ->

                                serviceViewModel.clearImageUris()
                                serviceViewModel.setActiveExtraction(false)
                                serviceViewModel.setActivePhoto(true)
                                serviceViewModel.setActiveTemplate(null)
                                serviceViewModel.setProgress(0f)
                                serviceViewModel.clearResult()
                                navController.navigate(Screen.Camera.routeWithOptionalArgs("templateId" to templateId.toHexString()))
                            ***REMOVED***
                        ***REMOVED***) {
                        Text(
                            text = stringResource(id = R.string.use),
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Visible
            ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
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
                                HelpIconButton(helpText = stringResource(R.string.this_is_the_title_of_the_template_this_is_irrelevant_for_the_extraction_just_for_your_own_reference))
                            ***REMOVED***)
                    ***REMOVED***
                ***REMOVED***
                item {
                    description?.let {
                        OutlinedTextField(value = it,
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Black,
                                unfocusedLabelColor = Color.Black,
                ***REMOVED***,
                            onValueChange = { newValue ->
                                templateViewModel.updateTemplateDescription(template!!, newValue)
                                description = newValue
                            ***REMOVED***,
                            label = { Text("Template Description", color = Color.Black) ***REMOVED***,
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = {
                                HelpIconButton(
                                    helpText = stringResource(R.string.this_is_the_description_of_the_template_this_will_be_used_for_the_extraction_but_it_is_highly_optional_and_can_be_left_empty_use_it_if_you_need_to_pass_more_context),
                                    title = stringResource(
                                        R.string.template_description
                        ***REMOVED***
                    ***REMOVED***
                            ***REMOVED***)
                    ***REMOVED***
                ***REMOVED***


                item {
                    Text(
                        stringResource(R.string.template_fields),
                        style = MaterialTheme.typography.titleMedium
        ***REMOVED***
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
                    AddButton(text = stringResource(R.string.field),
                        onClick = { templateViewModel.addField(template!!) ***REMOVED***)
                ***REMOVED***

                item { // Template Tables Section
                    Text(
                        stringResource(R.string.template_tables),
                        style = MaterialTheme.typography.titleMedium
        ***REMOVED***
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
                    AddButton(text = stringResource(R.string.table),
                        onClick = { templateViewModel.addTable(template!!) ***REMOVED***)
                ***REMOVED***
            ***REMOVED*** else {
                item {
                    Text(stringResource(R.string.template_not_found))
                ***REMOVED***

                item {
                    Button(onClick = {
                        navController.navigateUp()
                    ***REMOVED***) {
                        Text(stringResource(R.string.back))
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***

/**
 * Template key words section
 *
 * @param template the template its referring to
 * @param table the table its referring to
 * @param viewModel template view model
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TemplateKeyWordsSection(
    template: Template, table: TemplateTable, viewModel: TemplateViewModel
) {
    val context = LocalContext.current
    Column {
        Row {
            Text(
                stringResource(R.string.table_keywords),
                style = MaterialTheme.typography.titleMedium
***REMOVED***
            Spacer(modifier = Modifier.width(8.dp))
            HelpIconButton(
                helpText = stringResource(R.string.these_are_the_keywords_that_the_llm_will_look_for_in_the_text_to_extract_the_table_preferably_you_should_insert_words_that_will_be_in_this_specific_table_and_not_the_other_ones_as_to_distinguish_between_them),
                title = stringResource(R.string.table_keywords)
***REMOVED***
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
                                contentDescription = stringResource(R.string.remove_keyword),
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
            label = { Text(stringResource(R.string.add_keyword), color = Color.Black) ***REMOVED***,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                FaIcon(faIcon = FaIcons.Plus, tint = Color.Black, modifier = Modifier.clickable {
                    when {
                        newKey.isBlank() -> {
                            Toast.makeText(
                                context,
                                context.getString(R.string.keyword_cannot_be_empty),
                                Toast.LENGTH_SHORT
                ***REMOVED***.show()
                        ***REMOVED***

                        template.tags.size >= 10 -> {
                            Toast.makeText(
                                context,
                                context.getString(R.string.max_number_of_keywords_is_10),
                                Toast.LENGTH_SHORT
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

/**
 * Template field composable
 *
 * @param template The template its referring to
 * @param index The index of the field
 * @param viewModel The template view model
 * @param focusRequesterIndex The index of the field to focus on
 * @param changelistState The function to change the list state and scroll to the next field
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateFieldComposable(
    template: Template?,
    index: Int,
    viewModel: TemplateViewModel,
    focusRequesterIndex: Int? = null,
    changelistState: () -> Unit,

    ) {

    val focusRequester = remember { FocusRequester() ***REMOVED***
    val typeOptions = listOf("Text", "Date", "Number", "Boolean", "Float")
    var expanded by remember { mutableStateOf(false) ***REMOVED***
    val foundField by remember { mutableStateOf(template?.fields?.get(index)) ***REMOVED***
    var title by remember { mutableStateOf(foundField?.title) ***REMOVED***
    var description by remember { mutableStateOf(foundField?.description) ***REMOVED***
    var type by remember { mutableStateOf(foundField?.type) ***REMOVED***
    var required by remember { mutableStateOf(foundField?.required) ***REMOVED***
    var intelligentExtraction by remember { mutableStateOf(foundField?.intelligentExtraction) ***REMOVED***
    var default by remember { mutableStateOf(foundField?.default) ***REMOVED***
    var list by remember { mutableStateOf(foundField?.list) ***REMOVED***


    LaunchedEffect(key1 = focusRequesterIndex) { // Focus on the field when requested
        if (focusRequesterIndex == index) {
            focusRequester.requestFocus()
            changelistState()
            viewModel._focusRequesterIndex.value = null
        ***REMOVED***
    ***REMOVED***

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .focusRequester(focusRequester),
        colors = CardDefaults.cardColors(
            containerColor = base_card_color
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
                                label = { Text(stringResource(R.string.field_title)) ***REMOVED***,
                                modifier = Modifier.weight(1f),
                                trailingIcon = {
                                    HelpIconButton(
                                        helpText = stringResource(R.string.this_is_the_title_of_the_field_the_most_important_part_of_the_field_this_will_be_used_to_extract_the_data_of_the_corresponding_field_this_field_cannot_be_left_empty),
                                        title = stringResource(R.string.field_title)
                        ***REMOVED***
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
                                label = { Text(stringResource(R.string.field_description)) ***REMOVED***,
                                modifier = Modifier.weight(1f),
                                trailingIcon = {
                                    HelpIconButton(
                                        helpText = stringResource(R.string.this_is_the_description_of_the_field_this_will_also_be_used_to_extract_the_data_but_you_dont_need_to_be_super_specific_this_field_can_be_left_empty_if_the_title_is_descriptive_enough),
                                        title = stringResource(
                                            R.string.field_description
                            ***REMOVED***
                        ***REMOVED***
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
                                    label = { Text(stringResource(id = R.string.type)) ***REMOVED***,
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
                                        unfocusedContainerColor = base_card_color,
                                        focusedContainerColor = base_card_color
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
                        Spacer(modifier = Modifier.width(4.dp))
                        HelpIconButton(
                            helpText = stringResource(R.string.specify_the_type_of_data_expected_for_this_field_e_g_text_number_date_this_will_affect_the_type_of_the_extracted_data_the_default_value_must_be_set_accordingly),
                            title = stringResource(
                                id = R.string.type
                ***REMOVED***
            ***REMOVED***
                    ***REMOVED***
                    Spacer(modifier = Modifier.height(8.dp))
                    list?.let {
                        BooleanFieldWithLabel(
                            label = stringResource(R.string.list),
                            value = it,
                            help = stringResource(R.string.if_the_value_is_a_list_of_the_type_submitted),
                            onValueChange = { newValue ->
                                list = newValue
                                viewModel.updateTemplateItem(
                                    template, "list" to newValue, index
                    ***REMOVED***
                                default = "[]"
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
                        type = type ?: "Text",
                        viewModel = viewModel,
                        template = template,
                        index = index
        ***REMOVED***

                    Spacer(modifier = Modifier.height(8.dp))


                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(8.dp))

                    // Required & Intelligent Extraction

                    required?.let {
                        BooleanFieldWithLabel(
                            label = stringResource(id = R.string.required),
                            value = it,
                            help = stringResource(R.string.if_the_field_is_required_setting_this_to_false_could_mean_the_field_will_not_be_present_in_the_extraction_if_true_it_will_always_be_present_but_please_select_a_default_value),
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
                            label = stringResource(R.string.interpretative),
                            value = it,
                            help = stringResource(R.string.the_llm_will_try_to_interpret_the_instruction_given_and_return_a_more_loose_response_recommended_if_you_want_some_processed_information_from_the_text_instead_of_extracted_information_that_is_in_the_text),
                            onValueChange = { newValue ->
                                intelligentExtraction = newValue
                                viewModel.updateTemplateItem(
                                    template, "intelligentExtraction" to newValue, index
                    ***REMOVED***
                            ***REMOVED***,
            ***REMOVED***


                    ***REMOVED***
                    Spacer(modifier = Modifier.height(12.dp))
                    DeleteButton(text = stringResource(R.string.field),
                        onClick = { viewModel.deleteField(template, index) ***REMOVED***)
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***

/**
 * picker for the default value of the field
 *
 * @param default The default value
 * @param changeDefault The function to change the default value
 * @param isList Whether the field is a list
 * @param type The type of the field
 * @param viewModel The template view model
 * @param template The template its referring to
 * @param index The index of the field
 */
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
            "Text" -> { //normal text field
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
                        label = { Text(stringResource(R.string.field_default)) ***REMOVED***,
                        modifier = Modifier.weight(1f),
                        trailingIcon = {
                            HelpIconButton(
                                helpText = stringResource(R.string.this_is_the_default_value_for_the_field_if_the_extraction_does_not_find_anything_or_something_goes_wrong_this_will_be_the_value_returned_default_values_are_not_available_for_lists),
                                title = stringResource(
                                    R.string.field_default
                    ***REMOVED***
                ***REMOVED***
                        ***REMOVED***)
                ***REMOVED***
            ***REMOVED***

            "Number" -> { //number keyboard
                default?.let {

                    OutlinedTextField(value = it,
                        onValueChange = { newValue ->
                            changeDefault(newValue)
                            viewModel.updateTemplateItem(
                                template, "default" to newValue, index
                ***REMOVED***
                        ***REMOVED***,
                        enabled = !isList,
                        label = { Text(stringResource(R.string.field_default_number)) ***REMOVED***,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        trailingIcon = {
                            HelpIconButton(
                                helpText = stringResource(R.string.this_is_the_default_value_for_the_field_if_the_extraction_does_not_find_anything_or_something_goes_wrong_this_will_be_the_value_returned_default_values_are_not_available_for_lists),
                                title = stringResource(
                                    R.string.field_default
                    ***REMOVED***
                ***REMOVED***
                        ***REMOVED***

        ***REMOVED***

                ***REMOVED***
            ***REMOVED***

            "Date" -> { //date picker
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
                        ***REMOVED***)
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                            .padding(5.dp)
        ***REMOVED*** {

                        HelpIconButton(
                            helpText = stringResource(R.string.this_is_the_default_value_for_the_field_if_the_extraction_does_not_find_anything_or_something_goes_wrong_this_will_be_the_value_returned_default_values_are_not_available_for_lists),
                            title = stringResource(
                                R.string.field_default
                ***REMOVED***,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(6.dp)
            ***REMOVED***
                        DatePicker(
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

            "Boolean" -> { //boolean field checkbox
                default?.let {
                    BooleanFieldWithLabel(
                        label = stringResource(R.string.field_default_boolean),
                        value = it.toBoolean(),
                        onValueChange = { newValue ->
                            changeDefault(newValue.toString())
                            viewModel.updateTemplateItem(
                                template, "default" to newValue.toString(), index
                ***REMOVED***
                        ***REMOVED***,
                        help = stringResource(R.string.this_is_the_default_value_for_the_field),
                        enabled = !isList,
                        modifier = Modifier.weight(1f)
        ***REMOVED***
                ***REMOVED***
            ***REMOVED***

            "Float" -> { //float keyboard
                default?.let {
                    OutlinedTextField(value = it,
                        onValueChange = { newValue ->
                            changeDefault(newValue)
                            if (newValue == "") {
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
                        label = { Text(stringResource(R.string.field_default_float)) ***REMOVED***,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        trailingIcon = {
                            HelpIconButton(helpText = stringResource(R.string.this_is_the_default_value_for_the_field))
                        ***REMOVED***

        ***REMOVED***


                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***

/**
 * Template table display, displays an editable table with columns and rows
 *
 * @param tableIndex The index of the table
 * @param viewModel The template view model
 * @param template The template its referring to
 * @param focusRequesterIndex The index of the field to focus on
 * @param changelistState The function to change the list state and scroll to the next field
 */
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
    var all by remember { mutableStateOf(table.all) ***REMOVED***
    LaunchedEffect(key1 = focusRequesterIndex) {
        if (focusRequesterIndex == tableIndex + template.fields.size - 1) {
            focusRequester.requestFocus()
            changelistState()
            viewModel._focusRequesterIndex.value = -1
        ***REMOVED***
    ***REMOVED***
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .focusRequester(focusRequester),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = base_card_color
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(value = tableTitle,
                onValueChange = { newText ->
                    viewModel.updateTableItem(template, "title" to newText, tableIndex)
                    tableTitle = newText
                ***REMOVED***,
                label = { Text(stringResource(R.string.table_title)) ***REMOVED***,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
    ***REMOVED***,
                trailingIcon = {
                    HelpIconButton(
                        helpText = stringResource(R.string.this_is_the_title_of_the_table_totally_irrelevant_for_the_extraction_just_for_your_own_reference_will_be_invented_by_the_llm_if_left_empty),
                        title = stringResource(
                            R.string.table_title
            ***REMOVED***
        ***REMOVED***
                ***REMOVED***)

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(value = tableDescription, colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Black,
                unfocusedLabelColor = Color.Black,

    ***REMOVED***, trailingIcon = {
                HelpIconButton(
                    helpText = stringResource(R.string.this_is_the_description_of_the_table_irrelevant_for_the_extraction_just_for_your_own_reference),
                    title = stringResource(
                        R.string.table_description
        ***REMOVED***
    ***REMOVED***
            ***REMOVED***, onValueChange = { newText ->
                viewModel.updateTableItem(template, "description" to newText, tableIndex)
                tableDescription = newText
            ***REMOVED***, label = {
                Text(
                    stringResource(R.string.table_description),
                    color = Color.Black,
    ***REMOVED***
            ***REMOVED***, modifier = Modifier.fillMaxWidth()
***REMOVED***

            Spacer(modifier = Modifier.height(8.dp))

            TemplateKeyWordsSection(template, table, viewModel)

            Spacer(modifier = Modifier.height(16.dp))


            BooleanFieldWithLabel(
                label = stringResource(R.string.everything_you_find),
                value = all,
                onValueChange = {
                    all = it
                    viewModel.updateTableItem(template, "all" to it, tableIndex)
                ***REMOVED***,
                help = stringResource(R.string.if_you_want_to_extract_all_the_information_we_can_find_this_does_not_return_column_and_rows_name_and_can_be_inaccurate),
                title = stringResource(
                    R.string.extract_everything
    ***REMOVED***
***REMOVED***


            Spacer(modifier = Modifier.height(16.dp))

            // Table Grid
            if (!all) {
                TableGrid(viewModel, tableIndex, template)
            ***REMOVED***

            Spacer(modifier = Modifier.height(16.dp))

            // Delete Table Button
            DeleteButton(text = stringResource(R.string.table),
                onClick = { viewModel.deleteTable(template, tableIndex) ***REMOVED***)
        ***REMOVED***
    ***REMOVED***
***REMOVED***

/**
 * Table grid
 *
 * @param viewModel the template view model
 * @param tableIndex the index of the table
 * @param template the template its referring to
 */
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
        ) {  //first row
            TableCellTemplate(
                modifier = Modifier.weight(1f), invisible = true, first = true
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
        Row(modifier = Modifier.padding(1.dp)) { //last row
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
            Toast.makeText(
                context,
                stringResource(R.string.max_number_of_rows_and_columns_is_15),
                Toast.LENGTH_SHORT
***REMOVED***.show()
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

/**
 * Table cell template
 *
 * @param modifier The modifier for the cell
 * @param field The field its referring to
 * @param isHeader Whether the cell is a header cell
 * @param onValueChange The function to change the value of the field
 * @param invisible Whether the cell is invisible
 * @param isButton Whether the cell is a button
 * @param first Whether the cell is the first cell
 * @param buttonClick The function to call when the button is clicked
 * @param onDelete The function to call when the delete button is clicked
 */
@Composable
fun TableCellTemplate(
    modifier: Modifier = Modifier,
    field: TemplateField? = null,
    isHeader: Boolean = false,
    onValueChange: (TemplateField) -> Unit = {***REMOVED***,
    invisible: Boolean = false,
    isButton: Boolean = false,
    first: Boolean = false,
    buttonClick: () -> Unit = {***REMOVED***,
    onDelete: () -> Unit = {***REMOVED***
) {

    var modifierPadded = modifier
        .padding(1.dp)
        .defaultMinSize(minWidth = 24.dp, minHeight = 24.dp)
    var boxSize by remember { mutableStateOf(IntSize.Zero) ***REMOVED***
    val text = field?.title ?: ""

    if (!invisible) {
        modifierPadded = modifierPadded.border(1.dp, Color.Gray)
    ***REMOVED***
    if (isHeader) {
        modifierPadded = modifierPadded.background(Color.LightGray)
    ***REMOVED***
    if (isButton) {
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
        if (isHeader) { //header
            Text(text = text,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.clickable {
                    showDialog = true
                    editedText = text
                ***REMOVED***)
            if (showDialog) {
                AlertTable(
                    field = field,
                    onValueChange = { onValueChange(it) ***REMOVED***,
                    onDelete = onDelete,
                    changeShowDialog = { showDialog = it ***REMOVED***,
    ***REMOVED***
            ***REMOVED***
        ***REMOVED*** else if (isButton) { //button +
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

        ***REMOVED*** else { //invisible or normal cell
            if (first) { //first cell
                HelpIconButton(
                    helpText = stringResource(R.string.you_can_cut_and_add_column_and_rows_at_will_only_the_specified_columns_and_rows_will_be_extracted_if_the_table_is_bi_dimensional_or_you_can_choose_to_leave_one_of_the_two_empty_in_that_case_everything_found_in_the_rows_column_will_be_extracted_leaving_both_empty_or_toggling_the_find_everything_button_will_return_everything_found),
                    title = stringResource(
                        R.string.table_usage
        ***REMOVED***,
                    modifier = Modifier.align(Alignment.Center)
    ***REMOVED***
            ***REMOVED***
            Text(
                text = text,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1 // Limit to a single line

***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***

/**
 * modal dialog for editing a field
 *
 * @param field The field its referring to
 * @param onValueChange The function to change the value of the field
 * @param changeShowDialog The function to change the visibility of the dialog
 * @param onDelete The function to call when the delete button is clicked
 */
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
    var selectedType by remember { mutableStateOf(field?.type ?: "Text") ***REMOVED*** // Default type
    var selectedRequired by remember { mutableStateOf(field?.required ?: true) ***REMOVED***
    AlertDialog(containerColor = Color.White,
        onDismissRequest = { changeShowDialog(false) ***REMOVED***,
        title = { Text(stringResource(R.string.edit_header)) ***REMOVED***,
        text = {
            Column {
                OutlinedTextField(value = title,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Black,
                        unfocusedLabelColor = Color.Black,
        ***REMOVED***,
                    onValueChange = { title = it ***REMOVED***,
                    label = { Text(stringResource(R.string.enter_header), color = Color.Black) ***REMOVED***)

                Spacer(modifier = Modifier.height(8.dp))

                // Type Dropdown
                var expanded by remember { mutableStateOf(false) ***REMOVED***
                val typeOptions = listOf("Text", "Date", "Number", "Boolean", "Float")

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded ***REMOVED***) {
                    TextField(
                        value = selectedType,
                        onValueChange = { selectedType = it ***REMOVED***,
                        readOnly = true,
                        label = { Text(stringResource(R.string.type)) ***REMOVED***,
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
                    label = stringResource(R.string.required),
                    value = selectedRequired,
                    onValueChange = { newValue -> selectedRequired = newValue ***REMOVED***,
                    help = stringResource(R.string.if_the_field_is_required_setting_this_to_false_could_mean_the_field_will_not_be_present_in_the_extraction_if_true_it_will_always_be_present_but_please_select_a_default_value),
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
                    Text(stringResource(id = R.string.delete), color = dark_red)
                ***REMOVED***
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = { changeShowDialog(false) ***REMOVED***) {
                    Text(stringResource(id = R.string.cancel))
                ***REMOVED***
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(onClick = {
                    val newField = TemplateField().apply {
                        this.title = title
                        this.type = selectedType
                        this.required = selectedRequired
                    ***REMOVED***
                    onValueChange(newField)
                    changeShowDialog(false)
                ***REMOVED***) {
                    Text(stringResource(id = R.string.ok))
                ***REMOVED***

            ***REMOVED***

        ***REMOVED***)
***REMOVED***