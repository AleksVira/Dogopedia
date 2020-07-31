package ru.virarnd.dogopedia.favourites.main.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.virarnd.dogopedia.repository.database.data.BreedTypeConverter
import java.io.Serializable

@Keep
data class FavouriteDataItem(
    val singleOrParentName: String,
    val subName: String = "",
    var pictures: MutableList<String>
) : Serializable