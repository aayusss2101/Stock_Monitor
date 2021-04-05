package com.seedbx.stockmonitor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.annotations.SerializedName
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField

/*class DetailViewModel(private val dependency: Dependency, private val savedStateHandle: SavedStateHandle):ViewModel(), LifecycleObserver {

}*/

class DetailViewModel(stockId: String):ViewModel(){

    private fun getPosToLabel(stockData: StockData):ArrayList<Pair<String,Financial>>{
        val fields=stockData.javaClass.kotlin.memberProperties

        val posToLabel=ArrayList<Pair<String,Financial>>()

        for(f in fields) {
            var sameType=(f.returnType.classifier as KClass<*>).createInstance()

            if(sameType !is Financial)
                continue


            var annotation= f.javaField?.annotations?.toList()
            var value=f.getValue(stockData,f) as Financial
            var annotation0=annotation?.get(0) as SerializedName

            posToLabel.add(Pair(annotation0.value,value))
        }

        return posToLabel
    }

    val stockData=StocksApplication.getById(stockId)
    val posToLabel=getPosToLabel(stockData)

}

class DetailViewModelFactory(private val stockId: String):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(stockId) as T
    }
}