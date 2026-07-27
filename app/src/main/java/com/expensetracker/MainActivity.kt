package com.expensetracker

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    private val permissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            val readSmsGranted =
                permissions[Manifest.permission.READ_SMS] ?: false

            val receiveSmsGranted =
                permissions[Manifest.permission.RECEIVE_SMS] ?: false

            val notificationGranted =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                    permissions[Manifest.permission.POST_NOTIFICATIONS] ?: false
                else
                    true

            // You can use these values if needed
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permissions = mutableListOf(
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        permissionLauncher.launch(permissions.toTypedArray())

        setContent {

            MaterialTheme {

                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = "UPI Expense Tracker",
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Text(
                            text = "App-Specific Budgeting & SMS Reader Active"
                        )
                    }
                }
            }
        }
    }
}
