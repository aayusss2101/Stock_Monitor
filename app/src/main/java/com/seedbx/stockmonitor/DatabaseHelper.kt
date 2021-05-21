package com.seedbx.stockmonitor

import android.app.Application
import kotlinx.coroutines.*
import kotlin.properties.Delegates

class DatabaseHelper : Application() {

    companion object {

        /** A [StockDatabase] used to interact with the StockDatabase */
        private var stockDatabase by Delegates.notNull<StockDatabase>()

        /** A [StockRepository] used to perform operations on StockDatabase */
        var stockRepository by Delegates.notNull<StockRepository>()

        /** A [FilterDatabase] object used to interact with the FilterDatabase */
        private var filterDatabase by Delegates.notNull<FilterDatabase>()

        /** A [FilterRepository] object used to perform operations on FilterDatabase */
        var filterRepository by Delegates.notNull<FilterRepository>()

        /** A [CoroutineScope] object which serves the scope for coroutines in this class */
        private val applicationScope = CoroutineScope(SupervisorJob())

        /**
         * Inserts an Array of StockData into StockDatabase
         *
         * @param stocks A [Array]<[StockData]>} containing all the StockData to be inserted
         */
        fun insert(stocks: Array<StockData>) {
            applicationScope.launch {
                stockRepository.insert(stocks)
            }
        }

        /**
         * Inserts a single FilterData object into FilterDatabase
         *
         * @param filter A [FilterData] that is to be inserted
         */
        fun insertFilter(filter: FilterData) {
            applicationScope.launch {
                filterRepository.insertFilter(filter)
            }
        }

        /**
         * Finds StockData object for which field _id==id
         *
         * @param id A [String] and the id which is to be searched
         * @return A [StockData] object for which _id==id
         */
        fun getById(id: String): StockData {
            var stockData by Delegates.notNull<StockData>()
            runBlocking {
                val stockDataFromDB = async { stockRepository.getById(id) }
                runBlocking {
                    stockData = stockDataFromDB.await()
                }
            }
            return stockData
        }

        /**
         * Deletes all StockData objects stored in StockDatabase
         */
        fun deleteAll() {
            runBlocking {
                stockRepository.deleteAll()
            }
        }

        /**
         * Returns all the FilterData object in FilterDatabase
         *
         * @return A [List]<[FilterData]> containing all the FilterData object in FilterDatabase
         */
        fun getFilter(): List<FilterData> {
            return try {
                var filterList by Delegates.notNull<List<FilterData>>()
                runBlocking {
                    val filterListFromDB = async { filterRepository.getFilter() }
                    runBlocking {
                        filterList = filterListFromDB.await()
                    }
                }
                filterList
            } catch (e: Exception) {
                ArrayList()
            }
        }

        /**
         * Deletes all FilterData objects stored in FilterDatabase
         */
        fun deleteFilter(){
            applicationScope.launch {
                filterRepository.deleteFilter()
            }
        }

        /**
         * Returns all the StockData object in StockDatabase
         *
         * @return A [List]<[StockData]> containing all the StockData object in StockDatabase
         */
        fun getAll(): List<StockData> {

            return try {
                var stockDataList by Delegates.notNull<List<StockData>>()

                runBlocking {
                    val stockDataListFromDB = async { stockRepository.getAll() }
                    runBlocking {
                        stockDataList = stockDataListFromDB.await()
                    }
                }
                stockDataList
            } catch (e: Throwable) {
                ArrayList()
            }
        }

        /**
         * Updates the value of field addToWishlist=value for StockData object for which field _id=id
         *
         * @param id A [String] representing the id of StockData to be updated
         * @param value A [Boolean] representing the new value of addToWishlist
         */
        fun updateWishlist(id: String, value: Boolean) {
            applicationScope.launch {
                stockRepository.updateWishlist(id, value)
            }
        }

        /**
         * Returns all the StockData objects for which field addToWishlist==true
         *
         * @return A [List]<[StockData]> containing all the StockData objects for which field addToWishlist==true
         */
        fun getWishlist(): List<StockData> {
            var wishlist by Delegates.notNull<List<StockData>>()
            runBlocking {
                val wishlistFromDB = async { stockRepository.getWishlist() }
                runBlocking {
                    wishlist = wishlistFromDB.await()
                }
            }
            return wishlist
        }

        /**
         * Returns the minimum value in column 'columnName'
         *
         * @param columnName A [String], name of the column
         * @return A [Double]? representing the minimum value in column 'columnName' or null if the column is empty
         */
        fun getMin(columnName: String): Double? {
            var minValue: Double? = null
            runBlocking {
                val minValueFromDB = async { stockRepository.getMin(columnName) }
                runBlocking {
                    minValue = minValueFromDB.await()
                }
            }
            return minValue
        }

        /**
         * Returns the maximum value in column 'columnName'
         *
         * @param columnName A [String], name of the column
         * @return A [Double]? representing the maximum value in column 'columnName' or null if the column is empty
         */
        fun getMax(columnName: String): Double? {
            var maxValue: Double? = null
            runBlocking {
                val maxValueFromDB = async { stockRepository.getMax(columnName) }
                runBlocking {
                    maxValue = maxValueFromDB.await()
                }
            }
            return maxValue
        }

        /**
         * Returns all StockData objects which match the condition specified by filterData
         *
         * @param filterData A [FilterData] specifying all the conditions
         * @return A [List]<[StockData]> containing all StockData objects which match the condition specified by filterData
         */
        fun filterStock(filterData: FilterData):List<StockData> {
            return try {
                var stockData by Delegates.notNull<List<StockData>>()

                runBlocking {
                    val stockDataFromDB = async {
                        stockRepository.filterStock(filterData)
                    }
                    runBlocking {
                        stockData = stockDataFromDB.await()
                    }
                }
                stockData
            }catch (e:Exception){
                emptyList()
            }
        }

    }

    override fun onCreate() {
        super.onCreate()
        stockDatabase = StockDatabase.getDatabase(this)
        stockRepository = StockRepository(stockDatabase.stockDao())
        filterDatabase = FilterDatabase.getDatabase(this)
        filterRepository = FilterRepository(filterDatabase.filterDao())
    }
}
