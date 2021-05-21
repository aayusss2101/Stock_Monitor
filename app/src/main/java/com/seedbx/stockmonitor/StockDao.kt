package com.seedbx.stockmonitor

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface StockDao {
    @Query("SELECT * FROM stock_table")
    suspend fun getAll():List<StockData>

    @Query("SELECT * FROM stock_table where _id=:id")
    suspend fun getById(id:String):StockData

    @Insert
    suspend fun insertAll(vararg stocks: StockData)

    @Query("UPDATE stock_table SET addToWishlist=:value where _id=:id")
    suspend fun updateWishlist(id:String, value:Boolean)

    @Query("SELECT * FROM stock_table where addToWishlist=1")
    suspend fun getWishlist():List<StockData>

    @Delete
    fun delete(stock:StockData)

    @Query("DELETE FROM stock_table")
    suspend fun deleteAll()

    @RawQuery
    suspend fun getMax(query: SupportSQLiteQuery):Double?

    @RawQuery
    suspend fun getMin(query: SupportSQLiteQuery):Double?

    @RawQuery
    suspend fun filterStock(query: SupportSQLiteQuery):List<StockData>
}