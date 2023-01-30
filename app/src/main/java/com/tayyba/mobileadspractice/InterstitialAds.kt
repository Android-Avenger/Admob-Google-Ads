package com.tayyba.mobileadspractice

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.tayyba.mobileadspractice.databinding.ActivityInterstialAdsBinding

class InterstitialAds : AppCompatActivity() {

    lateinit var mBinding: ActivityInterstialAdsBinding
    private var mInterstitialAd: InterstitialAd? = null
    lateinit var dialog: Dialog
    private val TAG = "InterstitialAds"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityInterstialAdsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        MobileAds.initialize(this)

        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder().setTestDeviceIds(listOf("ABCDEF012345")).build()
        )
//        val animationView = findViewById<LottieAnimationView>(R.id.animations_view)
//        animationView.setAnimation("animation.json")
//        animationView.playAnimation()

//        mBinding.animationsView.setAnimation("loading.json")
//        mBinding.animationsView.playAnimation()

        mBinding.adsTest.setOnClickListener {
            displayInterstitial()
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
}