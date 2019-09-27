package com.chethan.airchip.di


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chethan.airchip.view.ScanTheQRCode.UserOrderListViewModel
import com.chethan.airchip.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(UserOrderListViewModel::class)
    abstract fun bindUserOrderListViewModel(userOrderListViewModel: UserOrderListViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
