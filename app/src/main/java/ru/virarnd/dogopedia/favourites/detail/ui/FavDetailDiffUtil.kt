package ru.virarnd.dogopedia.favourites.detail.ui

import androidx.recyclerview.widget.DiffUtil
import ru.virarnd.dogopedia.models.DetailPageItem


class FavDetailDiffUtil(
    private val oldList: List<DetailPageItem>,
    private val newList: List<DetailPageItem>
) : DiffUtil.Callback() {

    enum class PayloadKey {
        VALUE
    }

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldList[oldItemPosition].id == newList[newItemPosition].id &&
                oldList[oldItemPosition].isFavourite == newList[newItemPosition].isFavourite)
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return listOf(PayloadKey.VALUE)
    }
}