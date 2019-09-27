package com.chethan.airchip.repository

import androidx.lifecycle.LiveData
import com.chethan.airchip.ApiSuccessResponse
import com.chethan.airchip.NetWorkApi
import com.chethan.airchip.db.ProductsDao
import com.chethan.airchip.model.ConsumptionPayload
import com.chethan.airchip.model.Products
import com.chethan.airchip.testing.OpenForTesting
import com.chethan.demoproject.utils.RateLimiter
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton



@Singleton
@OpenForTesting
class ProductsRepository @Inject constructor(
    private val appExecutors: com.chethan.airchip.AppExecutors,
    private val productsDao: ProductsDao,
    private val netWorkApi: NetWorkApi
) {

    private val repoListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)
    /**
     * Expecting Master json response to deliver list of posts category
     */
    fun getProducts(productIds: List<ConsumptionPayload>): LiveData<Resource<List<Products>>> {
        return object : NetworkBoundResource<List<Products>, List<Products>>(appExecutors) {

            // inserting into data base
            override fun saveCallResult(item: List<Products>) {
                productsDao.insertProducts(item)
            }

            override fun shouldFetch(data: List<Products>?) = true

            override fun loadFromDb(): LiveData<List<Products>> {
                var productIdList = ArrayList<String>()
                for (item in productIds)
                    productIdList.add(item.id)

                return productsDao.loadProducts(productIdList)
            }

            override fun createCall() = netWorkApi.getProducts()

            override fun processResponse(response: ApiSuccessResponse<List<Products>>)
                    : List<Products> {
                val body = response.body
                return body
            }

        }.asLiveData()
    }


}
