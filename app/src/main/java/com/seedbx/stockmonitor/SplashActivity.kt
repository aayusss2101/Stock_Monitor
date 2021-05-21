package com.seedbx.stockmonitor

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityOptionsCompat
import com.airbnb.lottie.LottieAnimationView
import com.google.gson.Gson
import org.json.JSONArray


class SplashActivity:AppCompatActivity(), GetData.OnResponse{

    /** A [Boolean] specifying whether the response has been handled or not */
    private var completed:Boolean=false

    /** A [LottieAnimationView] that is displayed on the SplashActivity */
    private lateinit var splashLoadingAnimation: LottieAnimationView

    /** A [ConstraintLayout] that is the root element in the layout for this SplashActivity */
    private lateinit var splashConstraintLayout:ConstraintLayout

    /**
     * Converts string JSON response to list of StockData objects
     *
     * @param response A [String], JSON response sent by the server
     * @return A [ArrayList]<[StockData]> containing all the StockData objects in the response
     */
    private fun responseToStockData(response: String):ArrayList<StockData>{

        val stockDataList=ArrayList<StockData>()

        val responses= JSONArray(response)
        val gson= Gson()

        for(i in 0 until responses.length()) {
            val stockData = gson.fromJson(responses.getJSONObject(i).toString(), StockData::class.java)
            Log.d("SplashActivity",stockData.toString())
            stockDataList.add(stockData)
        }

        return stockDataList

    }

    /**
     * Starts AppCompatActivity activity after a delay of timeout milliseconds
     *
     * @param activity A [AppCompatActivity] which is to be started
     * @param timeout A [Long] the delay after which to start activity
     * @param view A [View]
     */
    fun moveToHome(activity: AppCompatActivity, timeout: Long, view: View){
        Handler(Looper.getMainLooper()).postDelayed({

            val options= ActivityOptionsCompat.makeSceneTransitionAnimation(this,view,"transition")

            val revealX=view.x.toInt()+view.width/2
            val revealY=view.y.toInt()+view.height/2

            val intent= Intent(this, activity::class.java)
            intent.putExtra("revealX",revealX)
            intent.putExtra("revealY",revealY)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
            startActivity(intent,options.toBundle())
            this.finish()

        }, timeout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashConstraintLayout=findViewById(R.id.splashConstraintLayout)

        splashLoadingAnimation=findViewById(R.id.splashLoadingAnimation)
        splashLoadingAnimation.repeatCount=ValueAnimator.INFINITE
        splashLoadingAnimation.addAnimatorListener(object : Animator.AnimatorListener{

            /** Does Nothing */
            override fun onAnimationStart(animation: Animator?) {}

            /** Starts HomeActivity if completed==true else does nothing */
            override fun onAnimationEnd(animation: Animator?) {
                if(completed)
                    moveToHome(HomeActivity(),0, splashConstraintLayout)
            }

            /** Does Nothing */
            override fun onAnimationCancel(animation: Animator?) {}

            /** Does Nothing */
            override fun onAnimationRepeat(animation: Animator?) {}

        })

        val getData=GetData(this, this)
        getData.getData()

    }

    /**
     * Handles successful completion of the POST request
     *
     * @param response A [String] which is the response returned from the POST request
     */
    override fun onResponseComplete(response: String){

        val stockDataList=responseToStockData(response)

        DatabaseHelper.deleteAll()
        DatabaseHelper.insert(stockDataList.toTypedArray())
        DatabaseHelper.deleteFilter()

        completed=true
        splashLoadingAnimation.repeatCount=1
    }

    /**
     * Handles unsuccessful completion of the POST request
     */
    override fun onResponseError() {
        Toast.makeText(this,"Some Error Occurred", Toast.LENGTH_SHORT).show()
        DatabaseHelper.deleteFilter()
        completed=true
        splashLoadingAnimation.repeatCount=0
    }
}

/*
    fun screenWidth():Double{
        return Resources.getSystem().displayMetrics.widthPixels.toDouble()
    }

    fun screenHeight():Double{
        return Resources.getSystem().displayMetrics.heightPixels.toDouble()
    }

    fun createRevealCircle(){
        var x:Int=splashConstraintLayout.x.toInt()+splashConstraintLayout.width/2
        var y:Int=splashConstraintLayout.y.toInt()+splashConstraintLayout.height/2
        var endRadius=0f
        var startRadius:Float=hypot(screenHeight(),screenWidth()).toFloat()

        var circleReveal=ViewAnimationUtils.createCircularReveal(splashConstraintLayout,x,y,startRadius,endRadius)
        circleReveal.duration=5000
        circleReveal.addListener(object :Animator.AnimatorListener{

            override fun onAnimationStart(animation: Animator?) {
                Log.d("SplashActivity","circleRevealAnimation started with values x=$x y=$y startRadius=$startRadius and endRadius=$endRadius")
            }

            override fun onAnimationEnd(animation: Animator?) {
                Log.d("SplashActivity","circleRevealAnimation ended with values x=$x y=$y startRadius=$startRadius and endRadius=$endRadius")
            }

            override fun onAnimationCancel(animation: Animator?) {}

            override fun onAnimationRepeat(animation: Animator?) {}
        })

        circleReveal.start()
    }
 */