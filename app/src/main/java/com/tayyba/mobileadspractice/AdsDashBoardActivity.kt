package com.tayyba.mobileadspractice

import android.content.Intent
import android.os.Build.VERSION_CODES.N
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tayyba.mobileadspractice.databinding.ActivityAdsDashBoardBinding

class AdsDashBoardActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityAdsDashBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAdsDashBoardBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.Banners.setOnClickListener {
            startActivity(Intent(applicationContext, BannersActivity::class.java))
        }
        mBinding.Interstitial.setOnClickListener {
            showInterstitial(this){
            startActivity(Intent(applicationContext, InterstitialAds::class.java))
            }
        }
        mBinding.Native.setOnClickListener {
            startActivity(Intent(applicationContext, NativeAdsActivity::class.java))
        }
    }
}