package com.alphavantage.stocksappdemo.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.alphavantage.stocksappdemo.BuildConfig
import com.alphavantage.stocksappdemo.data.local.CompanyDatabase
import com.alphavantage.stocksappdemo.data.remote.StockApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideStockApi(): StockApi {

        val httpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor()
                .apply { level =
                    if(BuildConfig.DEBUG)
                        HttpLoggingInterceptor.Level.BODY
                    else
                        HttpLoggingInterceptor.Level.BASIC
                })

        return Retrofit.Builder()
            .baseUrl(StockApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(StockApi::class.java)
    }


    @Provides
    @Singleton
    fun provideCompanyDatabase(app: Application) : CompanyDatabase {
        return Room.databaseBuilder(
            app,
            CompanyDatabase::class.java,
            "company_list.db"
        ).build()
    }

}