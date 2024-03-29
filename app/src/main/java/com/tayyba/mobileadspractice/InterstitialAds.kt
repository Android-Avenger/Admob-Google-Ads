package com.tayyba.mobileadspractice

import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.os.IResultReceiver.Default
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.tayyba.mobileadspractice.databinding.ActivityInterstialAdsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.timer

class InterstitialAds : AppCompatActivity() {

    lateinit var mBinding: ActivityInterstialAdsBinding
    private var mInterstitialAd: InterstitialAd? = null
    lateinit var dialog: Dialog
    private val TAG = "InterstitialAds"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityInterstialAdsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


        CoroutineScope(Dispatchers.Main).launch {
            playAnimation()
        }


    }

    fun loadAds() {

        val adsRequest = AdRequest.Builder().build()

        InterstitialAd.load(this, getString(R.string.interstitial), adsRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(p0: InterstitialAd) {
                    mInterstitialAd = p0
                    Log.d(TAG, "onAdLoaded: ")
                }

                override fun onAdFailedToLoad(e: LoadAdError) {
                    Log.d(TAG, "onAdFailedToLoad: ${e.message}")
                }
            })

    }

    fun showAds() {

        mInterstitialAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                Log.d(TAG, "onAdClicked: ")
            }

            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "onAdDismissedFullScreenContent: ")
            }

            override fun onAdFailedToShowFullScreenContent(error: AdError) {
                Log.d(TAG, "onAdFailedToShowFullScreenContent: ${error.message}")
            }

            override fun onAdImpression() {
                Log.d(TAG, "onAdImpression: ")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "onAdShowedFullScreenContent: ")
            }
        }
        mInterstitialAd!!.show(this)
    }

    private fun displayInterstitial() {
        loadAds()
        if (mInterstitialAd != null) {
            Log.d(TAG, "displayInterstitial: ")
            showAds()
        } else {
            Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    suspend fun playAnimation() {

        val animationView = findViewById<LottieAnimationView>(R.id.animation)
        animationView.setAnimationFromUrl("https://assets4.lottiefiles.com/packages/lf20_vkck9pkv.json")
        animationView.speed = 0.5f
        animationView.playAnimation()

        val animation = findViewById<LottieAnimationView>(R.id.animation_welldone)
        val timer = object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {

                animationView.visibility = View.GONE

                animation.setAnimationFromUrl("https://assets8.lottiefiles.com/packages/lf20_tzgci2yi.json")
                animation.playAnimation()
            }
        }
        timer.start()
    }

    override fun onBackPressed() {
       finish()
    }
}