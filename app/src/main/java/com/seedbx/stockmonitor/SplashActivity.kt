package com.seedbx.stockmonitor

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import org.json.JSONArray


class SplashActivity:AppCompatActivity(), GetData.OnResponse{

    fun responseToStockData(response: String):ArrayList<StockData>{

        val stockDataList=ArrayList<StockData>()

        val responses= JSONArray(response)
        val gson= Gson()

        for(i in 0 until responses.length()) {
            val stockData = gson.fromJson(responses.getJSONObject(i).toString(), StockData::class.java)
            stockDataList.add(stockData)
        }

        return stockDataList

    }

    fun moveToHome(activity: AppCompatActivity, timeout: Long){
        Handler(Looper.getMainLooper()).postDelayed({

            val intent= Intent(this, activity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
            startActivity(intent)
            this.finish()

        }, timeout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val getData=GetData(this, HomeActivity(), this)
        getData.getData()

    }

    override fun onResponseComplete(activity: AppCompatActivity, response: String, timeout: Long){

        val stockDataList=responseToStockData(response)

        StocksApplication.deleteAll()
        StocksApplication.insert(stockDataList.toTypedArray())

        moveToHome(activity, timeout)

    }

    override fun onResponseError(activity: AppCompatActivity, timeout: Long) {
        moveToHome(activity, timeout)
    }
}