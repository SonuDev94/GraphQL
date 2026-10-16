package com.idemia.wa.graphql.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ContinentDetailsScreen(
    modifier: Modifier = Modifier,
    detailsViewModel: ContinentsDetailsViewModel
) {

    val uiState by detailsViewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    if (uiState.error.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = uiState.error, style = MaterialTheme.typography.displayMedium)
        }
    }

    uiState.data?.let { data->
        Column(verticalArrangement = Arrangement.Center) {

            Text(text = data.continent?.name ?: "Not Found")
            data.continent?.countries?.forEach {
                Text(text = "${it.name} - ${it.emoji}")


                it.languages.forEach {
                    Text(text = it.name)
                }
            }


        }
    }

}