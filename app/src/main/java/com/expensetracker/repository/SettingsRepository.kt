package com.expensetracker.repository

import com.expensetracker.database.dao.SettingsDao
import com.expensetracker.database.entity.AppSettingsEntity
import kotlinx.coroutines.flow.Flow


class SettingsRepository(
    private val settingsDao: SettingsDao
) {


    fun getSettings(): Flow<AppSettingsEntity?> {

        return settingsDao.getSettings()

    }


    suspend fun saveSettings(
        settings: AppSettingsEntity
    ) {

        settingsDao.saveSettings(settings)

    }

}