package ru.virarnd.dogopedia.repository.network

import ru.virarnd.dogopedia.models.BreedListItem
import ru.virarnd.dogopedia.models.BreedSetListItem
import ru.virarnd.dogopedia.models.BreedSingleListItem
import ru.virarnd.dogopedia.models.SubBreedListItem

class NetworkDataConverter {

    fun toScreenBreedList(message: Map<String, List<String>>?): List<BreedListItem> {
        val result: ArrayList<BreedListItem> = arrayListOf()
        message?.forEach { (name, subBreeds) ->
            // Если массив пустой -- у нас просто порода, без подпород
            if (subBreeds.size == 0) {
                result.add(BreedSingleListItem(name.capitalize()))
            } else {
                val subBreedList: ArrayList<SubBreedListItem> = arrayListOf()
                subBreeds.forEach { subName ->
                    subBreedList.add(
                        SubBreedListItem(
                            name.capitalize(),
                            subName.capitalize()
                        )
                    )
                }
                result.add(
                    BreedSetListItem(
                        name.capitalize(),
                        subBreedList
                    )
                )
            }
        }
        return result
    }

}
