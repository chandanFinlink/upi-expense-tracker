package com.expensetracker.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.navigation.NavController
import com.expensetracker.ExpenseTrackerApplication
import com.expensetracker.navigation.Routes
import com.expensetracker.viewmodel.HomeViewModel
import com.expensetracker.viewmodel.ViewModelFactory
import java.text.NumberFormat
import java.util.Locale

import androidx.compose.foundation.layout.Row
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.PaddingValues

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {

    val application =
        LocalContext.current.applicationContext as ExpenseTrackerApplication

    val viewModel: HomeViewModel =
        viewModel(
            factory = ViewModelFactory(
                application.transactionRepository
            )
        )

    val totalExpense by
        viewModel.totalExpense.collectAsState()

    val todayExpense by
        viewModel.todayExpense.collectAsState()

    val todayCredit by
          viewModel.todayCredit.collectAsState()

    val todayDebit by
             viewModel.todayDebit.collectAsState()

    val netToday by
             viewModel.netToday.collectAsState()

    val monthlyExpense by
        viewModel.monthlyExpense.collectAsState()

    val transactionCount by
        viewModel.transactionCount.collectAsState()

    val transactions by
        viewModel.recentTransactions.collectAsState()

    val currencyFormatter =
        NumberFormat.getCurrencyInstance(
            Locale("en", "IN")
        )

    Scaffold(

        floatingActionButton = {

           FloatingActionButton(
                onClick = {
                    navController.navigate(Routes.AddTransaction.route)
                },
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.75f)
            ) {
                Text("+")
            }

        },

        topBar = {

            TopAppBar(
                title = {
                    Text("UPI Expense Tracker")
                }
            )

        }

    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp),

            verticalArrangement = Arrangement.spacedBy(16.dp),

            contentPadding = PaddingValues(
                top = 16.dp,
                bottom = 100.dp
            )
        ) {

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                     Text(
                        text = "Today's Summary",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text("Today's Debit")

                        Text(
                            text = "- " + currencyFormatter.format(todayDebit ?: 0.0),
                            color = Color.Red
                        )

                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text("Today's Credit")

                        Text(
                            text = "+ " + currencyFormatter.format(todayCredit ?: 0.0),
                            color = Color(0xFF2E7D32)
                        )

                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            "Net Amount",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text =
                            if ((netToday ?: 0.0) >= 0)
                                "+ " + currencyFormatter.format(netToday ?: 0.0)
                            else
                                "- " + currencyFormatter.format(kotlin.math.abs(netToday ?: 0.0)),

                            color =
                            if ((netToday ?: 0.0) >= 0)
                                Color(0xFF2E7D32)
                            else
                                Color.Red,

                            style = MaterialTheme.typography.titleMedium
                        )

                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text("This Month")

                        Text(
                            currencyFormatter.format(monthlyExpense ?: 0.0)
                        )

                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text("Total Transactions")

                        Text(transactionCount.toString())

                    }                  
                }                  

            }

            item {

                Text(
                    text = "Recent Transactions",
                    style = MaterialTheme.typography.titleMedium
                )
            }

           if (transactions.isEmpty()) {
                item {
                    Text(
                        text = "No transactions yet"
                    )
                }
            }

            } else {

                items(transactions) { transaction ->
                  item {
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),

                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Column {

                                    Text(
                                        text = transaction.merchant ?: "Unknown",
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    transaction.bankName?.let {

                                        Text(
                                            text = it,
                                            style = MaterialTheme.typography.bodySmall
                                        )

                                    }

                                }

                                val isDebit =
                                    transaction.transactionType == "DEBIT"

                                Text(

                                    text =
                                    if (isDebit)
                                        "- ${currencyFormatter.format(transaction.amount)}"
                                    else
                                        "+ ${currencyFormatter.format(transaction.amount)}",

                                    color =
                                    if (isDebit)
                                        Color.Red
                                    else
                                        Color(0xFF2E7D32),

                                    style =
                                    MaterialTheme.typography.titleMedium

                                )

                            }
                        }

                    }
                }

            }

        }
}