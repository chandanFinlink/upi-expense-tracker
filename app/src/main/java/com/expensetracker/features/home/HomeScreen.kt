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
import androidx.compose.foundation.layout.SpaceBetween
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

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
                }
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

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(16.dp)
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

                        Text("Today's Debit")

                        Text(
                            text = "- " + currencyFormatter.format(todayDebit ?: 0.0),
                            color = Color.Red
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

            Text(
                text = "Recent Transactions",
                style = MaterialTheme.typography.titleMedium
            )

            if (transactions.isEmpty()) {

                Text(
                    text = "No transactions yet"
                )

            } else {

                transactions.forEach { transaction ->

                    val merchant =
                        transaction.merchant ?: "Unknown"

                    Text(
                        text = merchant +
                                " • " +
                                currencyFormatter.format(transaction.amount)
                    )

                }

            }

        }

    }

}