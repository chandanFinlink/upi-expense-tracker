package com.expensetracker.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions"
)
data class TransactionEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val amount: Double,

    val merchant: String? = null,

    val categoryId: Long? = null,

    /**
     * DEBIT  = money spent
     * CREDIT = money received
     */
    val transactionType: String,

    val transactionDate: Long,

    val bankName: String? = null,

    val upiApp: String? = null,

    val referenceNumber: String? = null,

    /**
     * Original SMS content.
     * Useful for debugging and improving parser accuracy.
     */
    val smsBody: String? = null,

    val smsAddress: String? = null,

    val createdAt: Long = System.currentTimeMillis()
)