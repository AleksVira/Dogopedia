package ru.virarnd.dogopedia.repository.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.virarnd.dogopedia.repository.database.data.BreedTypeConverter

@TypeConverters(BreedTypeConverter::class)
@Entity(tableName = "single_breeds")
data class SingleBreedEntity(
    @PrimaryKey
    val name: String,
    val pictures: List<String>
)