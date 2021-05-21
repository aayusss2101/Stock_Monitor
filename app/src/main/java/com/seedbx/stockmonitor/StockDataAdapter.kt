package com.seedbx.stockmonitor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.VolleyError

class StockDataAdapter(
    private val context: Context,
    private val resource: Int,
    private val stockDataList: ArrayList<StockData>
) : RecyclerView.Adapter<StockDataAdapter.StockViewHolder>(),AddToWishlistClickListener.OnResponse {

    /** A [LayoutInflater] used to inflate layouts */
    private val inflater = LayoutInflater.from(context)

    /** A [ImageView] used to display the like image */
    private lateinit var imageView:ImageView

    class StockViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        /** A [TextView] used to store the field companyName */
        val companyName: TextView = v.findViewById(R.id.stockList_companyName)

        /** A [TextView] used to store the field stockExchange */
        val stockExchange: TextView = v.findViewById(R.id.stockList_stockExchange)

        /** A [TextView] used to store the field tickerSymbol */
        val tickerSymbol: TextView = v.findViewById(R.id.stockList_tickerSymbol)

        /** A [TextView] used to store the field priceToEarningsRatioTTM */
        val peRatioValue: TextView = v.findViewById(R.id.stockList_peRatioValue)

        /** A [TextView] used to store the field priceToBookFY */
        val pbRatioValue: TextView = v.findViewById(R.id.stockList_pbRatioValue)

        /** A [TextView] used to store the field fiveTwoWeekHigh */
        val fiftyTwoWkHighValue: TextView = v.findViewById(R.id.stockList_fiftyTwoWkHighValue)

        /** A [TextView] used to store the field fiveTwoWeekLow */
        val fiftyTwoWkLowValue: TextView = v.findViewById(R.id.stockList_fiftyTwoWkLowValue)

        /** A [ImageView] used to store the current like drawable being shown */
        val addToWishlistImageView: ImageView = v.findViewById(R.id.stockList_addToWishlistImageView)
    }

    /**
     * Creates the StockViewHolder with parent as 'parent'
     *
     * @param parent A [ViewGroup] which acts as the parent view for the FinancialViewHolder
     * @param viewType A [Int] specifying the type of the view for the new view
     * @return A [StockViewHolder] which is the newly created view holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view = inflater.inflate(resource, parent, false)
        return StockViewHolder(view)
    }

    /**
     * Returns the id of the appropriate image resource depending on the value of addToWishlist
     *
     * @param addToWishlist A [Boolean] specifying whether the field addTooWishlist is true or false
     * @return A [Int], the id of the drawable resource
     */
    private fun getImageResource(addToWishlist: Boolean): Int {
        return if (addToWishlist)
            R.drawable.favourite_filled
        else
            R.drawable.favourite_hollow
    }

    /**
     * Binds data to the StockViewHolder at position 'position' in RecyclerView
     *
     * @param stockViewHolder A [StockViewHolder] which is the view holder for the item in the RecyclerView
     * @param position A [Int] specifying the position of the item in the RecyclerView
     */
    override fun onBindViewHolder(stockViewHolder: StockViewHolder, position: Int) {

        val currentStock = stockDataList[position]
        stockViewHolder.companyName.text = Helper.getStringFromValue(currentStock.companyName)
        stockViewHolder.stockExchange.text = Helper.getStringFromValue(currentStock.stockExchange)
        stockViewHolder.tickerSymbol.text = Helper.getStringFromValue(currentStock.tickerSymbol)
        stockViewHolder.peRatioValue.text = Helper.getStringFromValue(currentStock.valuation?.priceToEarningsRatioTTM)
        stockViewHolder.pbRatioValue.text = Helper.getStringFromValue(currentStock.valuation?.priceToBookFY)
        stockViewHolder.fiftyTwoWkHighValue.text = Helper.getStringFromValue(currentStock.priceHistory?.fiveTwoWeekHigh)
        stockViewHolder.fiftyTwoWkLowValue.text = Helper.getStringFromValue(currentStock.priceHistory?.fiveTwoWeekLow)

        stockViewHolder.addToWishlistImageView.setImageResource(getImageResource(currentStock.addToWishlist))
        stockViewHolder.addToWishlistImageView.setOnClickListener {

            imageView=it as ImageView
            (imageView).setImageResource(getImageResource(AddToWishlistClickListener.not(currentStock.addToWishlist)))

            val addToWishlistClickListener=AddToWishlistClickListener(context)
            addToWishlistClickListener.updateWishlistValue(currentStock,this)

        }

        /** Handles item click that is starts DetailAcitvity\ */
        stockViewHolder.itemView.setOnClickListener {
            StockDataClickListener().onClick(
                context,
                currentStock
            )
        }

    }

    /**
     * Handles the successful completion of request to modify the field addToWishlist
     *
     * @param currentStock A [StockData] whose field was modified
     */
    override fun onResponseComplete(currentStock: StockData) {
        currentStock.addToWishlist = AddToWishlistClickListener.not(currentStock.addToWishlist)
        DatabaseHelper.updateWishlist(currentStock._id,currentStock.addToWishlist)
    }

    /**
     * Handles the unsuccessful completion of request to modify the field addToWishlist
     *
     * @param error A [VolleyError], the error that occurred
     * @param addToWishlist A [Boolean] that represents the before modification value of field addToWishlist
     */
    override fun onResponseError(error:VolleyError, addToWishlist: Boolean) {
        imageView.setImageResource(getImageResource(addToWishlist))
        Toast.makeText(context,"Some Error Occurred",Toast.LENGTH_SHORT).show()
    }

    /**
     * Returns the number of items in stockDataList
     *
     * @return A [Int] equal to the size of stockDataList
     */
    override fun getItemCount(): Int {
        return stockDataList.size
    }

}