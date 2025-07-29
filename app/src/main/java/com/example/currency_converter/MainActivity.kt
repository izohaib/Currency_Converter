package com.example.currency_converter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.currency_converter.presentation.AppViewModel
import com.example.currency_converter.presentation.HomeScreen.HomeViewModel
import com.example.currency_converter.presentation.navigation.AppNavGraph
import com.example.currency_converter.presentation.theme.Currency_ConverterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Currency_ConverterTheme {

                val navController = rememberNavController()
                val homeViewModel: HomeViewModel = hiltViewModel()
                val appViewModel: AppViewModel = hiltViewModel()

                AppNavGraph(
                    navController = navController,
                    homeViewModel = homeViewModel,
                    appViewModel = appViewModel
                )
            }

        }
    }
}

