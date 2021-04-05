package com.seedbx.stockmonitor

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

interface Financial

data class Valuation(
	@SerializedName("Market Capitalization")
	@ColumnInfo(name = "Market Capitalization")
	val marketCapitalization:String = "-",
	@SerializedName("Enterprise Value (MRQ)")
	@ColumnInfo(name = "Enterprise Value (MRQ)")
	val enterpriseValueMRQ:String = "-",
	@SerializedName("Enterprise Value/EBITDA (TTM)")
	@ColumnInfo(name = "Enterprise Value/EBITDA (TTM)")
	val enterpriseValueEBITDATTM:String = "-",
	@SerializedName("Total Shares Outstanding (MRQ)")
	@ColumnInfo(name = "Total Shares Outstanding (MRQ)")
	val totalSharesOutstandingMRQ:String = "-",
	@SerializedName("Number of Employees")
	@ColumnInfo(name = "Number of Employees")
	val numberOfEmployees:String = "-",
	@SerializedName("Number of Shareholders")
	@ColumnInfo(name = "Number of Shareholders")
	val numberOfShareholders:String = "-",
	@SerializedName("Price to Earnings Ratio (TTM)")
	@ColumnInfo(name = "Price to Earnings Ratio (TTM)")
	val priceToEarningsRatioTTM:String = "-",
	@SerializedName("Price to Revenue Ratio (TTM)")
	@ColumnInfo(name = "Price to Revenue Ratio (TTM)")
	val priceToRevenueRatioTTM:String = "-",
	@SerializedName("Price to Book (FY)")
	@ColumnInfo(name = "Price to Book (FY)")
	val priceToBookFY:String = "-",
	@SerializedName("Price to Sales (FY)")
	@ColumnInfo(name = "Price to Sales (FY)")
	val priceToSalesFY:String = "-",
):Financial

data class PriceHistory(
	@SerializedName("Average Volume (10 day)")
	@ColumnInfo(name = "Average Volume (10 day)")
	val averageVolume10Day:String = "-",
	@SerializedName("1-Year Beta")
	@ColumnInfo(name = "1-Year Beta")
	val oneYearBeta:String = "-",
	@SerializedName("52 Week High")
	@ColumnInfo(name = "52 Week High")
	val fiveTwoWeekHigh:String = "-",
	@SerializedName("52 Week Low")
	@ColumnInfo(name = "52 Week Low")
	val fiveTwoWeekLow:String = "-",
):Financial

data class BalanceSheet(
	@SerializedName("Quick Ratio (MRQ)")
	@ColumnInfo(name = "Quick Ratio (MRQ)")
	val quickRatioMRQ:String = "-",
	@SerializedName("Current Ratio (MRQ)")
	@ColumnInfo(name = "Current Ratio (MRQ)")
	val currentRatioMRQ:String = "-",
	@SerializedName("Debt to Equity Ratio (MRQ)")
	@ColumnInfo(name = "Debt to Equity Ratio (MRQ)")
	val debtToEquityRatioMRQ:String = "-",
	@SerializedName("Net Debt (MRQ)")
	@ColumnInfo(name = "Net Debt (MRQ)")
	val netDebtMRQ:String = "-",
	@SerializedName("Total Debt (MRQ)")
	@ColumnInfo(name = "Total Debt (MRQ)")
	val totalDebtMRQ:String = "-",
	@SerializedName("Total Assets (MRQ)")
	@ColumnInfo(name = "Total Assets (MRQ)")
	val totalAssetsMRQ:String = "-",
):Financial

data class Dividends(
	@SerializedName("Dividends Paid (FY)")
	@ColumnInfo(name = "Dividends Paid (FY)")
	val dividendsPaidFY:String = "-",
	@SerializedName("Dividends Yield (FY)")
	@ColumnInfo(name = "Dividends Yield (FY)")
	val dividendsYieldFY:String = "-",
	@SerializedName("Dividends per Share (FY)")
	@ColumnInfo(name = "Dividends per Share (FY)")
	val dividendsPerShareFY:String = "-",
):Financial

data class OperatingMetrics(
	@SerializedName("Return on Assets (TTM)")
	@ColumnInfo(name = "Return on Assets (TTM)")
	val returnOnAssetsTTM:String = "-",
	@SerializedName("Return on Equity (TTM)")
	@ColumnInfo(name = "Return on Equity (TTM)")
	val returnOnEquityTTM:String = "-",
	@SerializedName("Return on Invested Capital (TTM)")
	@ColumnInfo(name = "Return on Invested Capital (TTM)")
	val returnOnInvestedCapitalTTM:String = "-",
	@SerializedName("Revenue per Employee (TTM)")
	@ColumnInfo(name = "Revenue per Employee (TTM)")
	val revenuePerEmployeeTTM:String = "-",
):Financial

data class Margins(
	@SerializedName("Net Margin (TTM)")
	@ColumnInfo(name = "Net Margin (TTM)")
	val netMarginTTM:String = "-",
	@SerializedName("Gross Margin (TTM)")
	@ColumnInfo(name = "Gross Margin (TTM)")
	val grossMarginTTM:String = "-",
	@SerializedName("Operating Margin (TTM)")
	@ColumnInfo(name = "Operating Margin (TTM)")
	val operatingMarginTTM:String = "-",
	@SerializedName("Pretax Margin (TTM)")
	@ColumnInfo(name = "Pretax Margin (TTM)")
	val pretaxMarginTTM:String = "-",
):Financial

data class IncomeStatement(
	@SerializedName("Basic EPS (FY)")
	@ColumnInfo(name = "Basic EPS (FY)")
	val basicEPSFY:String = "-",
	@SerializedName("Basic EPS (TTM)")
	@ColumnInfo(name = "Basic EPS (TTM)")
	val basicEPSTTM:String = "-",
	@SerializedName("EPS Diluted (FY)")
	@ColumnInfo(name = "EPS Diluted (FY)")
	val ePSDilutedFY:String = "-",
	@SerializedName("Net Income (FY)")
	@ColumnInfo(name = "Net Income (FY)")
	val netIncomeFY:String = "-",
	@SerializedName("EBITDA (TTM)")
	@ColumnInfo(name = "EBITDA (TTM)")
	val eBITDATTM:String = "-",
	@SerializedName("Gross Profit (MRQ)")
	@ColumnInfo(name = "Gross Profit (MRQ)")
	val grossProfitMRQ:String = "-",
	@SerializedName("Gross Profit (FY)")
	@ColumnInfo(name = "Gross Profit (FY)")
	val grossProfitFY:String = "-",
	@SerializedName("Last Year Revenue (FY)")
	@ColumnInfo(name = "Last Year Revenue (FY)")
	val lastYearRevenueFY:String = "-",
	@SerializedName("Total Revenue (FY)")
	@ColumnInfo(name = "Total Revenue (FY)")
	val totalRevenueFY:String = "-",
	@SerializedName("Free Cash Flow (TTM)")
	@ColumnInfo(name = "Free Cash Flow (TTM)")
	val freeCashFlowTTM:String = "-",
):Financial

@Entity(tableName = "stock_table")
data class StockData(
	@SerializedName("Company Name")
	@ColumnInfo(name = "Company Name")
	val companyName:String = "-",
	@SerializedName("Stock Exchange")
	@ColumnInfo(name = "Stock Exchange")
	val stockExchange:String = "-",
	@SerializedName("Ticker Symbol")
	@ColumnInfo(name = "Ticker Symbol")
	val tickerSymbol:String = "-",
	@PrimaryKey
	@NotNull
	@SerializedName("_id")
	@ColumnInfo(name = "_id")
	val _id:String = "-",
	@Embedded
	@SerializedName("Valuation")
	val valuation: Valuation= Valuation(),
	@Embedded
	@SerializedName("Price History")
	val priceHistory: PriceHistory= PriceHistory(),
	@Embedded
	@SerializedName("Balance Sheet")
	val balanceSheet: BalanceSheet=BalanceSheet(),
	@Embedded
	@SerializedName("Dividends")
	val dividends: Dividends= Dividends(),
	@Embedded
	@SerializedName("Operating Metrics")
	val operatingMetrics: OperatingMetrics= OperatingMetrics(),
	@Embedded
	@SerializedName("Margins")
	val margins: Margins= Margins(),
	@Embedded
	@SerializedName("Income Statement")
	val incomeStatement: IncomeStatement= IncomeStatement(),
)