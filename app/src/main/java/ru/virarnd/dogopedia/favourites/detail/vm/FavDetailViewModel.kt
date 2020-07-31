package ru.virarnd.dogopedia.favourites.detail.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import ru.virarnd.dogopedia.common.ConsumableValue
import ru.virarnd.dogopedia.favourites.main.data.FavouriteDataItem
import ru.virarnd.dogopedia.models.DetailPageItem
import ru.virarnd.dogopedia.repository.BreedsRepository

class FavDetailViewModel(
    private val repository: BreedsRepository
) : ViewModel() {

    private val _favDetailPageLiveData: MutableLiveData<List<DetailPageItem>> = MutableLiveData()
    val favDetailPageLiveData: LiveData<List<DetailPageItem>> = _favDetailPageLiveData

    private val _favouriteItemDeleted: MutableLiveData<ConsumableValue<Boolean>> = MutableLiveData()
    val favouriteItemDeleted: LiveData<ConsumableValue<Boolean>> = _favouriteItemDeleted

    private val _uriRequest = MutableLiveData<ConsumableValue<Boolean>>()
    val uriRequest: LiveData<ConsumableValue<Boolean>> = _uriRequest

    private lateinit var currentDataItem: FavouriteDataItem

    fun setCurrentFavouriteItem(favouriteItem: FavouriteDataItem) {
        this.currentDataItem = favouriteItem
    }

    private fun deleteFromFavourites(favouriteItem: FavouriteDataItem, pictureUrl: String) {
        viewModelScope.launch(IO) {
            if (favouriteItem.pictures.size > 1) {
                val newPicturesUrls =
                    favouriteItem.pictures.filterNot { string -> string == pictureUrl }
                val newFavItem = favouriteItem.copy(pictures = ArrayList(newPicturesUrls))
                currentDataItem = newFavItem
                repository.updateFavourites(newFavItem)
                _favDetailPageLiveData.postValue(convertToDetailPageItemsList(newFavItem))
            } else {
                repository.deleteFavouriteBreed(favouriteItem)
                _favouriteItemDeleted.postValue(
                    ConsumableValue(
                        true
                    )
                )
            }
        }
    }

    fun getDetailPageItems(favouriteItem: FavouriteDataItem) {
        val newList =
            convertToDetailPageItemsList(favouriteItem)
        _favDetailPageLiveData.value = ArrayList(newList)
    }

    private fun convertToDetailPageItemsList(favouriteItem: FavouriteDataItem): List<DetailPageItem> {
        return favouriteItem.pictures.map { url ->
            DetailPageItem(url.hashCode().toLong(), url, true)
        }
    }

    fun askFileFromGlide() {
        _uriRequest.postValue(ConsumableValue(true))

    }

    fun favouriteIconClicked(pictureUrl: String) {
        deleteFromFavourites(currentDataItem, pictureUrl)
    }

}