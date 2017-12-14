package com.example.littletest.app

import com.example.littletest.STApplication
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * App module
 * @author John
 */

@Module
class AppModule(_application: STApplication) {

    internal val application = _application
        @Provides
        @Singleton
        get

    @AppPrivateDirectory
    @Provides
    @Singleton
    internal fun provideAppPrivateDirectory(): File = application.getExternalFilesDir(null)

    @Qualifier
    @Retention(AnnotationRetention.SOURCE)
    annotation class AppPrivateDirectory
}
