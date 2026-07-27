package com.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expensetracker.repository.TransactionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn


class HomeViewModel(
    private val transactionRepository: TransactionRepository
) : ViewModel() {


    val recentTransactions =
        transactionRepository
            .getRecentTransactions()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )


    val totalExpense =
        transactionRepository
            .getTotalExpense()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                0.0
            )

}