package com.expensetracker.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.expensetracker.database.entity.AppSettingsEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface SettingsDao {


    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun saveSettings(
        settings: AppSettingsEntity
    )


    @Query(
        """
        SELECT *
        FROM app_settings
        WHERE id = 1
        """
    )
    fun getSettings(): Flow<AppSettingsEntity?>

}