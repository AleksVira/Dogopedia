package ru.virarnd.dogopedia.repository.network

import com.squareup.moshi.Moshi
import ru.virarnd.dogopedia.models.BreedListItem
import ru.virarnd.dogopedia.models.SingleBreedDataItem
import ru.virarnd.dogopedia.models.SubBreedDataItem
import ru.virarnd.dogopedia.repository.network.helpers.Result
import timber.log.Timber


class BreedsNetwork(
    private val breedsApi: BreedsApi,
    private val networkDataConverter: NetworkDataConverter,
    private val responseHandler: ResponseHandler
) {

    suspend fun getAllBreedsList(): Result<List<BreedListItem>> {
        return try {
            val response = breedsApi.getAllBreeds()
            val message = response.message
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(Any::class.java)
            val jsonStructure = adapter.toJsonValue(message)
            val jsonObject = jsonStructure as Map<String, ArrayList<String>>?
            val result = networkDataConverter.toScreenBreedList(jsonObject)
            responseHandler.handleSuccess(result)
        } catch (e: Exception) {
            Timber.e("MyLog_BreedsNetwork_getAllBreedsList: $e")
            responseHandler.handleException(e)
        }
    }

    suspend fun getOneBreed(breedName: String): Result<SingleBreedDataItem> {
        return try {
            val singleResponse = breedsApi.getBreedImagesByName(breedName.toLowerCase())
            Timber.d("MyLog_BreedsNetwork_getOneBreed: ${singleResponse.message}")
            val breedResult = SingleBreedDataItem(
                breedName,
                ArrayList(singleResponse.message!!)
            )
            responseHandler.handleSuccess(breedResult)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun getSubBreed(parentName: String, subBreedName: String): Result<SubBreedDataItem> {
        return try {
            val subBreedResponse = breedsApi.getSubBreedImagesByName(parentName.toLowerCase(), subBreedName.toLowerCase())
            Timber.d("MyLog_BreedsNetwork_getOneBreed: ${subBreedResponse.message}")
            val breedResult = SubBreedDataItem(
                parentName,
                subBreedName,
                ArrayList(subBreedResponse.message!!)
            )
            responseHandler.handleSuccess(breedResult)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }


}