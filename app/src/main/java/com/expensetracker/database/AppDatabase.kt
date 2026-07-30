package com.expensetracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.expensetracker.database.dao.BudgetDao
import com.expensetracker.database.dao.CategoryDao
import com.expensetracker.database.dao.SettingsDao
import com.expensetracker.database.dao.TransactionDao
import com.expensetracker.database.entity.AppSettingsEntity
import com.expensetracker.database.entity.BudgetEntity
import com.expensetracker.database.entity.CategoryEntity
import com.expensetracker.database.entity.TransactionEntity


@Database(
    entities = [
        TransactionEntity::class,
        CategoryEntity::class,
        BudgetEntity::class,
        AppSettingsEntity::class
    ],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {


    abstract fun transactionDao(): TransactionDao


    abstract fun categoryDao(): CategoryDao


    abstract fun budgetDao(): BudgetDao


    abstract fun settingsDao(): SettingsDao


    companion object {


        @Volatile
        private var INSTANCE: AppDatabase? = null


        fun getDatabase(
            context: Context
        ): AppDatabase {


            return INSTANCE ?: synchronized(this) {


                val instance = Room.databaseBuilder(

                    context.applicationContext,

                    AppDatabase::class.java,

                    "expense_tracker_database"

                )
                .fallbackToDestructiveMigration()
                .build()


                INSTANCE = instance


                instance

            }

        }

    }

}