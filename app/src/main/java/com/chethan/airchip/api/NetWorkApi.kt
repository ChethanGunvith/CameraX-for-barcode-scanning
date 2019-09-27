package com.chethan.airchip

import androidx.lifecycle.LiveData
import com.chethan.airchip.model.Products
import retrofit2.http.GET

/**
 * Created by Chethan on 5/3/2019.
 * This interface contains the definition list of all the network endpoints used by the App.
 * Ref: Retrofit
 */
interface NetWorkApi {

    @GET("v2/venues/search")
    fun getUserInfo(
    ): LiveData<ApiResponse<Products>>


    @GET("products")
    fun getProducts(
    ): LiveData<ApiResponse<List<Products>>>
}