package com.example.littletest.app

import com.example.littletest.STApplication
import dagger.Component
import javax.inject.Singleton

/**
 * @author John
 */

@Singleton
@Component(
    modules = arrayOf(
        AppModule::class
    )
)
interface AppComponent {

    val application: STApplication

    fun inject(application: STApplication)
}