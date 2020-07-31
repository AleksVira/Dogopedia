package ru.virarnd.dogopedia.breed_list.detail.ui

import androidx.recyclerview.widget.DiffUtil
import ru.virarnd.dogopedia.models.DetailPageItem


class DetailDiffUtil(
    private val oldList: List<DetailPageItem>,
    private val newList: List<DetailPageItem>
) : DiffUtil.Callback() {

    enum class PayloadKey {
        VALUE
    }

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].pictureUrl == newList[newItemPosition].pictureUrl
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldList[oldItemPosition].pictureUrl == newList[newItemPosition].pictureUrl &&
                oldList[oldItemPosition].isFavourite == newList[newItemPosition].isFavourite)
    }


/*
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        val diff = Bundle()
        if (newItem.pictureUrl != oldItem.pictureUrl) {
            diff.putString("url", newItem.pictureUrl)
        }
        if (newItem.isFavourite != oldItem.isFavourite) {
            diff.putBoolean("favourite", newItem.isFavourite)
        }
        return if (diff.size() == 0) {
            null
        } else diff
    }
*/

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return listOf(PayloadKey.VALUE)
    }
}