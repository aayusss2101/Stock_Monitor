package com.seedbx.stockmonitor

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        /** A [android.content.Intent] object used to get parameters passed from HomeActivity */
        val intent = intent

        /** A [String] which is the id of the StockData object to display */
        val id = intent.getStringExtra("id").toString()

        /** A [DetailViewModel] object to store variable state */
        val detailViewModel: DetailViewModel by viewModels { DetailViewModelFactory(id) }

        /** A [StockData] object which is to be displayed */
        val stockData = detailViewModel.stockData

        /** A [ArrayList]<[Pair]<[String],[Financial]>> object that specifies for a given position of tab the label and Financial to display in DetailFragment */
        val posToLabel = detailViewModel.posToLabel

        /** A [TabLayout] which serves as the layout to display tabs on */
        val detailTabLayout: TabLayout = findViewById(R.id.detailTabLayout)

        /** A [ViewPager2] which handles swipes to change current tab */
        val detailViewPager: ViewPager2 = findViewById(R.id.detailViewPager)
        detailViewPager.adapter = DetailPageAdapter(this, this, posToLabel)
        //detailViewPager.setPageTransformer(ForegroundToBackgroundPageTransformer())

        /** A [TabLayoutMediator] acts as a mediator to link detailTabLayout to detailViewPager */
        TabLayoutMediator(detailTabLayout, detailViewPager) { tab, position ->
            tab.text = posToLabel[position].first
        }.attach()

        /** A [TextView] used to display the field StockData.companyName */
        val companyName: TextView = findViewById(R.id.displayActivity_companyName)

        /** A [TextView] used to display the field StockData.stockExchange */
        val stockExchange: TextView = findViewById(R.id.displayActivity_stockExchange)

        /** A [TextView] used to display the field StockData.tickerSymbol */
        val tickerSymbol: TextView = findViewById(R.id.displayActivity_tickerSymbol)

        companyName.text = Helper.getStringFromValue(stockData.companyName)
        stockExchange.text = Helper.getStringFromValue(stockData.stockExchange)
        tickerSymbol.text = Helper.getStringFromValue(stockData.tickerSymbol)

    }
}