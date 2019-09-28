package com.chethan.camerax.repository

import androidx.lifecycle.LiveData
import com.chethan.camerax.ApiSuccessResponse
import com.chethan.camerax.AppExecutors
import com.chethan.camerax.NetWorkApi
import com.chethan.camerax.db.ProductsDao
import com.chethan.camerax.model.ConsumptionPayload
import com.chethan.camerax.model.Products
import com.chethan.camerax.testing.OpenForTesting
import com.chethan.demoproject.utils.RateLimiter
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton



@Singleton
@OpenForTesting
class ProductsRepository @Inject constructor(
    private val appExecutors: AppExecutors,
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
