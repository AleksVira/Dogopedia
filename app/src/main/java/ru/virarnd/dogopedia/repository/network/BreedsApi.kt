package ru.virarnd.dogopedia.repository.network

import retrofit2.http.GET
import retrofit2.http.Path
import ru.virarnd.dogopedia.repository.network.models.AllBreedsResponse
import ru.virarnd.dogopedia.repository.network.models.SingleBreedResponse
import ru.virarnd.dogopedia.repository.network.models.SubBreedResponse

interface BreedsApi {

    @GET(ALL_BREEDS)
    suspend fun getAllBreeds(): AllBreedsResponse

    @GET(SINGLE_BREED_BY_NAME)
    suspend fun getBreedImagesByName(@Path("name") name: String): SingleBreedResponse

    @GET(SUB_BREED_BY_NAME)
    suspend fun getSubBreedImagesByName(
        @Path("parentName") parentName: String,
        @Path("subBreedName") subBreedName: String
    ): SubBreedResponse

}