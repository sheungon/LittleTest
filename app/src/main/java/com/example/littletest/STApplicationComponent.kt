package com.example.littletest

import com.example.littletest.app.AppModule
import com.example.littletest.contributor.AppActivitiesContributorModule
import com.example.littletest.contributor.FragmentsContributorModule
import com.example.sdk.SDKComponent
import com.example.sdk.scope.AppScope
import com.squareup.picasso.Picasso
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import java.io.File

@AppScope
@Component(
    dependencies = [SDKComponent::class],
    modules = [
        AndroidSupportInjectionModule::class,
        AppActivitiesContributorModule::class,
        FragmentsContributorModule::class,
        AppModule::class]
)
interface STApplicationComponent : AndroidInjector<STApplication>, SDKComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: STApplication): Builder

        fun sdkComponent(sdkComponent: SDKComponent): Builder

        fun build(): STApplicationComponent
    }

    val picasso: Picasso

    @get:AppModule.AppPrivateDirectory
    val privateDir: File?
}