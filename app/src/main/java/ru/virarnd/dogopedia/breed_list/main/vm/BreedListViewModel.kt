package ru.virarnd.dogopedia.breed_list.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.virarnd.dogopedia.models.BreedListItem
import ru.virarnd.dogopedia.repository.network.helpers.LoadingState
import ru.virarnd.dogopedia.repository.network.helpers.LoadingState.Companion.LOADED
import ru.virarnd.dogopedia.repository.network.helpers.LoadingState.Companion.LOADING
import ru.virarnd.dogopedia.repository.BreedsRepository

class BreedListViewModel(
    private val repository: BreedsRepository
) : ViewModel() {

    private val _breedsList: MutableLiveData<List<BreedListItem>> = MutableLiveData()
    val breedsList: LiveData<List<BreedListItem>> = _breedsList

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState> = _loadingState

    private val _errorState = MutableLiveData<Boolean>()
    val errorState: LiveData<Boolean> = _errorState

    fun getAllBreeds() {
        viewModelScope.launch {
            try {
                _loadingState.value = LOADING
                val answer = repository.getBreedsList()
                _loadingState.value = LOADED
                _breedsList.postValue(answer)
            } catch (e: Exception) {
                _loadingState.value = LoadingState.error(e.message)
                _errorState.value = true
            }
        }
    }
}