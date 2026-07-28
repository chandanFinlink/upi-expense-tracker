package com.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expensetracker.database.entity.BudgetEntity
import com.expensetracker.repository.BudgetRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BudgetViewModel(
    private val repository: BudgetRepository
) : ViewModel() {

    val budgets =
        repository
            .getBudgets()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    fun saveBudget(
        budget: BudgetEntity
    ) {

        viewModelScope.launch {

            repository.insertBudget(budget)

        }

    }

    fun deleteBudget(
        id: Long
    ) {

        viewModelScope.launch {

            repository.deleteBudget(id)

        }

    }
}