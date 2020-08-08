package ru.virarnd.dogopedia.repository.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AllBreedsResponse(
    val message: Map<String, List<String>?>,
    val status: String
)
