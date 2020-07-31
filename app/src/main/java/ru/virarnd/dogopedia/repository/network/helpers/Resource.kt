package ru.virarnd.dogopedia.repository.network.helpers

sealed class Resource<out T: Any>{

    data class Loading(val isLoading: Boolean): Resource<Nothing>()

    data class Success<out T: Any>(val data: T) : Resource<T>()

    data class Error(val msg: String) : Resource<Nothing>()

}
