package com.chethan.airchip.di

import com.chethan.airchip.view.ScanTheQRCode.ScanQRCodeFragment
import com.chethan.airchip.view.ScanTheQRCode.UserOrderListFragment
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
