package ru.virarnd.dogopedia.repository.database.data

import androidx.room.*
import ru.virarnd.dogopedia.favourites.main.data.FavouriteDataItem
import ru.virarnd.dogopedia.repository.database.entities.FavouriteEntity
import ru.virarnd.dogopedia.repository.database.entities.SingleBreedEntity
import ru.virarnd.dogopedia.repository.database.entities.SubBreedEntity

@Dao
abstract class FavouritePicturesDao {

    @Query("SELECT * FROM favourite_pictures")
    abstract suspend fun favouriteEntityList(): List<FavouriteEntity>?

    @Query("SELECT * FROM favourite_pictures WHERE single_or_parent_name = :name")
    abstract suspend fun getSingleBreedFavouritesByName(name: String): FavouriteEntity?

    @Query("SELECT * FROM favourite_pictures WHERE sub_name = :name")
    abstract suspend fun getSubBreedFavouritesByName(name: String): FavouriteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addOrUpdateFavourites(favouriteEntity: FavouriteEntity): Long

    @Delete
    abstract suspend fun deleteBreedFromFavourite(favouriteEntity: FavouriteEntity)

}