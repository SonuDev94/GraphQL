package com.idemia.wa.graphql.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.idemia.wa.graphql.presentation.continents.ContinentsScreen
import com.idemia.wa.graphql.presentation.continents.ContinentsViewModel
import com.idemia.wa.graphql.presentation.details.ContinentDetailsScreen
import com.idemia.wa.graphql.presentation.details.ContinentsDetailsViewModel
import kotlinx.serialization.Serializable

@Composable
fun MainNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Dest.Home) {
        composable<Dest.Home> {
            val viewModel = hiltViewModel<ContinentsViewModel>()
            ContinentsScreen(viewModel = viewModel){
                navController.navigate(Dest.Details(it))
            }
        }
        composable<Dest.Details> {
            val code = it.toRoute<Dest.Details>().code
            val viewModel = hiltViewModel<ContinentsDetailsViewModel>()
            LaunchedEffect(code) {
                viewModel.getContinentDetails(code)
            }
            ContinentDetailsScreen(detailsViewModel = viewModel)
        }
    }
}

@Serializable
sealed class Dest {

    @Serializable
    data object Home : Dest()

    @Serializable
    data class Details(val code: String) : Dest()
}