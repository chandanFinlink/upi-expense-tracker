package com.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expensetracker.database.entity.AppSettingsEntity
import com.expensetracker.repository.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class SettingsViewModel(
    private val repository: SettingsRepository
) : ViewModel() {


    val settings =
        repository
            .getSettings()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                null
            )


    fun saveSettings(
        settings: AppSettingsEntity
    ) {

        viewModelScope.launch {

            repository.saveSettings(settings)

        }

    }

}