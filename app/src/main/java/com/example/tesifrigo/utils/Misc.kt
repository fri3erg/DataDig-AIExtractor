package com.example.tesifrigo.utils

import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.example.tesifrigo.models.Extraction
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import java.io.File


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
fun TableCell(text: String, modifier: Modifier = Modifier, isHeader: Boolean = false) {
    Box(
        modifier = modifier
            .padding(4.dp)
            .background(if (isHeader) Color.Gray else Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Normal,
            fontSize = 14.sp
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

    fun openFile(uri: Uri) {
        val contentUri = FileProvider.getUriForFile(
            context,
            "com.example.tesifrigo.fileprovider", // Match the authorities in your manifest
            File(uri.path!!)
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(contentUri, context.contentResolver.getType(contentUri))
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        ***REMOVED***
        openFileLauncher?.launch(intent)
    ***REMOVED***

    Surface(
        modifier = modifier
            .clickable(onClick = { openFile(Uri.parse(extraction.fileUri!!)) ***REMOVED***)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
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
                    FaIcon(
                        faIcon = FaIcons.Download,
                        tint = Color.Gray,
                        size = 20.dp
        ***REMOVED***
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Download",
                        fontSize = 14.sp,
                        color = Color.Gray
        ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
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


