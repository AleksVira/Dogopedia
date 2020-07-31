package ru.virarnd.dogopedia.repository.database

import ru.virarnd.dogopedia.models.SingleBreedDataItem
import ru.virarnd.dogopedia.models.SubBreedDataItem
import ru.virarnd.dogopedia.favourites.main.data.FavouriteDataItem
import ru.virarnd.dogopedia.repository.database.entities.SingleBreedEntity
import ru.virarnd.dogopedia.repository.database.entities.FavouriteEntity
import ru.virarnd.dogopedia.repository.database.entities.SubBreedEntity

class BreedsDatabaseConverter {

    fun toSubBreedEntity(subBreedDataItem: SubBreedDataItem): SubBreedEntity {
        return SubBreedEntity(
            name = subBreedDataItem.subName,
            parentName = subBreedDataItem.parentName,
            pictures = subBreedDataItem.subPictures
        )
    }

    fun toSubBreedData(subBreedFromDb: SubBreedEntity): SubBreedDataItem {
        return SubBreedDataItem(
            subName = subBreedFromDb.name,
            parentName = subBreedFromDb.parentName,
            subPictures = ArrayList(subBreedFromDb.pictures)
        )
    }


    fun toSingleBreedEntity(singleBreedDataItem: SingleBreedDataItem): SingleBreedEntity {
        return SingleBreedEntity(
            name = singleBreedDataItem.name,
            pictures = singleBreedDataItem.singlePictures
        )
    }

    fun toFavouriteDataItem(favDbResult: FavouriteEntity): FavouriteDataItem {
        return FavouriteDataItem(
            singleOrParentName = favDbResult.singleOrParentName,
            subName = "",
            pictures = favDbResult.pictures.toMutableList()
        )
    }

    fun toFavouriteEntity(favoritesDataItem: FavouriteDataItem): FavouriteEntity {
        return FavouriteEntity(
            singleOrParentName = favoritesDataItem.singleOrParentName,
            subName = favoritesDataItem.subName,
            pictures = favoritesDataItem.pictures
        )
    }

    fun toFavouriteDataItemList(allFavourite: List<FavouriteEntity>): ArrayList<FavouriteDataItem> {
        val result = allFavourite.map {
            FavouriteDataItem(
                singleOrParentName = it.singleOrParentName,
                subName = it.subName,
                pictures = it.pictures.toMutableList()
            )
        }.sortedWith(compareBy {it.singleOrParentName})

//        val newResult = result.sortedWith(compareBy {it.singleOrParentName})

        return ArrayList(result)
    }


}
