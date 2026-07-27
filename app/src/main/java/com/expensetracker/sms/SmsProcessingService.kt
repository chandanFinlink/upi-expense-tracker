package com.expensetracker.sms

import com.expensetracker.repository.TransactionRepository
import com.expensetracker.parser.UpiSmsParser

class SmsProcessingService(
    private val repository: TransactionRepository
) {

    suspend fun processSms(
        sender: String?,
        body: String
    ) {

        val transaction = UpiSmsParser.parse(
            smsBody = body,
            sender = sender
        ) ?: return

        repository.insertTransaction(transaction)
    }
}