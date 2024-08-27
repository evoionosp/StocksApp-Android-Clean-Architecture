package com.alphavantage.stocksappdemo.presentation.company_listings

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphavantage.stocksappdemo.domain.repository.CompanyRepository
import com.alphavantage.stocksappdemo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CompanyListingViewModel @Inject constructor(
    private val repository: CompanyRepository
) : ViewModel() {

    var state by mutableStateOf(CompanyListingState())

    private var searchJob: Job? = null

    init {
        getCompanyListings()
    }

    fun onEvent(event: CompanyListingEvent) {
        when(event) {
            is CompanyListingEvent.onRefresh -> {
                getCompanyListings(fetchFromRemote = true)
            }

            is CompanyListingEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()

                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyListings()
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }

    private fun getCompanyListings(
        query: String = state.searchQuery.lowercase(Locale.US),
        fetchFromRemote: Boolean = false
    ) {

        viewModelScope.launch {
             repository
                 .getCompanyListings(fetchFromRemote, query)
                 .collect{result ->
                     when(result) {
                         is Resource.Success ->{
                             result.data?.let { listings ->
                                 state = state.copy(listing = listings)
                             }
                         }
                         is Resource.Error -> {
                             result.error?.let { message ->
                                 Timber.tag(TAG).e(message)
                             }
                             Unit
                         }
                         is Resource.Loading -> {
                             state = state.copy(isLoading = result.isLoading)
                         }
                     }
                 }
        }
    }

    companion object {
        const val TAG = "CompanyListingViewModel"
    }


}