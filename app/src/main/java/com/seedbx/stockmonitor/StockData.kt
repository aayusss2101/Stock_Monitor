package com.seedbx.stockmonitor

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/** A marker interface */
interface Financial

data class Valuation(
	@SerializedName("Market Capitalization")
	@ColumnInfo(name = "Market Capitalization")
	val marketCapitalization:Double? = null,
	@SerializedName("Enterprise Value (MRQ)")
	@ColumnInfo(name = "Enterprise Value (MRQ)")
	val enterpriseValueMRQ:Double? = null,
	@SerializedName("Enterprise Value/EBITDA (TTM)")
	@ColumnInfo(name = "Enterprise Value/EBITDA (TTM)")
	val enterpriseValueEBITDATTM:Double? = null,
	@SerializedName("Total Shares Outstanding (MRQ)")
	@ColumnInfo(name = "Total Shares Outstanding (MRQ)")
	val totalSharesOutstandingMRQ:Double? = null,
	@SerializedName("Number of Employees")
	@ColumnInfo(name = "Number of Employees")
	val numberOfEmployees:Double? = null,
	@SerializedName("Number of Shareholders")
	@ColumnInfo(name = "Number of Shareholders")
	val numberOfShareholders:Double? = null,
	@SerializedName("Price to Earnings Ratio (TTM)")
	@ColumnInfo(name = "Price to Earnings Ratio (TTM)")
	val priceToEarningsRatioTTM:Double? = null,
	@SerializedName("Price to Revenue Ratio (TTM)")
	@ColumnInfo(name = "Price to Revenue Ratio (TTM)")
	val priceToRevenueRatioTTM:Double? = null,
	@SerializedName("Price to Book (FY)")
	@ColumnInfo(name = "Price to Book (FY)")
	val priceToBookFY:Double? = null,
	@SerializedName("Price to Sales (FY)")
	@ColumnInfo(name = "Price to Sales (FY)")
	val priceToSalesFY:Double? = null,
):Financial

data class PriceHistory(
	@SerializedName("Average Volume (10 day)")
	@ColumnInfo(name = "Average Volume (10 day)")
	val averageVolume10Day:Double? = null,
	@SerializedName("1-Year Beta")
	@ColumnInfo(name = "1-Year Beta")
	val oneYearBeta:Double? = null,
	@SerializedName("52 Week High")
	@ColumnInfo(name = "52 Week High")
	val fiveTwoWeekHigh:Double? = null,
	@SerializedName("52 Week Low")
	@ColumnInfo(name = "52 Week Low")
	val fiveTwoWeekLow:Double? = null,
):Financial

data class BalanceSheet(
	@SerializedName("Quick Ratio (MRQ)")
	@ColumnInfo(name = "Quick Ratio (MRQ)")
	val quickRatioMRQ:Double? = null,
	@SerializedName("Current Ratio (MRQ)")
	@ColumnInfo(name = "Current Ratio (MRQ)")
	val currentRatioMRQ:Double? = null,
	@SerializedName("Debt to Equity Ratio (MRQ)")
	@ColumnInfo(name = "Debt to Equity Ratio (MRQ)")
	val debtToEquityRatioMRQ:Double? = null,
	@SerializedName("Net Debt (MRQ)")
	@ColumnInfo(name = "Net Debt (MRQ)")
	val netDebtMRQ:Double? = null,
	@SerializedName("Total Debt (MRQ)")
	@ColumnInfo(name = "Total Debt (MRQ)")
	val totalDebtMRQ:Double? = null,
	@SerializedName("Total Assets (MRQ)")
	@ColumnInfo(name = "Total Assets (MRQ)")
	val totalAssetsMRQ:Double? = null,
):Financial

data class Dividends(
	@SerializedName("Dividends Paid (FY)")
	@ColumnInfo(name = "Dividends Paid (FY)")
	val dividendsPaidFY:Double? = null,
	@SerializedName("Dividends Yield (FY)")
	@ColumnInfo(name = "Dividends Yield (FY)")
	val dividendsYieldFY:Double? = null,
	@SerializedName("Dividends per Share (FY)")
	@ColumnInfo(name = "Dividends per Share (FY)")
	val dividendsPerShareFY:Double? = null,
):Financial

data class OperatingMetrics(
	@SerializedName("Return on Assets (TTM)")
	@ColumnInfo(name = "Return on Assets (TTM)")
	val returnOnAssetsTTM:Double? = null,
	@SerializedName("Return on Equity (TTM)")
	@ColumnInfo(name = "Return on Equity (TTM)")
	val returnOnEquityTTM:Double? = null,
	@SerializedName("Return on Invested Capital (TTM)")
	@ColumnInfo(name = "Return on Invested Capital (TTM)")
	val returnOnInvestedCapitalTTM:Double? = null,
	@SerializedName("Revenue per Employee (TTM)")
	@ColumnInfo(name = "Revenue per Employee (TTM)")
	val revenuePerEmployeeTTM:Double? = null,
):Financial

data class Margins(
	@SerializedName("Net Margin (TTM)")
	@ColumnInfo(name = "Net Margin (TTM)")
	val netMarginTTM:Double? = null,
	@SerializedName("Gross Margin (TTM)")
	@ColumnInfo(name = "Gross Margin (TTM)")
	val grossMarginTTM:Double? = null,
	@SerializedName("Operating Margin (TTM)")
	@ColumnInfo(name = "Operating Margin (TTM)")
	val operatingMarginTTM:Double? = null,
	@SerializedName("Pretax Margin (TTM)")
	@ColumnInfo(name = "Pretax Margin (TTM)")
	val pretaxMarginTTM:Double? = null,
):Financial

data class IncomeStatement(
	@SerializedName("Basic EPS (FY)")
	@ColumnInfo(name = "Basic EPS (FY)")
	val basicEPSFY:Double? = null,
	@SerializedName("Basic EPS (TTM)")
	@ColumnInfo(name = "Basic EPS (TTM)")
	val basicEPSTTM:Double? = null,
	@SerializedName("EPS Diluted (FY)")
	@ColumnInfo(name = "EPS Diluted (FY)")
	val ePSDilutedFY:Double? = null,
	@SerializedName("Net Income (FY)")
	@ColumnInfo(name = "Net Income (FY)")
	val netIncomeFY:Double? = null,
	@SerializedName("EBITDA (TTM)")
	@ColumnInfo(name = "EBITDA (TTM)")
	val eBITDATTM:Double? = null,
	@SerializedName("Gross Profit (MRQ)")
	@ColumnInfo(name = "Gross Profit (MRQ)")
	val grossProfitMRQ:Double? = null,
	@SerializedName("Gross Profit (FY)")
	@ColumnInfo(name = "Gross Profit (FY)")
	val grossProfitFY:Double? = null,
	@SerializedName("Last Year Revenue (FY)")
	@ColumnInfo(name = "Last Year Revenue (FY)")
	val lastYearRevenueFY:Double? = null,
	@SerializedName("Total Revenue (FY)")
	@ColumnInfo(name = "Total Revenue (FY)")
	val totalRevenueFY:Double? = null,
	@SerializedName("Free Cash Flow (TTM)")
	@ColumnInfo(name = "Free Cash Flow (TTM)")
	val freeCashFlowTTM:Double? = null,
):Financial

@Entity(tableName = "stock_table")
data class StockData(
	@SerializedName("Company Name")
	@ColumnInfo(name = "Company Name")
	val companyName:String="-",
	@SerializedName("Stock Exchange")
	@ColumnInfo(name = "Stock Exchange")
	val stockExchange:String="-",
	@SerializedName("Ticker Symbol")
	@ColumnInfo(name = "Ticker Symbol")
	val tickerSymbol:String="-",
	@PrimaryKey
	@NonNull
	@SerializedName("_id")
	@ColumnInfo(name = "_id")
	val _id:String="-",
	@SerializedName("addToWishlist")
	@ColumnInfo(name="addToWishlist")
	var addToWishlist:Boolean=false,
	@Embedded
	@SerializedName("Valuation")
	val valuation: Valuation?= Valuation(),
	@Embedded
	@SerializedName("Price History")
	val priceHistory: PriceHistory?= PriceHistory(),
	@Embedded
	@SerializedName("Balance Sheet")
	val balanceSheet: BalanceSheet?=BalanceSheet(),
	@Embedded
	@SerializedName("Dividends")
	val dividends: Dividends?= Dividends(),
	@Embedded
	@SerializedName("Operating Metrics")
	val operatingMetrics: OperatingMetrics?= OperatingMetrics(),
	@Embedded
	@SerializedName("Margins")
	val margins: Margins?= Margins(),
	@Embedded
	@SerializedName("Income Statement")
	val incomeStatement: IncomeStatement= IncomeStatement(),
)