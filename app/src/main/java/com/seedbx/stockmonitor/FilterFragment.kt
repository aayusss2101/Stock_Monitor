package com.seedbx.stockmonitor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

open class FilterFragment(
    var layoutId: Int,
    var constraintLayoutId: Int,
    var financial: Financial?,
    var filterViewModel: FilterViewModel
) : Fragment() {

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
        val view = inflater.inflate(layoutId, container, false)
        activity?.let {
            Helper.createFinancialFragmentView(
                financial, constraintLayoutId, view,
                it, filterViewModel
            )
        }
        return view
    }
}

class ValuationFilterFragment(filterViewModel: FilterViewModel) : FilterFragment(
    R.layout.fragment_valuation_filter,
    R.id.valuationConstraintLayout,
    StockData().valuation,
    filterViewModel
)

class PriceHistoryFilterFragment(filterViewModel: FilterViewModel) : FilterFragment(
    R.layout.fragment_price_history_filter,
    R.id.priceHistoryConstraintLayout,
    StockData().priceHistory,
    filterViewModel
)

class BalanceSheetFilterFragment(filterViewModel: FilterViewModel) : FilterFragment(
    R.layout.fragment_balance_sheet_filter,
    R.id.balanceSheetConstraintLayout,
    StockData().balanceSheet,
    filterViewModel
)

class DividendsFilterFragment(filterViewModel: FilterViewModel) : FilterFragment(
    R.layout.fragment_dividends_filter,
    R.id.dividendsConstraintLayout,
    StockData().dividends,
    filterViewModel
)

class OperatingMetricsFilterFragment(filterViewModel: FilterViewModel) : FilterFragment(
    R.layout.fragment_operating_metrics_filter,
    R.id.operatingMetricsConstraintLayout,
    StockData().operatingMetrics,
    filterViewModel
)

class MarginsFilterFragment(filterViewModel: FilterViewModel) : FilterFragment(
    R.layout.fragment_margins_filter,
    R.id.marginsConstraintLayout,
    StockData().margins,
    filterViewModel
)

class IncomeStatementFilterFragment(filterViewModel: FilterViewModel) : FilterFragment(
    R.layout.fragment_income_statement_filter,
    R.id.incomeStatementConstraintLayout,
    StockData().incomeStatement,
    filterViewModel
)