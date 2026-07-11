package com.faezolmp.momeapp.presentation.screen.Home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.faezolmp.momeapp.presentation.ui.components.PlaceholderScreen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onAddManual: () -> Unit = {},
    onScan: () -> Unit = {},
    onHistory: () -> Unit = {},
    onStatistics: () -> Unit = {},
    onSettings: () -> Unit = {},
    onOnboarding: () -> Unit = {}
) {
    PlaceholderScreen(
        title = "Home / Dashboard",
        modifier = modifier,
        actions = listOf(
            "Tambah Manual" to onAddManual,
            "Scan Struk" to onScan,
            "Riwayat" to onHistory,
            "Statistik" to onStatistics,
            "Setting" to onSettings,
            "Onboarding" to onOnboarding
        )
    )
}
