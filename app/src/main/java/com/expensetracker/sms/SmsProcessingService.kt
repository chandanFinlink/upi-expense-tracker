package com.expensetracker.sms

import com.expensetracker.parser.UpiSmsParser
import com.expensetracker.repository.TransactionRepository

class SmsProcessingService(
    private val repository: TransactionRepository
) {

    suspend fun processSms(
        sender: String?,
        body: String,
        smsTime: Long
    ) {

        val transaction =
            UpiSmsParser.parse(
                smsBody = body,
                sender = sender,
                smsTime = smsTime
            ) ?: return

        val exists =
            repository.transactionExists(
                transaction.referenceNumber
            )

        if (exists) {
            return
        }

        repository.insertTransaction(transaction)
    }
}