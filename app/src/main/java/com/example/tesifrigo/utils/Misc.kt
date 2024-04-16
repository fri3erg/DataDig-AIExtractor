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
