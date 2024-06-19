package com.example.tesifrigo.ui.settings


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tesifrigo.viewmodels.Keys
import com.example.tesifrigo.viewmodels.ServiceViewModel

@Composable
fun SettingsScreen() {
    var serviceViewModel: ServiceViewModel = hiltViewModel()
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Settings",
        )
        ApiKeyInput(
            key = Keys.API_KEY_1,
            viewModel = serviceViewModel,
            keyName = "API Key 1"
        )
        ApiKeyInput(
            key = Keys.API_KEY_2,
            viewModel = serviceViewModel,
            keyName = "API Key w"
        )
    ***REMOVED***
***REMOVED***

@Composable
fun ApiKeyInput(
    key: Keys,
    viewModel: ServiceViewModel,
    keyName: String,
) {
    var showInput by remember { mutableStateOf(false) ***REMOVED***
    var apiKey by remember { mutableStateOf(viewModel.keyExists(key)) ***REMOVED***
    var newKey by remember { mutableStateOf("") ***REMOVED***

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = keyName)
            Spacer(modifier = Modifier.weight(1f)) // Push items to the right
            if (apiKey) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Key present",
                    tint = Color.Green
    ***REMOVED***
            ***REMOVED***
            else{
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Key is not present",
                    tint = Color.Red
    ***REMOVED***
            ***REMOVED***
            Button(onClick = { showInput = !showInput ***REMOVED***) {
                Text(if (showInput) "Hide Input" else "Show Input")
            ***REMOVED***
        ***REMOVED***

        if (showInput) {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = newKey,
                    onValueChange = { newKey = it***REMOVED***,
                    label = { Text("Enter $keyName") ***REMOVED***
    ***REMOVED***
                Button(
                    onClick = {
                        if (newKey.isNotBlank()) {
                            viewModel.storeApiKey(key, newKey)
                            apiKey = true
                            showInput = false
                        ***REMOVED***
                        else {
                            viewModel.storeApiKey(key, newKey)
                            apiKey = false
                            showInput = false
                        ***REMOVED***
                    ***REMOVED***,
                    modifier = Modifier.align(Alignment.End)
    ***REMOVED*** {
                    Text("Confirm")
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***

