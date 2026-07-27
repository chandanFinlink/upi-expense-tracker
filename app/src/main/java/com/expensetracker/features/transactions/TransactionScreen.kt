package com.expensetracker.features.transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.expensetracker.ExpenseTrackerApplication
import com.expensetracker.viewmodel.TransactionViewModel
import com.expensetracker.viewmodel.TransactionViewModelFactory

@Composable
fun TransactionScreen() {

    val application =
        LocalContext.current.applicationContext as ExpenseTrackerApplication

    val viewModel: TransactionViewModel =
        viewModel(
            factory = TransactionViewModelFactory(
                application.transactionRepository
            )
        )

    val transactions by
        viewModel.transactions.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items(transactions) { transaction ->

            TransactionItem(
                transaction = transaction
            )

        }

    }

}