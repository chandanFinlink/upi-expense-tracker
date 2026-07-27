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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {


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


            Text(
                text = "Total Expense",
                style = MaterialTheme.typography.titleMedium
            )


            Text(
                text = "₹${totalExpense ?: 0}",
                style = MaterialTheme.typography.headlineLarge
            )


            Text(
                text = "Recent Transactions"
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