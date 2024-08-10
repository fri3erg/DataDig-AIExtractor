package com.example.tesifrigo.ui.settings


import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tesifrigo.utils.HelpIconButton
import com.example.tesifrigo.viewmodels.Keys
import com.example.tesifrigo.viewmodels.ServiceViewModel
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons

@Composable
fun SettingsScreen(serviceViewModel: ServiceViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ClickableWebLink(
                text = "OpenAI API KEY",
                url = "https://platform.openai.com/api-keys",
                color = Color.Blue,
                fontSize = TextUnit.Unspecified,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal,
                textDecoration = TextDecoration.Underline
***REMOVED***
            Spacer(modifier = Modifier.width(8.dp))
            HelpIconButton(helpText = "OpenAI API Key")
        ***REMOVED***
        ApiKeyInput(
            key = Keys.API_KEY_1,
            viewModel = serviceViewModel,
            keyName = "OpenAI API Key"
        )
        HorizontalDivider()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ClickableWebLink(
                text = "AZURE API KEY",
                url = "https://portal.azure.com/#create/Microsoft.CognitiveServicesFormRecognizer",
                color = Color.Blue,
                fontSize = TextUnit.Unspecified,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal,
                textDecoration = TextDecoration.Underline
***REMOVED***
            Spacer(modifier = Modifier.width(8.dp))
            HelpIconButton(helpText = "Azure Form Recognizer API Key")
        ***REMOVED***
        ApiKeyInput(
            key = Keys.API_KEY_2,
            viewModel = serviceViewModel,
            keyName = "AZURE API Key"
        )
        ApiKeyInput(
            key = Keys.API_KEY_3,
            viewModel = serviceViewModel,
            keyName = "AZURE ENDPOINT"
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp),
        ) {
            Text(text = keyName)
            Spacer(modifier = Modifier.width(16.dp))
            FaIcon(
                faIcon = FaIcons.Check,
                tint = if (apiKey) Color.Green else Color.Red
***REMOVED***
            Spacer(modifier = Modifier.weight(1f)) // Push items to the right

            FaIcon(
                faIcon = if (showInput) FaIcons.ArrowUp else FaIcons.ArrowDown,
                modifier = Modifier.clickable {
                    showInput = !showInput
                ***REMOVED***,
                tint = Color.Black
***REMOVED***
        ***REMOVED***

        if (showInput) {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = newKey,
                    onValueChange = { newKey = it ***REMOVED***,
                    label = { Text("Enter $keyName") ***REMOVED***,
                    trailingIcon = {
                        FaIcon(
                            modifier = Modifier
                                .align(Alignment.End)
                                .clickable {
                                    if (newKey.isNotBlank()) {
                                        viewModel.storeApiKey(key, newKey)
                                        apiKey = true
                                        showInput = false
                                    ***REMOVED*** else {
                                        apiKey = false
                                        showInput = false
                                    ***REMOVED***
                                ***REMOVED***,
                            faIcon = FaIcons.Check,
            ***REMOVED***
                    ***REMOVED***
    ***REMOVED***

            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***


@Composable
fun ClickableWebLink(
    text: String,
    url: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Blue,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = FontWeight.Bold,
    fontStyle: FontStyle? = null,
    textDecoration: TextDecoration? = TextDecoration.Underline
) {
    val context = LocalContext.current
    Text(
        text = text,
        modifier = modifier.clickable {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        ***REMOVED***,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontStyle = fontStyle,
        textDecoration = textDecoration
    )
***REMOVED***
