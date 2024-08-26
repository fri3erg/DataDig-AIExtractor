package com.example.tesifrigo.ui.settings


import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.tesifrigo.R
import com.example.tesifrigo.ui.template.BooleanFieldWithLabel
import com.example.tesifrigo.ui.theme.dark_red
import com.example.tesifrigo.ui.theme.dark_green
import com.example.tesifrigo.ui.theme.vale
import com.example.tesifrigo.utils.HelpIconButton
import com.example.tesifrigo.viewmodels.Keys
import com.example.tesifrigo.viewmodels.ServiceViewModel
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons

@Composable
fun SettingsScreen(serviceViewModel: ServiceViewModel) {
    var resize by remember { mutableStateOf(serviceViewModel.options.value?.resize ?: true) ***REMOVED***
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

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(5.dp),
            colors= CardDefaults.cardColors(
                containerColor = vale
***REMOVED***,
            shape= RoundedCornerShape(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.Center
***REMOVED*** {
                Text(text = "API KEYS",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
    ***REMOVED***
                Spacer(modifier = Modifier.width(8.dp))
                HelpIconButton(helpText = stringResource(id = R.string.keys_disclaimer), title = stringResource(
                    id = R.string.api_keys
    ***REMOVED***,
    ***REMOVED***
            ***REMOVED***
            HorizontalDivider()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ClickableWebLink(
                text = "OpenAI API KEY",
                url = stringResource(R.string.https_platform_openai_com_api_keys),
                color = Color.Blue,
                fontSize = TextUnit.Unspecified,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal,
                textDecoration = TextDecoration.Underline
***REMOVED***
            Spacer(modifier = Modifier.width(8.dp))
            HelpIconButton(helpText = stringResource(R.string.keys_you_created_on_the_openai_platform_which_you_can_find_at_the_link), title = stringResource(
                R.string.openai_api_key
***REMOVED***,
                link = stringResource(R.string.https_platform_openai_com_api_keys)
***REMOVED***
        ***REMOVED***
        ApiKeyInput(
            key = Keys.API_KEY_1, viewModel = serviceViewModel, keyName = "OpenAI API Key"
        )
        HorizontalDivider()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ClickableWebLink(
                text = "AZURE API KEY",
                url = stringResource(R.string.https_portal_azure_com_create_microsoft_cognitiveservicesformrecognizer),
                color = Color.Blue,
                fontSize = TextUnit.Unspecified,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal,
                textDecoration = TextDecoration.Underline
***REMOVED***
            Spacer(modifier = Modifier.width(8.dp))
            HelpIconButton(helpText = stringResource(R.string.keys_connected_to_your_azure_document_intelligence_resource_which_you_can_create_in_the_azure_portal_at_the_link), title = stringResource(
                R.string.azure_api_key
***REMOVED***,
                link=stringResource(R.string.https_portal_azure_com_create_microsoft_cognitiveservicesformrecognizer))

        ***REMOVED***
        Row {
            ApiKeyInput(
                key = Keys.API_KEY_2, viewModel = serviceViewModel, keyName = "AZURE API Key", helpText = stringResource(R.string.azure_form_recognizer_api_key_the_key_connected_to_the_instance_of_the_document_intelligence_resource_you_created), title = stringResource(
                    R.string.azure_api_key
    ***REMOVED***
***REMOVED***

        ***REMOVED***
Row {

        ApiKeyInput(
            key = Keys.API_KEY_3, viewModel = serviceViewModel, keyName = "AZURE ENDPOINT", helpText = stringResource(R.string.the_endpoint_associated_to_your_document_intelligence_resource), title = stringResource(
                R.string.azure_endpoint
***REMOVED***
        )
    ***REMOVED***
        ***REMOVED***
            BooleanFieldWithLabel(label = stringResource(R.string.resize_the_image), value = resize, onValueChange = { resize = it
                serviceViewModel.changeOptions("resize", it)***REMOVED***,
                help = stringResource(R.string.allows_the_extractor_to_resize_the_image_down_to_4mb_the_limit_for_the_free_version_of_azure_document_intelligence_only_disable_this_if_you_are_using_the_pro_version), title = stringResource(
                R.string.resize)
***REMOVED***



    ***REMOVED***
***REMOVED***

@Composable
fun ApiKeyInput(
    key: Keys,
    viewModel: ServiceViewModel,
    keyName: String,
    helpText: String = "",
    title: String = ""
) {
    var showInput by remember { mutableStateOf(false) ***REMOVED***
    var apiKey by remember { mutableStateOf(viewModel.keyExists(key)) ***REMOVED***
    var newKey by remember { mutableStateOf("") ***REMOVED***

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp),
        ) {
            Text(text = keyName)
            Spacer(modifier = Modifier.width(16.dp))
            FaIcon(
                faIcon = FaIcons.Check, tint = if (apiKey) dark_green else dark_red
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
                Row {
                    OutlinedTextField(value = newKey,
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = { newKey = it ***REMOVED***,
                        label = { Text(stringResource(R.string.enter, keyName)) ***REMOVED***,
                        trailingIcon = {
                            Row(
                                modifier = Modifier
                                    .padding(end=16.dp)
                ***REMOVED*** {
                            FaIcon(
                                modifier = Modifier
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
                                faIcon = FaIcons.Save,
                ***REMOVED***
                            if (helpText.isNotEmpty()) {
                                Spacer(modifier = Modifier.width(16.dp))
                                HelpIconButton(helpText = helpText, title = title)
                            ***REMOVED***
                            ***REMOVED***
                        ***REMOVED***)
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
        modifier = modifier
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            ***REMOVED***
            .padding(8.dp),
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontStyle = fontStyle,
        textDecoration = textDecoration
    )
***REMOVED***
