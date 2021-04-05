package com.seedbx.stockmonitor

import android.view.View
import androidx.viewpager2.widget.ViewPager2


class ForegroundToBackgroundPageTransformer : ViewPager2.PageTransformer {
    override fun transformPage(page: View, pos: Float) {
        val height: Float = page.height.toFloat()
        val width: Float = page.width.toFloat()
        val scale: Float = kotlin.math.min(if (pos > 0) 1f else kotlin.math.abs(1f + pos),1f)
        page.scaleX = scale
        page.scaleY = scale
        page.pivotX = width * 0.5f
        page.pivotY = height * 0.5f
        page.translationX = if (pos > 0) width * pos else -width * pos * 0.25f
    }
}