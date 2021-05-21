package com.seedbx.stockmonitor

import android.os.Parcelable
import androidx.lifecycle.ViewModel

class HomeViewModel:ViewModel() {

    /** A [ArrayList]<[StockData]> containing list of StockData objects to be displayed on the HomeActivity */
    var stockDataList=ArrayList<StockData>()

    /** A [Boolean] which is equal to false if stockDataList is to be sorted in ascending order else true if to be sorted in descending order */
    var stateChecked:Boolean=false

    /** A [Parcelable]? used to store the state of stockRecyclerView.layoutManager */
    var listState:Parcelable?=null
}