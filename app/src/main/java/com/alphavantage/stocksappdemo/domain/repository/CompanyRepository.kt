package com.alphavantage.stocksappdemo.domain.repository

import com.alphavantage.stocksappdemo.domain.model.CompanyInfo
import com.alphavantage.stocksappdemo.domain.model.CompanyListing
import com.alphavantage.stocksappdemo.domain.model.IntradayData
import com.alphavantage.stocksappdemo.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CompanyRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ) : Flow<Resource<List<CompanyListing>>>


    suspend fun getCompanyInfo(
        symbol: String
    ) : Flow<Resource<CompanyInfo>>

    suspend fun getIntradayData(
        symbol: String
    ) : Flow<Resource<List<IntradayData>>>



}