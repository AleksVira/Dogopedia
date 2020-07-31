package ru.virarnd.dogopedia.breed_list.sub.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.virarnd.dogopedia.MainActivity
import ru.virarnd.dogopedia.breed_list.main.ui.BreedListAdapter
import ru.virarnd.dogopedia.models.BreedListItem
import ru.virarnd.dogopedia.models.SubBreedListItem
import ru.virarnd.dogopedia.databinding.FragmentBreedsListBinding
import ru.virarnd.dogopedia.repository.network.helpers.LoadingState
import timber.log.Timber

class SubBreedsListFragment : Fragment() {

    private val args: SubBreedsListFragmentArgs by navArgs()

    private var _binding: FragmentBreedsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var breedListAdapter: BreedListAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBreedsListBinding.inflate(inflater, container, false)

        breedListAdapter = BreedListAdapter { item: BreedListItem -> subBreedNameClicked(item as SubBreedListItem) }

        with(binding.mainList) {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = breedListAdapter
        }

        return binding.root
    }

    private fun subBreedNameClicked(item: SubBreedListItem) {
        Timber.d("MyLog_BreedsListFragment_breedNameClicked: CLICKED: $item")
        val action = SubBreedsListFragmentDirections.actionSubBreedsListFragmentToDetailFragment(item.subBreedName, true, item.parentName)
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setTitleText(args.breedsSet.parentName)

        breedListAdapter.setData(args.breedsSet.subBreedList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}