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


    val todayExpense =
    transactionRepository
        .getTodayExpense()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            0.0
        )

    val todayCredit =
    transactionRepository
        .getTodayCredit()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            0.0
        )

    val todayDebit =
        transactionRepository
            .getTodayDebit()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                0.0
            )

    val netToday =
        kotlinx.coroutines.flow.combine(
            todayCredit,
            todayDebit
        ) { credit, debit ->

            (credit ?: 0.0) - (debit ?: 0.0)

        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            0.0
        )

    val monthlyExpense =
        transactionRepository
            .getMonthlyExpense()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                0.0
            )

    val transactionCount =
        transactionRepository
            .getTransactionCount()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                0
            )

}