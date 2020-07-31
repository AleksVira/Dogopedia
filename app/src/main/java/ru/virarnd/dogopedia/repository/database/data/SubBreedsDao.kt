package ru.virarnd.dogopedia.repository.database.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.virarnd.dogopedia.repository.database.entities.SubBreedEntity

@Dao
abstract class SubBreedsDao {

    @Query("SELECT * FROM sub_breeds")
    abstract suspend fun entitySubBreedList(): List<SubBreedEntity>?

    @Query("SELECT * FROM sub_breeds WHERE name = :name")
    abstract suspend fun getSubBreedByName(name: String): SubBreedEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addSubBreed(subBreedEntity: SubBreedEntity)
}