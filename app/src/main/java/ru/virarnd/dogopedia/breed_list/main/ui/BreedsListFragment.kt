package ru.virarnd.dogopedia.breed_list.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.virarnd.dogopedia.MainActivity
import ru.virarnd.dogopedia.R
import ru.virarnd.dogopedia.breed_list.main.vm.BreedListViewModel
import ru.virarnd.dogopedia.common.extensions.setDivider
import ru.virarnd.dogopedia.databinding.FragmentBreedsListBinding
import ru.virarnd.dogopedia.models.BreedListItem
import ru.virarnd.dogopedia.models.BreedSetListItem
import ru.virarnd.dogopedia.models.BreedSingleListItem
import ru.virarnd.dogopedia.models.SubBreedListItem
import ru.virarnd.dogopedia.repository.network.helpers.LoadingState
import timber.log.Timber

class BreedsListFragment : Fragment() {

    private var _binding: FragmentBreedsListBinding? = null
    private val binding get() = _binding!!

    private val breedListViewModel: BreedListViewModel by viewModel()

    private val breedListAdapter = BreedListAdapter { item: BreedListItem -> breedNameClicked(item) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBreedsListBinding.inflate(inflater, container, false)

        with(binding.mainList) {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = breedListAdapter
            setDivider(R.drawable.horizontal_divider)
        }
        return binding.root
    }

    private fun breedNameClicked(item: BreedListItem) {
        val action: NavDirections
        when (item) {
            is BreedSingleListItem -> {
                Timber.d("MyLog_BreedsListFragment_breedNameClicked: CLICKED: ${item.singleBreedName}")
                action = BreedsListFragmentDirections.actionBreedsListFragmentToDetailFragment(
                    item.singleBreedName,
                    false
                )
                findNavController().navigate(action)
            }
            is SubBreedListItem -> {
                Timber.e("MyLog_BreedsListFragment_breedNameClicked: It's impossible !!!")
            }
            is BreedSetListItem -> {
                action =
                    BreedsListFragmentDirections.actionBreedsListFragmentToSubBreedsListFragment(
                        item
                    )
                findNavController().navigate(action)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setTitleText("Breeds")

        breedListViewModel.breedsList.observe(viewLifecycleOwner, Observer { newBreedsList ->
            breedListAdapter.setData(newBreedsList)
        })
        breedListViewModel.getAllBreeds()

        breedListViewModel.loadingState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                LoadingState.LOADING -> (activity as MainActivity).startProgress(true)
                LoadingState.LOADED -> (activity as MainActivity).startProgress(false)
            }
        })

        breedListViewModel.errorState.observe(viewLifecycleOwner, Observer {
            (activity as MainActivity).showBasicDialog(view)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}