package com.seedbx.stockmonitor

class FilterRepository(private val filterDao: FilterDao) {

    /**
     * Returns all the FilterData object in FilterDatabase
     *
     * @return [List]<[FilterData]> representing all the [FilterData] object in [FilterDatabase]
     */
    suspend fun getFilter():List<FilterData>{
        return filterDao.getFilter()
    }

    /**
     * Inserts filter in FilterDatabase
     *
     * @param filter A [FilterData] which is to be inserted
     */
    suspend fun insertFilter(filter: FilterData){
        filterDao.insertFilter(filter)
    }

    /**
     * Deletes all the FilterData object in FilterDatabase
     */
    suspend fun deleteFilter(){
        filterDao.deleteFilter()
    }
}