package com.expensetracker.features.transactions

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import com.expensetracker.ExpenseTrackerApplication
import com.expensetracker.database.entity.TransactionEntity
import com.expensetracker.viewmodel.TransactionViewModel
import com.expensetracker.viewmodel.TransactionViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen() {

    val application =
        LocalContext.current.applicationContext as ExpenseTrackerApplication

    val viewModel: TransactionViewModel =
        viewModel(
            factory = TransactionViewModelFactory(
                application.transactionRepository
            )
        )

    var merchant by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Add Transaction")
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                value = merchant,
                onValueChange = { merchant = it },
                label = { Text("Merchant") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {

                    val value =
                        amount.toDoubleOrNull() ?: return@Button

                    viewModel.addTransaction(
                        TransactionEntity(
                            amount = value,
                            merchant = merchant,
                            transactionType = "DEBIT",
                            transactionDate = System.currentTimeMillis()
                        )
                    )

                    merchant = ""
                    amount = ""

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Transaction")
            }

        }

    }

}