package com.seedbx.stockmonitor

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FilterData::class], version=1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class FilterDatabase:RoomDatabase() {
    abstract fun filterDao():FilterDao

    companion object{

        @Volatile
        private var INSTANCE:FilterDatabase?=null

        fun getDatabase(context: Context):FilterDatabase{
            return INSTANCE?: synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    FilterDatabase::class.java,
                    "filter_database"
                ).build()
                INSTANCE=instance
                instance
            }
        }
    }
}