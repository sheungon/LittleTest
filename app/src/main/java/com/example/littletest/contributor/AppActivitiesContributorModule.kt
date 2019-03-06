package com.example.littletest.contributor

import com.example.littletest.main.MainActivity
import com.example.sdk.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * All activities contributor
 * @author sheungon
 * */
@Module
abstract class AppActivitiesContributorModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [])
    abstract fun mainActivity(): MainActivity
}