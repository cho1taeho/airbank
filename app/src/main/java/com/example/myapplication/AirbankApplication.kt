package com.example.myapplication

import android.app.Application
import com.example.myapplication.Util.PreferenceUtil
import dagger.hilt.android.HiltAndroidApp
@HiltAndroidApp
class AirbankApplication : Application() {
    companion object {
        lateinit var  prefs: PreferenceUtil
    }
    override fun onCreate() {
        super.onCreate()
        prefs = PreferenceUtil(applicationContext)
    }
}
