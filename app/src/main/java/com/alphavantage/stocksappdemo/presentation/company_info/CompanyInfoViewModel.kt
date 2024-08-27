package com.alphavantage.stocksappdemo.presentation.company_info

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphavantage.stocksappdemo.domain.model.CompanyInfo
import com.alphavantage.stocksappdemo.domain.model.IntradayData
import com.alphavantage.stocksappdemo.domain.repository.CompanyRepository
import com.alphavantage.stocksappdemo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: CompanyRepository
) : ViewModel() {

    var state by mutableStateOf(CompanyInfoState())

    private var searchJob: Job? = null

    init {
        val symbol: String? = savedStateHandle.get<String>("symbol")

        symbol?.let {
            viewModelScope.launch {
                getCompanyData(symbol)
            }

        }
    }

    private fun handleResponse(infoRes: Resource<CompanyInfo>, intraRes: Resource<List<IntradayData>>) {

        state = when(infoRes) {
            is Resource.Error -> {
                state.copy(error = infoRes.error ?: "")
            }

            is Resource.Success -> {
                state.copy(companyInfo = infoRes.data)
            }

            is Resource.Loading -> {
                state.copy(isLoading = infoRes.isLoading)
            }
        }

        state = when(intraRes) {
            is Resource.Error -> {
                state.copy(error = intraRes.error ?: "")
            }

            is Resource.Success -> {
                state.copy(stockInfo = intraRes.data ?: emptyList())
            }

            is Resource.Loading -> {
                state.copy(isLoading = intraRes.isLoading)
            }
        }



    }


    private suspend fun getCompanyData(symbol: String) {
        combine(
            repository.getCompanyInfo(symbol),
            repository.getIntradayData(symbol)
            ) { infoRes, intraRes ->
            handleResponse(infoRes, intraRes)
        }.collect{}
    }


    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }


    companion object {
        const val TAG = "CompanyInfoViewModel"
    }


}