package ru.virarnd.dogopedia.repository.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.virarnd.dogopedia.repository.database.data.BreedTypeConverter

@TypeConverters(BreedTypeConverter::class)
@Entity(primaryKeys = ["single_or_parent_name", "sub_name"], tableName = "favourite_pictures")
data class FavouriteEntity(
    @ColumnInfo(name = "single_or_parent_name") val singleOrParentName: String,
    @ColumnInfo(name = "sub_name") val subName: String = "",
    val pictures: List<String>
)