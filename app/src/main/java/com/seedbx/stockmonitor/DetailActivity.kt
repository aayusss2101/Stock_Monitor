package com.seedbx.stockmonitor

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity:AppCompatActivity() {

    val TAG="DetailActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val intent=intent

        val id=intent.getStringExtra("id").toString()
        val detailViewModel:DetailViewModel by viewModels { DetailViewModelFactory(id) }

        val stockData=detailViewModel.stockData
        val posToLabel=detailViewModel.posToLabel

        val detailTabLayout:TabLayout=findViewById(R.id.detailTabLayout)
        val detailViewPager: ViewPager2 =findViewById(R.id.detailViewPager)
        detailViewPager.adapter = DetailPageAdapter(this, this, posToLabel)
        detailViewPager.setPageTransformer(ForegroundToBackgroundPageTransformer())



        TabLayoutMediator(detailTabLayout, detailViewPager
        ) { tab, position -> tab.text = posToLabel[position].first }.attach()

        val companyName:TextView=findViewById(R.id.displayActivity_companyName)
        val stockExchange:TextView=findViewById(R.id.displayActivity_stockExchange)
        val tickerSymbol:TextView=findViewById(R.id.displayActivity_tickerSymbol)

        companyName.text=stockData.companyName
        stockExchange.text=stockData.stockExchange
        tickerSymbol.text=stockData.tickerSymbol

    }
}