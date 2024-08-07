package com.example.tesifrigo.utils

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.activity.ComponentActivity
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
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
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
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


@Composable
fun EditableTextWithTitle(
    title: String,
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier
) {
    Column {
        Text(
            text = title,
        )
        OutlinedTextField(
            modifier = modifier,
            value = text,
            onValueChange = onTextChange,
            label = { Text("Enter text") ***REMOVED*** // Optional label
        )
    ***REMOVED***
***REMOVED***



@Composable
fun DeleteButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showAlert by remember { mutableStateOf(false) ***REMOVED***
    Button(
        onClick = { showAlert = true ***REMOVED***,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
        border = BorderStroke(2.dp, Color.Red),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Text("Delete $text",maxLines = 1, textAlign = TextAlign.Center, fontStyle = FontStyle.Normal, fontSize = 20.sp)
    ***REMOVED***
    if (showAlert)
        AlertDialog(
            onDismissRequest = { showAlert = false ***REMOVED***,
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
            ***REMOVED***
        )
***REMOVED***


@Composable
fun TableCell(text: String, modifier: Modifier = Modifier, isHeader: Boolean = false, onTextChange: (String) -> Unit) {
    val modifierPadded = modifier
        .padding(4.dp)
    Box(
        modifier = modifierPadded,
        contentAlignment = Alignment.Center
    ) {
        if (isHeader) {
            Text(
                text = text,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold ,
                overflow = TextOverflow.Ellipsis,
***REMOVED***
        ***REMOVED***
        else {
            OutlinedTextField(
                modifier = modifier.align(Alignment.Center),
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
fun HelpIconButton(helpText: String) {
    var showDialog by remember { mutableStateOf(false) ***REMOVED***

    IconButton(
        onClick = { showDialog = true ***REMOVED***,
        modifier = Modifier.size(20.dp) // Make the icon smaller
    ) {
        FaIcon(FaIcons.InfoCircle, modifier = Modifier.offset(y = (-4).dp))

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
            FaIcon(faIcon = FaIcons.EllipsisV, tint = Color.Gray)
        ***REMOVED***

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false ***REMOVED***,
            modifier = Modifier
                .width(150.dp) // Adjust width as needed
                .align(Alignment.TopEnd) // Position at the top right corner
        ) {
            DropdownMenuItem(
                text = { Text("Use") ***REMOVED***,
                onClick = {
                    onUse()
                    expanded = false
                ***REMOVED***
***REMOVED***
            DropdownMenuItem(
                text = { Text("Edit") ***REMOVED***,
                onClick = {
                    onEdit()
                    expanded = false
                ***REMOVED***
***REMOVED***
            DropdownMenuItem(
                text = { Text("Delete") ***REMOVED***,
                onClick = {
                    showDeleteDialog = true
                    expanded = false
                ***REMOVED***
***REMOVED***
        ***REMOVED***
    ***REMOVED***

    // Confirmation Dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false ***REMOVED***,
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
            ***REMOVED***
        )
    ***REMOVED***
***REMOVED***


@Composable
fun TextWithTitle(
    title: String,
    text: String,
    modifier: Modifier
) {
    Column {
        Text(
            text = title,
        )
        Text(
            text = text,
            modifier = modifier
        )
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
    extraction: Extraction,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val openFileLauncher = remember {
        (context as? ComponentActivity)?.activityResultRegistry?.register(
            "openFile",
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            // Handle the result if needed
        ***REMOVED***
    ***REMOVED***
    fun downloadFile(uri: Uri?) {
        uri?.let { fileUri ->
            val contentUri = FileProvider.getUriForFile(
                context,
                "com.example.tesifrigo.fileprovider",
                File(fileUri.path!!)
***REMOVED***

            // Get the filename from the content URI
            val fileName = contentUri.lastPathSegment ?: "downloaded_file"

            // Create a destination file in the Downloads directory
            val destinationFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)

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
                Toast.makeText(context, "File downloaded to Downloads folder", Toast.LENGTH_SHORT).show()

            ***REMOVED*** catch (e: IOException) {
                // Handle exceptions (e.g., log the error, show an error message to the user)
                if ((e is FileNotFoundException) && (e.message?.contains("EEXIST") == true)){

                            Toast.makeText(context, "File already downloaded", Toast.LENGTH_SHORT).show()

                    ***REMOVED***
                    else{
                        // Handle other IOException cases
                        e.printStackTrace()
                        Toast.makeText(context, "Error downloading file", Toast.LENGTH_SHORT).show()
                    ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
    fun openFile(uri: Uri) {
        val contentUri = FileProvider.getUriForFile(
            context,
            "com.example.tesifrigo.fileprovider",
            File(uri.path!!)
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(contentUri, context.contentResolver.getType(contentUri))
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        ***REMOVED***

        val packageManager = context.packageManager
        val activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)

        if (activities.isNotEmpty())
        {
            // There's at least one app that can handle the intent, so launch it
            openFileLauncher?.launch(intent)
        ***REMOVED*** else {
            // No app can handle the intent, so download the file
            downloadFile(uri)
        ***REMOVED***
    ***REMOVED***
    fun shareFile(uri: Uri?) {
        uri?.let { fileUri ->
            val contentUri = FileProvider.getUriForFile(
                context,
                "com.example.tesifrigo.fileprovider",
                File(fileUri.path!!)
***REMOVED***

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = context.contentResolver.getType(contentUri)
                putExtra(Intent.EXTRA_STREAM, contentUri)
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            ***REMOVED***

            context.startActivity(Intent.createChooser(shareIntent, "Share File"))
        ***REMOVED***
    ***REMOVED***

    Surface(
        modifier = modifier
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable(onClick = { openFile(Uri.parse(extraction.fileUri!!)) ***REMOVED***), // Make the Row clickable
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
                    faIcon = FaIcons.File,
                    tint = Color.Gray,
                    size = 24.dp
    ***REMOVED***
            ***REMOVED***

            Spacer(modifier = Modifier.width(16.dp))

            // File Info Section
            Column(
                modifier = Modifier.weight(1f)
***REMOVED*** {
                Text(
                    text = extraction.template!!.title,
                    fontSize = 16.sp,
                    color = Color.Black
    ***REMOVED***

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
    ***REMOVED*** {
                    // Make the icons clickable
                    Box(modifier = Modifier
                        .clickable {
                            downloadFile(Uri.parse(extraction.fileUri!!))
                        ***REMOVED***
        ***REMOVED*** {
                        FaIcon(
                            faIcon = FaIcons.Download,
                            tint = Color.Gray,
                            size = 20.dp
            ***REMOVED***
                    ***REMOVED***

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(modifier = Modifier
                        .clickable {
                            shareFile(Uri.parse(extraction.fileUri!!))
                        ***REMOVED***
        ***REMOVED*** {
                        FaIcon(
                            faIcon = FaIcons.Share,
                            tint = Color.Gray,
                            size = 20.dp
            ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***




@Composable
fun AddButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(onClick = {
        onClick()
    ***REMOVED***,
        modifier = modifier.fillMaxWidth()) {
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


