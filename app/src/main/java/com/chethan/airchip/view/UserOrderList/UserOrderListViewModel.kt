package com.chethan.airchip.view.ScanTheQRCode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.chethan.airchip.model.ConsumptionPayload
import com.chethan.airchip.model.Products
import com.chethan.airchip.repository.ProductsRepository
import com.chethan.airchip.repository.Resource
import com.chethan.airchip.testing.OpenForTesting
import javax.inject.Inject

/**
 * Created by Chethan on 9/17/2019.
 */

@OpenForTesting
class UserOrderListViewModel @Inject constructor(productsRepository: ProductsRepository) : ViewModel() {

    private val _productId = MutableLiveData<List<ConsumptionPayload>>()

    val products: LiveData<Resource<List<Products>>> = Transformations
        .switchMap(_productId) { input ->
            productsRepository.getProducts(input)
        }


    fun setProductIds(productId: List<ConsumptionPayload>) {
        if (_productId.value == productId) {
            return
        }
        _productId.value = productId
    }
}


