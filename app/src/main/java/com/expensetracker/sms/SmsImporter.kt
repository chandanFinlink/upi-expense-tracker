package com.expensetracker.sms

import android.content.Context
import android.net.Uri
import android.provider.Telephony
import com.expensetracker.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar

class SmsImporter(
    private val context: Context,
    private val repository: TransactionRepository
) {

    suspend fun importLastDays(
        days: Int = 90,
        onProgress: ((processed: Int, imported: Int) -> Unit)? = null
    ) = withContext(Dispatchers.IO) {

        val uri = Uri.parse("content://sms/inbox")

        val projection = arrayOf(
            Telephony.Sms.ADDRESS,
            Telephony.Sms.BODY,
            Telephony.Sms.DATE
        )

        val calendar = Calendar.getInstance()

        calendar.add(Calendar.DAY_OF_YEAR, -days)

        val startTime = calendar.timeInMillis

        val cursor = context.contentResolver.query(
            uri,
            projection,
            "${Telephony.Sms.DATE} >= ?",
            arrayOf(startTime.toString()),
            "${Telephony.Sms.DATE} DESC"
        ) ?: return@withContext

        var processed = 0
        var imported = 0

        val smsService = SmsProcessingService(repository)

        cursor.use {

            val addressIndex =
                cursor.getColumnIndexOrThrow(
                    Telephony.Sms.ADDRESS
                )

            val bodyIndex =
                cursor.getColumnIndexOrThrow(
                    Telephony.Sms.BODY
                )

            val dateIndex =
                cursor.getColumnIndexOrThrow(
                    Telephony.Sms.DATE
                )

            while (cursor.moveToNext()) {

                processed++

                val sender =
                    cursor.getString(addressIndex)

                val body =
                    cursor.getString(bodyIndex)

                val smsTime =
                    cursor.getLong(dateIndex)

                val inserted =
                    smsService.processSms(
                        sender = sender,
                        body = body,
                        smsTime = smsTime
                    )

                if (inserted) {
                    imported++
                }

                onProgress?.invoke(
                    processed,
                    imported
                )

            }

        }

    }

}