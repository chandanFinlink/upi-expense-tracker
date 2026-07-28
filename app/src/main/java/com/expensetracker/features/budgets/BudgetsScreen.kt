package com.expensetracker.features.budgets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.expensetracker.ExpenseTrackerApplication
import com.expensetracker.database.entity.BudgetEntity
import com.expensetracker.viewmodel.BudgetViewModel
import com.expensetracker.viewmodel.BudgetViewModelFactory
import java.text.NumberFormat
import java.util.Locale

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

    val budget by viewModel.budget.collectAsState()
    val monthlyExpense by viewModel.monthlyExpense.collectAsState()

    var budgetAmount by remember {
        mutableStateOf("")
    }

    var alertPercentage by remember {
        mutableStateOf("80")
    }

    LaunchedEffect(budget) {

        budget?.let {

            budgetAmount = it.monthlyLimit.toString()
            alertPercentage = it.alertPercentage.toString()

        }

    }

    val formatter =
        NumberFormat.getCurrencyInstance(
            Locale("en", "IN")
        )

    val totalBudget =
        budget?.monthlyLimit ?: 0.0

    val spent =
        monthlyExpense ?: 0.0

    val remaining =
        totalBudget - spent

    val progress =
        if (totalBudget > 0)
            (spent / totalBudget).toFloat()
        else
            0f

    Scaffold(

        topBar = {

            TopAppBar(
                title = {
                    Text("Monthly Budget")
                }
            )

        }

    ) { padding ->

        Column(

            modifier = Modifier
                .padding(padding)
                .padding(16.dp),

            verticalArrangement =
                Arrangement.spacedBy(16.dp)

        ) {

            OutlinedTextField(

                value = budgetAmount,

                onValueChange = {
                    budgetAmount = it
                },

                label = {
                    Text("Budget Amount")
                },

                modifier = Modifier.fillMaxWidth()

            )

            OutlinedTextField(

                value = alertPercentage,

                onValueChange = {
                    alertPercentage = it
                },

                label = {
                    Text("Alert Percentage")
                },

                modifier = Modifier.fillMaxWidth()

            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Button(

                    modifier = Modifier.weight(1f),

                    onClick = {

                        val amount =
                            budgetAmount.toDoubleOrNull()
                                ?: return@Button

                        val alert =
                            alertPercentage.toIntOrNull()
                                ?: 80

                        if (budget == null) {

                            viewModel.saveBudget(

                                BudgetEntity(
                                    categoryId = 0,
                                    monthlyLimit = amount,
                                    alertPercentage = alert,
                                    month = "Monthly"
                                )

                            )

                        } else {

                            viewModel.updateBudget(
                                budget!!.id,
                                amount,
                                alert
                            )

                        }

                    }

                ) {

                    Text(
                        if (budget == null)
                            "Save"
                        else
                            "Update"
                    )

                }

                if (budget != null) {

                    OutlinedButton(

                        modifier = Modifier.weight(1f),

                        onClick = {

                            viewModel.deleteBudget(
                                budget!!.id
                            )

                            budgetAmount = ""
                            alertPercentage = "80"

                        }

                    ) {

                        Text("Delete")

                    }

                }

            }

            if (budget != null) {

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            "Monthly Budget",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            "Budget : ${formatter.format(totalBudget)}"
                        )

                        Text(
                            "Spent : ${formatter.format(spent)}"
                        )

                        Text(
                            "Remaining : ${formatter.format(remaining)}"
                        )

                        Spacer(Modifier.height(12.dp))

                        LinearProgressIndicator(
                            progress = {
                                progress.coerceIn(0f,1f)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(12.dp))

                        val percent =
                            progress * 100

                        when {

                            percent >= 100f ->
                                Text(
                                    "Over Budget",
                                    color = MaterialTheme.colorScheme.error
                                )

                            percent >= budget!!.alertPercentage ->
                                Text(
                                    "Warning (${budget!!.alertPercentage}%)"
                                )

                            else ->
                                Text("Within Budget")

                        }

                    }

                }

            }

        }

    }

}