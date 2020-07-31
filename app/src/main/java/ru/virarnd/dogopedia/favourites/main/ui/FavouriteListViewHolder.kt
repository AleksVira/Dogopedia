package ru.virarnd.dogopedia.favourites.main.ui

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.virarnd.dogopedia.databinding.ItemBreedBinding
import ru.virarnd.dogopedia.common.extensions.setOnSingleClick
import ru.virarnd.dogopedia.favourites.main.data.FavouriteDataItem

class FavouriteListViewHolder(
    private val binding: ItemBreedBinding,
    private val onClickFavouriteListener: (favouriteDataItem: FavouriteDataItem) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(favouriteDataItem: FavouriteDataItem) {
        binding.root.setOnSingleClick { onClickFavouriteListener.invoke(favouriteDataItem) }
        binding.breedHeader.text =
            "${favouriteDataItem.singleOrParentName} ${favouriteDataItem.subName}"

        with(binding.subBreedsCounterField) {
            visibility = View.VISIBLE
            text = "(${favouriteDataItem.pictures.size} photos)"
        }
    }
}
