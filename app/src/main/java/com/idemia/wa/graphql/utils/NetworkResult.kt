package com.idemia.wa.graphql.utils

sealed class NetworkResult<T>(val data: T? = null, val message: String = ""){
    data class Success<T>(val mdata: T?) : NetworkResult<T>(mdata)
    data class Error<T>(val mmessage: String) : NetworkResult<T>(message = mmessage)
    class Loading<T>() : NetworkResult<T>()
}
