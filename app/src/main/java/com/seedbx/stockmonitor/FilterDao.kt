package com.seedbx.stockmonitor

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FilterDao {
    @Insert
    suspend fun insertFilter(filter:FilterData)

    @Query("SELECT * FROM filter_table")
    suspend fun getFilter():List<FilterData>

    @Query("DELETE from filter_table")
    suspend fun deleteFilter()
}