package com.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expensetracker.database.entity.TransactionEntity
import com.expensetracker.repository.TransactionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class TransactionViewModel(
    private val repository: TransactionRepository
) : ViewModel() {


    val transactions =
        repository
            .getTransactions()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )


    fun addTransaction(
        transaction: TransactionEntity
    ) {

        viewModelScope.launch {

            repository.insertTransaction(transaction)

        }

    }

}