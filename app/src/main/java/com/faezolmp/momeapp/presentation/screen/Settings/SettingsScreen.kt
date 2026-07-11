package com.faezolmp.momeapp.presentation.screen.Settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.faezolmp.momeapp.presentation.ui.components.PlaceholderScreen

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {}
) {
    PlaceholderScreen(
        title = "Setting",
        modifier = modifier,
        onBack = onBack
    )
}
