package com.seedbx.stockmonitor

import androidx.sqlite.db.SimpleSQLiteQuery
import kotlin.reflect.full.memberProperties

class StockRepository(private val stockDao: StockDao) {

    /**
     * Insert Array of StockData Objects in the StockDatabase
     *
     * @param stocks A [Array]<[StockData]> that has to be inserted in the StockDatabase
     */
    suspend fun insert(stocks: Array<StockData>){
        stockDao.insertAll(*stocks)
    }

    /**
     * Finds StockData Object where the field _id==id
     *
     * @param id A [String], the id of the StockData which is to be found
     * @return A [StockData] whose field _id==id
     */
    suspend fun getById(id:String):StockData{
        return stockDao.getById(id)
    }

    /**
     * Delete all StockData Objects in the Database
     */
    suspend fun deleteAll(){
        stockDao.deleteAll()
    }

    /**
     * Get all StockData Objects in the Database
     *
     * @return A [List]<[StockData]> containing all the StockData objects in the StockDatabase
     */
    suspend fun getAll():List<StockData>{
        return stockDao.getAll()
    }

    /**
     * Updates the value of field addToWishlist=value for StockData object for which field _id=id
     *
     * @param id A [String] representing the id of StockData to be updated
     * @param value A [Boolean] representing the new value of addToWishlist
     */
    suspend fun updateWishlist(id:String, value:Boolean){
        stockDao.updateWishlist(id,value)
    }


    /**
     * Returns all the StockData objects for which field addToWishlist==true
     *
     * @return A [List]<[StockData]> containing all the StockData objects for which field addToWishlist==true
     */
    suspend fun getWishlist():List<StockData>{
        return stockDao.getWishlist()
    }

    /**
     * Returns the minimum value in column 'columnName'
     *
     * @param columnName A [String], name of the column
     * @return A [Double]? representing the minimum value in column 'columnName' or null if the column is empty
     */
    suspend fun getMin(columnName: String):Double?{
        val query="SELECT MIN(\"$columnName\") FROM stock_table"
        return stockDao.getMin(SimpleSQLiteQuery(query))
    }

    /**
     * Returns the maximum value in column 'columnName'
     *
     * @param columnName A [String], name of the column
     * @return A [Double]? representing the maximum value in column 'columnName' or null if the column is empty
     */
    suspend fun getMax(columnName: String):Double?{
        val query="SELECT MAX(\"$columnName\") FROM stock_table"
        return stockDao.getMax(SimpleSQLiteQuery(query))
    }

    /**
     * Returns all StockData objects which match the condition specified by filterData
     *
     * @param filterData A [FilterData] specifying all the conditions
     * @return A [List]<[StockData]> containing all StockData objects which match the condition specified by filterData
     */
    suspend fun filterStock(filterData: FilterData):List<StockData>{

        val query= StringBuilder("SELECT * FROM stock_table WHERE")
        val fields=filterData.javaClass.kotlin.memberProperties
        var first=true

        for(f in fields){

            try {
                val columnName: String = Helper.getColumnValue(f.name)


                val value = f.getValue(filterData, f) as Array<Double?>

                val minValue = value[0]
                val maxValue = value[1]

                val masterValue=Helper.masterFilterData[f.name]!!
                val masterMinValue=masterValue[0]
                val masterMaxValue=masterValue[1]

                val starting = if (first) " " else " AND "
                val subQuery = StringBuilder("$starting(\"$columnName\" BETWEEN $minValue AND $maxValue")

                if(minValue==masterMinValue && maxValue==masterMaxValue)
                    subQuery.append(" OR \"$columnName\" IS NULL")
                subQuery.append(")")

                query.append(subQuery.toString())
                first = false

            }catch (e:Exception){
                continue
            }
        }

        return stockDao.filterStock(SimpleSQLiteQuery(query.toString()))
    }
}