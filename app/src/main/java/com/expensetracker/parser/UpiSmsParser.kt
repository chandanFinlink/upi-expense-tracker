package com.expensetracker.parser

import com.expensetracker.database.entity.TransactionEntity

object UpiSmsParser {

    fun parse(
            smsBody: String,
            sender: String?,
            smsTime: Long
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

        if (amount <= 0.0 || amount > 1000000.0) {
            return null
        }

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
            transactionDate = smsTime,
            bankName = sender,
            smsBody = sms,
            smsAddress = sender,
            referenceNumber = extractReference(sms)
        )
    }

    private fun extractAmount( text: String ): Double? {

        val patterns = listOf(

            Regex(
                "(?:Rs\\.?|INR|₹)\\s*([0-9,]+(?:\\.[0-9]{1,2})?)",
                RegexOption.IGNORE_CASE
            ),

            Regex(
                "Sent\\s+(?:Rs\\.?|INR|₹)?\\s*([0-9,]+(?:\\.[0-9]{1,2})?)",
                RegexOption.IGNORE_CASE
            ),

            Regex(
                "debited\\s+by\\s+(?:Rs\\.?|INR|₹)?\\s*([0-9,]+(?:\\.[0-9]{1,2})?)",
                RegexOption.IGNORE_CASE
            )

        )

        patterns.forEach { regex ->

            val match = regex.find(text)

            if (match != null) {

                return match.groupValues[1]
                    .replace(",", "")
                    .toDoubleOrNull()

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

            // Paid to Shop Name
            Regex(
                "paid\\s+to\\s+([A-Za-z0-9 .@&_-]+)",
                RegexOption.IGNORE_CASE
            ),

            // To Shop Name
            Regex(
                "\\bto\\b\\s+([A-Za-z0-9 .@&_-]+)",
                RegexOption.IGNORE_CASE
            ),

            // HDFC
            Regex(
                "To\\s+(.+?)\\s+On",
                RegexOption.IGNORE_CASE
            ),

            // Kotak
            Regex(
                "to\\s+(.+?)\\s+on",
                RegexOption.IGNORE_CASE
            ),

            // BOB
            Regex(
                "Cr\\.\\s+to\\s+(.+?)\\.\\s+Ref",
                RegexOption.IGNORE_CASE
            )

        )

        for (pattern in patterns) {

            val match = pattern.find(text)

            if (match != null) {

                var merchant = match.groupValues[1].trim()

                merchant = merchant
                    .replace(Regex("\\s+"), " ")
                    .replace(".", "")
                    .trim()

                return merchant

            }

        }

        return "Unknown"

    }
}