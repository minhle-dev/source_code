package com.minhle.flickkfinal.application

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class FlickkApplication : Application() {
    companion object {
        lateinit var instance: FlickkApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}
