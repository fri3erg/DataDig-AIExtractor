package com.example.tesifrigo.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier




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


