package com.seedbx.stockmonitor

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StockDao {
    @Query("SELECT * FROM stock_table")
    suspend fun getAll():List<StockData>

    @Query("SELECT * FROM stock_table where _id=:id")
    suspend fun getById(id:String):StockData

    @Insert
    suspend fun insertAll(vararg stocks: StockData)

    @Delete
    fun delete(stock:StockData)

    @Query("DELETE FROM stock_table")
    suspend fun deleteAll()
}