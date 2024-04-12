package com.example.tesifrigo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.tesifrigo.ui.common.NavBar
import com.example.tesifrigo.ui.theme.TesiFrigoTheme
import com.example.tesifrigo.viewmodels.TemplateViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TesiFrigoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
    ***REMOVED*** {
                    MainAppScreen()
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***
@Composable
fun MainAppScreen() {

    val navController = rememberNavController()
    val viewModel = viewModel<TemplateViewModel>()
    Column {
        AppNavigation(viewModel,navController)
        NavBar(navController)
    ***REMOVED***
***REMOVED***


@Preview(showBackground = true)
@Composable
fun Preview() {
    TesiFrigoTheme {
        MainAppScreen()
    ***REMOVED***
***REMOVED***