package com.seedbx.stockmonitor

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class DetailFragment(private val contextActivity: Context, private val financial: Financial): Fragment() {

    /**
     * Returns the keys of HashMap as a List
     *
     * @param hashMap A [HashMap]<[String],[Any]?> whose keys need to be returned
     * @return A [List]<[String]> containing the keys of the HashMap
     */
    private fun keyToList(hashMap: HashMap<String,Any?>):List<String>{
        return hashMap.keys.toList()
    }

    /**
     * Converts a Financial to HashMap
     *
     * @param financial A [Financial] which is to be converted
     * @return A [HashMap]<[String],[Any]?> mapping fields in financial to its values
     */
    private fun toHashMap(financial: Financial):HashMap<String, Any?>{
        val gson= GsonBuilder().serializeNulls().create()
        val json=gson.toJson(financial)
        val hashMapType =object: TypeToken<HashMap<String, Any?>>(){}.type
        return gson.fromJson(json, hashMapType)
    }

    /**
     * Connects the financial to financialRecyclerView
     *
     * @param financial A [Financial] which is to be displayed in the current fragment
     * @param id A [Int] which is the id of the financialRecyclerView
     */
    private fun connectToRecyclerView(financial: Financial, id:Int){
        val financialMap=toHashMap(financial)
        val financialKeyList=keyToList(financialMap)

        val financialDataAdapter=FinancialDataAdapter(contextActivity, R.layout.financial_list_record, financialMap, financialKeyList)
        val financialRecyclerView= view?.findViewById<RecyclerView>(id)
        financialRecyclerView!!.layoutManager =
            LinearLayoutManager(contextActivity, LinearLayoutManager.VERTICAL, false)
        financialRecyclerView.adapter=financialDataAdapter
    }

    /**
     * Creates the view associated with the current DetailFragment
     *
     * @param inflater A [LayoutInflater] used to inflate layout
     * @param container A [ViewGroup]? which is the parent view to which the DetailFragment's created view is attached
     * @param savedInstanceState A [Bundle]? containing saved instance states (if any)
     * @return A [View]? representing the view created by inflater
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail,container,false)
    }

    /**
     * Connects the DetailFragment to RecyclerView
     *
     * @param view A [View] which is same as the view created in onCreateView
     * @param savedInstanceState A [Bundle]? containing saved instance states (if any)
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        connectToRecyclerView(financial, R.id.fragmentRecylerView)
    }
}