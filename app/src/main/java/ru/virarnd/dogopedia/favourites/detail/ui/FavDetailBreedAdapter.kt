package ru.virarnd.dogopedia.favourites.detail.ui

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager.widget.PagerAdapter.POSITION_NONE
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import ru.virarnd.dogopedia.favourites.child.ui.FavPageDetailFragment
import ru.virarnd.dogopedia.models.DetailPageItem
import timber.log.Timber


class FavDetailBreedAdapter(
    val fragment: Fragment
) : FragmentStateAdapter(fragment) {

    private var detailPageItemList: ArrayList<DetailPageItem> = arrayListOf()

    companion object {
        internal const val TRANSACTION_SCREEN_OFFSCREEN_LIMIT = 3
    }

    override fun getItemCount(): Int = detailPageItemList.size

    override fun createFragment(position: Int): Fragment {
        val newDetailItem = detailPageItemList[position]
        return FavPageDetailFragment.getInstance(newDetailItem)
    }

    fun setViewPagerData(newDataItemList: ArrayList<DetailPageItem>) {
        val callback = FavDetailDiffUtil(detailPageItemList, newDataItemList)
        val diff = DiffUtil.calculateDiff(callback)
        detailPageItemList.clear()
        detailPageItemList.addAll(newDataItemList)
        diff.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            Timber.d("MyLog_FavDetailBreedAdapter_onBindViewHolder: BIND")
            val tag = "f" + holder.itemId
            val fragment = fragment.childFragmentManager.findFragmentByTag(tag)
            if (fragment != null) {
                (fragment as FavPageDetailFragment).bindView()
            } else {
                super.onBindViewHolder(holder, position, payloads)
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }


    override fun getItemId(position: Int): Long {
        return detailPageItemList[position].id
    }

    override fun containsItem(itemId: Long): Boolean {
        return detailPageItemList.any { it.id == itemId }
    }

}