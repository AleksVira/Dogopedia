package ru.virarnd.dogopedia.models

import androidx.annotation.Keep
import java.io.Serializable

@Keep
sealed class BreedListItem(val name: String): Serializable

@Keep
data class BreedSingleListItem(val singleBreedName: String) : BreedListItem(singleBreedName)

@Keep
data class SubBreedListItem(val parentName: String, val subBreedName: String) : BreedListItem(parentName)

@Keep
data class BreedSetListItem(
    val parentName: String,
    val subBreedList: List<SubBreedListItem>
): BreedListItem(parentName)

