package com.chethan.camerax.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.chethan.camerax.API_REST_URL
import com.chethan.camerax.NetWorkApi
import com.chethan.camerax.db.AppDatabase
import com.chethan.camerax.db.ProductsDao
import com.chethan.demoproject.utils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideGithubService(): NetWorkApi {
        val retrofit =
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .baseUrl(API_REST_URL)
                .build()
        return retrofit.create(NetWorkApi::class.java)
    }


    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "CameraX.db")
            .fallbackToDestructiveMigration()
            .build()
    }


    @Singleton
    @Provides
    fun provideProductsDao(db: AppDatabase): ProductsDao {
        return db.userProductsDao()
    }

    @Singleton
    @Provides
    fun provideContext(app: Application): Context {
        return app
    }

}
