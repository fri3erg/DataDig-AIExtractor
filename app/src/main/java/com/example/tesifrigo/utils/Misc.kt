package com.example.tesifrigo.utils

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.example.tesifrigo.models.Extraction
import com.example.tesifrigo.ui.theme.cyan_custom
import com.example.tesifrigo.ui.theme.dark_red
import com.example.tesifrigo.ui.theme.light_gray
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
        title = { Text("Delete $text?") ***REMOVED***,
        text = { Text("Are you sure you want to delete this ${text.lowercase()***REMOVED***?") ***REMOVED***,
        confirmButton = {
            TextButton(onClick = {
                onClick()
                showAlert = false
            ***REMOVED***) {
                Text("OK")
            ***REMOVED***
        ***REMOVED***,
        dismissButton = {
            TextButton(onClick = { showAlert = false ***REMOVED***) {
                Text("Cancel")
            ***REMOVED***
        ***REMOVED***)
***REMOVED***


@Composable
fun TableCell(
    text: String,
    modifier: Modifier = Modifier,
    isHeader: Boolean = false,
    onTextChange: (String) -> Unit
) {
    val modifierPadded = modifier.padding(4.dp)
    Box(
        modifier = modifierPadded, contentAlignment = Alignment.Center
    ) {
        if (isHeader) {
            Text(
                text = text,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
***REMOVED***
        ***REMOVED*** else {
            OutlinedTextField(modifier = modifier.align(Alignment.Center),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Blue,
                    unfocusedBorderColor = Color.LightGray,
                    cursorColor = Color.Gray,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,

        ***REMOVED***,
                value = text,
                onValueChange = {
                    onTextChange(it)
                ***REMOVED***,
                maxLines = 4,
                label = { Text("Edit") ***REMOVED*** // Optional label
***REMOVED***
        ***REMOVED***
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
        label = { Text("Search") ***REMOVED***,
        modifier = modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        singleLine = true, // Ensure single-line input
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search, contentDescription = "Search Icon"
***REMOVED***
        ***REMOVED***)
***REMOVED***


@Composable
fun HelpIconButton(helpText: String, modifier: Modifier = Modifier, title: String = "Help") {
    var showDialog by remember { mutableStateOf(false) ***REMOVED***

    IconButton(
        onClick = { showDialog = true ***REMOVED***, modifier = modifier.size(20.dp), // Make the icon smaller
        colors = IconButtonDefaults.filledIconButtonColors(
            contentColor = Color.Black, containerColor = Color.Black
        )
    ) {

        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Help Icon",
            tint = Color.White,
            //modifier = Modifier.border(1.dp, Color.Black, RoundedCornerShape(10.dp))
        ) // Use the Info icon from Material Design

    ***REMOVED***

    if (showDialog) {
        AlertDialog(onDismissRequest = { showDialog = false ***REMOVED***,
            title = { Text(title) ***REMOVED***,
            text = { Text(helpText) ***REMOVED***,
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
            DropdownMenuItem(text = { Text("Use") ***REMOVED***, onClick = {
                onUse()
                expanded = false
            ***REMOVED***)
            DropdownMenuItem(text = { Text("Edit") ***REMOVED***, onClick = {
                onEdit()
                expanded = false
            ***REMOVED***)
            DropdownMenuItem(text = { Text("Delete") ***REMOVED***, onClick = {
                showDeleteDialog = true
                expanded = false
            ***REMOVED***)
        ***REMOVED***
    ***REMOVED***

    // Confirmation Dialog
    if (showDeleteDialog) {
        AlertDialog(onDismissRequest = { showDeleteDialog = false ***REMOVED***,
            title = { Text("Confirm Delete") ***REMOVED***,
            text = { Text("Are you sure you want to delete this template?") ***REMOVED***,
            confirmButton = {
                Button(onClick = {
                    onDelete()
                    showDeleteDialog = false
                ***REMOVED***) {
                    Text("Delete")
                ***REMOVED***
            ***REMOVED***,
            dismissButton = {
                Button(onClick = { showDeleteDialog = false ***REMOVED***) {
                    Text("Cancel")
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
            Text(selectedItem)
        ***REMOVED***

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false ***REMOVED***,
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEach { item ->
                DropdownMenuItem(text = { Text(item) ***REMOVED***, onClick = {
                    selectedItem = item
                    onItemSelected(item)
                    expanded = false
                ***REMOVED***)
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***


@Composable
fun MyImageArea(
    imageUris: List<Uri>,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(pageCount = { imageUris.size ***REMOVED***)

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 16.dp),
        ) { page ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
***REMOVED*** {
                AsyncImage(
                    model = imageUris[page],
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
    ***REMOVED***
            ***REMOVED***
        ***REMOVED***

        // Custom Indicator
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            repeat(pagerState.pageCount) { page ->
                Box(
                    modifier = Modifier
                        .size(if (pagerState.currentPage == page) 10.dp else 6.dp)
                        .clip(CircleShape)
                        .background(if (pagerState.currentPage == page) Color.DarkGray else Color.LightGray)
    ***REMOVED***
            ***REMOVED***
        ***REMOVED***

        Spacer(modifier = Modifier.height(16.dp))
    ***REMOVED***
***REMOVED***


@Composable
fun FileCard(
    extraction: Extraction, modifier: Modifier = Modifier
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
                    context, "File downloaded to Downloads folder", Toast.LENGTH_SHORT
    ***REMOVED***.show()
            ***REMOVED***
        ***REMOVED*** catch (e: IOException) {
            // Handle exceptions (e.g., log the error, show an error message to the user)
            if ((e is FileNotFoundException) && (e.message?.contains("EEXIST") == true)) {

                Toast.makeText(context, "File already downloaded", Toast.LENGTH_SHORT).show()

            ***REMOVED*** else {
                // Handle other IOException cases
                e.printStackTrace()
                Toast.makeText(context, "Error downloading file", Toast.LENGTH_SHORT).show()
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    fun openFile(uri: Uri) {
        if (uri.path == null) return
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
                context, "No app found to open the file, downloading instead", Toast.LENGTH_SHORT
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
        modifier = modifier.padding(8.dp), shape = RoundedCornerShape(8.dp), color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable(onClick = { openFile(Uri.parse(extraction.fileUri)) ***REMOVED***), // Make the Row clickable
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
                    faIcon = FaIcons.File, tint = Color.Gray, size = 24.dp
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
                            if (extraction.fileUri != null) {
                                downloadFileExtra(Uri.parse(extraction.fileUri))
                            ***REMOVED***
                        ***REMOVED***) {
                        FaIcon(
                            faIcon = FaIcons.Download, tint = Color.Gray, size = 20.dp
            ***REMOVED***
                    ***REMOVED***

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(modifier = Modifier.clickable {
                            if (extraction.fileUri != null) {
                                shareFile(Uri.parse(extraction.fileUri))
                            ***REMOVED***
                        ***REMOVED***) {
                        FaIcon(
                            faIcon = FaIcons.Share, tint = Color.Gray, size = 20.dp
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

