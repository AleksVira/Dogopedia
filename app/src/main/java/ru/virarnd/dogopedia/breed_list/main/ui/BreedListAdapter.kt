package ru.virarnd.dogopedia.breed_list.main.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.virarnd.dogopedia.models.BreedListItem
import ru.virarnd.dogopedia.databinding.ItemBreedBinding

class BreedListAdapter(
    private val clickListener: (BreedListItem) -> Unit
): RecyclerView.Adapter<BreedListViewHolder>() {

    private var breeds: List<BreedListItem> = emptyList()

    fun setData(breedsList: List<BreedListItem>){
        this.breeds = breedsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedListViewHolder {
        val binding = ItemBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreedListViewHolder(binding, clickListener)
    }

    override fun getItemCount() = breeds.size

    override fun onBindViewHolder(holder: BreedListViewHolder, position: Int) {
        holder.bind(breeds[position])
    }

}
