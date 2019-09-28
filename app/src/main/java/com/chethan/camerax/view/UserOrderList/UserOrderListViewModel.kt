package com.chethan.camerax.view.ScanTheQRCode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.chethan.camerax.model.ConsumptionPayload
import com.chethan.camerax.model.Products
import com.chethan.camerax.repository.ProductsRepository
import com.chethan.camerax.repository.Resource
import com.chethan.camerax.testing.OpenForTesting
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


