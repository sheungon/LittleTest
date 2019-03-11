package com.example.littletest

import android.os.Build
import android.webkit.WebView
import com.example.sdk.SDK
import com.sotwtm.util.Log
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * @author John Tsai
 */
class STApplication : DaggerApplication() {

    lateinit var appComponent: STApplicationComponent
        private set

    override fun onCreate() {
        instance = this
        super.onCreate()

        Log.defaultLogTag = LOG_TAG
        Log.logLevel = if (BuildConfig.DEBUG) Log.VERBOSE else Log.NONE

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(Log.isDebuggable)
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>
     = DaggerSTApplicationComponent.builder()
        .application(this)
        .sdkComponent(SDK.init(this).sdkComponent)
        .build()
        .also {
            appComponent = it
        }

    companion object {

        internal const val LOG_TAG = "SimpleTest"

        @JvmStatic
        lateinit var instance: STApplication
            private set
    }
}