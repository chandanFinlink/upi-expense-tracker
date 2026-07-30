package com.expensetracker.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.expensetracker.ExpenseTrackerApplication
import com.expensetracker.parser.UpiSmsParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.expensetracker.sms.SmsProcessingService


class SMSReceiver : BroadcastReceiver() {


    override fun onReceive(
        context: Context,
        intent: Intent
    ) {


        if (
            intent.action ==
            Telephony.Sms.Intents.SMS_RECEIVED_ACTION
        ) {


            val messages =
                Telephony.Sms.Intents
                    .getMessagesFromIntent(intent)



            val application =
                context.applicationContext
                        as ExpenseTrackerApplication



            val smsProcessingService = SmsProcessingService(
                application.transactionRepository
                 )



            messages.forEach { sms ->


                val sender = sms.originatingAddress
                val body = sms.messageBody

                CoroutineScope(Dispatchers.IO).launch {

                    smsProcessingService.processSms(
                        sender = sender,
                        body = body,
                        smsTime = sms.timestampMillis
                    )
                }



              


            }

        }

    }

}