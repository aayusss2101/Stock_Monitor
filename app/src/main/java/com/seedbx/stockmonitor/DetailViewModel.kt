package com.seedbx.stockmonitor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.annotations.SerializedName
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField

class DetailViewModel(stockId: String) : ViewModel() {

    /**
     * Returns an ArrayList object that specifies, for a given position of tab, the label and Financial to display in DetailFragment
     *
     * @param stockData A [StockData] whose Financial fields are to be stored
     * @return A [ArrayList]<[Pair]<[String],[Financial]>> object that specifies, for a given position of tab, the label and Financial to display in DetailFragment
     */
    private fun getPosToLabel(stockData: StockData): ArrayList<Pair<String, Financial>> {
        val fields = stockData.javaClass.kotlin.memberProperties

        val posToLabel = ArrayList<Pair<String, Financial>>()

        for (f in fields) {
            try {
                val sameType = (f.returnType.classifier as KClass<*>).createInstance()

                if (sameType !is Financial)
                    continue

                val annotation = f.javaField?.annotations?.toList()
                val value = f.getValue(stockData, f) as Financial
                val annotation0 = annotation?.get(0) as SerializedName

                posToLabel.add(Pair(annotation0.value, value))
            } catch (e: Throwable) {
                continue
            }
        }

        return posToLabel
    }

    /** A [StockData] object which is to be displayed */
    val stockData = DatabaseHelper.getById(stockId)

    /** A [ArrayList]<[Pair]<[String],[Financial]>> object that specifies, for a given position of tab, the label and Financial to display in DetailFragment */
    val posToLabel = getPosToLabel(stockData)

}

class DetailViewModelFactory(private val stockId: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(stockId) as T
    }
}