package com.chethan.airchip

import android.app.Activity
import android.app.Application
import com.chethan.airchip.di.AppInjector
import com.facebook.stetho.Stetho
import com.google.firebase.FirebaseApp
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Chethan on 9/17/2019.
 */

class MainApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
        }
        AppInjector.init(this)


    }

    override fun activityInjector() = dispatchingAndroidInjector
}
