package com.example.sdk

import android.app.Application

/**
 * SDK
 * @author sheungon
 * */
object SDK {

    @Volatile
    lateinit var sdkComponent: SDKComponent
        private set

    @Synchronized
    fun init(application: Application): SDK {
        if (!::sdkComponent.isInitialized) {
            sdkComponent = DaggerSDKComponent.builder()
                .application(application)
                .build()
        }
        return this
    }
}