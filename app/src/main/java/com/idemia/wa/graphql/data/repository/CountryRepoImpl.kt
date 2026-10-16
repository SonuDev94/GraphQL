package com.idemia.wa.graphql.data.repository

import com.apollographql.apollo.ApolloClient
import com.idemia.wa.graphql.ContinentFetchingQuery
import com.idemia.wa.graphql.FetchContinentDetailsQuery
import com.idemia.wa.graphql.domain.repository.CountryRepo

class CountryRepoImpl(private val apolloClient: ApolloClient) : CountryRepo {
    override suspend fun getContinents(): Result<ContinentFetchingQuery.Data> {
        return try {
            val response = apolloClient.query(ContinentFetchingQuery()).execute()
            response.data?.let {
                Result.success(it)
            } ?: run {
                Result.failure(response.exception!!)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getContinentDetails(code: String): Result<FetchContinentDetailsQuery.Data> {
        return try {
            val response = apolloClient.query(FetchContinentDetailsQuery(code)).execute()
            response.data?.let {
                Result.success(it)
            } ?: run {
                Result.failure(response.exception!!)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}