package com.alphavantage.stocksappdemo.domain.model

import java.time.LocalDateTime

data class IntradayData(
    val datetime: LocalDateTime,
    val close: Double
) {
}