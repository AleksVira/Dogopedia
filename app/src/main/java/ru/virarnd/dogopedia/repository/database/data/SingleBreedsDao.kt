package ru.virarnd.dogopedia.repository.database.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.virarnd.dogopedia.repository.database.entities.SingleBreedEntity

@Dao
abstract class SingleBreedsDao {

    @Query("SELECT * FROM single_breeds")
    abstract suspend fun singleBreedEntityList(): List<SingleBreedEntity>

    @Query("SELECT * FROM single_breeds WHERE name = :name")
    abstract suspend fun getSingleBreedByName(name: String): SingleBreedEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addSingleBreed(subBreedEntity: SingleBreedEntity)
}