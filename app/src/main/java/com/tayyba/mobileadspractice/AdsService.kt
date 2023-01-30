package com.tayyba.mobileadspractice

import android.app.Service
import android.content.Intent
import android.os.IBinder

class AdsService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}