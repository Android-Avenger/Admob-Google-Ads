package com.tayyba.mobileadspractice

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

fun Activity.showInterstitial(
    activity: Activity,
    onAdsDismissedClick: () -> Unit
) {

    var mInterstitialAd: InterstitialAd? = null
    val TAG = "InterstitialAds"

    MobileAds.initialize(this)

    MobileAds.setRequestConfiguration(
        RequestConfiguration.Builder().setTestDeviceIds(listOf("ABCDEF012345")).build()
    )

    val adsRequest = AdRequest.Builder().build()


    val fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdClicked() {

            Log.d(TAG, "onAdClicked: ")
        }

        override fun onAdDismissedFullScreenContent() {
            onAdsDismissedClick()
            Log.d(TAG, "onAdDismissedFullScreenContent: ")
        }

        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            Log.d(TAG, "onAdFailedToShowFullScreenContent: ")
        }

        override fun onAdImpression() {
            Log.d(TAG, "onAdImpression: ")
        }

        override fun onAdShowedFullScreenContent() {
            Log.d(TAG, "onAdShowedFullScreenContent: ")
        }
    }
    InterstitialAd.load(this, getString(R.string.interstitial), adsRequest,
        object : InterstitialAdLoadCallback() {
            override fun onAdLoaded(p0: InterstitialAd) {
                mInterstitialAd = p0
                mInterstitialAd!!.fullScreenContentCallback = fullScreenContentCallback
                mInterstitialAd!!.show(activity)
                Log.d(TAG, "onAdLoaded: ")
            }

            override fun onAdFailedToLoad(e: LoadAdError) {
                Log.d(TAG, "onAdFailedToLoad: ${e.message}")
            }
        })

}