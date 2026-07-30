package com.expensetracker.repository

import com.expensetracker.database.dao.TransactionDao
import com.expensetracker.database.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow


class TransactionRepository(
    private val transactionDao: TransactionDao
) {


    fun getTransactions(): Flow<List<TransactionEntity>> {

        return transactionDao.getAllTransactions()

    }


    fun getRecentTransactions(): Flow<List<TransactionEntity>> {

        return transactionDao.getRecentTransactions()

    }


    fun getTotalExpense(): Flow<Double?> {

        return transactionDao.getTotalExpense()

    }

    fun getTodayExpense() =
    transactionDao.getTodayExpense()

    fun getTodayCredit() =
    transactionDao.getTodayCredit()

    fun getTodayDebit() =
    transactionDao.getTodayDebit()

    fun getMonthlyExpense() =
        transactionDao.getMonthlyExpense()

    fun getTransactionCount() =
        transactionDao.getTransactionCount()

    fun getTotalIncome() =
    transactionDao.getTotalIncome()

    fun getDebitCount() =
        transactionDao.getDebitCount()

    fun getCreditCount() =
        transactionDao.getCreditCount()


    suspend fun insertTransaction(
        transaction: TransactionEntity
    ): Long {

        return transactionDao.insertTransaction(transaction)

    }


    suspend fun insertTransactions(
        transactions: List<TransactionEntity>
    ) {

        transactionDao.insertTransactions(transactions)

    }


    suspend fun deleteAll() {

        transactionDao.deleteAllTransactions()

    }

    suspend fun transactionExists(
    referenceNumber: String?
    ): Boolean {

        if (referenceNumber.isNullOrBlank()) {
            return false
        }

        return transactionDao.countByReferenceNumber(
            referenceNumber
        ) > 0
    }

}