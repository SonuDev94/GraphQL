package com.idemia.wa.graphql.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.idemia.wa.graphql.ContinentFetchingQuery
import com.idemia.wa.graphql.FetchContinentDetailsQuery
import com.idemia.wa.graphql.domain.use_cases.FetchContinentDetailsUseCase
import com.idemia.wa.graphql.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ContinentsDetailsViewModel @Inject constructor(private val continentDetailsUseCase: FetchContinentDetailsUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow(DetailsScreen.UIState())
    val uiState = _uiState.asStateFlow()


    fun getContinentDetails(code: String) = continentDetailsUseCase.invoke(code).onEach {
        when (it) {
            is NetworkResult.Error -> _uiState.value = DetailsScreen.UIState(error = it.message)
            is NetworkResult.Loading -> _uiState.value = DetailsScreen.UIState(isLoading = true)
            is NetworkResult.Success -> _uiState.value = DetailsScreen.UIState(data = it.data)
        }
    }.launchIn(viewModelScope)
}

object DetailsScreen {
    data class UIState(
        val isLoading: Boolean = false,
        val error: String = "",
        val data: FetchContinentDetailsQuery.Data? = null
    )
}