package com.expensetracker.parser

import com.expensetracker.database.entity.TransactionEntity

object UpiSmsParser {

    fun parse(
        smsBody: String,
        sender: String?
    ): TransactionEntity? {

        val sms = smsBody.trim()
        val lower = sms.lowercase()

        // Ignore OTP / promotional / incomplete messages
        val ignoreWords = listOf(
            "otp",
            "one time password",
            "authorize debit",
            "login",
            "click here",
            "autopay",
            "mandate",
            "reminder",
            "offer",
            "cashback",
            "reward",
            "failed",
            "pending",
            "declined",
            "reversed"
        )

        if (ignoreWords.any { lower.contains(it) }) {
            return null
        }

        val success =
            lower.contains("sent") ||
            lower.contains("paid") ||
            lower.contains("debited") ||
            lower.contains("dr.") ||
            lower.contains("dr ") ||
            lower.contains("credited") ||
            lower.contains("cr.") ||
            lower.contains("received")

        if (!success) {
            return null
        }

        val amount = extractAmount(sms) ?: return null

        val type =
            if (lower.contains("credited") ||
                lower.contains("received") ||
                lower.contains("cr.")
            ) "CREDIT"
            else "DEBIT"

        return TransactionEntity(
            amount = amount,
            merchant = extractMerchant(sms) ?: "Unknown",
            transactionType = type,
            transactionDate = System.currentTimeMillis(),
            bankName = sender,
            smsBody = sms,
            smsAddress = sender,
            referenceNumber = extractReference(sms)
        )
    }

    private fun extractAmount(text: String): Double? {

        val patterns = listOf(

            Regex("Rs\\.?\\s*([0-9,]+(?:\\.[0-9]{1,2})?)", RegexOption.IGNORE_CASE),

            Regex("INR\\s*([0-9,]+(?:\\.[0-9]{1,2})?)", RegexOption.IGNORE_CASE),

            Regex("₹\\s*([0-9,]+(?:\\.[0-9]{1,2})?)")

        )

        for (regex in patterns) {

            val value = regex.find(text)?.groupValues?.get(1)

            if (value != null) {
                return value.replace(",", "").toDoubleOrNull()
            }
        }

        return null
    }

    private fun extractReference(text: String): String? {

        val patterns = listOf(

            Regex("UPI\\s*Ref\\s*[: ]\\s*(\\d+)", RegexOption.IGNORE_CASE),

            Regex("Ref\\s*[: ]\\s*(\\d+)", RegexOption.IGNORE_CASE),

            Regex("txn\\s*ID\\s*(\\d+)", RegexOption.IGNORE_CASE),

            Regex("UTR\\s*[: ]\\s*(\\d+)", RegexOption.IGNORE_CASE)

        )

        for (regex in patterns) {

            val value = regex.find(text)?.groupValues?.get(1)

            if (value != null)
                return value
        }

        return null
    }

    private fun extractMerchant(text: String): String? {

        val patterns = listOf(

            Regex("to\\s+(.+?)\\s+on", RegexOption.IGNORE_CASE),

            Regex("to\\s+(.+?)\\.", RegexOption.IGNORE_CASE),

            Regex("paid to\\s+(.+?)\\.", RegexOption.IGNORE_CASE),

            Regex("Cr\\. to\\s+(.+?)\\.", RegexOption.IGNORE_CASE)

        )

        for (regex in patterns) {

            val match = regex.find(text)

            if (match != null) {

                return match.groupValues[1]
                    .trim()
                    .replace("\n", " ")
            }
        }

        return null
    }
}