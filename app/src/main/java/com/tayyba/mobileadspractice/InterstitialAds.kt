package com.tayyba.mobileadspractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.tayyba.mobileadspractice.databinding.ActivityInterstialAdsBinding
import kotlinx.coroutines.*

class InterstitialAds : AppCompatActivity() {

    lateinit var mBinding: ActivityInterstialAdsBinding

    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityInterstialAdsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        MobileAds.initialize(this)

        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder().setTestDeviceIds(listOf("ABCDEF012345")).build()
        )

        mBinding.adsLoad.setOnClickListener {

                loadAds()
                displayInterstitial()

        }

    }

     fun loadAds() {

        val adsRequest = AdRequest.Builder().build()

        InterstitialAd.load(this, getString(R.string.interstitial), adsRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(p0: InterstitialAd) {
                    mInterstitialAd = p0
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                }
            })

    }

     fun showAds() {
        mInterstitialAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                super.onAdClicked()
            }

            override fun onAdDismissedFullScreenContent() {
                CoroutineScope(Dispatchers.Default).launch {
                    loadAds()
                }

                mInterstitialAd = null
//                startActivity(Intent(applicationContext, MainActivity::class.java))
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                mInterstitialAd = null
                Toast.makeText(
                    applicationContext,
                    "${p0.cause}   ${p0.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onAdImpression() {
                super.onAdImpression()
            }

            override fun onAdShowedFullScreenContent() {

                Toast.makeText(applicationContext, "Ad has been loaded", Toast.LENGTH_SHORT).show()
            }
        }
        mInterstitialAd!!.show(this)
    }

    fun displayInterstitial() {
        if (mInterstitialAd != null) {
            showAds()
        } else {
            Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
        }
    }
}