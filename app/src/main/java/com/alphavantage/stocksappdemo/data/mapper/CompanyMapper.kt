package com.alphavantage.stocksappdemo.data.mapper

import com.alphavantage.stocksappdemo.data.local.CompanyListingEntity
import com.alphavantage.stocksappdemo.data.remote.dto.CompanyInfoDto
import com.alphavantage.stocksappdemo.domain.model.CompanyInfo
import com.alphavantage.stocksappdemo.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing() : CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity() : CompanyListingEntity {
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyInfoDto.toCompanyInfo() : CompanyInfo {

    return CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name  ?: "",
        country = country  ?: "",
        industry = industry  ?: ""
    )
}