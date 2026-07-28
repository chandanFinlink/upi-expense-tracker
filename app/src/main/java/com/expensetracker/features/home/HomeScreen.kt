package com.expensetracker.features.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.expensetracker.viewmodel.HomeViewModel
import com.expensetracker.viewmodel.ViewModelFactory
import com.expensetracker.ExpenseTrackerApplication
import androidx.compose.ui.platform.LocalContext

import androidx.navigation.NavController
import com.expensetracker.navigation.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {


    val application =
        LocalContext.current.applicationContext
                as ExpenseTrackerApplication


    val viewModel: HomeViewModel =
        viewModel(
            factory = ViewModelFactory(
                application.transactionRepository
            )
        )


    val totalExpense by
    viewModel.totalExpense.collectAsState()


    val transactions by
    viewModel.recentTransactions.collectAsState()


    val todayExpense by
    viewModel.todayExpense.collectAsState()

    val monthlyExpense by
        viewModel.monthlyExpense.collectAsState()

    val transactionCount by
        viewModel.transactionCount.collectAsState()



    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text("UPI Expense Tracker")

                }

            )

        }

    ) { padding ->


        Column(

            modifier =
            Modifier
                .padding(padding)
                .padding(16.dp),

            verticalArrangement =
            Arrangement.spacedBy(16.dp)

        ) {


            Card(
    modifier = Modifier.fillMaxWidth()
) {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

                Text(
                    text = "This Month",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "₹${monthlyExpense ?: 0.0}",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Today's Expense"
                )

                Text(
                    text = "₹${todayExpense ?: 0.0}"
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Total Transactions"
                )

                Text(
                    text = transactionCount.toString()
                )
            }
        }

        Text(
            text = "Recent Transactions",
            style = MaterialTheme.typography.titleMedium
        )


            if(transactions.isEmpty()) {


                Text(
                    text = "No transactions yet"
                )


            } else {


                transactions.forEach {

                    Text(
                        text = "${it.merchant} ₹${it.amount}"
                    )

                }


            }

        }

    }

}