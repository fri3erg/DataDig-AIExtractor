package com.example.tesifrigo.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.example.tesifrigo.R
import com.example.tesifrigo.models.Extraction
import com.example.tesifrigo.ui.settings.ClickableWebLink
import com.example.tesifrigo.ui.template.AlertTableExtraction
import com.example.tesifrigo.ui.theme.cyan_custom
import com.example.tesifrigo.ui.theme.dark_blue
import com.example.tesifrigo.ui.theme.dark_red
import com.example.tesifrigo.ui.theme.light_gray
import com.example.tesifrigo.ui.theme.vale
import com.example.tesifrigo.viewmodels.ExtractionViewModel
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


@Composable
fun DeleteButton(
    text: String, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    var showAlert by remember { mutableStateOf(false) ***REMOVED***
    Button(
        onClick = { showAlert = true ***REMOVED***,
        colors = ButtonDefaults.buttonColors(
            containerColor = light_gray,
        ),
        border = BorderStroke(2.dp, dark_red),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            "Delete $text",
            maxLines = 1,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Normal,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            color = dark_red
        )
    ***REMOVED***
    if (showAlert) AlertDialog(onDismissRequest = { showAlert = false ***REMOVED***,
        title = { Text(stringResource(R.string.delete_text, text)) ***REMOVED***,
        text = { Text(
            stringResource(
                R.string.are_you_sure_you_want_to_delete_this,
                text.lowercase()
***REMOVED***) ***REMOVED***,
        confirmButton = {
            TextButton(onClick = {
                onClick()
                showAlert = false
            ***REMOVED***) {
                Text(stringResource(R.string.ok))
            ***REMOVED***
        ***REMOVED***,
        dismissButton = {
            TextButton(onClick = { showAlert = false ***REMOVED***) {
                Text(stringResource(R.string.cancel))
            ***REMOVED***
        ***REMOVED***)
***REMOVED***

@Composable
fun ExtractionTableCell(
    modifier: Modifier = Modifier,
    text: String = "",
    isHeader: Boolean = false,
    onValueChange: (String) -> Unit = {***REMOVED***,
    invisible: Boolean = false,
    isButton: Boolean = false,
    buttonClick: (String) -> Unit = {***REMOVED***,
    onDelete: (() -> Unit)? = null
) {
    var showDialog by remember { mutableStateOf(false) ***REMOVED***
    var modifierPadded = modifier
        .padding(1.dp)
        .defaultMinSize(minWidth = 24.dp, minHeight = 24.dp) // Ensure a minimum width for cells
    var boxSize by remember { mutableStateOf(IntSize.Zero) ***REMOVED***
    val editedText by remember { mutableStateOf(text) ***REMOVED***

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
    var showX by remember { mutableStateOf(false) ***REMOVED***
    Box(
        modifier = modifierPadded.onSizeChanged { size ->
            boxSize = size
        ***REMOVED***, contentAlignment = Alignment.Center

    ) {
        if (isHeader) {
            if (showX) {
                FaIcon(faIcon = FaIcons.Times,
                    tint = dark_red,
                    size = 20.dp,
                    modifier = Modifier
                        .clickable {
                            if (onDelete != null) {
                                onDelete()
                            ***REMOVED***
                            showX = false
                        ***REMOVED***
                        .fillMaxWidth()
                        .align(Alignment.Center))

            ***REMOVED*** else {
                Text(text = text,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1, // Limit to a single line
                    modifier = Modifier
                        .clickable {
                            showX = true
                        ***REMOVED***
                        .fillMaxWidth())
            ***REMOVED***

        ***REMOVED*** else if (isButton) {
                FaIcon(faIcon = FaIcons.Plus,
                    tint = Color.White,
                    size = 20.dp,
                    modifier = Modifier
                        .clickable {
                            showDialog = true
                        ***REMOVED***
                        .align(Alignment.Center)
    ***REMOVED***
        ***REMOVED***
        else{
            Text(text = text,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1, // Limit to a single line
                modifier = Modifier
                    .clickable {
                        showDialog = true
                    ***REMOVED***
                    .fillMaxWidth())
        ***REMOVED***
    ***REMOVED***
    if(showDialog){
        AlertTableExtraction(
            text = editedText, onValueChange =if(isButton) buttonClick else onValueChange, changeShowDialog = {
        showDialog = false
        ***REMOVED***,
            onDelete =onDelete)
    ***REMOVED***
***REMOVED***


@Composable
fun SearchBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(value = text,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = cyan_custom,
            unfocusedBorderColor = Color.Black,
            focusedLeadingIconColor = cyan_custom,
        ),
        onValueChange = {
            onTextChange(it) // Update the ViewModel's searchText state
            onSearch(it) // Trigger the search function (optional)
        ***REMOVED***,
        label = { Text(stringResource(R.string.search)) ***REMOVED***,
        modifier = modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        singleLine = true, // Ensure single-line input
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search, contentDescription = stringResource(R.string.search_icon)
***REMOVED***
        ***REMOVED***)
***REMOVED***


@Composable
fun HelpIconButton(helpText: String, modifier: Modifier = Modifier, title: String = "Help", link:String="") {
    var showDialog by remember { mutableStateOf(false) ***REMOVED***

    IconButton(
        onClick = { showDialog = true ***REMOVED***, modifier = modifier.size(20.dp), // Make the icon smaller
        colors = IconButtonDefaults.filledIconButtonColors(
            contentColor = Color.Black, containerColor = Color.Black
        )
    ) {

        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = stringResource(R.string.help_icon),
            tint = Color.White,
            //modifier = Modifier.border(1.dp, Color.Black, RoundedCornerShape(10.dp))
        ) // Use the Info icon from Material Design

    ***REMOVED***

    if (showDialog) {
        AlertDialog(onDismissRequest = { showDialog = false ***REMOVED***,
            title = { Text(title) ***REMOVED***,
            text = { Column {
                Text(helpText)
                if(link.isNotEmpty()){
                    ClickableWebLink(text = link, url = link)
                ***REMOVED***
            ***REMOVED***
                   ***REMOVED***,
            confirmButton = {
                TextButton(onClick = { showDialog = false ***REMOVED***) {
                    Text("OK")
                ***REMOVED***
            ***REMOVED***)
    ***REMOVED***
***REMOVED***

@Composable
fun DropdownWithNavigation(onUse: () -> Unit, onEdit: () -> Unit, onDelete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) ***REMOVED***
    var showDeleteDialog by remember { mutableStateOf(false) ***REMOVED***

    Box { // Use a Box for positioning
        // Icon to trigger dropdown
        IconButton(
            onClick = { expanded = true ***REMOVED***,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(bottom = 6.dp)
        ) {
            FaIcon(faIcon = FaIcons.EllipsisV, tint = Color.Black)
        ***REMOVED***

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false ***REMOVED***,
            modifier = Modifier
                .width(150.dp) // Adjust width as needed
                .align(Alignment.TopEnd)
                .background(Color.White)
        ) {
            DropdownMenuItem(text = { Text(stringResource(R.string.use)) ***REMOVED***, onClick = {
                onUse()
                expanded = false
            ***REMOVED***)
            DropdownMenuItem(text = { Text(stringResource(R.string.edit)) ***REMOVED***, onClick = {
                onEdit()
                expanded = false
            ***REMOVED***)
            DropdownMenuItem(text = { Text(stringResource(R.string.delete)) ***REMOVED***, onClick = {
                showDeleteDialog = true
                expanded = false
            ***REMOVED***)
        ***REMOVED***
    ***REMOVED***

    // Confirmation Dialog
    if (showDeleteDialog) {
        AlertDialog(onDismissRequest = { showDeleteDialog = false ***REMOVED***,
            title = { Text(stringResource(R.string.confirm_delete)) ***REMOVED***,
            text = { Text(stringResource(R.string.are_you_sure_you_want_to_delete_this_template)) ***REMOVED***,
            confirmButton = {
                Button(onClick = {
                    onDelete()
                    showDeleteDialog = false
                ***REMOVED***) {
                    Text(stringResource(R.string.delete))
                ***REMOVED***
            ***REMOVED***,
            dismissButton = {
                Button(onClick = { showDeleteDialog = false ***REMOVED***) {
                    Text(stringResource(R.string.cancel))
                ***REMOVED***
            ***REMOVED***)
    ***REMOVED***
***REMOVED***


@Composable
fun LabeledSwitch(
    label: String,

    checked: Boolean, onCheckedChange: (Boolean) -> Unit, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors( // Customize colors if needed
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                checkedTrackColor = MaterialTheme.colorScheme.primaryContainer
***REMOVED***
        )
    ***REMOVED***
***REMOVED***

@Composable
fun DropDownGeneral(
    items: List<String>,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    defaultSelectedItemIndex: Int? = null
) {
    var expanded by remember { mutableStateOf(false) ***REMOVED***
    var selectedItem by remember { mutableStateOf(items[defaultSelectedItemIndex ?: 0]) ***REMOVED***

    Box(modifier = modifier) {
        TextButton(
            onClick = { expanded = true ***REMOVED***, modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                Text(selectedItem,
                    modifier=Modifier.padding(start=6.dp),
                    fontSize = 13.sp,
                    color = Color.Black

    ***REMOVED***
                Spacer(modifier = Modifier.weight(1f))
                FaIcon(faIcon = if (expanded) FaIcons.ChevronUp else FaIcons.ChevronDown,
                    tint = cyan_custom,
                    size = 16.dp,
                    modifier=Modifier.offset(y=2.dp)
    ***REMOVED***

            ***REMOVED***
        ***REMOVED***

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false ***REMOVED***,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            items.forEach { item ->
                DropdownMenuItem(text = { Text(item) ***REMOVED***, onClick = {
                    selectedItem = item
                    onItemSelected(item)
                    expanded = false
                ***REMOVED***)
                HorizontalDivider()
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***

@Composable
fun MyImageArea(
    imageUris: List<Uri>, modifier: Modifier = Modifier, onDelete: ((Int) -> Unit)? = null
) {
    val contentResolver = LocalContext.current.contentResolver
    val pagerState = rememberPagerState(pageCount = { imageUris.size ***REMOVED***)
    val showDialog = remember { mutableStateOf(false) ***REMOVED***
    var deletedPage by remember { mutableIntStateOf(0) ***REMOVED***
    val context = LocalContext.current
    var showImage by remember { mutableStateOf(false) ***REMOVED***
    var selectedImageUri by remember { mutableStateOf(Uri.EMPTY) ***REMOVED***
    val openFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {***REMOVED***
    fun openFile(uri: Uri) {
        uri.path?.let {

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, context.contentResolver.getType(uri))
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            ***REMOVED***

            val packageManager = context.packageManager
            val activities =
                packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)

            if (activities.isNotEmpty()) {
                openFileLauncher.launch(intent)
            ***REMOVED*** else {
                Toast.makeText(context,
                    context.getString(R.string.no_app_found_to_open_the_file), Toast.LENGTH_SHORT).show()
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
    Box(modifier = modifier
        .fillMaxWidth()
        .padding(top = 8.dp, bottom = 8.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(vale)) {
        Column {
            HorizontalPager(
                state = pagerState, modifier = Modifier
                    .fillMaxWidth()
***REMOVED*** { page ->

                val uri = imageUris[page]
                val mimeType = remember { contentResolver.getType(uri) ***REMOVED***
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent),
    ***REMOVED*** {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clickable {
                                if (mimeType == "application/pdf") {
                                    openFile(uri)
                                ***REMOVED*** else {
                                    selectedImageUri = uri // Set the selected image URI
                                    showImage = true
                                ***REMOVED***
                            ***REMOVED***,

                        shape = RoundedCornerShape(32.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = vale, contentColor = Color.Black
            ***REMOVED***
        ***REMOVED*** {


                        if (mimeType == "application/pdf") {
                            // PDF Thumbnail with Filename
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.Gray),
                                contentAlignment = Alignment.Center
                ***REMOVED*** {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    FaIcon(faIcon = FaIcons.FilePdf, tint = Color.White)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = getPdfFileName(context, uri) ?: "Pdf File",
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                        ***REMOVED***
                                ***REMOVED***
                            ***REMOVED***
                        ***REMOVED*** else {
                            // Image Preview (cropped or reduced)
                            AsyncImage(
                                model = uri,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp) // Reduce height to scale down image display
                                    .padding(16.dp)
                                    .clip(RoundedCornerShape(16.dp)),
                                contentScale = ContentScale.Crop, // Crop the image to fit
                                placeholder = painterResource(id = R.drawable.placeholder),
                                error = painterResource(id = R.drawable.error404) // Replace with your error image resource

                ***REMOVED***
                        ***REMOVED***


                    ***REMOVED***
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                top = 8.dp
                ***REMOVED***  // Padding applied first
                            .shadow(20.dp)                        // Then the shadow, outside the padding
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = 16.dp,
                                    bottomEnd = 16.dp
                    ***REMOVED***
                ***REMOVED*** // Clipping next
                            .background(Color.White),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
        ***REMOVED*** {

                        Text(
                            text = if (mimeType == "application/pdf") getPdfFileName(context, uri)
                                ?: "Pdf File" else "Image " + (imageUris.indexOf(uri) + 1).toString(),
                            color = Color.Black,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 24.dp)
            ***REMOVED***
                        if (onDelete != null) {
                            IconButton(
                                onClick = {
                                    showDialog.value = true
                                    deletedPage = page
                                ***REMOVED***
                ***REMOVED*** {
                                FaIcon(faIcon = FaIcons.Trash, tint = Color.Black)
                            ***REMOVED***
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
                // Delete button
            ***REMOVED***

            // Custom Indicator
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
***REMOVED*** {
                repeat(pagerState.pageCount) { page ->
                    Box(
                        modifier = Modifier
                            .size(if (pagerState.currentPage == page) 10.dp else 6.dp)
                            .clip(CircleShape)
                            .background(if (pagerState.currentPage == page) Color.Black else Color.LightGray)
        ***REMOVED***
                ***REMOVED***
                Spacer(modifier = Modifier.height(8.dp))
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
    // Confirmation dialog for deletion
    if (showDialog.value && onDelete != null) {
        AlertDialog(onDismissRequest = { showDialog.value = false ***REMOVED***,
            title = { Text(stringResource(R.string.confirm_delete)) ***REMOVED***,
            text = { Text(stringResource(R.string.are_you_sure_you_want_to_delete_this_image)) ***REMOVED***,
            confirmButton = {
                Button(onClick = {
                    onDelete(deletedPage)
                    showDialog.value = false
                ***REMOVED***) {
                    Text(stringResource(R.string.delete))
                ***REMOVED***
            ***REMOVED***,
            dismissButton = {
                Button(onClick = { showDialog.value = false ***REMOVED***) {
                    Text(stringResource(R.string.cancel))
                ***REMOVED***
            ***REMOVED***)
    ***REMOVED***
    if (showImage && selectedImageUri != null) {
        Dialog(onDismissRequest = { showImage = false ***REMOVED***) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
***REMOVED*** {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Fit // Adjust contentScale as needed
    ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***


@Composable
fun FileCard(
    extraction: Extraction, modifier: Modifier = Modifier, extractionViewModel: ExtractionViewModel
) {

    val context = LocalContext.current
    val openFileLauncher = remember {
        (context as? ComponentActivity)?.activityResultRegistry?.register(
            "openFile", ActivityResultContracts.StartActivityForResult()
        ) { result ->
            Log.d("FileCard", "Result: $result")
        ***REMOVED***
    ***REMOVED***

    fun downloadFile(uri: Uri?, failedOpen: Boolean = false) {
        if (uri == null || uri.path == null) return
        val contentUri = FileProvider.getUriForFile(
            context, "com.example.tesifrigo.fileprovider", File(uri.path!!)
        )

        // Get the filename from the content URI
        val fileName = contentUri.lastPathSegment ?: "downloaded_file"

        // Create a destination file in the Downloads directory
        val destinationFile = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName
        )

        try {
            // Open input and output streams
            val inputStream = context.contentResolver.openInputStream(contentUri)
            val outputStream = FileOutputStream(destinationFile)

            // Copy the file
            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                ***REMOVED***
            ***REMOVED***

            // Show a Toast message indicating success
            if (!failedOpen) {
                Toast.makeText(
                    context,
                    context.getString(R.string.file_downloaded_to_downloads_folder), Toast.LENGTH_SHORT
    ***REMOVED***.show()
            ***REMOVED***
        ***REMOVED*** catch (e: IOException) {
            // Handle exceptions (e.g., log the error, show an error message to the user)
            if ((e is FileNotFoundException) && (e.message?.contains("EEXIST") == true)) {

                Toast.makeText(context,
                    context.getString(R.string.file_already_downloaded), Toast.LENGTH_SHORT).show()

            ***REMOVED*** else {
                // Handle other IOException cases
                e.printStackTrace()
                Toast.makeText(context,
                    context.getString(R.string.error_downloading_file), Toast.LENGTH_SHORT).show()
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    fun openFile(uri: Uri) {
        if (uri.path == "null") {
            Toast.makeText(context, context.getString(R.string.file_not_found), Toast.LENGTH_SHORT).show()
            return
        ***REMOVED***
        val contentUri = FileProvider.getUriForFile(
            context, "com.example.tesifrigo.fileprovider", File(uri.path!!)
        )

        val mimeType = context.contentResolver.getType(contentUri)

        // First, try to open the file using ACTION_VIEW
        val viewIntent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(contentUri, mimeType)
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        ***REMOVED***

        val packageManager = context.packageManager
        val viewActivities =
            packageManager.queryIntentActivities(viewIntent, PackageManager.MATCH_DEFAULT_ONLY)

        // Filter activities based on MIME type
        val filteredActivities = viewActivities.filter {
            it.activityInfo.exported && it.filter?.hasDataType(mimeType) == true
        ***REMOVED***

        if (filteredActivities.isNotEmpty()) {
            // There's a default app that can handle the intent, so launch it
            openFileLauncher?.launch(viewIntent)
        ***REMOVED*** else {
            // No suitable app found, so download the file
            Toast.makeText(
                context,
                context.getString(R.string.no_app_found_to_open_the_file_downloading_instead), Toast.LENGTH_SHORT
***REMOVED***.show()
            downloadFile(uri, true)
        ***REMOVED***

    ***REMOVED***

    fun downloadFileExtra(uri: Uri) {
        if (uri.path == null) return
        val contentUri = FileProvider.getUriForFile(
            context, "com.example.tesifrigo.fileprovider", File(uri.path!!)
        )

        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply { // Use ACTION_CREATE_DOCUMENT
            type = context.contentResolver.getType(contentUri)
            putExtra(Intent.EXTRA_STREAM, contentUri)
            // Suggest a filename for the downloaded file (optional)
            val fileName = contentUri.lastPathSegment ?: "downloaded_file"
            putExtra(Intent.EXTRA_TITLE, fileName)
            addCategory(Intent.CATEGORY_OPENABLE)
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        ***REMOVED***

        val packageManager = context.packageManager
        val activities =
            packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)

        if (activities.isNotEmpty()) {
            openFileLauncher?.launch(intent)
        ***REMOVED*** else {
            // If no app can handle ACTION_CREATE_DOCUMENT, fall back to manual download
            downloadFile(uri)
        ***REMOVED***
    ***REMOVED***

    fun shareFile(uri: Uri?) {
        if (uri == null || uri.path == null) return
        val contentUri = FileProvider.getUriForFile(
            context, "com.example.tesifrigo.fileprovider", File(uri.path!!)
        )

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = context.contentResolver.getType(contentUri)
            putExtra(Intent.EXTRA_STREAM, contentUri)
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        ***REMOVED***

        context.startActivity(Intent.createChooser(shareIntent, "Share File"))
    ***REMOVED***

    Surface(
        modifier = modifier.padding(8.dp), shape = RoundedCornerShape(8.dp), color = light_gray, shadowElevation = 6.dp, border = BorderStroke(1.dp, Color.Gray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable(onClick = {
                    extractionViewModel.updateFile(extraction, extraction.format, context)
                    openFile(Uri.parse(extraction.fileUri)) ***REMOVED***), // Make the Row clickable
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Preview Section
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.Gray.copy(alpha = 0.3f), shape = RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
***REMOVED*** {
                FaIcon(
                    faIcon = FaIcons.File, tint = Color.Black, size = 24.dp
    ***REMOVED***
            ***REMOVED***

            Spacer(modifier = Modifier.width(16.dp))

            // File Info Section
            Column(
                modifier = Modifier.weight(1f)
***REMOVED*** {
                Text(
                    text = extraction.title, fontSize = 16.sp, color = Color.Black
    ***REMOVED***

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
    ***REMOVED*** {
                    // Make the icons clickable
                    Box(modifier = Modifier.clickable {
                        extractionViewModel.updateFile(extraction, extraction.format, context)
                        if (extraction.fileUri != null) {
                            downloadFileExtra(Uri.parse(extraction.fileUri))
                        ***REMOVED***
                    ***REMOVED***) {
                        FaIcon(
                            faIcon = FaIcons.Download, tint = Color.Black, size = 20.dp
            ***REMOVED***
                    ***REMOVED***

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(modifier = Modifier.clickable {
                        extractionViewModel.updateFile(extraction, extraction.format, context)

                        if (extraction.fileUri != null) {
                            shareFile(Uri.parse(extraction.fileUri))
                        ***REMOVED***
                    ***REMOVED***) {
                        FaIcon(
                            faIcon = FaIcons.Share, tint = Color.Black, size = 20.dp
            ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***


@Composable
fun AddButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = {
            onClick()
        ***REMOVED***, modifier = modifier.fillMaxWidth()
    ) {
        Text("Add $text", textAlign = TextAlign.Center)
    ***REMOVED***
***REMOVED***


fun calculateCloseness(text1: String, text2: String): Int {//simple Levenshtein implementation
    if (text1 == text2) return 0  // Identical strings
    val m = text1.length
    val n = text2.length

    // Bonus for exact substring match (if search query is 2 or more chars)
    if (n >= 2 && text1.contains(text2, ignoreCase = true)) {
        return -1 // Negative value to prioritize exact matches
    ***REMOVED***

    val d = Array(m + 1) { IntArray(n + 1) ***REMOVED***
    for (i in 0..m) d[i][0] = i
    for (j in 0..n) d[0][j] = j
    for (j in 1..n) {
        for (i in 1..m) {
            val cost = if (text1[i - 1] == text2[j - 1]) 0 else 1
            d[i][j] = minOf(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + cost)
        ***REMOVED***
    ***REMOVED***
    return d[m][n]
***REMOVED***

fun getPdfFileName(context: Context, uri: Uri): String? {
    val projection = arrayOf(MediaStore.MediaColumns.DISPLAY_NAME)

    return context.contentResolver.query(uri, projection, null,
    null, null)?.use { cursor
        ->
        if (cursor.moveToFirst()) {
            val nameIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
            cursor.getString(nameIndex)
        ***REMOVED*** else {
            null
        ***REMOVED***
    ***REMOVED***
***REMOVED***

fun isFirstTimeVisit(context: Context, key: String): Boolean {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    val isFirstTime = sharedPreferences.getBoolean(key, true)

    if (isFirstTime) {
        // Set the value to false so it won't trigger again
        sharedPreferences.edit().putBoolean(key, false).apply()
    ***REMOVED***

    return isFirstTime
***REMOVED***


fun encodeImageToBase64(context: Context, imageUri: Uri): String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(imageUri)
        val bytes = inputStream?.readBytes()
        bytes?.let { Base64.encodeToString(it, Base64.DEFAULT) ***REMOVED***
    ***REMOVED*** catch (e: IOException) {
        e.printStackTrace()
        null

    ***REMOVED***
***REMOVED***