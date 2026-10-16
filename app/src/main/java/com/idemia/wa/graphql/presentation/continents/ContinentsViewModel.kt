package com.idemia.wa.graphql.presentation.continents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.idemia.wa.graphql.ContinentFetchingQuery
import com.idemia.wa.graphql.domain.use_cases.FetchContinentUseCase
import com.idemia.wa.graphql.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ContinentsViewModel @Inject constructor(private val fetchContinentsUseCase: FetchContinentUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow(ContinentsScreen.UIState())
    val uiState = _uiState.asStateFlow()

    init {
        getContinents()
    }

    fun getContinents() = fetchContinentsUseCase.invoke().onEach {
        when (it) {
            is NetworkResult.Loading -> {
                _uiState.value = ContinentsScreen.UIState(isLoading = true)
            }

            is NetworkResult.Success -> {
                _uiState.value = ContinentsScreen.UIState(data = it.data)

            }

            is NetworkResult.Error -> {
                _uiState.value = ContinentsScreen.UIState(error = it.message)
            }
        }
    }.launchIn(viewModelScope)

}

object ContinentsScreen {
    data class UIState(
        val isLoading: Boolean = false,
        val error: String = "",
        val data: ContinentFetchingQuery.Data? = null
    )
}