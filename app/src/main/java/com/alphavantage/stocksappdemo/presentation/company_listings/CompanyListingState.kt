package com.alphavantage.stocksappdemo.presentation.company_listings

import com.alphavantage.stocksappdemo.domain.model.CompanyListing

data class CompanyListingState (
    val listing: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = ""
)