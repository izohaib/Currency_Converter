package com.example.currency_converter.presentation.HomeScreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(HomeUiStates())
    val state: StateFlow<HomeUiStates> = _state.asStateFlow()

    fun onEvent(event: HomeEvents) {
        when (event) {
            is HomeEvents.FromCurrencySelected -> {
                _state.update {
                    it.copy(
                        fromCurrencyCode = event.currencyCode,
                        fromCountryCode = event.countryCode
                    )
                }
            }

            is HomeEvents.ToCurrencyChangeSelected -> {
                _state.update {
                    it.copy(
                        toCurrencyCode = event.currencyCode,
                        toCountryCode = event.countryCode
                    )
                }
            }

            is HomeEvents.SearchQueryChange -> {
                _state.update {
                    it.copy(
                        searchQuery = event.query
                    )
                }
            }

            is HomeEvents.CurrencyAmount -> {
                _state.update {
                    it.copy(
                        currentAmount = event.amount
                    )
                }
            }

            HomeEvents.SwapCurrency -> {
                _state.update {
                    it.copy(
                        fromCurrencyCode = it.toCurrencyCode,
                        toCurrencyCode = it.fromCurrencyCode
                    )
                }

            }
        }
    }
}