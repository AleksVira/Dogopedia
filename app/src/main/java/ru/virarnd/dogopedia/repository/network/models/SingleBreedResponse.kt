package ru.virarnd.dogopedia.repository.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SingleBreedResponse(
    val message: List<String>?,
    val status: String?
)