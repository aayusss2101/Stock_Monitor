package com.seedbx.stockmonitor

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

class FinancialDataAdapter(context:Context, private val resource:Int, private val financialMap: HashMap<String,Any>, private val keyList:List<String>)
    :RecyclerView.Adapter<FinancialDataAdapter.FinancialViewHolder>(){

    private val inflater=LayoutInflater.from(context)

    class FinancialViewHolder(v: View):RecyclerView.ViewHolder(v){
        val financialLabel:TextView=v.findViewById(R.id.financialLabel)
        val financialValue:TextView=v.findViewById(R.id.financialValue)
    }

    fun getText(label:String,length:Int):String{
        /*if(label.length<=length)
            return label
        return label.slice(0 until length)+"..."*/
        return label
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinancialViewHolder {
        val view=inflater.inflate(resource, parent, false)
        return FinancialViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(financialViewHolder: FinancialViewHolder, position: Int) {
        val currentFinancialKey=keyList[position]
        financialViewHolder.financialLabel.text=getText(currentFinancialKey,15)
        //financialViewHolder.financialLabel.tooltipText=currentFinancialKey
        financialViewHolder.financialValue.text=getText(financialMap[currentFinancialKey].toString(),10)
        //financialViewHolder.financialValue.tooltipText=financialMap[currentFinancialKey].toString()
    }

    override fun getItemCount(): Int {
        return keyList.size
    }

}