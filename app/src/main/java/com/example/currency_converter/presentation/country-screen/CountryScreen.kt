package com.example.currency_converter.presentation.country

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.currency_converter.presentation.AppViewModel
import com.example.currency_converter.presentation.HomeScreen.HomeEvents
import com.example.currency_converter.presentation.HomeScreen.HomeViewModel
import com.example.currency_converter.presentation.theme.LocalTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.currency_converter.R
import com.example.currency_converter.Utils.CurrencyInfo
import com.example.currency_converter.Utils.getCountryList
//import com.example.currency_converter.Utils.systemBarsColor
import com.example.currency_converter.presentation.components.ListComponents
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryScreen(
    homeViewModel: HomeViewModel,
    appViewModel: AppViewModel,
    isFrom: Boolean,
    navController: NavHostController
) {
    val theme = LocalTheme.current
//    systemBarsColor(backgroundColor = theme.background)
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    //remember scrolling
    val listState = rememberLazyListState()

    val context = LocalContext.current
    val countryList = getCountryList(context) //call util function to get the country list from country.json

    val uiState by homeViewModel.state.collectAsState()

    val searchQuery = uiState.searchQuery.trim().lowercase()


    // Filter currency list by search query
    val filteredList: List<CurrencyInfo> = remember(searchQuery) {
        if (searchQuery.isEmpty()) countryList
        else countryList.filter {
            it.countryCode.lowercase().contains(searchQuery) ||
                    it.countryName.lowercase().contains(searchQuery) ||
                    it.currencyCode.lowercase().contains(searchQuery)
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppHeader(
                title = "Search Currencies",
                scrollBehavior = scrollBehavior,
                listState = listState,
                searchQuery = searchQuery,
                onSearchQueryChange = {
                    homeViewModel.onEvent(HomeEvents.SearchQueryChange(it))
                },
                onNavigationIconClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { innerPadding ->
        if (filteredList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(theme.background),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "(~_^)", color = theme.onBackground, fontSize = 94.sp)
            }
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(theme.background)
            ) {
                items(filteredList) { country ->
                    ListComponents(
                        code = country.countryCode,
                        name = country.countryName.uppercase(),
                        onClick = {
                            homeViewModel.onEvent(
                                if (isFrom)
                                    HomeEvents.FromCurrencySelected(countryCode = country.countryCode, currencyCode = country.currencyCode)
                                else
                                    HomeEvents.ToCurrencyChangeSelected(countryCode = country.countryCode, currencyCode = country.currencyCode)
                            )
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppHeader(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    listState: LazyListState,
    onNavigationIconClick: () -> Unit = {},
) {
    val theme = LocalTheme.current
    var isSearching by remember { mutableStateOf(false) }
    var isScrolling by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    //is searching will be true when the first item of lazy column is shows

    // Observe scroll position and auto hide/show search
    LaunchedEffect(listState) {
        listState?.let { state ->
            snapshotFlow { state.firstVisibleItemIndex }
                .collect { index ->
                    if (index > 0 && isSearching) {
                        isSearching = false
                    } else if (index == 0 ) {
                        isSearching = true
                    }
                }
        }
    }

    val titleContent: @Composable () -> Unit = {
        if (isSearching) {
            TextField(
                textStyle = TextStyle(fontSize = 16.sp) ,
                value = searchQuery,              // use state from viewmodel
                onValueChange = { newText ->
                    onSearchQueryChange(newText)  // Notify ViewModel of change
                },
                placeholder = {
                    Text(
                        "Search...",
                        color = theme.onBackground,
                        fontFamily = FontFamily(Font(R.font.poppins_medium))
                    )

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
                    .height(60.dp)
                    .border(
                        width = 1.dp,
                        color = theme.onBackground,
                        shape = MaterialTheme.shapes.large
                    ),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = theme.background,
                    focusedContainerColor = theme.background,
                    unfocusedIndicatorColor = theme.background,
                    focusedIndicatorColor = theme.background,
                    cursorColor = theme.onBackground,
                    focusedTextColor = theme.onBackground,
                ),
                shape = MaterialTheme.shapes.large,
                keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Text )
            )
        } else {
            Text(title, color = theme.onBackground)
        }
    }

    //scroll to top
    val actionsContent: @Composable RowScope.() -> Unit = {
        IconButton(onClick = {
                if (!isScrolling) {  //isScrolling prevents another touches at the same time
                    coroutineScope.launch {
                        isScrolling = true    // 🔒 lock scroll
                        listState.animateScrollToItem(0)
                        isScrolling = false   // 🔓 unlock after scroll is done
                    }
                }
        }) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Localized description",
                tint = theme.onBackground
            )
        }
    }

    LargeTopAppBar(
        title = titleContent,

        navigationIcon = {
            IconButton(onClick =  onNavigationIconClick ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Localized description",
                    tint = theme.onBackground
                )
            }
        },

        actions = {
            actionsContent()
        },

        scrollBehavior = scrollBehavior,

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = theme.background,
            scrolledContainerColor = theme.background,
            titleContentColor = theme.background,
        ),
    )
}