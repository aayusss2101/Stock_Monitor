package com.seedbx.stockmonitor

import kotlin.math.pow

class SortComparator {
    val companyNameComparator={ob:StockData->
        val value=ob.companyName
        value
    }
    val marketCapitalisationComparator = { ob: StockData ->
        val value = ob.valuation.marketCapitalization
        try {
            when (value.last().toLowerCase()) {
                'm' -> value.substring(0, value.length - 1).toDouble() * 10.0.pow(6)
                'b' -> value.substring(0, value.length - 1).toDouble() * 10.0.pow(9)
                't' -> value.substring(0, value.length - 1).toDouble() * 10.0.pow(12)
                else -> value.toDouble()
            }
        } catch (e: Throwable) {
            Double.MAX_VALUE
        }
    }
}