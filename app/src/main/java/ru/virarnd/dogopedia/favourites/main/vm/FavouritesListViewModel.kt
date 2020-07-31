package ru.virarnd.dogopedia.favourites.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.virarnd.dogopedia.common.ConsumableValue
import ru.virarnd.dogopedia.favourites.main.data.FavouriteDataItem
import ru.virarnd.dogopedia.repository.BreedsRepository
import ru.virarnd.dogopedia.repository.network.helpers.LoadingState
import timber.log.Timber

class FavouritesListViewModel(
    private val repository: BreedsRepository
) : ViewModel() {

    private val _favouritesListLiveData: MutableLiveData<ConsumableValue<List<FavouriteDataItem>>> =
        MutableLiveData()
    val favouritesListLiveData: LiveData<ConsumableValue<List<FavouriteDataItem>>> =
        _favouritesListLiveData

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState> = _loadingState


    fun getFavourites() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loadingState.postValue(LoadingState.LOADING)
                val favouriteList = repository.getFavouriteList()
                _favouritesListLiveData.postValue(ConsumableValue(favouriteList))
                _loadingState.postValue(LoadingState.LOADED)
            } catch (e: Exception) {
                Timber.d("MyLog_FavouritesListViewModel_getFavourites: ${e.message}")
                _loadingState.postValue(LoadingState.error(e.message))
            }
        }
    }


}
