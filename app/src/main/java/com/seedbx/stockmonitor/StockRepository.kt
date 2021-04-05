package com.seedbx.stockmonitor

class StockRepository(private val stockDao: StockDao) {

    suspend fun insert(stocks: Array<StockData>){
        stockDao.insertAll(*stocks)
    }

    suspend fun getById(id:String):StockData{
        return stockDao.getById(id)
    }

    suspend fun deleteAll(){
        stockDao.deleteAll()
    }

    suspend fun getAll():List<StockData>{
        return stockDao.getAll()
    }
}