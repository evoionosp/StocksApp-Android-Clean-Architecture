package com.alphavantage.stocksappdemo.domain.model

data class CompanyListing(
    val name: String,
    val symbol: String,
    val exchange: String
)