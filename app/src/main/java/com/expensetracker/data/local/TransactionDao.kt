package com.expensetracker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(
        transaction: TransactionEntity
    )

    @Query(
        "SELECT * FROM transactions ORDER BY timestamp DESC"
    )
    suspend fun getAll(): List<TransactionEntity>

    @Query(
        "DELETE FROM transactions"
    )
    suspend fun deleteAll()
}
