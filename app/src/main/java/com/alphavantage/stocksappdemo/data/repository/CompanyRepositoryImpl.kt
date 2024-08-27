package com.alphavantage.stocksappdemo.data.repository

import com.alphavantage.stocksappdemo.data.mapper.csv.CSVParser
import com.alphavantage.stocksappdemo.data.local.CompanyDatabase
import com.alphavantage.stocksappdemo.data.mapper.toCompanyInfo
import com.alphavantage.stocksappdemo.data.mapper.toCompanyListing
import com.alphavantage.stocksappdemo.data.mapper.toCompanyListingEntity
import com.alphavantage.stocksappdemo.data.mapper.toIntradayData
import com.alphavantage.stocksappdemo.data.remote.StockApi
import com.alphavantage.stocksappdemo.data.remote.dto.IntradayDataDto
import com.alphavantage.stocksappdemo.domain.model.CompanyInfo
import com.alphavantage.stocksappdemo.domain.model.CompanyListing
import com.alphavantage.stocksappdemo.domain.model.IntradayData
import com.alphavantage.stocksappdemo.domain.repository.CompanyRepository
import com.alphavantage.stocksappdemo.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val db: CompanyDatabase,
    private val companyListingParser: CSVParser<CompanyListing>,
    private val intradayDataParser: CSVParser<IntradayData>
) : CompanyRepository{


    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))

            val localListings = db.dao.searchCompanyListing(query).map {
                it.toCompanyListing() }




            emit(Resource.Success(
                data = localListings
            ))

            val isDbEmpty = localListings.isEmpty() && query.isBlank()

            if(fetchFromRemote || isDbEmpty) {
                val remoteListings: List<CompanyListing>? = try {
                    val response = api.getListings()
                    companyListingParser.parse(response.byteStream())
                } catch (e: IOException) {
                    e.printStackTrace()
                    emit(Resource.Error("IOException: Couldn't load data"))
                    null
                } catch (e: HttpException) {
                    e.printStackTrace()
                    emit(Resource.Error("HttpException: Couldn't load data"))
                    null
                }

                remoteListings?.let { listings ->

                    db.dao.clearCompanyListings()
                    db.dao.insertCompanyListings(
                        listings.map { it.toCompanyListingEntity() }
                    )
                    emit(Resource.Success(db.dao.searchCompanyListing(query).map {

                        it.toCompanyListing() }))
                    emit(Resource.Loading(false))

                }

            } else {
                emit(Resource.Loading(false))
                return@flow
            }
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Flow<Resource<CompanyInfo>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val companyInfo: CompanyInfo = api.getCompanyInfo(symbol = symbol).toCompanyInfo()
                emit(Resource.Success(companyInfo))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("IOException: Couldn't load data"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("HttpException: Couldn't load data"))
            } finally {
                emit(Resource.Loading(false))
            }
        }
    }



    override suspend fun getIntradayData(symbol: String): Flow<Resource<List<IntradayData>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val response = api.getIntradayData(symbol = symbol)
                val intradayDtoList = intradayDataParser.parse(response.byteStream())
                emit(Resource.Success(intradayDtoList
                    .filter { it.datetime.dayOfMonth == LocalDateTime.now().minusDays(1).dayOfMonth }
                    .sortedBy { it.datetime.hour }
                ))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("IOException: Couldn't load data"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("HttpException: Couldn't load data"))
            } finally {
                emit(Resource.Loading(false))
            }
        }
    }

}
