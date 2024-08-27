package com.alphavantage.stocksappdemo.data.mapper

import com.alphavantage.stocksappdemo.data.remote.dto.IntradayDataDto
import com.alphavantage.stocksappdemo.domain.model.IntradayData
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale



fun IntradayDataDto.toIntradayData(): IntradayData {

    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.US)

    val localDateTime: LocalDateTime? = try {
        LocalDateTime.parse(timestamp, formatter)
    } catch (e: DateTimeParseException) {
        Timber.tag("IntradayMapper").e("Error while parsing text to date-time: $timestamp")
        e.printStackTrace()
        null
    }

    return IntradayData(
        datetime = localDateTime ?: LocalDateTime.now(),
        close=  close.toDoubleOrNull() ?: Double.NaN)

}