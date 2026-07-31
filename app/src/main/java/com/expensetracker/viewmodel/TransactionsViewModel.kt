package com.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expensetracker.database.entity.TransactionEntity
import com.expensetracker.repository.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar

import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.asStateFlow


class TransactionsViewModel(
    private val repository: TransactionRepository
) : ViewModel() {

    enum class Filter {

        ALL,
        TODAY,
        YESTERDAY,
        LAST_7_DAYS,
        LAST_30_DAYS,
        LAST_90_DAYS,
        DEBIT,
        CREDIT

    }

    private val selectedFilter =
        MutableStateFlow(Filter.ALL)

    private val searchQuery =
         MutableStateFlow("")

    val currentFilter: StateFlow<Filter> =
        selectedFilter

    val transactions =
    combine(
        selectedFilter,
        searchQuery
    ) { filter, query ->

        Pair(filter, query)

    }
    .flatMapLatest { (filter, query) ->

        if (query.isNotBlank()) {

            repository.searchTransactions(query)

        } else {

            when (filter) {

                Filter.ALL ->
                    repository.getTransactions()

                Filter.TODAY ->
                    repository.getTodayTransactions()

                Filter.YESTERDAY ->
                    repository.getYesterdayTransactions()

                Filter.LAST_7_DAYS ->
                    repository.getTransactionsAfter(
                        getDaysAgo(7)
                    )

                Filter.LAST_30_DAYS ->
                    repository.getTransactionsAfter(
                        getDaysAgo(30)
                    )

                Filter.LAST_90_DAYS ->
                    repository.getTransactionsAfter(
                        getDaysAgo(90)
                    )

                Filter.DEBIT ->
                    repository.getTransactionsByType("DEBIT")

                Filter.CREDIT ->
                    repository.getTransactionsByType("CREDIT")

            }

        }

    }
    .stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )



    fun setFilter( filter: Filter ) {
        selectedFilter.value = filter
    }

    private fun getDaysAgo(
        days: Int
    ): Long {

        val calendar = Calendar.getInstance()

        calendar.add(
            Calendar.DAY_OF_YEAR,
            -days
        )

        return calendar.timeInMillis

    }

    fun setSearchQuery(query: String) {
            searchQuery.value = query
    }

    fun clearSearch() {
        searchQuery.value = ""
    }

}