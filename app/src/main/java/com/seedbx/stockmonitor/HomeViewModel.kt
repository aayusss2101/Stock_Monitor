package com.seedbx.stockmonitor

import androidx.lifecycle.ViewModel

class HomeViewModel:ViewModel() {
    var stockDataList=ArrayList<StockData>()
    var stateChecked:Boolean=false
}