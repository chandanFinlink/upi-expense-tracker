package com.expensetracker.features.transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.expensetracker.ExpenseTrackerApplication
import com.expensetracker.database.entity.TransactionEntity
import com.expensetracker.viewmodel.TransactionViewModel
import com.expensetracker.viewmodel.TransactionViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    navController: NavController
) {

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

                    val value = amount.toDoubleOrNull() ?: return@Button

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

                    navController.popBackStack()

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Transaction")
            }
        }
    }
}