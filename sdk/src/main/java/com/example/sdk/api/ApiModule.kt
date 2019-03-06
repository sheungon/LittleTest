package com.example.sdk.api

import com.example.sdk.scope.SDKScope
import dagger.Module
import dagger.Provides

@Module
class ApiModule {
    @Provides
    @SDKScope
    fun goodService() = GoodService()
}