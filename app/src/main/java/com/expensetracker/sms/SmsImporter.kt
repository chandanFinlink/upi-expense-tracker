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

    suspend fun importInbox() = withContext(Dispatchers.IO) {

        val uri = Uri.parse("content://sms/inbox")

        val projection = arrayOf(
            Telephony.Sms.ADDRESS,
            Telephony.Sms.BODY,
            Telephony.Sms.DATE
        )

        val calendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val startOfDay = calendar.timeInMillis

        val cursor = context.contentResolver.query(
            uri,
            projection,
            "${Telephony.Sms.DATE} >= ?",
            arrayOf(startOfDay.toString()),
            "${Telephony.Sms.DATE} DESC"
        ) ?: return@withContext

        cursor.use {

            val addressIndex = cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)

            val bodyIndex = cursor.getColumnIndexOrThrow(Telephony.Sms.BODY)
            val dateIndex = cursor.getColumnIndexOrThrow( Telephony.Sms.DATE )

            while (cursor.moveToNext()) {

                val sender = cursor.getString(addressIndex)

                val body = cursor.getString(bodyIndex)
                val smsTime = cursor.getLong(dateIndex)

                SmsProcessingService(repository).processSms(
                    sender = sender,
                    body = body,
                    smsTime = smsTime
                )

            }

        }

    }

}