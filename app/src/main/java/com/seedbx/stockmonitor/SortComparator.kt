package com.seedbx.stockmonitor

class SortComparator {

    /** A ([StockData])->[String] which returns the field companyName */
    val companyNameComparator={ob:StockData->
        ob.companyName
    }

    /** A ([StockData])->[Double] which returns the field valuation.marketCapitalization if it is not null else Double.MAX_VALUE */
    val marketCapitalisationComparator = {ob:StockData->
        ob.valuation?.marketCapitalization ?: Double.MAX_VALUE
    }
}