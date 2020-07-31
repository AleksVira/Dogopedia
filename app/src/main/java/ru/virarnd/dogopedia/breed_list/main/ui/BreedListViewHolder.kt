package ru.virarnd.dogopedia.breed_list.main.ui

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.virarnd.dogopedia.models.BreedListItem
import ru.virarnd.dogopedia.models.BreedSetListItem
import ru.virarnd.dogopedia.models.BreedSingleListItem
import ru.virarnd.dogopedia.models.SubBreedListItem
import ru.virarnd.dogopedia.databinding.ItemBreedBinding
import ru.virarnd.dogopedia.common.extensions.setOnSingleClick

class BreedListViewHolder(
    private val binding: ItemBreedBinding,
    private val onClickBreedListener: (breedListItem: BreedListItem) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(breedListItem: BreedListItem) {
        binding.root.setOnSingleClick { onClickBreedListener.invoke(breedListItem) }
        when (breedListItem) {
            is BreedSetListItem -> {
                binding.breedHeader.text = breedListItem.parentName
                with(binding.subBreedsCounterField) {
                    visibility = View.VISIBLE
                    text = "(${breedListItem.subBreedList.size} subbreeds)"
                }
            }
            is BreedSingleListItem -> {
                binding.breedHeader.text = breedListItem.singleBreedName
                binding.subBreedsCounterField.visibility = View.GONE
            }
            is SubBreedListItem -> {
                binding.breedHeader.text = "${breedListItem.parentName} ${breedListItem.subBreedName}"
                binding.subBreedsCounterField.visibility = View.GONE
            }
        }

    }
}