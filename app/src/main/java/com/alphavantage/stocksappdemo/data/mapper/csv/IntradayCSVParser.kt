package com.alphavantage.stocksappdemo.data.mapper.csv

import com.alphavantage.stocksappdemo.data.mapper.toIntradayData
import com.alphavantage.stocksappdemo.data.remote.dto.IntradayDataDto
import com.alphavantage.stocksappdemo.domain.model.CompanyListing
import com.alphavantage.stocksappdemo.domain.model.IntradayData
import com.opencsv.CSVReader
import com.opencsv.bean.ColumnPositionMappingStrategyBuilder
import com.opencsv.bean.CsvToBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntradayCSVParser @Inject constructor (): CSVParser<IntradayData> {

    override suspend fun parse(stream: InputStream): List<IntradayData> {
        val csvReader = CSVReader(InputStreamReader(stream))

        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0)
                    val close =  line.getOrNull(4)

                    IntradayDataDto(
                        timestamp = timestamp ?: return@mapNotNull null,
                        close = close ?: return@mapNotNull null
                    ).toIntradayData()
                }
        }
            .also {
                csvReader.close()
            }


    }
}