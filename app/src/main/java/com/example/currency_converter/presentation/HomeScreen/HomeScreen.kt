package com.example.currency_converter.presentation.HomeScreen


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.currency_converter.R
import com.example.currency_converter.Utils.getCurrencySymbol
import com.example.currency_converter.Utils.systemBarsColor
import com.example.currency_converter.domain.useCases.GetRateUseCase
import com.example.currency_converter.presentation.AppViewModel
import com.example.currency_converter.presentation.components.SelectedCurrency
import com.example.currency_converter.presentation.navigation.Routes
import com.example.currency_converter.presentation.theme.LocalTheme


@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    appViewModel: AppViewModel,
    navController: NavHostController
) {

    val theme = LocalTheme.current
    systemBarsColor(theme.background)

    val uiState = homeViewModel.state.collectAsState()
    val appState = appViewModel.state.collectAsState()

    val amountNotEmpty = uiState.value.currentAmount.isNotEmpty()

    //fetch rates when app start
    LaunchedEffect(uiState.value.fromCurrencyCode) {
        appViewModel.fetchRates(uiState.value.fromCurrencyCode)
    }

    //getting input from user
    val amount = uiState.value.currentAmount.toDoubleOrNull() ?: 0.0
    val fromCountryCode = uiState.value.fromCurrencyCode.lowercase()
    val toCountryCode = uiState.value.toCurrencyCode.lowercase()

    //getting currency symbol
    val symbol = getCurrencySymbol(toCountryCode)

    //get rates from api according to base country(fromCurrencyCode)
    val rateMap = appState.value.data?.rates.orEmpty()
    Log.d("HomeScreen", "rateMap: $rateMap")

    //currency conversion
    val convertedAmount = remember(fromCountryCode, toCountryCode, amount, rateMap) {
        val toRate = rateMap[toCountryCode] ?: 1.0
        amount * toRate
    }

    val animatedAmount by animateFloatAsState(
        targetValue = convertedAmount.toFloat(),
        // Configure the animation duration and easing.
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "alpha"
    )
    Log.d("HomeScreen", "convertedAmount: $convertedAmount")

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = theme.background,
                    titleContentColor = theme.onBackground,
                ),
                title = {
                    Text(
                        "Currency Converter",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description",
                            tint = theme.onBackground
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(theme.background)
                .padding(12.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                //from currency
                SelectedCurrency(
                    modifier = Modifier.weight(1f),
                    currencyCode = uiState.value.fromCurrencyCode,
                    onClick = {
                        //navigation
                        navController.navigate("${Routes.SELECT_CURRENCY}/true")
                    },
                    countryCode = uiState.value.fromCountryCode
                )

                //swap icon
                IconButton(
                    onClick = {
                        homeViewModel.onEvent(HomeEvents.SwapCurrency)
                    },
                    modifier = Modifier.rotate(-17f)
                ) {
                    Icon(
                        imageVector = Icons.Default.SwapHoriz,
                        contentDescription = null,
                        tint = theme.onBackground
                    )
                }

                //to currency
                SelectedCurrency(
                    modifier = Modifier.weight(1f),
                    currencyCode = uiState.value.toCurrencyCode,
                    onClick = {
                        navController.navigate("${Routes.SELECT_CURRENCY}/false")
                    },
                    countryCode = uiState.value.toCountryCode
                )
            }

            Spacer(modifier = Modifier.size(10.dp))

            TextField(
                modifier = Modifier.fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = theme.onBackground,
                        shape = MaterialTheme.shapes.large
                    ),
                value = uiState.value.currentAmount,
                onValueChange = {
                    homeViewModel.onEvent(
                        HomeEvents.CurrencyAmount(it)
                    )
                },
                placeholder = {
                    Text(
                        text = "Input Amount",
                        color = theme.onBackground,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = theme.background,
                    focusedContainerColor = theme.background,
                    unfocusedIndicatorColor = theme.background,
                    focusedIndicatorColor = theme.background,
                    cursorColor = theme.onBackground,
                    focusedTextColor = theme.onBackground,
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = MaterialTheme.shapes.medium,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                val scrollState = rememberScrollState()

                Row(
                    modifier = Modifier
                        .horizontalScroll(scrollState)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { }
                        .graphicsLayer(
                            alpha = 0.8f + (animatedAmount % 1f), // subtle pulsating fade
                            scaleX = 0.95f + (animatedAmount % 0.1f), // slight zoom
                            scaleY = 0.95f + (animatedAmount % 0.1f),
                        )
                ) {
                    Text(
                        text = "$symbol ${String.format("%.2f", animatedAmount)} ",
                        fontSize = 36.sp,
                        color = theme.onBackground,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        fontWeight = FontWeight.Medium,
                    )
                }
            }



            Button(
                onClick = { homeViewModel.onEvent(HomeEvents.CurrencyAmount("")) },
                enabled = amountNotEmpty,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (amountNotEmpty) theme.onBackground else theme.surface.copy(
                        alpha = 0.3f
                    ),
                    contentColor = if (amountNotEmpty) theme.background else theme.onBackground.copy(
                        alpha = 0.5f
                    )
                )
            ) {
                Text(
                    "Reset",
                    fontSize = 17.sp,
                )
            }
        }
    }
}


