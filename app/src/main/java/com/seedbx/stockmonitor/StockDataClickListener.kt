package com.seedbx.stockmonitor

import android.content.Context
import android.content.Intent

class StockDataClickListener{

    /**
     * Starts DetailActivity with the field _id passed using intent
     *
     * @param context A [Context] that represents context of HomeActivity
     * @param stockData A [StockData] which is to be passed to DetailActivity
     */
    private fun startNewActivity(context: Context, stockData:StockData){
        val intent= Intent(context, DetailActivity::class.java)
        intent.putExtra("id", stockData._id)
        context.startActivity(intent)
    }

    /**
     * Listener Function used to start DetailActivity when RecyclerView item is clicked
     *
     * @param context A [Context] that represents context of HomeActivity
     * @param stockData A [StockData] which is to be passed to DetailActivity
     */
    fun onClick(context:Context,stockData: StockData){
        startNewActivity(context,stockData)
    }
}