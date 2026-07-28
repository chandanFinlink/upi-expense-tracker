package com.expensetracker.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.expensetracker.database.entity.BudgetEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface BudgetDao {


    @Insert
    suspend fun insertBudget(
        budget: BudgetEntity
    ): Long


    @Query(
        """
        SELECT *
        FROM budgets
        ORDER BY month DESC
        """
    )
    fun getBudgets(): Flow<List<BudgetEntity>>


    @Query(
        """
        DELETE FROM budgets
        WHERE id = :id
        """
    )
    suspend fun deleteBudget(
        id: Long
    )

    @Query("""
    SELECT *
    FROM budgets
    LIMIT 1
    """)
    fun getCurrentBudget(): Flow<BudgetEntity?>


    @Query("""
    UPDATE budgets
    SET
    monthlyLimit = :monthlyLimit,
    alertPercentage = :alertPercentage
    WHERE id = :id
    """)
    suspend fun updateBudget(
        id: Long,
        monthlyLimit: Double,
        alertPercentage: Int
    )

}