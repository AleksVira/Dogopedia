package ru.virarnd.dogopedia.breed_list.detail.child.ui

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.virarnd.dogopedia.R
import ru.virarnd.dogopedia.app.GlideApp
import ru.virarnd.dogopedia.breed_list.detail.vm.DetailViewModel
import ru.virarnd.dogopedia.databinding.FragmentPageBreedDetailBinding
import ru.virarnd.dogopedia.models.DetailPageItem
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.util.*


class PageBreedDetailFragment : Fragment() {

    private var _binding: FragmentPageBreedDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var pageItem: DetailPageItem

    private lateinit var dogBitmap: Bitmap

    private val detailViewModel: DetailViewModel by sharedViewModel()

    companion object {
        private const val BUNDLE_TYPE_PAGE_ITEM = "bundle_type_page_item"

        fun getInstance(
            pageItem: DetailPageItem
        ) = PageBreedDetailFragment().apply {
            arguments = bundleOf(
                BUNDLE_TYPE_PAGE_ITEM to pageItem
            )
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            pageItem = bundle.getParcelable(BUNDLE_TYPE_PAGE_ITEM) ?: DetailPageItem(
                0,
                "",
                false
            )
        }

        val builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPageBreedDetailBinding.inflate(inflater, container, false)

        bindFavourite(pageItem.isFavourite)

        return binding.root
    }

    fun bindFavourite(isFavourite: Boolean) {
        if (isFavourite) {
            binding.favouriteIcon.setImageResource(R.drawable.ic_favorite_filled)
        } else {
            binding.favouriteIcon.setImageResource(R.drawable.ic_favorite_outline)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (pageItem.pictureUrl.isNotEmpty()) {
            GlideApp.with(this)
                .asBitmap()
                .load(pageItem.pictureUrl)
                .apply(RequestOptions.bitmapTransform(CenterCrop()))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.placeholder_dog)
                .error(R.drawable.ic_broken)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        binding.dogImage.setImageBitmap(resource as Bitmap?)
                        dogBitmap = resource
                    }
                })
        } else {
            Timber.e("MyLog_PageBreedDetailFragment_onViewCreated: SOMETHING WRONG!")
        }

        binding.favouriteIcon.setOnClickListener {
            detailViewModel.favouriteIconClicked(pageItem)
        }

        detailViewModel.uriRequest.observe(viewLifecycleOwner, Observer { event ->
            event.handle {

                Timber.d("MyLog_PageBreedDetailFragment_onViewCreated: BITMAP = $dogBitmap")

                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_TEXT, "Hey view and download this image")
                val path: String = MediaStore.Images.Media.insertImage(
                    requireActivity().contentResolver,
                    dogBitmap,
                    "",
                    null
                )
                Timber.d("MyLog_PageBreedDetailFragment_onViewCreated: $dogBitmap")
                val screenshotUri = Uri.parse(path)
                intent.putExtra(Intent.EXTRA_STREAM, screenshotUri)
                intent.type = "image/*"
                startActivity(Intent.createChooser(intent, "Share image via..."))
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}