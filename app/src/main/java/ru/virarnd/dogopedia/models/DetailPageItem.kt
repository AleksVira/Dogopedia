package ru.virarnd.dogopedia.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
data class DetailPageItem(
    var id: Long,
    val pictureUrl: String,
    var isFavourite: Boolean

) : Parcelable