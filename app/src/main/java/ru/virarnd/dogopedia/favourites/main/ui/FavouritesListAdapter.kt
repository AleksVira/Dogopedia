package ru.virarnd.dogopedia.favourites.main.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.virarnd.dogopedia.databinding.ItemBreedBinding
import ru.virarnd.dogopedia.favourites.main.data.FavouriteDataItem

class FavouritesListAdapter(
    private val isFavouriteListener: (FavouriteDataItem) -> Unit
) : RecyclerView.Adapter<FavouriteListViewHolder>() {

    private var favouriteBreedItem: List<FavouriteDataItem> = emptyList()

    fun setData(breedsList: List<FavouriteDataItem>) {
        this.favouriteBreedItem = breedsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteListViewHolder {
        val binding = ItemBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouriteListViewHolder(binding, isFavouriteListener)
    }

    override fun getItemCount() = favouriteBreedItem.size

    override fun onBindViewHolder(holder: FavouriteListViewHolder, position: Int) {
        holder.bind(favouriteBreedItem[position])
    }

}
