package com.expensetracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Double,
    val bankName: String,
    val paymentMethod: String,
    val merchantOrVpa: String,
    val sourceApp: String, // "Google Pay", "PhonePe", "Paytm", "Other UPI"
    val timestamp: Long
)

@Entity(tableName = "app_budgets")
data class AppBudgetEntity(
    @PrimaryKey val appName: String,
    val allocatedBudget: Double
)
