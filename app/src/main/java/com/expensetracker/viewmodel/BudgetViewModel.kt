package com.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expensetracker.database.entity.BudgetEntity
import com.expensetracker.repository.BudgetRepository
import com.expensetracker.repository.TransactionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BudgetViewModel(
    private val budgetRepository: BudgetRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    val budget =
        budgetRepository
            .getCurrentBudget()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                null
            )

    val monthlyExpense =
        transactionRepository
            .getMonthlyExpense()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                0.0
            )

    fun saveBudget(
        budget: BudgetEntity
    ) {

        viewModelScope.launch {

            budgetRepository.insertBudget(budget)

        }

    }

    fun updateBudget(
        id: Long,
        amount: Double,
        alert: Int
    ) {

        viewModelScope.launch {

            budgetRepository.updateBudget(
                id,
                amount,
                alert
            )

        }

    }

    fun deleteBudget(
        id: Long
    ) {

        viewModelScope.launch {

            budgetRepository.deleteBudget(id)

        }

    }

}