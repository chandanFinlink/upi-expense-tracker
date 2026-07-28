package com.expensetracker.features.settings

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.expensetracker.BuildConfig
import com.expensetracker.ExpenseTrackerApplication
import com.expensetracker.viewmodel.SettingsViewModel
import com.expensetracker.viewmodel.SettingsViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {

    val context = LocalContext.current

    val application =
        context.applicationContext as ExpenseTrackerApplication

    val viewModel: SettingsViewModel =
        viewModel(
            factory = SettingsViewModelFactory(
                application.transactionRepository
            )
        )

    val smsPermissionGranted =
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_SMS
        ) == PackageManager.PERMISSION_GRANTED

    Scaffold(

        topBar = {

            TopAppBar(
                title = {
                    Text("Settings")
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

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        "SMS Permission",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        if (smsPermissionGranted)
                            "Granted"
                        else
                            "Not Granted"
                    )

                }

            }

            Button(

                modifier = Modifier.fillMaxWidth(),

                onClick = {

                    viewModel.clearDatabase()

                }

            ) {

                Text("Clear Local Database")

            }

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        "App Version",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(BuildConfig.VERSION_NAME)

                }

            }

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        "Today's SMS Import",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        "Automatic on app startup"
                    )

                }

            }

        }

    }

}