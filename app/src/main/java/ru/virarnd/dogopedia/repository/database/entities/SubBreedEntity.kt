package ru.virarnd.dogopedia.repository.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.virarnd.dogopedia.repository.database.data.BreedTypeConverter

@TypeConverters(BreedTypeConverter::class)
@Entity(tableName = "sub_breeds")
data class SubBreedEntity(
    @PrimaryKey
    val name: String,
    @ColumnInfo(name = "parent_name") val parentName: String,
    val pictures: List<String>
)