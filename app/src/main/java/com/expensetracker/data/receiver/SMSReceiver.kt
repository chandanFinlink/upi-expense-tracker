package com.expensetracker.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log

class SMSReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context?,
        intent: Intent?
    ) {

        if (intent?.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
            return

        val messages =
            Telephony.Sms.Intents.getMessagesFromIntent(intent)

        for (sms in messages) {

            Log.d(
                "SMSReceiver",
                """
Sender : ${sms.displayOriginatingAddress}

Message :

${sms.messageBody}
                """.trimIndent()
            )
        }
    }
}
