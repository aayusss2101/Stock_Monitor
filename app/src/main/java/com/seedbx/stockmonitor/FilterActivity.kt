package com.seedbx.stockmonitor

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FilterActivity: AppCompatActivity() {

    /** A [Fragment] which is the same as the current FilterFragment being shown in FilterActivity */
    lateinit var addedFragment:Fragment

    /** A [ConstraintLayout] that is the root element in the layout for this FilterActivity */
    lateinit var filterConstraintLayout: ConstraintLayout

    /** A [Int] that is equal to the id of the radio button selected in filterPopupWindow */
    private var filterPopupWindowSelectedId:Int=R.id.valuationRadioButton

    /** A [String] used to store the value of radio button selected in filterPopupWindow */
    private var filterRadioButtonValue:String="Nothing Selected"

    /** A [FilterViewModel] used to hold variable states */
    private val filterViewModel:FilterViewModel by viewModels()

    /** A [View.OnClickListener] which is invoked when filterFAB is clicked */
    private val filterClickListener=View.OnClickListener {

        filterConstraintLayout.background= ColorDrawable(ContextCompat.getColor(this,R.color.shadow))

        val layoutInflater=LayoutInflater.from(this)
        val filterPopupView=layoutInflater.inflate(R.layout.popup_filter,null)
        val filterPopupWindow=FilterPopupWindow(filterPopupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)
        val filterRadioGroup=filterPopupView.findViewById<RadioGroup>(R.id.filterRadioGroup)
        filterRadioGroup.check(filterPopupWindowSelectedId)

        val filterOnDismissListener=PopupWindow.OnDismissListener {
            filterConstraintLayout.background=ColorDrawable(ContextCompat.getColor(this,R.color.white))
            val selectedId=filterRadioGroup.checkedRadioButtonId
            filterRadioButtonValue=try{
                filterPopupView.findViewById<RadioButton>(selectedId).text.toString()
            }catch(e:Exception){
                "Nothing Selected"
            }
            changeFilterFragment(filterRadioButtonValue)
            filterPopupWindowSelectedId=selectedId
        }
        filterPopupWindow.setOnDismissListener(filterOnDismissListener)
        filterPopupWindow.showAtLocation(filterConstraintLayout, Gravity.CENTER, 0, 0)

    }

    /**
     * Changes the FilterFragment displayed in FilterActivity and updates the value of addedFragment accordingly
     *
     * @param value A [String] that is the name of the new FilterFragment to be displayed
     */
    private fun changeFilterFragment(value:String){

        val filterFragment:Fragment=filterViewModel.filterFragments[value]?:addedFragment

        if(addedFragment!=filterFragment){
            supportFragmentManager.beginTransaction().remove(addedFragment).commit()
            supportFragmentManager.beginTransaction().add(R.id.filterFragmentContainerView,filterFragment).commit()
            addedFragment=filterFragment
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        filterConstraintLayout=findViewById(R.id.filterConstraintLayout)

        filterViewModel.initialise()
        filterViewModel.initFragments()

        addedFragment=filterViewModel.filterFragments["Valuation"]!!
        supportFragmentManager.beginTransaction().add(R.id.filterFragmentContainerView,addedFragment).commit()

        /** A [FloatingActionButton] that acts as the menu for filterFragments */
        val filterFAB=findViewById<FloatingActionButton>(R.id.filterFAB)
        filterFAB.setOnClickListener(filterClickListener)

    }

    /**
     * Invoked when back is pressed and it inserts filterData in FilterDatabase
     */
    override fun onBackPressed() {
        DatabaseHelper.deleteFilter()
        DatabaseHelper.insertFilter(filterViewModel.filterData)
        intent.putExtra("data",true)
        setResult(Activity.RESULT_OK,intent)
        finish()
        super.onBackPressed()
    }

}