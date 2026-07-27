package com.expensetracker.domain

import com.expensetracker.data.local.TransactionEntity

object RegexParser {

    fun parseSMS(body: String, sender: String, timestamp: Long): TransactionEntity? {
        if (!body.contains("debited", ignoreCase = true) && !body.contains("paid", ignoreCase = true)) {
            return null
        }

        // Extract Amount
        val amountRegex = Regex(@"(?:Rs\.?|INR)\s*([\d,]+\.?\d*)", RegexOption.IGNORE_CASE)
        val amount = amountRegex.find(body)?.groupValues?.get(1)?.replace(",", "")?.toDoubleOrNull() ?: return null

        // Extract VPA / Merchant
        val vpaRegex = Regex(@"(?:to|vpa|info)\s+([\w\.-]+@[\w\.-]+)", RegexOption.IGNORE_CASE)
        val vpaMatch = vpaRegex.find(body)?.groupValues?.get(1) ?: ""

        // Detect Source App
        val sourceApp = when {
            vpaMatch.contains("@ok", ignoreCase = true) || body.contains("gpay", ignoreCase = true) -> "Google Pay"
            vpaMatch.contains("@ybl", ignoreCase = true) || vpaMatch.contains("@ibl", ignoreCase = true) || vpaMatch.contains("@axl", ignoreCase = true) -> "PhonePe"
            vpaMatch.contains("@paytm", ignoreCase = true) -> "Paytm"
            else -> "Other UPI"
        }

        val isCredit = body.contains("credit card", ignoreCase = true) || body.contains("cc", ignoreCase = true)

        return TransactionEntity(
            amount = amount,
            bankName = if (sender.contains("HDFCBK")) "HDFC Bank" else "Bank",
            paymentMethod = if (isCredit) "RuPay Credit Card" else "Bank Account / Debit",
            merchantOrVpa = if (vpaMatch.isNotEmpty()) vpaMatch else "Merchant",
            sourceApp = sourceApp,
            timestamp = timestamp
        )
    }
}
