package com.idemia.wa.graphql.domain.repository

import com.idemia.wa.graphql.ContinentFetchingQuery
import com.idemia.wa.graphql.FetchContinentDetailsQuery

interface CountryRepo {
    suspend fun getContinents(): Result<ContinentFetchingQuery.Data>

    suspend fun getContinentDetails(code: String) : Result<FetchContinentDetailsQuery.Data>
}