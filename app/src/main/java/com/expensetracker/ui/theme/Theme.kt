package com.expensetracker.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = Primary,
    secondary = Secondary,
    background = Background
)

@Composable
fun ExpenseTrackerTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = LightColors,
        typography = AppTypography,
        content = content
    )

}