package com.seedbx.stockmonitor

import android.content.Context
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class GetData(context: Context, private val listener: OnResponse) {

    /** A [RequestQueue] to store all the network requests */
    private val queue: RequestQueue = Volley.newRequestQueue(context)

    /** A [String] which URL of the API end-point which returns list of StockData */
    private val url="${context.resources.getString(R.string.ip_address)}/stock/get/data"

    /** Interface containing functions which describe how response should be handled */
    interface OnResponse{

        /**
         * Handles successful completion of response
         *
         * @param response A [String], the response of the POST request
         */
        fun onResponseComplete(response:String)

        /**
         * Handles unsuccessful completion of response
         */
        fun onResponseError()
    }

    /**
     * Sends a POST request to url and handles its response
     */
    fun getData() {

        val stringRequest = StringRequest(
            Request.Method.POST, url,
                { response ->
                    listener.onResponseComplete(response)
                },
                {
                    listener.onResponseError()
                }
        )

        /** Retry policy for request */
        stringRequest.retryPolicy = DefaultRetryPolicy(
                1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        queue.add(stringRequest)
    }

}