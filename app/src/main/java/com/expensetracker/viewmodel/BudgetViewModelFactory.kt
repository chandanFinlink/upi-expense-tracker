package com.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.expensetracker.repository.BudgetRepository
import com.expensetracker.repository.TransactionRepository

class BudgetViewModelFactory(
    private val budgetRepository: BudgetRepository,
    private val transactionRepository: TransactionRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        return BudgetViewModel(
            budgetRepository,
            transactionRepository
        ) as T

    }

}