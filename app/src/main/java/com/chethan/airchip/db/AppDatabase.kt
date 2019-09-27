package com.chethan.airchip.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chethan.airchip.model.Products

/**
 * Created by Chethan on 9/17/2019.
 */

@Database(
    entities = [
        Products::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(ProviderConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userProductsDao(): ProductsDao
}
