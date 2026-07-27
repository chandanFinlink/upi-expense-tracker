package com.expensetracker.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var database: ExpenseDatabase? = null

    fun getDatabase(
        context: Context
    ): ExpenseDatabase {

        return database ?: synchronized(this) {

            database ?: Room.databaseBuilder(
                context.applicationContext,
                ExpenseDatabase::class.java,
                "expense_tracker.db"
            )
                .fallbackToDestructiveMigration()
                .build()
                .also {
                    database = it
                }
        }
    }
}
