package com.expensetracker.features.transactions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.expensetracker.ExpenseTrackerApplication
import com.expensetracker.viewmodel.TransactionsViewModel
import com.expensetracker.viewmodel.TransactionsViewModelFactory
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsScreen() {

    val application =
        LocalContext.current.applicationContext as ExpenseTrackerApplication

    val viewModel: TransactionsViewModel =
        viewModel(
            factory = TransactionsViewModelFactory(
                application.transactionRepository
            )
        )

    val transactions by
        viewModel.transactions.collectAsState()

    val currency =
        NumberFormat.getCurrencyInstance(
            Locale("en", "IN")
        )

    val formatter =
        SimpleDateFormat(
            "dd MMM yyyy hh:mm a",
            Locale.getDefault()
        )

    Scaffold(

        topBar = {

            TopAppBar(
                title = {
                    Text("Transactions")
                }
            )

        }

    ) { padding ->

        if (transactions.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {

                Text("No transactions found")

            }

        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {

                items(transactions) { transaction ->

                    Card(
                        modifier = Modifier
                            .padding(
                                horizontal = 12.dp,
                                vertical = 6.dp
                            )
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                text = transaction.merchant ?: "Unknown",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = currency.format(transaction.amount)
                            )

                            Text(
                                text = transaction.transactionType
                            )

                            Text(
                                text = formatter.format(
                                    Date(transaction.transactionDate)
                                )
                            )

                            transaction.bankName?.let {

                                Text(
                                    text = it
                                )

                            }

                        }

                    }

                }

            }

        }

    }

}