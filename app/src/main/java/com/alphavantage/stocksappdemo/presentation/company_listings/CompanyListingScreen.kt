package com.alphavantage.stocksappdemo.presentation.company_listings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alphavantage.stocksappdemo.presentation.destinations.CompanyInfoScreenDestination
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Composable
@Destination(start = true)
fun CompanyListingScreen (
    navigator: DestinationsNavigator,
    viewModel: CompanyListingViewModel = hiltViewModel()
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = viewModel.state.isLoading)

     val state = viewModel.state


    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = {viewModel.onEvent(CompanyListingEvent.OnSearchQueryChange(it))},
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            placeholder = {
                Text(text = "Search...")
            },
            maxLines = 1,
            singleLine = true
        )

        SwipeRefresh(state = swipeRefreshState, onRefresh = {
            viewModel.onEvent(CompanyListingEvent.onRefresh)
        }) {
            
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.listing.size) { i ->
                    val company = state.listing[i]
                    CompanyItem(company = company,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navigator.navigate(CompanyInfoScreenDestination(symbol = company.symbol))
                            }
                            .padding(horizontal = 16.dp, vertical = 2.dp)
                    )
                    if(i < state.listing.size) {
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    }
                }
            }
        }
    }
}