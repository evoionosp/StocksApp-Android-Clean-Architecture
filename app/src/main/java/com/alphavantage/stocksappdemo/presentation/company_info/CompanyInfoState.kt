package com.alphavantage.stocksappdemo.presentation.company_info

import com.alphavantage.stocksappdemo.domain.model.CompanyInfo
import com.alphavantage.stocksappdemo.domain.model.CompanyListing
import com.alphavantage.stocksappdemo.domain.model.IntradayData
import com.alphavantage.stocksappdemo.utils.Resource

data class CompanyInfoState (
    val stockInfo: List<IntradayData> = emptyList(),
    val companyInfo: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)