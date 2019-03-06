package com.example.littletest.app

import com.example.littletest.STApplication
import com.example.sdk.scope.AppScope
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Qualifier

/**
 * App module
 * @author John
 */

@Module
class AppModule() {

    @AppPrivateDirectory
    @Provides
    @AppScope
    fun provideAppPrivateDirectory(application: STApplication): File? =
        application.getExternalFilesDir(null)

    @Provides
    @AppScope
    fun picasso(application: STApplication) = Picasso.Builder(application).build()

    @Qualifier
    @Retention(AnnotationRetention.SOURCE)
    annotation class AppPrivateDirectory
}
