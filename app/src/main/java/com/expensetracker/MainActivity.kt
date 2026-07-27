package com.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.expensetracker.navigation.AppNavigation
import com.expensetracker.ui.theme.ExpenseTrackerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            ExpenseTrackerTheme {

                AppNavigation()

            }

        }

    }

}