package ru.virarnd.dogopedia.favourites.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.virarnd.dogopedia.MainActivity
import ru.virarnd.dogopedia.R
import ru.virarnd.dogopedia.databinding.FragmentBreedsListBinding
import ru.virarnd.dogopedia.favourites.main.data.FavouriteDataItem
import ru.virarnd.dogopedia.favourites.main.vm.FavouritesListViewModel
import ru.virarnd.dogopedia.repository.network.helpers.LoadingState
import timber.log.Timber

class FavouritesFragment : Fragment() {

    private var _binding: FragmentBreedsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var favouritesListAdapter: FavouritesListAdapter

    private val favouritesListViewModel: FavouritesListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val backPressedCallback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            Timber.d("MyLog_FavouritesFragment_onCreate: BACK PRESSED!")
            findNavController().popBackStack(R.id.breedsListFragment, true)
            findNavController().navigate(R.id.breedsListFragment)

        }
        backPressedCallback.isEnabled

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBreedsListBinding.inflate(inflater, container, false)

        favouritesListAdapter =
            FavouritesListAdapter { item: FavouriteDataItem ->
                favBreedNameClicked(item)
            }

        with(binding.mainList) {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = favouritesListAdapter
        }

        return binding.root
    }

    private fun favBreedNameClicked(item: FavouriteDataItem) {
        val action: NavDirections =
            FavouritesFragmentDirections.actionFavouritesFragmentToFavDetailFragment(item)
        findNavController().navigate(action)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setTitleText("Favourites")

        favouritesListViewModel.favouritesListLiveData.observe(viewLifecycleOwner,
            Observer { event ->
                event.handle { favouritesList ->
                    favouritesListAdapter.setData(favouritesList)
                }
            })

        favouritesListViewModel.getFavourites()

        favouritesListViewModel.loadingState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                LoadingState.LOADING -> (activity as MainActivity).startProgress(true)
                LoadingState.LOADED -> (activity as MainActivity).startProgress(false)
            }
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}