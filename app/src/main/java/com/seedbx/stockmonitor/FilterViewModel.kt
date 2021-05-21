package com.seedbx.stockmonitor

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModel
import androidx.room.TypeConverter
import java.util.*
import kotlin.collections.HashMap
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.memberProperties


class FilterViewModel : ViewModel() {

    /** A [FilterData] which stores all the filtering conditions */
    var filterData=FilterData()

    /** A [HashMap]<[String],[FilterFragment]> which stores as entries fragment name along with the corresponding FilterFragment */
    var filterFragments=HashMap<String,FilterFragment>()

    /** A [HashMap]<[String],[KMutableProperty1]<[FilterData],[Array]<[Double]?>>> which stores as entries names of fields in filterData and its corresponding Reflection Object */
    var nameToVariable=HashMap<String,KMutableProperty1<FilterData,Array<Double?>>>()

    /**
     * Converts and Returns Double value for its corresponding String value
     *
     * @param str A [String]? which is to be converted
     * @return A [Double]? which is the converted value
     */
    private fun getValueFromString(str: String?): Double? {
        val upperStr = str?.toUpperCase(Locale.ROOT)
        val trillion = 1e12
        val billion = 1e9
        val million = 1e6
        val thousand = 1e3
        return try {
            when (upperStr?.last()) {
                'T' -> upperStr.substring(0, str.length - 1).toDouble() * trillion
                'B' -> upperStr.substring(0, str.length - 1).toDouble() * billion
                'M' -> upperStr.substring(0, str.length - 1).toDouble() * million
                'K' -> upperStr.substring(0, str.length - 1).toDouble() * thousand
                else -> upperStr?.toDouble()
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Returns the FilterFragment for the given key
     *
     * @param key A [String] which is the label of the desired FilterFragment
     * @return A [FilterFragment] object which is the desired FilterFragment
     */
    private fun getFragment(key:String):FilterFragment{
        return when(key){
            "Valuation"->ValuationFilterFragment(this)
            "Price History"->PriceHistoryFilterFragment(this)
            "Balance Sheet"->BalanceSheetFilterFragment(this)
            "Dividends"->DividendsFilterFragment(this)
            "Operating Metrics"->OperatingMetricsFilterFragment(this)
            "Margins"->MarginsFilterFragment(this)
            else->IncomeStatementFilterFragment(this)
        }
    }

    /**
     * Inserts FilterFragment object for the label 'key' into filterFragments
     *
     * @param key A [String] which acts as the label for the FilterFragment object
     */
    private fun addFragment(key:String){
        if(filterFragments[key]==null) {
            val filterFragment = getFragment(key)
            filterFragments[key] = filterFragment
        }
    }

    /**
     * Initialises filterFragment by adding all different FilterFragments in it
     */
    fun initFragments(){
        addFragment("Valuation")
        addFragment("Price History")
        addFragment("Balance Sheet")
        addFragment("Dividends")
        addFragment("Operating Metrics")
        addFragment("Margins")
        addFragment("Income Statement")
    }

    /**
     * Initialises all the fields in FilterViewModel to its Default Values
     */
    fun initialise(){

        try{
            val filterDataList=DatabaseHelper.getFilter()
            filterData=filterDataList[0]
            initialiseValues()
        }catch(e:Exception) { }
        finally{
            filterFragments=HashMap()
            nameToVariable=HashMap()
            initialiseValues()
        }
    }

    /**
     * Initialises values of the field filterData with minimum value and maximum value of the columns
     */
    private fun initialiseValues() {
        val fields = filterData.javaClass.kotlin.memberProperties

        for (f in fields) {
            try {

                val variable=f as KMutableProperty1<FilterData,Array<Double?>>

                nameToVariable[variable.name]=variable

                val value=f.getValue(filterData,f)
                var minValue=value[0]
                var maxValue=value[1]

                if(minValue!=null&&maxValue!=null)
                    continue

                val columnName=Helper.getColumnValue(f.name)

                if(columnName=="")
                    continue

                if(minValue==null)
                    minValue=DatabaseHelper.getMin(columnName)
                if(maxValue==null)
                    maxValue=DatabaseHelper.getMax(columnName)

                variable.set(filterData, arrayOf(minValue,maxValue))

                if(Helper.masterFilterData.containsKey(variable.name))
                        continue
                Helper.masterFilterData.put(variable.name, arrayOf(minValue,maxValue))


            } catch (e: Exception) {
                continue
            }
        }
    }

    /**
     * Returns the value of the desired field in filterData
     *
     * @param name A [String] specifying the name and index of the field in filterData
     * @return A [Double]? equal to the desired value
     */
    fun getValueOfVariable(name: String):Double?{
        return try{
            val parts = name.split('_')
            val variable = nameToVariable[parts[0]]!!
            val value= variable.getValue(filterData, variable)
            val idx=if(parts[1]=="min") 0 else 1
            value[idx]
        }catch (e:Exception) {
            null
        }
    }

    /**
     * Sets value of variable 'name' to be equal to Double? equivalent of stringValue
     *
     * @param name A [String] specifying the name and index of the field in filterData
     * @param stringValue A [String]? which is the value of the desired field
     */
    fun setValueOfVariable(name:String,stringValue:String?){
        val value= getValueFromString(stringValue) ?: return
        try {
            val parts = name.split('_')
            val variable = nameToVariable[parts[0]]!!
            val idx = if (parts[1] == "min") 0 else 1
            val previousValue = variable.getValue(filterData, variable)
            val newValue = if (idx == 0) {
                arrayOf(value, previousValue[1])
            } else {
                arrayOf(previousValue[0], value)
            }
            variable.set(filterData, newValue)
        }catch (e:Exception){}
    }

}


class FilterTextWatcher(var name:String,var filterViewModel:FilterViewModel): TextWatcher {

    /** Does Nothing */
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    /**
     * Sets the value of variable specified by name with s
     *
     * @param s A [CharSequence]? which is the new value for the variable
     * @param start A [Int] which is the starting index after which characters are changed
     * @param before A [Int] which is equal to the length of the characters changed
     * @param count A [Int] which is the number of new characters added
     */
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        filterViewModel.setValueOfVariable(name,s.toString())
    }

    /** Does Nothing */
    override fun afterTextChanged(s: Editable?) {}
}

class Converter{

    /**
     * Converts Array<Double?> to String
     *
     * @param field A [Array]<[Double]?>
     * @return A [String]
     */
    @TypeConverter
    fun fromArrayDouble(field:Array<Double?>):String{
        val s=StringBuilder("")
        var first=true
        for(k in field){
            s.append(k.toString())
            if(first){
                s.append(",")
                first=false
            }
        }
        return s.toString()
    }

    /**
     * Converts String to Array<Double?>
     *
     * @param str A [String]
     * @return A [Array]<[Double]?>
     */
    @TypeConverter
    fun fromString(str:String):Array<Double?>{
        val parts=str.split(',')
        val res=ArrayList<Double?>()
        for(p in parts){
            try{
                res.add(p.toDouble())
            }catch(e:Exception){
                res.add(null)
            }
        }
        return res.toTypedArray()
    }

}