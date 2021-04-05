package com.seedbx.stockmonitor

import android.content.Context
import android.content.Intent
import com.google.gson.Gson

class StockDataClickListener{

    val TAG="StockDataClickListener"

    private fun convertToString(stockData: StockData):String{
        val gson= Gson()
        val json=gson.toJson(stockData)
        return json.toString()
    }

    private fun startNewActivity(context: Context, stockData:StockData){
        val intent= Intent(context, DetailActivity::class.java)
        //intent.putExtra("stockData", convertToString(stockData))
        intent.putExtra("id", stockData._id)
        context.startActivity(intent)

        //Log.d(TAG, "startNewActivity: activity is $activity and stockData is $stockData")
    }

    fun onClick(context:Context,stockData: StockData){
        startNewActivity(context,stockData)
    }
}