package com.expensetracker.repository

import com.expensetracker.database.dao.BudgetDao
import com.expensetracker.database.entity.BudgetEntity
import kotlinx.coroutines.flow.Flow


class BudgetRepository(
    private val budgetDao: BudgetDao
) {


    fun getBudgets(): Flow<List<BudgetEntity>> {

        return budgetDao.getBudgets()

    }


    suspend fun insertBudget(
        budget: BudgetEntity
    ): Long {

        return budgetDao.insertBudget(budget)

    }


    suspend fun deleteBudget(
        id: Long
    ) {

        budgetDao.deleteBudget(id)

    }

    fun getCurrentBudget() =
    budgetDao.getCurrentBudget()


    suspend fun updateBudget(
        id: Long,
        monthlyLimit: Double,
        alertPercentage: Int
    ) {

        budgetDao.updateBudget(
            id,
            monthlyLimit,
            alertPercentage
        )

    }

}