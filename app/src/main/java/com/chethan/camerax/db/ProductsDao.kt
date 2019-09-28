package com.chethan.camerax.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chethan.camerax.model.Products
import com.chethan.camerax.testing.OpenForTesting


@Dao
@OpenForTesting
abstract class ProductsDao {

    // to insert single users
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertProducts(vararg repos: Products)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertProducts(repositories: List<Products>)

    // insert users if not exist
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun createProductsIfNotExists(userDetails: Products): Long

    // to delete users
    @Delete
    abstract fun delete(item: Products)

    // to delete whole category list
    @Query("DELETE FROM Products")
    abstract fun deleteAll()

    @Query("SELECT * FROM Products WHERE `id`  in (:productIds)")
    abstract fun loadProducts(productIds: List<String>): LiveData<List<Products>>

}
