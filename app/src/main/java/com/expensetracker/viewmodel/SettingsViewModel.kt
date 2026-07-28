package com.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expensetracker.repository.TransactionRepository
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: TransactionRepository
) : ViewModel() {

    fun clearDatabase() {

        viewModelScope.launch {

            repository.deleteAll()

        }

    }

}