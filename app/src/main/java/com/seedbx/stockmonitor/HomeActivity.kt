package com.seedbx.stockmonitor

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList


class HomeActivity : AppCompatActivity() {

    /** A [HomeViewModel] used to hold variable states */
    private val homeViewModel: HomeViewModel by viewModels()

    /** A [ConstraintLayout] that is the root element in the layout for this HomeActivity */
    private lateinit var homeConstraintLayout: ConstraintLayout

    /** A [Int] that is equal to the id of the radio button selected in sortPopupWindow */
    private var sortPopupWindowSelectedId: Int = -1

    /** A [String] used to store the value of radio button selected in sortPopupWindow */
    private var sortRadioButtonValue: String = "Nothing Selected"

    /** A [RecyclerView] used to display StockData objects in HomeActivity */
    private lateinit var stockRecyclerView: RecyclerView

    /** A [FilterData] used to store the FilterData object returned from FilterActivity */
    private lateinit var filterData: FilterData

    /** A [Boolean] that specifies whether stockDataList should be filtered or not */
    private var filtered = false

    /** A [View.OnClickListener] which is invoked when filterButton is clicked */
    private val filterClickListener =
        registerForActivityResult(FilterActivityContract()) { result ->
            try {
                filterData = DatabaseHelper.getFilter()[0]
                filtered = true
                updateStockDataList(ArrayList(DatabaseHelper.filterStock(filterData)))
            } catch (e: Exception) {}

        }

    /** A [View.OnClickListener] which is invoked when sortButton is clicked */
    private val sortClickListener = View.OnClickListener {

        homeConstraintLayout.background =
            ColorDrawable(ContextCompat.getColor(this, R.color.shadow))

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
            homeConstraintLayout.background =
                ColorDrawable(ContextCompat.getColor(this, R.color.white))
            val selectedId = sortPopupRadioGroup.checkedRadioButtonId
            sortRadioButtonValue = try {
                sortPopupView.findViewById<RadioButton>(selectedId).text.toString()
            } catch (e: Throwable) {
                "Nothing Selected"
            }
            homeViewModel.stateChecked = ascendingDescendingToggleButton.isChecked
            homeViewModel.stockDataList = sortStockDataList(
                homeViewModel.stockDataList,
                sortRadioButtonValue,
                homeViewModel.stateChecked
            )
            sortPopupWindowSelectedId = selectedId
            connectWithRecyclerView(homeViewModel.stockDataList)
        }

        sortPopupWindow.animationStyle = R.style.sort_popup_animation
        sortPopupWindow.setOnDismissListener(sortOnDismissListener)
        sortPopupWindow.showAtLocation(homeConstraintLayout, Gravity.BOTTOM, 0, 0)

    }

    /** A [View.OnClickListener] which is invoked when wishlistButton is clicked */
    private val wishlistClickListener = View.OnClickListener {
        val intent = Intent(this, WishlistActivity::class.java)
        startActivity(intent)
    }

    /**
     * Sorts stockDataList according to the value of selectedRadioButtonValue
     *
     * @param stockDataList A [ArrayList]<[StockData]>, list of StockData objects to be sorted
     * @param selectedRadioButtonValue A [String], the value of the radio button selected in sortPopupWindow
     * @param orderBy A [Boolean] used to specify if stockDataList is to be sorted in ascending or descending order (false for ascending and true for descending)
     * @return A [ArrayList]<[StockData]>, sorted list of StockData objects
     */
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

    /**
     * Changes the TextColour of ascendingTextView and descendingTextView depending on the value of stateChecked
     *
     * @param ascendingTextView A [TextView]
     * @param descendingTextView A [TextView]
     * @param stateChecked A [Boolean]
     */
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

    /**
     * Returns a new StockDataAdapter object
     *
     * @param stockDataList A [ArrayList]<[StockData]>
     * @return A [StockDataAdapter]
     */
    private fun getStockDataAdapter(stockDataList: ArrayList<StockData>): StockDataAdapter {
        return StockDataAdapter(this, R.layout.stock_list_record, stockDataList)
    }

    /**
     * Connects the stockDataList to stockRecyclerView
     *
     * @param stockDataList A [ArrayList]<[StockData]> which contains all the StockData objects to be displayed
     */
    private fun connectWithRecyclerView(stockDataList: ArrayList<StockData>) {
        val stockDataAdapter = getStockDataAdapter(stockDataList)
        stockRecyclerView = findViewById(R.id.stockRecyclerView)
        val itemTouchHelper = ItemTouchHelper(SwipeCallback(0, ItemTouchHelper.RIGHT))
        stockRecyclerView.layoutManager = LinearLayoutManager(this)
        stockRecyclerView.adapter = stockDataAdapter
        itemTouchHelper.attachToRecyclerView(stockRecyclerView)
    }

    /** A [GestureDetectorCompat] used to detect swipe on RecyclerView item, currently does nothing */
    private lateinit var gestureDetector: GestureDetectorCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        homeViewModel.stockDataList = ArrayList(DatabaseHelper.getAll())
        connectWithRecyclerView(homeViewModel.stockDataList)

        homeConstraintLayout = findViewById(R.id.homeConstraintLayout)

        val sortButton: Button = findViewById(R.id.home_btnSort)
        sortButton.setOnClickListener(sortClickListener)
        val filterButton: Button = findViewById(R.id.home_btnFilter)
        filterButton.setOnClickListener { filterClickListener.launch(Activity.RESULT_OK) }
        val wishlistButton: Button = findViewById(R.id.home_btnWishlist)
        wishlistButton.setOnClickListener(wishlistClickListener)

    }

    /**
     * Updates stockDataList, sorts it according to the values in sortPopupWindow and updates the stockRecyclerView
     *
     * @param newStockDataList A [ArrayList]<[StockData]> which is the object to replace stockDataList
     */
    private fun updateStockDataList(newStockDataList: ArrayList<StockData>) {
        homeViewModel.stockDataList = newStockDataList

        homeViewModel.stockDataList = sortStockDataList(
            homeViewModel.stockDataList,
            sortRadioButtonValue,
            homeViewModel.stateChecked
        )

        stockRecyclerView.swapAdapter(getStockDataAdapter(homeViewModel.stockDataList), true)

        if (homeViewModel.listState != null) {
            stockRecyclerView.layoutManager?.onRestoreInstanceState(homeViewModel.listState)
        }
    }

    /**
     * Handles the Restart of HomeActivity by calling updateStockDataList
     */
    override fun onRestart() {
        super.onRestart()
        if (filtered)
            updateStockDataList(ArrayList(DatabaseHelper.filterStock(filterData)))
        else
            updateStockDataList(ArrayList(DatabaseHelper.getAll()))
    }

    /** Does Nothing */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (gestureDetector.onTouchEvent(event))
            true
        else
            super.onTouchEvent(event)
    }

    /** Currently Handles swipe on RecyclerView item and then swipeback */
    inner class SwipeCallback(dragDirs: Int, swipeDirs: Int) :
        ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

        var swipeBack: Boolean = false

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            when (direction) {
                ItemTouchHelper.RIGHT -> {
                    Log.d(
                        "HomeActivity",
                        "swiped: ${(viewHolder as StockDataAdapter.StockViewHolder).companyName}"
                    )
                }
            }
        }

        override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
            if (swipeBack) {
                swipeBack = false
                return 0
            }
            return super.convertToAbsoluteDirection(flags, layoutDirection)
        }

        /**
         *
         *
         * @param c A [Canvas] which RecyclerView is drawing its children in
         * @param recyclerView A [RecyclerView] to which ItemTouchHelper is attached to
         * @param viewHolder A [RecyclerView.ViewHolder] which is being interacted by the user
         * @param dX A [Float] specifying the amount of horizontal displacement
         * @param dY A [Float] specifying the amount of vertical displacement
         * @param actionState A [Int] specifying the type of user interaction with viewHolder
         * @param isCurrentlyActive A [Boolean] specifying whether user is currently interacting with it or not
         */
        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            if (actionState == ACTION_STATE_SWIPE)
                setTouchListener(recyclerView)
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }

        /**
         *
         *
         * @param recyclerView A [RecyclerView] to which ItemTouchHelper is attached to
         */
        @SuppressLint("ClickableViewAccessibility")
        private fun setTouchListener(recyclerView: RecyclerView) {
            recyclerView.setOnTouchListener(View.OnTouchListener { _, event ->
                swipeBack =
                    (event.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_UP)
                return@OnTouchListener false
            })
        }

    }

    /*inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        val SWIPE_THRESHOLD = 100;
        val SWIPE_VELOCITY_THRESHOLD = 100;

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var result = false
            var diffY: Float = e1?.y?.let { e2?.y?.minus(it) } ?: 0f
            var diffX: Float = e1?.x?.let { e2?.x?.minus(it) } ?: 0f
            Log.d("HomeActivity", "$diffX $diffY")

            if (abs(diffX) > abs(diffY)) {
                if (abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX < 0)
                        this@HomeActivity.onSwipeLeft()
                    else
                        this@HomeActivity.onSwipeRight()
                    result = true
                }
            } else {
                if (abs(diffY) > SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY < 0)
                        this@HomeActivity.onSwipeDown()
                    else
                        this@HomeActivity.onSwipeUp()
                    result = true
                }
            }
            return if (!result) super.onFling(e1, e2, velocityX, velocityY) else result
        }

    }

    private fun onSwipeUp() {
        Toast.makeText(this, "Swipe Up", Toast.LENGTH_SHORT).show()
    }

    private fun onSwipeDown() {
        Toast.makeText(this, "Swipe Down", Toast.LENGTH_SHORT).show()
    }

    private fun onSwipeRight() {
        Toast.makeText(this, "Swipe Right", Toast.LENGTH_SHORT).show()
    }

    private fun onSwipeLeft() {
        Toast.makeText(this, "Swipe Left", Toast.LENGTH_SHORT).show()
    }*/
}

class FilterActivityContract : ActivityResultContract<Int, Boolean?>() {

    /**
     *
     *
     * @param context A [Context]
     * @param input A [Int]?
     */
    override fun createIntent(context: Context, input: Int?): Intent {
        return Intent(context, FilterActivity::class.java)
    }

    /**
     * Parses the result returned from FilterActivity
     *
     * @param resultCode A [Int] which is the code returned by FilterActivity
     * @param intent A [Intent]? which was used to start FilterActivity
     * @return A [Boolean]? which is the data returned by FilterActivity if resultCode matches else null
     */
    override fun parseResult(resultCode: Int, intent: Intent?): Boolean? {
        return when {
            resultCode != Activity.RESULT_OK -> null
            else -> intent?.getBooleanExtra("data", false)
        }
    }

}