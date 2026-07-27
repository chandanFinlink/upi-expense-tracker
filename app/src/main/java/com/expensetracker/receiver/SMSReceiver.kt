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



            val repository =
                application.transactionRepository



            messages.forEach { sms ->


                val sender =
                    sms.originatingAddress


                val body =
                    sms.messageBody



                val transaction =
                    UpiSmsParser.parse(
                        smsBody = body,
                        sender = sender
                    )



                if (transaction != null) {


                    CoroutineScope(
                        Dispatchers.IO
                    ).launch {


                        repository.insertTransaction(
                            transaction
                        )


                    }

                }


            }

        }

    }

}