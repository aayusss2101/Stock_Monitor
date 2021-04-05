package com.seedbx.stockmonitor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StockDataAdapter(private val context: Context, private val resource:Int, private val stockDataList:ArrayList<StockData>)
    :RecyclerView.Adapter<StockDataAdapter.StockViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    class StockViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val companyName: TextView =v.findViewById(R.id.stockList_companyName)
        val stockExchange: TextView =v.findViewById(R.id.stockList_stockExchange)
        val tickerSymbol: TextView =v.findViewById(R.id.stockList_tickerSymbol)
        val peRatioValue: TextView =v.findViewById(R.id.stockList_peRatioValue)
        val pbRatioValue: TextView =v.findViewById(R.id.stockList_pbRatioValue)
        val fiftyTwoWkHighValue: TextView =v.findViewById(R.id.stockList_fiftyTwoWkHighValue)
        val fiftyTwoWkLowValue: TextView =v.findViewById(R.id.stockList_fiftyTwoWkLowValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view=inflater.inflate(resource, parent, false)
        return StockViewHolder(view)
    }

    override fun onBindViewHolder(stockViewHolder: StockViewHolder, position: Int) {

        val currentStock=stockDataList[position]
        stockViewHolder.companyName.text=currentStock.companyName
        stockViewHolder.stockExchange.text=currentStock.stockExchange
        stockViewHolder.tickerSymbol.text=currentStock.tickerSymbol
        stockViewHolder.peRatioValue.text=currentStock.valuation.priceToEarningsRatioTTM
        stockViewHolder.pbRatioValue.text=currentStock.valuation.priceToBookFY
        stockViewHolder.fiftyTwoWkHighValue.text=currentStock.priceHistory.fiveTwoWeekHigh
        stockViewHolder.fiftyTwoWkLowValue.text=currentStock.priceHistory.fiveTwoWeekLow

        stockViewHolder.itemView.setOnClickListener { StockDataClickListener().onClick(context, currentStock) }

    }

    override fun getItemCount(): Int {
        return stockDataList.size
    }


}