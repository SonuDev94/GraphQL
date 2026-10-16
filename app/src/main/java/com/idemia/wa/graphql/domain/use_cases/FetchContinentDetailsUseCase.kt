package com.idemia.wa.graphql.domain.use_cases

import com.idemia.wa.graphql.FetchContinentDetailsQuery
import com.idemia.wa.graphql.domain.repository.CountryRepo
import com.idemia.wa.graphql.utils.NetworkResult
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FetchContinentDetailsUseCase @Inject constructor(private val countryRepo: CountryRepo) {
    operator fun invoke(code: String): Flow<NetworkResult<FetchContinentDetailsQuery.Data>> = flow {
        emit(NetworkResult.Loading())
        val response = countryRepo.getContinentDetails(code);
        if (response.isSuccess) {
            emit(NetworkResult.Success(response.getOrNull()))
        } else {
            emit(NetworkResult.Error(response.exceptionOrNull()?.message.toString()))
        }
    }.catch() {
        emit(NetworkResult.Error(it.message.toString()))
    }
        .flowOn(Dispatchers.IO)
}