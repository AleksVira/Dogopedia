package ru.virarnd.dogopedia.repository.network.helpers

data class LoadingState(val status: Status, val msg: String? = null) {
    companion object {
        val LOADED =
            LoadingState(
                Status.SUCCESS
            )
        val LOADING =
            LoadingState(
                Status.RUNNING
            )
        fun error(msg: String?) =
            LoadingState(
                Status.FAILED,
                msg
            )
    }

    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED
    }
}