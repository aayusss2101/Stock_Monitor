package com.seedbx.stockmonitor

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="filter_table")
data class FilterData (

    /** A [Int] which serves as the Primary Key in the table 'filter_table' */
    @PrimaryKey(autoGenerate = true)
    var pId:Int=0,

    /** Valuation */
    var marketCapitalization:Array<Double?> = arrayOf(null, null),
    var enterpriseValueMRQ:Array<Double?> = arrayOf(null, null),
    var enterpriseValueEBITDATTM:Array<Double?> = arrayOf(null, null),
    var totalSharesOutstandingMRQ:Array<Double?> = arrayOf(null, null),
    var numberOfEmployees:Array<Double?> = arrayOf(null, null),
    var numberOfShareholders:Array<Double?> = arrayOf(null, null),
    var priceToEarningsRatioTTM:Array<Double?> = arrayOf(null, null),
    var priceToRevenueRatioTTM:Array<Double?> = arrayOf(null, null),
    var priceToBookFY:Array<Double?> = arrayOf(null, null),
    var priceToSalesFY:Array<Double?> = arrayOf(null, null),

    /** Price History */
    var averageVolume10Day:Array<Double?> = arrayOf(null, null),
    var oneYearBeta:Array<Double?> = arrayOf(null, null),
    var fiveTwoWeekHigh:Array<Double?> = arrayOf(null, null),
    var fiveTwoWeekLow:Array<Double?> = arrayOf(null, null),

    /** Balance Sheet */
    var quickRatioMRQ:Array<Double?> = arrayOf(null, null),
    var currentRatioMRQ:Array<Double?> = arrayOf(null, null),
    var debtToEquityRatioMRQ:Array<Double?> = arrayOf(null, null),
    var netDebtMRQ:Array<Double?> = arrayOf(null, null),
    var totalDebtMRQ:Array<Double?> = arrayOf(null, null),
    var totalAssetsMRQ:Array<Double?> = arrayOf(null, null),

    /** Dividends */
    var dividendsPaidFY:Array<Double?> = arrayOf(null, null),
    var dividendsYieldFY:Array<Double?> = arrayOf(null, null),
    var dividendsPerShareFY:Array<Double?> = arrayOf(null, null),

    /** Operating Metrics */
    var returnOnAssetsTTM:Array<Double?> = arrayOf(null, null),
    var returnOnEquityTTM:Array<Double?> = arrayOf(null, null),
    var returnOnInvestedCapitalTTM:Array<Double?> = arrayOf(null, null),
    var revenuePerEmployeeTTM:Array<Double?> = arrayOf(null, null),

    /** Margins */
    var netMarginTTM:Array<Double?> = arrayOf(null, null),
    var grossMarginTTM:Array<Double?> = arrayOf(null, null),
    var operatingMarginTTM:Array<Double?> = arrayOf(null, null),
    var pretaxMarginTTM:Array<Double?> = arrayOf(null, null),

    /** Income Statement */
    var basicEPSFY:Array<Double?> = arrayOf(null, null),
    var basicEPSTTM:Array<Double?> = arrayOf(null, null),
    var ePSDilutedFY:Array<Double?> = arrayOf(null, null),
    var netIncomeFY:Array<Double?> = arrayOf(null, null),
    var eBITDATTM:Array<Double?> = arrayOf(null, null),
    var grossProfitMRQ:Array<Double?> = arrayOf(null, null),
    var grossProfitFY:Array<Double?> = arrayOf(null, null),
    var lastYearRevenueFY:Array<Double?> = arrayOf(null, null),
    var totalRevenueFY:Array<Double?> = arrayOf(null, null),
    var freeCashFlowTTM:Array<Double?> = arrayOf(null, null),

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FilterData

        if (pId != other.pId) return false
        if (!marketCapitalization.contentEquals(other.marketCapitalization)) return false
        if (!enterpriseValueMRQ.contentEquals(other.enterpriseValueMRQ)) return false
        if (!enterpriseValueEBITDATTM.contentEquals(other.enterpriseValueEBITDATTM)) return false
        if (!totalSharesOutstandingMRQ.contentEquals(other.totalSharesOutstandingMRQ)) return false
        if (!numberOfEmployees.contentEquals(other.numberOfEmployees)) return false
        if (!numberOfShareholders.contentEquals(other.numberOfShareholders)) return false
        if (!priceToEarningsRatioTTM.contentEquals(other.priceToEarningsRatioTTM)) return false
        if (!priceToRevenueRatioTTM.contentEquals(other.priceToRevenueRatioTTM)) return false
        if (!priceToBookFY.contentEquals(other.priceToBookFY)) return false
        if (!priceToSalesFY.contentEquals(other.priceToSalesFY)) return false
        if (!averageVolume10Day.contentEquals(other.averageVolume10Day)) return false
        if (!oneYearBeta.contentEquals(other.oneYearBeta)) return false
        if (!fiveTwoWeekHigh.contentEquals(other.fiveTwoWeekHigh)) return false
        if (!fiveTwoWeekLow.contentEquals(other.fiveTwoWeekLow)) return false
        if (!quickRatioMRQ.contentEquals(other.quickRatioMRQ)) return false
        if (!currentRatioMRQ.contentEquals(other.currentRatioMRQ)) return false
        if (!debtToEquityRatioMRQ.contentEquals(other.debtToEquityRatioMRQ)) return false
        if (!netDebtMRQ.contentEquals(other.netDebtMRQ)) return false
        if (!totalDebtMRQ.contentEquals(other.totalDebtMRQ)) return false
        if (!totalAssetsMRQ.contentEquals(other.totalAssetsMRQ)) return false
        if (!dividendsPaidFY.contentEquals(other.dividendsPaidFY)) return false
        if (!dividendsYieldFY.contentEquals(other.dividendsYieldFY)) return false
        if (!dividendsPerShareFY.contentEquals(other.dividendsPerShareFY)) return false
        if (!returnOnAssetsTTM.contentEquals(other.returnOnAssetsTTM)) return false
        if (!returnOnEquityTTM.contentEquals(other.returnOnEquityTTM)) return false
        if (!returnOnInvestedCapitalTTM.contentEquals(other.returnOnInvestedCapitalTTM)) return false
        if (!revenuePerEmployeeTTM.contentEquals(other.revenuePerEmployeeTTM)) return false
        if (!netMarginTTM.contentEquals(other.netMarginTTM)) return false
        if (!grossMarginTTM.contentEquals(other.grossMarginTTM)) return false
        if (!operatingMarginTTM.contentEquals(other.operatingMarginTTM)) return false
        if (!pretaxMarginTTM.contentEquals(other.pretaxMarginTTM)) return false
        if (!basicEPSFY.contentEquals(other.basicEPSFY)) return false
        if (!basicEPSTTM.contentEquals(other.basicEPSTTM)) return false
        if (!ePSDilutedFY.contentEquals(other.ePSDilutedFY)) return false
        if (!netIncomeFY.contentEquals(other.netIncomeFY)) return false
        if (!eBITDATTM.contentEquals(other.eBITDATTM)) return false
        if (!grossProfitMRQ.contentEquals(other.grossProfitMRQ)) return false
        if (!grossProfitFY.contentEquals(other.grossProfitFY)) return false
        if (!lastYearRevenueFY.contentEquals(other.lastYearRevenueFY)) return false
        if (!totalRevenueFY.contentEquals(other.totalRevenueFY)) return false
        if (!freeCashFlowTTM.contentEquals(other.freeCashFlowTTM)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pId
        result = 31 * result + marketCapitalization.contentHashCode()
        result = 31 * result + enterpriseValueMRQ.contentHashCode()
        result = 31 * result + enterpriseValueEBITDATTM.contentHashCode()
        result = 31 * result + totalSharesOutstandingMRQ.contentHashCode()
        result = 31 * result + numberOfEmployees.contentHashCode()
        result = 31 * result + numberOfShareholders.contentHashCode()
        result = 31 * result + priceToEarningsRatioTTM.contentHashCode()
        result = 31 * result + priceToRevenueRatioTTM.contentHashCode()
        result = 31 * result + priceToBookFY.contentHashCode()
        result = 31 * result + priceToSalesFY.contentHashCode()
        result = 31 * result + averageVolume10Day.contentHashCode()
        result = 31 * result + oneYearBeta.contentHashCode()
        result = 31 * result + fiveTwoWeekHigh.contentHashCode()
        result = 31 * result + fiveTwoWeekLow.contentHashCode()
        result = 31 * result + quickRatioMRQ.contentHashCode()
        result = 31 * result + currentRatioMRQ.contentHashCode()
        result = 31 * result + debtToEquityRatioMRQ.contentHashCode()
        result = 31 * result + netDebtMRQ.contentHashCode()
        result = 31 * result + totalDebtMRQ.contentHashCode()
        result = 31 * result + totalAssetsMRQ.contentHashCode()
        result = 31 * result + dividendsPaidFY.contentHashCode()
        result = 31 * result + dividendsYieldFY.contentHashCode()
        result = 31 * result + dividendsPerShareFY.contentHashCode()
        result = 31 * result + returnOnAssetsTTM.contentHashCode()
        result = 31 * result + returnOnEquityTTM.contentHashCode()
        result = 31 * result + returnOnInvestedCapitalTTM.contentHashCode()
        result = 31 * result + revenuePerEmployeeTTM.contentHashCode()
        result = 31 * result + netMarginTTM.contentHashCode()
        result = 31 * result + grossMarginTTM.contentHashCode()
        result = 31 * result + operatingMarginTTM.contentHashCode()
        result = 31 * result + pretaxMarginTTM.contentHashCode()
        result = 31 * result + basicEPSFY.contentHashCode()
        result = 31 * result + basicEPSTTM.contentHashCode()
        result = 31 * result + ePSDilutedFY.contentHashCode()
        result = 31 * result + netIncomeFY.contentHashCode()
        result = 31 * result + eBITDATTM.contentHashCode()
        result = 31 * result + grossProfitMRQ.contentHashCode()
        result = 31 * result + grossProfitFY.contentHashCode()
        result = 31 * result + lastYearRevenueFY.contentHashCode()
        result = 31 * result + totalRevenueFY.contentHashCode()
        result = 31 * result + freeCashFlowTTM.contentHashCode()
        return result
    }
}