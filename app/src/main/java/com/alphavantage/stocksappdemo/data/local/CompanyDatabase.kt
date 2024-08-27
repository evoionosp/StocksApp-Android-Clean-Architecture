package com.alphavantage.stocksappdemo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [CompanyListingEntity::class],
    version = 1
)
abstract class CompanyDatabase: RoomDatabase() {
    abstract val dao : CompanyDao
}