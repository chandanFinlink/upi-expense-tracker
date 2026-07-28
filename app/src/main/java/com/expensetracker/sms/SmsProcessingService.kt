package com.expensetracker.sms

import com.expensetracker.repository.TransactionRepository
import com.expensetracker.parser.UpiSmsParser

class SmsProcessingService(
    private val repository: TransactionRepository
) {

 suspend fun processSms(
    sender: String?,
    body: String,
    smsTime: Long
) {

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