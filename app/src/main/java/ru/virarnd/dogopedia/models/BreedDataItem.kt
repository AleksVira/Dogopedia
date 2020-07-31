package ru.virarnd.dogopedia.models

import androidx.annotation.Keep
import java.io.Serializable

@Keep
sealed class BreedDataItem(val name: String, val pictures: ArrayList<String>) : Serializable

@Keep
data class SingleBreedDataItem(
    val singleName: String,
    val singlePictures: ArrayList<String>
) : BreedDataItem(singleName, singlePictures)

@Keep
data class SubBreedDataItem(
    val parentName: String,
    val subName: String,
    val subPictures: ArrayList<String>
) : BreedDataItem("$parentName $subName", subPictures)