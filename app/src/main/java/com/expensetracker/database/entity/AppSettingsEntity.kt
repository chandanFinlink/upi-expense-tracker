package com.expensetracker.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "app_settings"
)
data class AppSettingsEntity(

    @PrimaryKey
    val id: Int = 1,

    val currency: String = "INR",

    val darkMode: Boolean = false,

    val smsPermissionGranted: Boolean = false,

    val notificationsEnabled: Boolean = true

)