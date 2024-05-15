package com.example.tesifrigo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.tesifrigo.ui.common.NavBar
import com.example.tesifrigo.ui.theme.TesiFrigoTheme
import com.example.tesifrigo.viewmodels.ExtractionViewModel
import com.example.tesifrigo.viewmodels.TemplateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val extractionViewModel : ExtractionViewModel by viewModels()
    private val templateViewModel : TemplateViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TesiFrigoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
    ***REMOVED*** {
                        MainAppScreen(templateViewModel, extractionViewModel)
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***
@Composable
fun MainAppScreen(templateViewModel: TemplateViewModel, extractionViewModel: ExtractionViewModel) {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = { NavBar(navController) ***REMOVED***,
        modifier = Modifier.fillMaxSize(),
        content = { innerPadding -> // Important for content overlap
            AppNavigation( navController, modifier = Modifier.padding(innerPadding), templateViewModel, extractionViewModel)
        ***REMOVED***
    )
***REMOVED***

