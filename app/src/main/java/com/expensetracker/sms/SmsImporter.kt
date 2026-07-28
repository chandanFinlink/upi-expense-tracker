package com.expensetracker.sms

import android.content.Context
import android.net.Uri
import android.provider.Telephony
import com.expensetracker.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

        val cursor = context.contentResolver.query(
            uri,
            projection,
            null,
            null,
            "${Telephony.Sms.DATE} DESC"
        ) ?: return@withContext

        cursor.use {

            val addressIndex =
                cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)

            val bodyIndex =
                cursor.getColumnIndexOrThrow(Telephony.Sms.BODY)

            while (cursor.moveToNext()) {

                val sender =
                    cursor.getString(addressIndex)

                val body =
                    cursor.getString(bodyIndex)

                SmsProcessingService(repository)
                    .processSms(
                        sender = sender,
                        body = body
                    )

            }

        }

    }

}