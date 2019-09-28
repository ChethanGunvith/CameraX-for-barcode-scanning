package com.chethan.camerax.di

import com.chethan.camerax.view.ScanTheQRCode.ScanQRCodeFragment
import com.chethan.camerax.view.ScanTheQRCode.UserOrderListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun scanQRCodeFragment(): ScanQRCodeFragment


    @ContributesAndroidInjector
    abstract fun userOrderListFragment(): UserOrderListFragment
}
