package com.alphavantage.stocksappdemo.di

import com.alphavantage.stocksappdemo.data.mapper.csv.CSVParser
import com.alphavantage.stocksappdemo.data.mapper.csv.CompanyListingCSVParser
import com.alphavantage.stocksappdemo.data.mapper.csv.IntradayCSVParser
import com.alphavantage.stocksappdemo.data.repository.CompanyRepositoryImpl
import com.alphavantage.stocksappdemo.domain.model.CompanyListing
import com.alphavantage.stocksappdemo.domain.model.IntradayData
import com.alphavantage.stocksappdemo.domain.repository.CompanyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingCSVParser: CompanyListingCSVParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindIntradayParser(
        intradayCSVParser: IntradayCSVParser
    ): CSVParser<IntradayData>

    @Binds
    @Singleton
    abstract fun bindCompanyRepository(
        companyRepositoryImpl: CompanyRepositoryImpl
    ): CompanyRepository


}