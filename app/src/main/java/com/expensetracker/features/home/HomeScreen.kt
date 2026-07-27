package com.expensetracker.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

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

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {

            Text(
                text = "Good Morning 👋",
                style = MaterialTheme.typography.headlineMedium
            )

            Card(
                colors = CardDefaults.cardColors()
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Total Expenses",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "₹0.00",
                        style = MaterialTheme.typography.headlineLarge
                    )

                }

            }

            Card {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Recent Transactions"
                    )

                    Text(
                        text = "No transactions found."
                    )

                }

            }

        }

    }

}