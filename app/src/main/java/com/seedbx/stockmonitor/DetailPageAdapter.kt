package com.seedbx.stockmonitor

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailPageAdapter(
    var context: Context,
    fragmentActivity: FragmentActivity,
    private val posToLabel: ArrayList<Pair<String, Financial>>
) : FragmentStateAdapter(fragmentActivity) {

    val TAG = "DetailPageAdapter"

    override fun getItemCount(): Int = posToLabel.size

    override fun createFragment(position: Int): DetailFragment =
        DetailFragment(context, posToLabel[position].second)

}