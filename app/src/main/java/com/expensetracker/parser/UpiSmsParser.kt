package com.expensetracker.parser

import com.expensetracker.database.entity.TransactionEntity


object UpiSmsParser {


    fun parse(
        smsBody: String,
        sender: String?
    ): TransactionEntity? {


        val lowerCaseSms =
            smsBody.lowercase()



        // Ignore unrelated SMS

        if (
            !(
                lowerCaseSms.contains("upi") ||
                lowerCaseSms.contains("debited") ||
                lowerCaseSms.contains("credited") ||
                lowerCaseSms.contains("paid")
            )
        ) {

            return null

        }



        val amount =
            extractAmount(smsBody)



        if (amount == null) {

            return null

        }



        val transactionType =

            if (
                lowerCaseSms.contains("credited")
            ) {

                "CREDIT"

            } else {

                "DEBIT"

            }



        return TransactionEntity(

            amount = amount,

            merchant =
                extractMerchant(smsBody),

            transactionType = transactionType,

            transactionDate =
                System.currentTimeMillis(),

            bankName = sender,

            smsBody = smsBody,

            smsAddress = sender,

            referenceNumber =
                extractReferenceNumber(smsBody)

        )

    }



    private fun extractAmount(
        text: String
    ): Double? {


        val regex =
            Regex(
                "(rs\\.?|inr|₹)\\s?([0-9,]+(\\.[0-9]+)?)",
                RegexOption.IGNORE_CASE
            )


        val match =
            regex.find(text)



        return match
            ?.groupValues
            ?.get(2)
            ?.replace(",", "")
            ?.toDoubleOrNull()

    }



    private fun extractReferenceNumber(
        text: String
    ): String? {


        val regex =
            Regex(
                "(ref|reference|txn|transaction)[^0-9]*([0-9]{6,})",
                RegexOption.IGNORE_CASE
            )


        return regex.find(text)
            ?.groupValues
            ?.get(2)

    }



    private fun extractMerchant(
        text: String
    ): String? {


        val patterns = listOf(

            Regex(
                "to ([A-Za-z0-9 @._-]+)",
                RegexOption.IGNORE_CASE
            ),

            Regex(
                "paid to ([A-Za-z0-9 @._-]+)",
                RegexOption.IGNORE_CASE
            )

        )



        for(pattern in patterns) {

            val result =
                pattern.find(text)


            if(result != null) {

                return result.groupValues[1]
                    .trim()

            }

        }


        return null

    }

}