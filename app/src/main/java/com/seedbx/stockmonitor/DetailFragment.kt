package com.seedbx.stockmonitor

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DetailFragment(private val contextActivity: Context, private val financial: Financial): Fragment() {

    private fun keyToList(hashMap: HashMap<String,Any>):List<String>{
        return hashMap.keys.toList()
    }

    private fun toHashMap(financial: Financial):HashMap<String, Any>{
        val gson= Gson()
        val json=gson.toJson(financial)
        val hashMapType =object: TypeToken<HashMap<String, Any>>(){}.type
        return gson.fromJson(json, hashMapType)
    }

    fun connectToRecyclerView(financial: Financial, id:Int){
        val financialMap=toHashMap(financial)
        val financialKeyList=keyToList(financialMap)
        val financialDataAdapter=FinancialDataAdapter(contextActivity, R.layout.financial_list_record, financialMap, financialKeyList)
        val financialRecyclerView= view?.findViewById<RecyclerView>(id)
        financialRecyclerView!!.layoutManager =
            LinearLayoutManager(contextActivity, LinearLayoutManager.VERTICAL, false)
        financialRecyclerView.adapter=financialDataAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        connectToRecyclerView(financial, R.id.fragmentRecylerView)
    }
}