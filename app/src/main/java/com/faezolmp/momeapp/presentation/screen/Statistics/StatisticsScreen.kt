package com.faezolmp.momeapp.presentation.screen.Statistics

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.faezolmp.momeapp.presentation.ui.components.PlaceholderScreen

@Composable
fun StatisticsScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {}
) {
    PlaceholderScreen(
        title = "Statistik",
        modifier = modifier,
        onBack = onBack
    )
}
