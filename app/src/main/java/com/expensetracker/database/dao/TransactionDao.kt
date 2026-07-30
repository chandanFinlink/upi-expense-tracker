package com.expensetracker.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.expensetracker.database.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow
import androidx.room.OnConflictStrategy


@Dao
interface TransactionDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaction(
        transaction: TransactionEntity
    ): Long


    @Insert
    suspend fun insertTransactions(
        transactions: List<TransactionEntity>
    )


    @Query(
        """
        SELECT * 
        FROM transactions
        ORDER BY transactionDate DESC
        """
    )
    fun getAllTransactions(): Flow<List<TransactionEntity>>


    @Query(
        """
        SELECT *
        FROM transactions
        ORDER BY transactionDate DESC
        LIMIT 10
        """
    )
    fun getRecentTransactions(): Flow<List<TransactionEntity>>


    @Query(
        """
        SELECT SUM(amount)
        FROM transactions
        WHERE transactionType = 'DEBIT'
        """
    )
    fun getTotalExpense(): Flow<Double?>


    @Query(
        """
        DELETE FROM transactions
        """
    )
    suspend fun deleteAllTransactions()

    @Query(
    """
    SELECT COUNT(*)
    FROM transactions
    WHERE referenceNumber = :referenceNumber
    """
    )
    suspend fun countByReferenceNumber(
        referenceNumber: String
    ): Int

    @Query("""
    SELECT SUM(amount)
    FROM transactions
    WHERE transactionType='DEBIT'
    AND date(transactionDate/1000,'unixepoch','localtime')
    =
    date('now','localtime')
    """)
    fun getTodayExpense(): Flow<Double?>


    @Query("""
    SELECT SUM(amount)
    FROM transactions
    WHERE transactionType='CREDIT'
    AND date(transactionDate/1000,'unixepoch','localtime')
    =
    date('now','localtime')
    """)
    fun getTodayCredit(): Flow<Double?>

    @Query("""
    SELECT SUM(amount)
    FROM transactions
    WHERE transactionType='DEBIT'
    AND date(transactionDate/1000,'unixepoch','localtime')
    =
    date('now','localtime')
    """)
    fun getTodayDebit(): Flow<Double?>


    @Query("""
    SELECT SUM(amount)
    FROM transactions
    WHERE transactionType='DEBIT'
    AND strftime('%Y-%m',transactionDate/1000,'unixepoch','localtime')
    =
    strftime('%Y-%m','now','localtime')
    """)
    fun getMonthlyExpense(): Flow<Double?>

    @Query("""
    SELECT COUNT(*)
    FROM transactions
    """)
    fun getTransactionCount(): Flow<Int>


    @Query("""
    SELECT SUM(amount)
    FROM transactions
    WHERE transactionType='CREDIT'
    """)
    fun getTotalIncome(): Flow<Double?>

    @Query("""
    SELECT COUNT(*)
    FROM transactions
    WHERE transactionType='DEBIT'
    """)
    fun getDebitCount(): Flow<Int>

    @Query("""
    SELECT COUNT(*)
    FROM transactions
    WHERE transactionType='CREDIT'
    """)
    fun getCreditCount(): Flow<Int>


    @Query("""
    SELECT *
    FROM transactions
    WHERE date(transactionDate/1000,'unixepoch','localtime')
    =
    date('now','localtime')
    ORDER BY transactionDate DESC
    """)
    fun getTodayTransactions(): Flow<List<TransactionEntity>>


    @Query("""
    SELECT *
    FROM transactions
    WHERE date(transactionDate/1000,'unixepoch','localtime')
    =
    date('now','-1 day','localtime')
    ORDER BY transactionDate DESC
    """)
    fun getYesterdayTransactions(): Flow<List<TransactionEntity>>


    @Query("""
    SELECT *
    FROM transactions
    WHERE transactionDate >= :startTime
    ORDER BY transactionDate DESC
    """)
    fun getTransactionsAfter(
        startTime: Long
    ): Flow<List<TransactionEntity>>


    @Query("""
    SELECT *
    FROM transactions
    WHERE transactionType = :type
    ORDER BY transactionDate DESC
    """)
    fun getTransactionsByType(
        type: String
    ): Flow<List<TransactionEntity>>


    @Query("""
    SELECT *
    FROM transactions
    WHERE merchant LIKE '%' || :keyword || '%'
    ORDER BY transactionDate DESC
    """)
    fun searchTransactions(
        keyword: String
    ): Flow<List<TransactionEntity>>


    @Query("""
    SELECT *
    FROM transactions
    WHERE bankName = :bankName
    ORDER BY transactionDate DESC
    """)
    fun getTransactionsByBank(
        bankName: String
    ): Flow<List<TransactionEntity>>

}