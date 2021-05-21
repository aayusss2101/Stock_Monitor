package com.seedbx.stockmonitor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FinancialDataAdapter(context:Context, private val resource:Int, private val financialMap: HashMap<String,Any?>, private val keyList:List<String>)
    :RecyclerView.Adapter<FinancialDataAdapter.FinancialViewHolder>(){

    /** A [LayoutInflater] used to inflate layouts */
    private val inflater=LayoutInflater.from(context)

    class FinancialViewHolder(v: View):RecyclerView.ViewHolder(v){

        /** A [TextView] which stores the label for the entry in DetailFragment */
        val financialLabel:TextView=v.findViewById(R.id.financialLabel)

        /** A [TextView] which stores the value for the financialLabel field in Financial in DetailFragment */
        val financialValue:TextView=v.findViewById(R.id.financialValue)
    }

    /**
     * Creates the FinancialViewHolder with parent as 'parent'
     *
     * @param parent A [ViewGroup] which acts as the parent view for the FinancialViewHolder
     * @param viewType A [Int] specifying the type of the view for the new view
     * @return A [FinancialViewHolder] which is the newly created view holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinancialViewHolder {
        val view=inflater.inflate(resource, parent, false)
        return FinancialViewHolder(view)
    }

    /**
     * Binds data to the FinancialViewHolder at position 'position' in RecyclerView
     *
     * @param financialViewHolder A [FinancialViewHolder] which is the view holder for the item in the RecyclerView
     * @param position A [Int] specifying the position of the item in the RecyclerView
     */
    override fun onBindViewHolder(financialViewHolder: FinancialViewHolder, position: Int) {
        val currentFinancialKey=keyList[position]
        financialViewHolder.financialLabel.text=currentFinancialKey
        financialViewHolder.financialValue.text=Helper.getStringFromValue(financialMap[currentFinancialKey])
    }

    /**
     * Returns the number of items in keyList
     *
     * @return A [Int] equal to the size of keyList
     */
    override fun getItemCount(): Int {
        return keyList.size
    }

}