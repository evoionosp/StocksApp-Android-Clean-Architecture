package com.alphavantage.stocksappdemo.data.remote

import com.alphavantage.stocksappdemo.data.remote.dto.CompanyInfoDto
import com.alphavantage.stocksappdemo.domain.model.CompanyInfo
import com.alphavantage.stocksappdemo.utils.Resource
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("query")
    suspend fun getListings(
        @Query("function") function: String = "LISTING_STATUS",
        @Query("apikey") apiKey: String = API_KEY
    ) : ResponseBody

    @GET("query")
    suspend fun getCompanyInfo(
        @Query("function") function: String = "OVERVIEW",
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = API_KEY
    ) : CompanyInfoDto

    @GET("query")
    suspend fun getIntradayData(
        @Query("function") function: String = "TIME_SERIES_INTRADAY",
        @Query("symbol") symbol: String,
        @Query("interval") interval: String = "60min",
        @Query("apikey") apiKey: String = API_KEY,
        @Query("datatype") datatype: String = "csv"
    ) : ResponseBody

    companion object {
        const val BASE_URL = "https://www.alphavantage.co"
        const val API_KEY = "" //Update your API key from AlphaVantage
    }

}