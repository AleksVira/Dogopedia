package ru.virarnd.dogopedia.favourites.detail.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.virarnd.dogopedia.MainActivity
import ru.virarnd.dogopedia.R
import ru.virarnd.dogopedia.breed_list.detail.ui.DetailBreedAdapter.Companion.TRANSACTION_SCREEN_OFFSCREEN_LIMIT
import ru.virarnd.dogopedia.databinding.FragmentDetailBinding
import ru.virarnd.dogopedia.favourites.detail.vm.FavDetailViewModel
import ru.virarnd.dogopedia.favourites.main.data.FavouriteDataItem
import timber.log.Timber

class FavDetailFragment : Fragment() {

    private val args: FavDetailFragmentArgs by navArgs()

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var favDetailBreedAdapter: FavDetailBreedAdapter

    private lateinit var favouriteItem: FavouriteDataItem

    private val favDetailViewModel: FavDetailViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        favDetailBreedAdapter = FavDetailBreedAdapter(this)

        binding.breedViewPager.apply {
            adapter = favDetailBreedAdapter
            offscreenPageLimit = TRANSACTION_SCREEN_OFFSCREEN_LIMIT as Int
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favouriteItem = args.favItem
        favDetailViewModel.setCurrentFavouriteItem(favouriteItem)

        (activity as MainActivity).setTitleText(favouriteItem.singleOrParentName)
        setHasOptionsMenu(true)

        favDetailViewModel.getDetailPageItems(favouriteItem)

        favDetailViewModel.favDetailPageLiveData.observe(viewLifecycleOwner, Observer {
            favDetailBreedAdapter.setViewPagerData(ArrayList(it))
        })

        favDetailViewModel.favouriteItemDeleted.observe(viewLifecycleOwner, Observer { event ->
            event.handle {
                findNavController().popBackStack()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.share_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            Timber.d("MyLog_FavDetailFragment_onOptionsItemSelected: ARROW BACK")
        } else if (item.itemId == R.id.action_share) {
            Timber.d("MyLog_FavDetailFragment_onOptionsItemSelected: SHARE!")
            favDetailViewModel.askFileFromGlide()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}