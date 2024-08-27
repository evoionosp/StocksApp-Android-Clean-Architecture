package com.alphavantage.stocksappdemo.presentation.company_listings

sealed class CompanyListingEvent {
    object onRefresh: CompanyListingEvent()
    data class OnSearchQueryChange(val query: String): CompanyListingEvent()
}