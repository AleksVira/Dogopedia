package ru.virarnd.dogopedia.breed_list.detail.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.virarnd.dogopedia.MainActivity
import ru.virarnd.dogopedia.R
import ru.virarnd.dogopedia.breed_list.detail.ui.DetailBreedAdapter.Companion.TRANSACTION_SCREEN_OFFSCREEN_LIMIT
import ru.virarnd.dogopedia.breed_list.detail.vm.DetailViewModel
import ru.virarnd.dogopedia.databinding.FragmentDetailBinding
import ru.virarnd.dogopedia.repository.network.helpers.LoadingState
import timber.log.Timber

class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var detailBreedAdapter: DetailBreedAdapter

    private val detailViewModel: DetailViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        detailBreedAdapter = DetailBreedAdapter(this)

        binding.breedViewPager.apply {
            adapter = detailBreedAdapter
            offscreenPageLimit = TRANSACTION_SCREEN_OFFSCREEN_LIMIT as Int
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        val breedName = args.breedName
        val isSubBreed = args.isSubBreed

        // Собираю данные о породе
        if (isSubBreed) {
            val parentName = args.parentName
            detailViewModel.fetchSubBreedData(parentName!!, breedName)
            (activity as MainActivity).setTitleText("$parentName $breedName")
        } else {
            detailViewModel.fetchSingleBreedData(breedName)
            (activity as MainActivity).setTitleText(breedName)
        }

        detailViewModel.breedDetailPageLiveData.observe(viewLifecycleOwner, Observer { event ->
            event.handle {
                detailBreedAdapter.setViewPagerData(ArrayList(it))
            }
        })

        detailViewModel.loadingState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                LoadingState.LOADING -> (activity as MainActivity).startProgress(true)
                LoadingState.LOADED -> (activity as MainActivity).startProgress(false)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.share_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            Timber.d("MyLog_DetailFragment_onOptionsItemSelected: ARROW BACK")
        } else if (item.itemId == R.id.action_share) {
            Timber.d("MyLog_DetailFragment_onOptionsItemSelected: SHARE!")
            detailViewModel.askFileFromGlide()

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}