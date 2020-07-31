package ru.virarnd.dogopedia.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.virarnd.dogopedia.repository.database.data.FavouritePicturesDao
import ru.virarnd.dogopedia.repository.database.data.SingleBreedsDao
import ru.virarnd.dogopedia.repository.database.entities.SubBreedEntity
import ru.virarnd.dogopedia.repository.database.data.SubBreedsDao
import ru.virarnd.dogopedia.repository.database.entities.FavouriteEntity
import ru.virarnd.dogopedia.repository.database.entities.SingleBreedEntity


@Database(
    entities = [SubBreedEntity::class, SingleBreedEntity::class, FavouriteEntity::class], version = 1, exportSchema = false
)
abstract class BreedsDatabase: RoomDatabase() {
    abstract val subBreedsDao: SubBreedsDao
    abstract val singleBreedsDao: SingleBreedsDao
    abstract val favouritePicturesDao: FavouritePicturesDao

}