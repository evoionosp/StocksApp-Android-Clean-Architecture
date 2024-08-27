package com.alphavantage.stocksappdemo.data.remote.dto

data class IntradayDataDto(
    val timestamp: String,
    val close: String
) {
}