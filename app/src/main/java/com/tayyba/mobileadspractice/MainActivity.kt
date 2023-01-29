package com.tayyba.mobileadspractice

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.tayyba.mobileadspractice.databinding.ActivityMainBinding


const val GAME_LENGTH_MILLISECONDS = 3000L
const val AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var interstitialAd: InterstitialAd? = null
    private var countdownTimer: CountDownTimer? = null
    private var gameIsInProgress = false
    private var adIsLoading: Boolean = false
    private var timerMilliseconds = 0L
    private var TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Log.d(TAG, "Google Mobile Ads SDK Version: " + MobileAds.getVersion())

        MobileAds.initialize(
            this
        ) { }
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder().setTestDeviceIds(listOf("ABCDEF012345")).build()
        )

        binding.retryButton.setOnClickListener {
            showInterstitial()
        }
    }

    private fun loadAd() {

        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            AD_UNIT_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError.message.let { Log.d(TAG, it) }
                    interstitialAd = null
                    adIsLoading = false
                    val error =
                        "domain: ${adError.domain}, code: ${adError.code}, " + "message: ${adError.message}"
                    Toast.makeText(
                        this@MainActivity,
                        "onAdFailedToLoad() with error $error",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAdLoaded(ad: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded.")
                    interstitialAd = ad
                    adIsLoading = false
                    Toast.makeText(this@MainActivity, "onAdLoaded()", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    // Create the game timer, which counts down to the end of the level
    // and shows the "retry" button.
    private fun createTimer(milliseconds: Long) {
        countdownTimer?.cancel()

        countdownTimer =
            object : CountDownTimer(milliseconds, 50) {
                override fun onTick(millisUntilFinished: Long) {
                    timerMilliseconds = millisUntilFinished
                }

                override fun onFinish() {
                    gameIsInProgress = false
                    Toast.makeText(applicationContext, "done ", Toast.LENGTH_SHORT).show()
                    binding.retryButton.visibility = View.VISIBLE
                }
            }
    }

    private fun showInterstitial() {
        if (interstitialAd != null) {
            interstitialAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Ad was dismissed.")
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        interstitialAd = null
                        loadAd()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        Log.d(TAG, "Ad failed to show.")
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        interstitialAd = null
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d(TAG, "Ad showed fullscreen content.")
                        // Called when ad is dismissed.
                    }
                }
            interstitialAd?.show(this)
        } else {
            Toast.makeText(this, "Ad wasn't loaded.", Toast.LENGTH_SHORT).show()
            startGame()
        }
    }

    // Request a new ad if one isn't already loaded, hide the button, and kick off the timer.
    private fun startGame() {
        if (!adIsLoading && interstitialAd == null) {
            adIsLoading = true
            loadAd()
        }

        binding.retryButton.visibility = View.INVISIBLE
        resumeGame(GAME_LENGTH_MILLISECONDS)
    }

    private fun resumeGame(milliseconds: Long) {
        // Create a new timer for the correct length and start it.
        gameIsInProgress = true
        timerMilliseconds = milliseconds
        createTimer(milliseconds)
        countdownTimer?.start()
    }

    // Resume the game if it's in progress.
    public override fun onResume() {
        super.onResume()

        if (gameIsInProgress) {
            resumeGame(timerMilliseconds)
        }
    }

    // Cancel the timer if the game is paused.
    public override fun onPause() {
        countdownTimer?.cancel()
        super.onPause()
    }
}