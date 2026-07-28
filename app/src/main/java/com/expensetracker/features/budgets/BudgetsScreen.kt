package com.expensetracker.features.budgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.expensetracker.ExpenseTrackerApplication
import com.expensetracker.database.entity.BudgetEntity
import com.expensetracker.viewmodel.BudgetViewModel
import com.expensetracker.viewmodel.BudgetViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetsScreen() {

    val application =
        LocalContext.current.applicationContext as ExpenseTrackerApplication

    val viewModel: BudgetViewModel =
        viewModel(
            factory = BudgetViewModelFactory(
                application.budgetRepository,
                application.transactionRepository
            )
        )

    val budgets by viewModel.budgets.collectAsState()

    var monthlyLimit by remember { mutableStateOf("") }

    Scaffold(

        topBar = {
            TopAppBar(
                title = {
                    Text("Budgets")
                }
            )
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                value = monthlyLimit,
                onValueChange = { monthlyLimit = it },
                label = { Text("Monthly Budget") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {

                    val amount =
                        monthlyLimit.toDoubleOrNull() ?: return@Button

                    viewModel.saveBudget(

                        BudgetEntity(
                            categoryId = 0,
                            monthlyLimit = amount,
                            month = "2026-07"
                        )

                    )

                    monthlyLimit = ""

                }
            ) {

                Text("Save Budget")

            }

            LazyColumn {

                items(budgets) { budget ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                "Month : ${budget.month}",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                "Budget : ₹${budget.monthlyLimit}"
                            )

                            Text(
                                "Alert : ${budget.alertPercentage}%"
                            )

                        }

                    }

                }

            }

        }

    }

}