package com.seedbx.stockmonitor

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class GetData(private val context: Context, private val activity: AppCompatActivity, private val listener: OnResponse) {

    private val queue: RequestQueue = Volley.newRequestQueue(context)

    private val url="http://192.168.1.12:3000/stock/get/data"

    private val TAG = "GetData"

    private val SPLASH_TIMEOUT=500L

    interface OnResponse{
        fun onResponseComplete(activity:AppCompatActivity, response:String, timeout: Long)
        fun onResponseError(activity: AppCompatActivity, timeout: Long)
    }

    fun getData() {

        val stringRequest = StringRequest(Request.Method.POST, url,
                { response ->
                    listener.onResponseComplete(activity, response, SPLASH_TIMEOUT)
                },
                { error ->
                    Log.e(TAG, "getData: error is $error")
                    listener.onResponseError(activity, SPLASH_TIMEOUT)
                }
        )

        stringRequest.retryPolicy = DefaultRetryPolicy(
                1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        queue.add(stringRequest)
    }

}