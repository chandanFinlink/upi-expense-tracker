package com.expensetracker.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "budgets"
)
data class BudgetEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val categoryId: Long,

    val monthlyLimit: Double,

    /**
     * Alert when spending reaches this percentage.
     * Example: 80 means alert at 80%.
     */
    val alertPercentage: Int = 80,

    val month: String

)