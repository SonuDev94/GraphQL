package com.idemia.wa.graphql.data.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import com.idemia.wa.graphql.data.repository.CountryRepoImpl
import com.idemia.wa.graphql.domain.repository.CountryRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun provideApolloClient(): ApolloClient {
        val okHttpClient = OkHttpClient.Builder().build()
        val client = ApolloClient.Builder()
            .serverUrl("https://countries.trevorblades.com/")
            .okHttpClient(okHttpClient)
            .build()
        return client
    }

    @Provides
    fun provideCountryRepo(apolloCLinet: ApolloClient): CountryRepo {
        return CountryRepoImpl(apolloCLinet)
    }
}