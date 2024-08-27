package com.alphavantage.stocksappdemo.data.mapper.csv

import java.io.InputStream

interface CSVParser <T> {

    suspend fun parse(stream: InputStream): List<T>
}