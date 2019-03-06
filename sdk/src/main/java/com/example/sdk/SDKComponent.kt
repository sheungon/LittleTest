package com.example.sdk

import android.app.Application
import com.example.sdk.api.ApiModule
import com.example.sdk.api.GoodService
import com.example.sdk.scope.SDKScope
import dagger.BindsInstance
import dagger.Component

@SDKScope
@Component(
    modules = [ApiModule::class]
)
interface SDKComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): SDKComponent
    }

    val goodService: GoodService
}