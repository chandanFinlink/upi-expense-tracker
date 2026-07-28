package com.expensetracker.features.analytics

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
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
import com.expensetracker.viewmodel.AnalyticsViewModel
import com.expensetracker.viewmodel.AnalyticsViewModelFactory
import java.text.NumberFormat
import java.util.Locale



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen() {


    val application =
        LocalContext.current.applicationContext
                as ExpenseTrackerApplication


    val viewModel: AnalyticsViewModel =
        viewModel(
            factory = AnalyticsViewModelFactory(
                application.transactionRepository
            )
        )


    val totalExpense by
        viewModel.totalExpense.collectAsState()


    val totalIncome by
        viewModel.totalIncome.collectAsState()


    val todayExpense by
        viewModel.todayExpense.collectAsState()


    val monthlyExpense by
        viewModel.monthlyExpense.collectAsState()


    val transactionCount by
        viewModel.transactionCount.collectAsState()


    val debitCount by
        viewModel.debitCount.collectAsState()


    val creditCount by
        viewModel.creditCount.collectAsState()



    val formatter =
        NumberFormat.getCurrencyInstance(
            Locale("en", "IN")
        )



    Scaffold(

        topBar = {

            TopAppBar(
                title = {
                    Text("Analytics")
                }
            )

        }

    ) { padding ->


        Column(

            modifier =
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),

            verticalArrangement =
            Arrangement.spacedBy(12.dp)

        ) {


            AnalyticsCard(
                title = "Total Expense",
                value = formatter.format(totalExpense ?: 0.0)
            )


            AnalyticsCard(
                title = "Total Income",
                value = formatter.format(totalIncome ?: 0.0)
            )


            AnalyticsCard(
                title = "Today's Expense",
                value = formatter.format(todayExpense ?: 0.0)
            )


            AnalyticsCard(
                title = "This Month Expense",
                value = formatter.format(monthlyExpense ?: 0.0)
            )


            AnalyticsCard(
                title = "Total Transactions",
                value = transactionCount.toString()
            )


            AnalyticsCard(
                title = "Debit Transactions",
                value = debitCount.toString()
            )


            AnalyticsCard(
                title = "Credit Transactions",
                value = creditCount.toString()
            )


        }

    }

}



@Composable
private fun AnalyticsCard(
    title: String,
    value: String
) {

    Card(

        modifier =
        Modifier.fillMaxWidth()

    ) {


        Column(

            modifier =
            Modifier.padding(16.dp)

        ) {


            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )


            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall
            )


        }

    }

}