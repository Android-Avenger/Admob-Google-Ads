package com.tayyba.mobileadspractice

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.tayyba.mobileadspractice.databinding.ActivityBannersBinding

class BannersActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityBannersBinding
    lateinit var AdView: AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityBannersBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        MobileAds.initialize(this) {

        }
        val adRequest = AdRequest.Builder().build()

        mBinding.simpleBanner.loadAd(adRequest)
        mBinding.largeBanner.loadAd(adRequest)
        mBinding.mediumBanner.loadAd(adRequest)
        mBinding.smartBanner.loadAd(adRequest)

    }
}