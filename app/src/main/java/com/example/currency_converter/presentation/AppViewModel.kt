package com.example.currency_converter.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currency_converter.Utils.Resource
import com.example.currency_converter.domain.useCases.GetRateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class AppViewModel @Inject constructor(
    private val getRateUseCase: GetRateUseCase
): ViewModel() {
    private val _state = MutableStateFlow(ApiState())
    val state: StateFlow<ApiState> = _state.asStateFlow()

    fun fetchRates(base: String) {
        viewModelScope.launch {
            getRateUseCase(base).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                loading = false,
                                error = result.errorMessage ?: "Unknown error"
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(loading = true)
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(loading = false, data = result.data)
                        }
                    }
                }
            }
        }
    }

}