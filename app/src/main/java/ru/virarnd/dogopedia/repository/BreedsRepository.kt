package ru.virarnd.dogopedia.repository

import ru.virarnd.dogopedia.models.BreedDataItem
import ru.virarnd.dogopedia.models.BreedListItem
import ru.virarnd.dogopedia.models.SubBreedDataItem
import ru.virarnd.dogopedia.favourites.main.data.FavouriteDataItem
import ru.virarnd.dogopedia.repository.database.BreedsDatabase
import ru.virarnd.dogopedia.repository.database.BreedsDatabaseConverter
import ru.virarnd.dogopedia.repository.network.BreedsNetwork
import ru.virarnd.dogopedia.repository.network.helpers.RequestResult
import timber.log.Timber

class BreedsRepository(
    private val breedsNetwork: BreedsNetwork,
    private val breedsDatabase: BreedsDatabase,
    private val breedsDatabaseConverter: BreedsDatabaseConverter

) {


    suspend fun getBreedsList(): List<BreedListItem> {
        return try {
            val networkResult = breedsNetwork.getAllBreedsList()
            when (networkResult) {
                is RequestResult.Success -> {
                    networkResult.data
                }
                is RequestResult.Error -> {
                    emptyList()
                }
            }
        } catch (e: Exception) {
            Timber.d("MyLog_BreedsRepository_getBreedsList: $e")
            emptyList()
        }
    }


    suspend fun getSingleBreedData(breedName: String): BreedDataItem? {
        return try {
            val singleBreedNetworkResult = breedsNetwork.getOneBreed(breedName)
            when (singleBreedNetworkResult) {
                is RequestResult.Success -> {
                    breedsDatabase.singleBreedsDao.addSingleBreed(
                        breedsDatabaseConverter.toSingleBreedEntity(
                            singleBreedNetworkResult.data
                        )
                    )
                    singleBreedNetworkResult.data
                }
                is RequestResult.Error -> null
            }
        } catch (e: Exception) {
            Timber.d("MyLog_BreedsRepository_getOneBreedData: $e")
            null
        }
    }

    suspend fun getSubBreedData(parentName: String, subBreedName: String): SubBreedDataItem? {
        // TODO Сделать сначала запрос в БД, потом в сеть, потом обновить данные если будет разница
//        val subBreedFromDb = breedsDatabase.subBreedsDao.getSubBreedByName(subBreedName)
//        Timber.d("MyLog_BreedsRepository_getSubBreedData: $subBreedFromDb")
//        if (subBreedFromDb != null) {
//            return (breedsDatabaseConverter.toSubBreedData(subBreedFromDb))
//        }


        return try {
            val subBreedNetworkResult = breedsNetwork.getSubBreed(parentName, subBreedName)
            when (subBreedNetworkResult) {
                is RequestResult.Success -> {
                    breedsDatabase.subBreedsDao.addSubBreed(
                        breedsDatabaseConverter.toSubBreedEntity(
                            subBreedNetworkResult.data
                        )
                    )


                    subBreedNetworkResult.data
                }
                is RequestResult.Error -> null
            }
        } catch (e: Exception) {
            Timber.d("MyLog_BreedsRepository_getSubBreedData: $e")
            null
        }
    }

    suspend fun getSingleBreedFavourites(singleBreedName: String): FavouriteDataItem {
        val favDbResult =
            breedsDatabase.favouritePicturesDao.getSingleBreedFavouritesByName(singleBreedName)
        return when {
            favDbResult != null -> breedsDatabaseConverter.toFavouriteDataItem(favDbResult)
            else -> FavouriteDataItem(singleBreedName, "", mutableListOf())
        }
    }

    suspend fun getSubBreedFavourites(parentName: String, subName: String): FavouriteDataItem {
        val favDbResult =
            breedsDatabase.favouritePicturesDao.getSubBreedFavouritesByName(subName)
        return when {
            favDbResult != null -> breedsDatabaseConverter.toFavouriteDataItem(favDbResult)
            else -> FavouriteDataItem(parentName, subName, mutableListOf())
        }
    }

    suspend fun updateFavourites(currentFavoritesDataItem: FavouriteDataItem) {
        breedsDatabase.favouritePicturesDao.addOrUpdateFavourites(breedsDatabaseConverter.toFavouriteEntity(currentFavoritesDataItem))
    }

    suspend fun getFavouriteList(): ArrayList<FavouriteDataItem> {
        val allFavourite = breedsDatabase.favouritePicturesDao.favouriteEntityList()
        return when {
            allFavourite != null -> breedsDatabaseConverter.toFavouriteDataItemList(allFavourite)
            else -> arrayListOf()
        }
    }

    suspend fun deleteFavouriteBreed(favouriteItem: FavouriteDataItem) {
        breedsDatabase.favouritePicturesDao.deleteBreedFromFavourite(breedsDatabaseConverter.toFavouriteEntity(favouriteItem))
    }


}