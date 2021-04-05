package com.seedbx.stockmonitor

import android.app.Application
import android.util.Log
import kotlinx.coroutines.*
import kotlin.properties.Delegates

class StocksApplication: Application() {

    companion object {
        private var database by Delegates.notNull<StockDatabase>()
        var repository by Delegates.notNull<StockRepository>()

        private val applicationScope= CoroutineScope(SupervisorJob())

        fun insert(stocks: Array<StockData>){
           applicationScope.launch {
               repository.insert(stocks)
           }
        }

        fun getById(id:String):StockData{
            var stockData by Delegates.notNull<StockData>()
            runBlocking {
                val stockDataFromDB=async { repository.getById(id)}
                runBlocking {
                    stockData=stockDataFromDB.await()
                }
            }
            return stockData
        }

        fun deleteAll(){
            runBlocking {
                repository.deleteAll()
            }
        }

        fun getAll():List<StockData>{
            var stockDataList by Delegates.notNull<List<StockData>>()
            runBlocking {
                val stockDataListFromDB=async { repository.getAll() }
                runBlocking {
                    stockDataList=stockDataListFromDB.await()
                }
            }
            return stockDataList
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("STOCKSAPPLICATIONS", "onCreate called")
        database= StockDatabase.getDatabase(this)
        repository= StockRepository(database.stockDao())
        Log.d("STOCKSAPPLICATIONS", "onCreate ended")
    }
}
