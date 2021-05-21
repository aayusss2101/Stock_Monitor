package com.seedbx.stockmonitor

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.google.gson.annotations.SerializedName
import kotlin.math.abs
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField

class Helper {

    companion object {

        /** A [HashMap]<[String],[String]>? used to store the corresponding column name for a given variable name */
        private var columnValue: HashMap<String, String>? = null

        /** A [HashMap]<[String],[Array]<[Double]?>> that stores all the minimum and maximum value for each variable field in FilterData */
        var masterFilterData=HashMap<String,Array<Double?>>()

        /**
         * Returns a user-friendly string representation of value
         *
         * @param value A [Any]?
         * @return A [String] equal to "-" if value is null else to a user-friendly string representation of value
         */
        fun getStringFromValue(value: Any?): String {
            if (value == null)
                return "-"
            if (value is String)
                return value
            val trillion = 1e12
            val billion = 1e9
            val million = 1e6
            val thousand = 1e3
            try {
                val valueDouble = value.toString().toDouble()
                return if (abs(valueDouble) > trillion)
                    String.format("%.2f", valueDouble / trillion) + "T"
                else if (abs(valueDouble) > billion)
                    String.format("%.2f", valueDouble / billion) + "B"
                else if (abs(valueDouble) > million)
                    String.format("%.2f", valueDouble / million) + "M"
                else if (abs(valueDouble) > thousand)
                    String.format("%.2f", valueDouble / thousand) + "K"
                else
                    valueDouble.toString()
            } catch (e: Throwable) {
                return value.toString()
            }
        }

        /**
         * Maps the variable name to its corresponding column name
         *
         * @param data A [Any] whose fields should be mapped
         * @return A [HashMap]<[String],[String]> that stores the column name for a given variable name
         */
        private fun mapToColumnValue(data: Any): HashMap<String, String> {
            val fields = data.javaClass.kotlin.memberProperties
            val columnValue = HashMap<String, String>()
            for (f in fields) {
                try {
                    val type = (f.returnType.classifier as KClass<*>).createInstance()
                    if (type is Financial) {
                        val financialData = f.getValue(data, f) as Financial
                        val financialColumnValue = mapToColumnValue(financialData)
                        columnValue.putAll(financialColumnValue)
                        continue
                    }
                } catch (e: Throwable) {}

                val name = f.name
                val annotation = f.javaField?.annotations?.toList()
                val annotation0 = annotation?.get(0) as SerializedName
                val columnName = annotation0.value
                columnValue[name] = columnName

            }
            return columnValue
        }

        /**
         * Returns the column name for a given variableName
         *
         * @param variableName A [String] that is the name of the variable
         * @return A [String] representing the column name for variableName if variableName is in columnValue else ""
         */
        fun getColumnValue(variableName: String): String {
            if (columnValue == null)
                columnValue = mapToColumnValue(StockData())
            return columnValue?.get(variableName) ?: ""
        }

        /**
         * Converts camelCase Representation to a Representation containing words separated by space
         *
         * @param name A [String] in camelCase Representation which is to be converted
         * @return A [String] containing words in name separated by space
         */
        private fun convertFromCamelCase(name: String): String {
            val foo = StringBuilder()
            for (c in name) {
                if (c.isUpperCase() && foo.isNotEmpty())
                    foo.append(' ')
                foo.append(c)
            }
            return foo.toString()
        }

        /**
         * Converts size in dp to size in pixels(px)
         *
         * @param dp A [Int] which is the size in dp
         * @param context A [Context] is the context of the activity from which the function is called
         * @return A [Int] which is the size in px
         */
        private fun convertDPtoPX(dp: Int, context: Context): Int {
            return (dp * context.resources.displayMetrics.density).toInt()
        }

        /**
         * Creates and populates the 'view' for the Financial 'financial'
         *
         * @param financial A [Financial]? for which the view is being populated
         * @param constraintLayoutID A [Int] specifying the id of the ConstraintLayout
         * @param view A [View] which is to be populated
         * @param context A [Context] is the context of the activity from which the function is called
         * @param filterViewModel A [FilterViewModel] object that stores the value
         */
        fun createFinancialFragmentView(financial: Financial?, constraintLayoutID: Int, view: View, context: Context,filterViewModel: FilterViewModel) {

            val constraintLayout = view.findViewById<ConstraintLayout>(constraintLayoutID)
            val separatorText = "-"

            val fragmentConstraintSet = ConstraintSet()
            fragmentConstraintSet.clone(constraintLayout)
            val className=financial!!::class.java.simpleName

            /** Fragment Heading */
            val fragmentHeading = TextView(context)
            fragmentHeading.text = convertFromCamelCase(className)
            fragmentHeading.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            fragmentHeading.isAllCaps = true
            fragmentHeading.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                context.resources.getDimension(R.dimen.heading_font)
            )
            fragmentHeading.id = View.generateViewId()
            constraintLayout.addView(fragmentHeading)
            fragmentConstraintSet.clone(constraintLayout)
            fragmentConstraintSet.connect(
                fragmentHeading.id, ConstraintSet.START, constraintLayoutID, ConstraintSet.START,
                convertDPtoPX(8, context)
            )
            fragmentConstraintSet.connect(
                fragmentHeading.id, ConstraintSet.TOP, constraintLayoutID, ConstraintSet.TOP,
                convertDPtoPX(8, context)
            )
            fragmentConstraintSet.applyTo(constraintLayout)

            /** Divider */
            val divider = View(context)
            divider.layoutParams =
                ViewGroup.LayoutParams(convertDPtoPX(0, context), convertDPtoPX(1, context))
            divider.background = ColorDrawable(ContextCompat.getColor(context, R.color.black))
            divider.id = View.generateViewId()
            constraintLayout.addView(divider)
            fragmentConstraintSet.clone(constraintLayout)
            fragmentConstraintSet.connect(
                divider.id,
                ConstraintSet.START,
                constraintLayoutID,
                ConstraintSet.START,
                convertDPtoPX(4, context)
            )
            fragmentConstraintSet.connect(
                divider.id,
                ConstraintSet.END,
                constraintLayoutID,
                ConstraintSet.END,
                convertDPtoPX(4, context)
            )
            fragmentConstraintSet.connect(
                divider.id,
                ConstraintSet.TOP,
                fragmentHeading.id,
                ConstraintSet.BOTTOM,
                convertDPtoPX(4, context)
            )
            fragmentConstraintSet.applyTo(constraintLayout)


            var previousView: TextView? = null
            val numOfEms=4

            val fields = financial.javaClass.kotlin.memberProperties

            for (f in fields) {
                val name = f.name

                val fieldValue =
                    (f.javaField?.annotations?.toList()?.get(0) as SerializedName).value

                /** MinValue EditText */
                val minValue = EditText(context)
                minValue.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    context.resources.getDimension(R.dimen.normal_font)
                )
                minValue.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                val minValueName="${name}_min"
                minValue.id = View.generateViewId()
                minValue.maxEms = numOfEms
                minValue.minEms = numOfEms
                minValue.isSingleLine = true
                minValue.isHorizontalScrollBarEnabled = true
                minValue.setText(getStringFromValue(filterViewModel.getValueOfVariable(minValueName)))
                minValue.addTextChangedListener(FilterTextWatcher(minValueName,filterViewModel))
                constraintLayout.addView(minValue)
                fragmentConstraintSet.clone(constraintLayout)
                if (previousView == null)
                    fragmentConstraintSet.connect(
                        minValue.id,
                        ConstraintSet.TOP,
                        divider.id,
                        ConstraintSet.BOTTOM,
                        convertDPtoPX(16, context)
                    )
                else
                    fragmentConstraintSet.connect(
                        minValue.id,
                        ConstraintSet.TOP,
                        previousView.id,
                        ConstraintSet.BOTTOM,
                        convertDPtoPX(8, context)
                    )
                fragmentConstraintSet.applyTo(constraintLayout)
                previousView = minValue

                /** Title TextView */
                val title = TextView(context)
                title.text = convertFromCamelCase(fieldValue)
                title.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    context.resources.getDimension(R.dimen.normal_font)
                )
                title.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                title.id = View.generateViewId()
                constraintLayout.addView(title)
                fragmentConstraintSet.clone(constraintLayout)
                fragmentConstraintSet.connect(
                    title.id,
                    ConstraintSet.START,
                    constraintLayoutID,
                    ConstraintSet.START,
                    convertDPtoPX(8, context)
                )
                fragmentConstraintSet.connect(
                    title.id,
                    ConstraintSet.BASELINE,
                    minValue.id,
                    ConstraintSet.BASELINE,
                    convertDPtoPX(0, context)
                )
                fragmentConstraintSet.applyTo(constraintLayout)

                /** Separator TextView */
                val separator = TextView(context)
                separator.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    context.resources.getDimension(R.dimen.normal_font)
                )
                separator.id = View.generateViewId()
                separator.text = separatorText
                separator.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                constraintLayout.addView(separator)
                fragmentConstraintSet.clone(constraintLayout)
                fragmentConstraintSet.connect(
                    minValue.id,
                    ConstraintSet.END,
                    separator.id,
                    ConstraintSet.START,
                    convertDPtoPX(8, context)
                )
                fragmentConstraintSet.connect(
                    separator.id,
                    ConstraintSet.BASELINE,
                    minValue.id,
                    ConstraintSet.BASELINE,
                    convertDPtoPX(0, context)
                )
                fragmentConstraintSet.applyTo(constraintLayout)

                /** MaxValue EditText */
                val maxValueName="${name}_max"
                val maxValue = EditText(context)
                maxValue.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    context.resources.getDimension(R.dimen.normal_font)
                )
                maxValue.id = View.generateViewId()
                maxValue.maxEms = numOfEms
                maxValue.minEms = numOfEms
                maxValue.isSingleLine = true
                maxValue.isHorizontalScrollBarEnabled = true
                maxValue.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                maxValue.setText(getStringFromValue(filterViewModel.getValueOfVariable(maxValueName)))
                maxValue.addTextChangedListener(FilterTextWatcher(maxValueName,filterViewModel))
                constraintLayout.addView(maxValue)
                fragmentConstraintSet.clone(constraintLayout)
                fragmentConstraintSet.connect(
                    maxValue.id,
                    ConstraintSet.END,
                    constraintLayoutID,
                    ConstraintSet.END,
                    convertDPtoPX(8, context)
                )
                fragmentConstraintSet.connect(
                    maxValue.id,
                    ConstraintSet.BASELINE,
                    minValue.id,
                    ConstraintSet.BASELINE,
                    convertDPtoPX(0, context)
                )
                fragmentConstraintSet.connect(
                    separator.id,
                    ConstraintSet.END,
                    maxValue.id,
                    ConstraintSet.START,
                    convertDPtoPX(8, context)
                )
                fragmentConstraintSet.applyTo(constraintLayout)
            }
        }
    }
}