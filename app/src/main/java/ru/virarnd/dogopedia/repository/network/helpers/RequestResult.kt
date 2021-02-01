package ru.virarnd.dogopedia.repository.network.helpers

sealed class RequestResult<out T : Any>{

    data class Success<out T : Any>(val data : T) : RequestResult<T>()

    data class Error(val msg: String)  : RequestResult<Nothing>()
}