package com.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.expensetracker.repository.TransactionRepository

class SettingsViewModelFactory(
    private val repository: TransactionRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        return SettingsViewModel(repository) as T

    }

}