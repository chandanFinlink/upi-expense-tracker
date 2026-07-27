package com.expensetracker.utils

object RegexParser {

    private val amountRegex = Regex(
        """(?:Rs\.?|INR)\s*([\d,]+(?:\.\d{1,2})?)""",
        RegexOption.IGNORE_CASE
    )

    private val upiRegex = Regex(
        """(?:to|vpa|info)\s+([\w.-]+@[\w.-]+)""",
        RegexOption.IGNORE_CASE
    )

    fun extractAmount(message: String): Double? {

        val match = amountRegex.find(message)
            ?: return null

        return match.groupValues[1]
            .replace(",", "")
            .toDoubleOrNull()
    }

    fun extractUpiId(message: String): String? {

        val match = upiRegex.find(message)
            ?: return null

        return match.groupValues[1]
    }
}
