package com.idemia.wa.graphql.domain.use_cases

import com.idemia.wa.graphql.ContinentFetchingQuery
import com.idemia.wa.graphql.domain.repository.CountryRepo
import com.idemia.wa.graphql.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchContinentUseCase  @Inject constructor(private val countryRepo: CountryRepo) {

    operator fun invoke(): Flow<NetworkResult<ContinentFetchingQuery.Data>> =
        flow {
            emit(NetworkResult.Loading())
            val response = countryRepo.getContinents()
            if (response.isSuccess) {
                emit(NetworkResult.Success(response.getOrNull()))
            } else {
                emit(NetworkResult.Error(response.exceptionOrNull()?.message.toString()))
            }


        }.catch {
            emit(NetworkResult.Error(it.message.toString()))
        }
            .flowOn(Dispatchers.IO)

}