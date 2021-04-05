package com.seedbx.stockmonitor

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class HomeActivity : AppCompatActivity() {

    //private val TAG = "HomeActivity"
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var homeConstraintLayout: ConstraintLayout
    private var sortPopupWindowSelectedId: Int = -1

    private val sortClickListener = View.OnClickListener {

        val layoutInflater = LayoutInflater.from(this)
        val sortPopupView = layoutInflater.inflate(R.layout.popup_sort, null)
        val sortPopupWindow = SortPopupWindow(
            sortPopupView,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )
        val sortPopupRadioGroup: RadioGroup =
            sortPopupView.findViewById(R.id.sortPopupRadioGroup)
        sortPopupRadioGroup.check(sortPopupWindowSelectedId)

        val ascendingDescendingToggleButton: ToggleButton =
            sortPopupView.findViewById(R.id.ascendingDescendingToggleButton)
        ascendingDescendingToggleButton.isChecked = homeViewModel.stateChecked

        val ascendingTextView: TextView = sortPopupView.findViewById(R.id.ascendingTextView)
        val descendingTextView: TextView = sortPopupView.findViewById(R.id.descendingTextView)

        changeColorToggleButton(ascendingTextView, descendingTextView, homeViewModel.stateChecked)

        val toggleButtonOnCheckedChangeListener =
            CompoundButton.OnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
                changeColorToggleButton(ascendingTextView, descendingTextView, isChecked)
            }

        ascendingDescendingToggleButton.setOnCheckedChangeListener(
            toggleButtonOnCheckedChangeListener
        )

        val sortOnDismissListener = PopupWindow.OnDismissListener {
            val selectedId = sortPopupRadioGroup.checkedRadioButtonId
            val radioButtonValue =
                sortPopupView.findViewById<RadioButton>(selectedId).text.toString()
            homeViewModel.stateChecked = ascendingDescendingToggleButton.isChecked
            homeViewModel.stockDataList = sortStockDataList(
                homeViewModel.stockDataList,
                radioButtonValue,
                homeViewModel.stateChecked
            )
            sortPopupWindowSelectedId = selectedId
            connectWithRecyclerView(homeViewModel.stockDataList)
        }

        sortPopupWindow.animationStyle = R.style.sort_popup_animation
        sortPopupWindow.setOnDismissListener(sortOnDismissListener)
        sortPopupWindow.showAtLocation(homeConstraintLayout, Gravity.BOTTOM, 0, 0)

    }

    val filterClickListener=View.OnClickListener {
        val intent= Intent(this, FilterActivity::class.java)
        startActivity(intent)
    }

    private fun sortStockDataList(
        stockDataList: ArrayList<StockData>,
        selectedRadioButtonValue: String,
        orderBy: Boolean
    ): ArrayList<StockData> {

        val sortComparator = SortComparator()

        return ArrayList(
            when (orderBy) {
                false -> {

                    when (selectedRadioButtonValue) {
                        getString(R.string.sort_radio_btn_1) ->
                            stockDataList.sortedWith(compareBy(sortComparator.companyNameComparator))

                        getString(R.string.sort_radio_btn_2) ->
                            stockDataList.sortedWith(compareBy(sortComparator.marketCapitalisationComparator))

                        else -> stockDataList
                    }
                }
                true -> {
                    when (selectedRadioButtonValue) {
                        getString(R.string.sort_radio_btn_1) ->
                            stockDataList.sortedWith(compareByDescending(sortComparator.companyNameComparator))

                        getString(R.string.sort_radio_btn_2) ->
                            stockDataList.sortedWith(compareByDescending(sortComparator.marketCapitalisationComparator))

                        else -> stockDataList
                    }
                }
            }
        )
    }

    private fun changeColorToggleButton(
        ascendingTextView: TextView,
        descendingTextView: TextView,
        stateChecked: Boolean
    ) {
        if (stateChecked) {
            ascendingTextView.setTextColor(ContextCompat.getColor(this, R.color.white))
            descendingTextView.setTextColor(ContextCompat.getColor(this, R.color.black))
        } else {
            ascendingTextView.setTextColor(ContextCompat.getColor(this, R.color.black))
            descendingTextView.setTextColor(ContextCompat.getColor(this, R.color.white))
        }
    }

    private fun connectWithRecyclerView(stockDataList: ArrayList<StockData>) {
        val stockDataAdapter = StockDataAdapter(this, R.layout.stock_list_record, stockDataList)
        val stockRecyclerView = findViewById<RecyclerView>(R.id.stockRecyclerView)
        stockRecyclerView.layoutManager = LinearLayoutManager(this)
        stockRecyclerView.adapter = stockDataAdapter
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        homeViewModel.stockDataList = ArrayList(StocksApplication.getAll())
        connectWithRecyclerView(homeViewModel.stockDataList)

        homeConstraintLayout = findViewById(R.id.homeConstraintLayout)
        val sortButton: Button = findViewById(R.id.home_btnSort)
        sortButton.setOnClickListener(sortClickListener)
        val filterButton:Button=findViewById(R.id.hme_btnFilter)
        filterButton.setOnClickListener(filterClickListener)
    }

}