package com.example.littletest

import android.app.Application
import android.os.Build
import android.webkit.WebView
import com.example.littletest.app.AppComponent
import com.example.littletest.app.AppModule
import com.example.littletest.app.DaggerAppComponent
import com.sotwtm.util.Log

/**
 * @author John
 */
class STApplication : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        instance = this
        super.onCreate()

        Log.defaultLogTag = LOG_TAG
        Log.logLevel = if (BuildConfig.DEBUG) Log.VERBOSE else Log.NONE

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(Log.isDebuggable)
        }

        initComponent()
    }

    private fun initComponent() {

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(instance))
            .build()
        appComponent.inject(this)
    }

    companion object {

        internal const val LOG_TAG = "SimpleTest"

        @JvmStatic
        lateinit var instance: STApplication
            private set
    }
}