package com.expensetracker.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.expensetracker.features.analytics.AnalyticsScreen
import com.expensetracker.features.budgets.BudgetsScreen
import com.expensetracker.features.home.HomeScreen
import com.expensetracker.features.settings.SettingsScreen
import com.expensetracker.features.transactions.TransactionsScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    Scaffold(

        bottomBar = {

            NavigationBar {

                val navBackStackEntry by navController.currentBackStackEntryAsState()

                val currentDestination = navBackStackEntry?.destination

                BottomNavigationItems.forEach { item ->

                    NavigationBarItem(

                        selected = currentDestination?.hierarchy?.any {
                            it.route == item.route
                        } == true,

                        onClick = {

                            navController.navigate(item.route) {

                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }

                                launchSingleTop = true

                                restoreState = true
                            }

                        },

                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },

                        label = {
                            Text(item.title)
                        }

                    )

                }

            }

        }

    ) { padding ->

        NavHost(

            modifier = Modifier.padding(padding),

            navController = navController,

            startDestination = Routes.Home.route

        ) {

            composable(Routes.Home.route) {

                HomeScreen()

            }

            composable(Routes.Transactions.route) {

                TransactionsScreen()

            }

            composable(Routes.Budgets.route) {

                BudgetsScreen()

            }

            composable(Routes.Analytics.route) {

                AnalyticsScreen()

            }

            composable(Routes.Settings.route) {

                SettingsScreen()

            }

        }

    }

}