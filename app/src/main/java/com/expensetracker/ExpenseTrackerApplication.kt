package com.expensetracker

import android.app.Application
import com.expensetracker.database.AppDatabase
import com.expensetracker.repository.BudgetRepository
import com.expensetracker.repository.CategoryRepository
import com.expensetracker.repository.SettingsRepository
import com.expensetracker.repository.TransactionRepository


class ExpenseTrackerApplication : Application() {


    lateinit var database: AppDatabase
        private set


    lateinit var transactionRepository: TransactionRepository
        private set


    lateinit var categoryRepository: CategoryRepository
        private set


    lateinit var budgetRepository: BudgetRepository
        private set


    lateinit var settingsRepository: SettingsRepository
        private set



    override fun onCreate() {

        super.onCreate()


        database = AppDatabase.getDatabase(this)


        transactionRepository =
            TransactionRepository(
                database.transactionDao()
            )


        categoryRepository =
            CategoryRepository(
                database.categoryDao()
            )


        budgetRepository =
            BudgetRepository(
                database.budgetDao()
            )


        settingsRepository =
            SettingsRepository(
                database.settingsDao()
            )

    }

}