package com.seedbx.stockmonitor

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailPageAdapter(
    var context: Context,
    fragmentActivity: FragmentActivity,
    private val posToLabel: ArrayList<Pair<String, Financial>>
) : FragmentStateAdapter(fragmentActivity) {

    /**
     * Returns the number of items in posToLabel
     *
     * @return A [Int] equal to the size of posToLabel
     */
    override fun getItemCount(): Int = posToLabel.size

    /**
     *  Returns the DetailFragment for the Financial posToLabel.at(position).second
     *
     * @param position A [Int] specifying the position of the fragment
     * @return A [DetailFragment] which is to be displayed
     */
    override fun createFragment(position: Int): DetailFragment =
        DetailFragment(context, posToLabel[position].second)

}