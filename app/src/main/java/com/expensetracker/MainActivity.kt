package com.expensetracker

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.expensetracker.navigation.AppNavigation
import com.expensetracker.ui.theme.ExpenseTrackerTheme

import androidx.lifecycle.lifecycleScope
import com.expensetracker.sms.SmsImporter
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val smsPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestSmsPermissions()

        val application = application as ExpenseTrackerApplication

        lifecycleScope.launch {
            SmsImporter(
                context = this@MainActivity,
                repository = application.transactionRepository
            ).importInbox()

        }

        setContent {
            ExpenseTrackerTheme {
                AppNavigation()
            }
        }
    }

    private fun requestSmsPermissions() {

        val permissions = mutableListOf<String>()

        if (
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.READ_SMS)
        }

        if (
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECEIVE_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.RECEIVE_SMS)
        }

        if (permissions.isNotEmpty()) {
            smsPermissionLauncher.launch(
                permissions.toTypedArray()
            )
        }
    }
}