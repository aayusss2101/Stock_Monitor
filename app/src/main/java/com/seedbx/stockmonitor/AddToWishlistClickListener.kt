package com.seedbx.stockmonitor

import android.content.Context
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class AddToWishlistClickListener(var context: Context) {

    /** A [RequestQueue] to store all the network requests */
    private val queue: RequestQueue = Volley.newRequestQueue(context)

    /** A [String] which URL of the API end-point where Wishlist Value is updated */
    private val url = "${context.resources.getString(R.string.ip_address)}/stock/wishlist"

    /** Interface containing functions which describe how response should be handled */
    interface OnResponse{

        /**
         * Handles successful completion of request
         *
         * @param currentStock A [StockData] whose addToWishlist value was changed
         */
        fun onResponseComplete(currentStock: StockData)

        /**
         * Handles unsuccessful completion of request
         *
         * @param error A [VolleyError] indicating the error
         * @param addToWishlist A [Boolean]
         */
        fun onResponseError(error:VolleyError,addToWishlist: Boolean)
    }

    companion object{

        /**
         * Negates the value of addToWishlist
         *
         * @param addToWishlist A [Boolean] whose value is to be negated
         * @return A [Boolean], false if addToWishlist==true else true
         */
        fun not(addToWishlist: Boolean): Boolean {
            if (addToWishlist)
                return false
            return true
        }

    }

    /**
     * Updates Value of addToWishlist field for a StockData object
     *
     * @param currentStock A [StockData] whose addToWishlist value is to be updated
     * @param onResponse A [OnResponse] used to handle after response events
     */
    fun updateWishlistValue(currentStock:StockData,onResponse: OnResponse){

        val stringRequest = object : StringRequest(
            Method.POST, url,
            { response ->
                if(response=="success")
                    onResponse.onResponseComplete(currentStock)
                else
                    onResponse.onResponseError(VolleyError("Some error in database"),currentStock.addToWishlist)
            },
            { error ->
                onResponse.onResponseError(error, currentStock.addToWishlist)
            }
        ) {

            /**
             * Handle POST parameters
             *
             * @return postParams A [MutableMap]<[String],[String]> containing the post parameters
             */
            override fun getParams(): MutableMap<String, String> {
                val postParams = mutableMapOf<String, String>()
                postParams["id"] = currentStock._id
                return postParams
            }

        }

        /** Retry policy used */
        stringRequest.retryPolicy = DefaultRetryPolicy(
            1000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        queue.add(stringRequest)
    }
}