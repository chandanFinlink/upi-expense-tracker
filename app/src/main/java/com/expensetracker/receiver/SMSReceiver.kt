package com.expensetracker.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log


class SMSReceiver : BroadcastReceiver() {


    override fun onReceive(
        context: Context,
        intent: Intent
    ) {


        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {


            val messages =
                Telephony.Sms.Intents.getMessagesFromIntent(intent)


            messages.forEach { sms ->


                val sender =
                    sms.originatingAddress


                val body =
                    sms.messageBody


                Log.d(
                    "SMS_RECEIVER",
                    "Sender: $sender Body: $body"
                )


            }

        }

    }

}