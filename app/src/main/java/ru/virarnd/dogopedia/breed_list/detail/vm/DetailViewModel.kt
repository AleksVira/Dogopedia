package ru.virarnd.dogopedia.breed_list.detail.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import ru.virarnd.dogopedia.common.ConsumableValue
import ru.virarnd.dogopedia.favourites.main.data.FavouriteDataItem
import ru.virarnd.dogopedia.models.BreedDataItem
import ru.virarnd.dogopedia.models.DetailPageItem
import ru.virarnd.dogopedia.repository.BreedsRepository
import ru.virarnd.dogopedia.repository.network.helpers.LoadingState
import timber.log.Timber

class DetailViewModel(
    private val repository: BreedsRepository
) : ViewModel() {

    private val _breedDetailPageLiveData: MutableLiveData<ConsumableValue<List<DetailPageItem>>> =
        MutableLiveData()
    val breedDetailPageLiveData: LiveData<ConsumableValue<List<DetailPageItem>>> =
        _breedDetailPageLiveData

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState> = _loadingState

    private val _uriRequest = MutableLiveData<ConsumableValue<Boolean>>()
    val uriRequest: LiveData<ConsumableValue<Boolean>> = _uriRequest

    private var currentBreedDataItem: BreedDataItem? = null
    private lateinit var currentDataItem: FavouriteDataItem
    private lateinit var detailPageItemsList: MutableList<DetailPageItem>

    fun fetchSingleBreedData(breedName: String) {
        viewModelScope.launch(IO) {
            try {
                _loadingState.postValue(LoadingState.LOADING)
                currentBreedDataItem = repository.getSingleBreedData(breedName)
                currentDataItem = repository.getSingleBreedFavourites(breedName)
                detailPageItemsList =
                    currentBreedDataItem?.pictures?.mapIndexed { index, url ->
                        DetailPageItem(
                            index.toLong(),
                            url,
                            currentDataItem.pictures.contains(url)
                        )
                    }?.toMutableList() ?: mutableListOf()
                _breedDetailPageLiveData.postValue(
                    ConsumableValue(
                        detailPageItemsList
                    )
                )
                _loadingState.postValue(LoadingState.LOADED)
            } catch (e: Exception) {
                Timber.e("MyLog_DetailViewModel_fetchSingleBreedData: ${e.message}")
                _loadingState.postValue(LoadingState.error(e.message))
            }
        }
    }

    fun fetchSubBreedData(parentName: String, subName: String) {
        viewModelScope.launch(IO) {
            try {
                _loadingState.postValue(LoadingState.LOADING)
                currentBreedDataItem = repository.getSubBreedData(parentName, subName)
                currentDataItem = repository.getSubBreedFavourites(parentName, subName)
                detailPageItemsList =
                    currentBreedDataItem?.pictures?.mapIndexed { index, url ->
                        DetailPageItem(
                            index.toLong(),
                            url,
                            currentDataItem.pictures.contains(url)
                        )
                    }?.toMutableList() ?: mutableListOf()
                _breedDetailPageLiveData.postValue(
                    ConsumableValue(
                        detailPageItemsList
                    )
                )
                _loadingState.postValue(LoadingState.LOADED)
            } catch (e: Exception) {
                Timber.e("MyLog_DetailViewModel_fetchSubBreedData: ${e.message}")
                _loadingState.postValue(LoadingState.error(e.message))
            }
        }
    }

    fun deleteFromFavourites(detailPageItem: DetailPageItem) {
        Timber.d("MyLog_DetailViewModel_deleteFromFavourites: $currentDataItem, ${detailPageItem.pictureUrl}")
        // Сначала инверсия поля "isFavourite"
        detailPageItemsList[detailPageItem.id.toInt()] =
            DetailPageItem(
                detailPageItem.id,
                detailPageItem.pictureUrl,
                !detailPageItem.isFavourite
            )

        val urlToWork = detailPageItem.pictureUrl

        // Если будем добавлять в Избранное, то...
        if (!detailPageItem.isFavourite) {
            currentDataItem.pictures.add(urlToWork)
            viewModelScope.launch(IO) {
                repository.updateFavourites(currentDataItem)
                _breedDetailPageLiveData.postValue(
                    ConsumableValue(
                        detailPageItemsList
                    )
                )
            }
            // Иначе будем удалять из Избранного
        } else {
            currentDataItem.pictures.remove(urlToWork)
            viewModelScope.launch(IO) {
                repository.updateFavourites(currentDataItem)
                _breedDetailPageLiveData.postValue(
                    ConsumableValue(
                        detailPageItemsList
                    )
                )
            }
        }

    }

    fun askFileFromGlide() {
        _uriRequest.postValue(ConsumableValue(true))
    }

    fun favouriteIconClicked(pageItem: DetailPageItem) {
        deleteFromFavourites(pageItem)
    }
}