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

import androidx.compose.material3.AssistChip
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material.icons.filled.Check

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue

import java.util.LinkedHashMap
import androidx.compose.foundation.layout.fillMaxWidth



private fun getHeader( time: Long ): String {

    val today =
        SimpleDateFormat(
            "yyyyMMdd",
            Locale.getDefault()
        ).format(Date())

    val yesterday =
        SimpleDateFormat(
            "yyyyMMdd",
            Locale.getDefault()
        ).format(
            Date(
                System.currentTimeMillis() - 86400000L
            )
        )

    val date =
        SimpleDateFormat(
            "yyyyMMdd",
            Locale.getDefault()
        ).format(Date(time))

    return when (date) {

        today -> "Today"

        yesterday -> "Yesterday"

        else -> SimpleDateFormat(
            "dd MMM yyyy",
            Locale.getDefault()
        ).format(Date(time))

    }

}

private fun groupTransactions( list: List<com.expensetracker.database.entity.TransactionEntity> ): LinkedHashMap<String, List<com.expensetracker.database.entity.TransactionEntity>> {
    return LinkedHashMap( list.groupBy { getHeader(it.transactionDate) })
}



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

    val groupedTransactions =
            groupTransactions(transactions)

    val selectedFilter by
         viewModel.currentFilter.collectAsState()

    var search by remember {
        mutableStateOf("")
    }


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

       Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(padding)
) {

        OutlinedTextField(

        value = search,

        onValueChange = {

            search = it
            viewModel.setSearchQuery(it)

        },

        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            ),

        placeholder = {

            Text("Search merchant or bank")

        },

        singleLine = true,

        leadingIcon = {

            Icon(
                imageVector =Icons.Filled.Search,
                contentDescription = null
            )

        },

        colors = OutlinedTextFieldDefaults.colors()

    )

    Spacer(
        modifier = Modifier.height(8.dp)
    )

    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 12.dp, vertical = 8.dp),

        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        TransactionsViewModel.Filter.entries.forEach { filter ->

           FilterChip(

            selected = selectedFilter == filter,

            onClick = {
                viewModel.setFilter(filter)
            },

            label = {
                Text(
                    filter.name
                        .replace("_", " ")
                        .lowercase()
                        .replaceFirstChar { it.uppercase() }
                )
            },

            leadingIcon = {

                if (selectedFilter == filter) {

                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null
                    )

                }

            },

            colors = FilterChipDefaults.filterChipColors()

        )

        }

    }

        if (transactions.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Text("No transactions found")

            }

        } else {

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {

                items(
                    items = transactions,
                    key = { it.id }
                ) { transaction ->

                    // KEEP YOUR EXISTING CARD EXACTLY AS IT IS

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
                                    text = transaction.merchant ?: "Unknown Merchant",
                                    style = MaterialTheme.typography.titleMedium,
                                    maxLines = 1
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = formatter.format(
                                        Date(transaction.transactionDate)
                                    ),
                                    style = MaterialTheme.typography.bodySmall
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = transaction.bankName ?: "Unknown Bank",
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                    text = if (transaction.transactionType == "DEBIT")
                                            "- ${currency.format(transaction.amount)}"
                                        else
                                            "+ ${currency.format(transaction.amount)}",

                                    style = MaterialTheme.typography.headlineSmall,
                                    color =
                                        if (transaction.transactionType == "DEBIT")
                                            MaterialTheme.colorScheme.error
                                        else
                                            MaterialTheme.colorScheme.primary
                                )

                               FilterChip(
                                    selected = false,
                                    onClick = { },
                                    label = { Text( transaction.transactionType ) }
                                )

                            }
                        }

                    }

                }

            }

        }

    }
}