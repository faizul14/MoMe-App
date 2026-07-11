package com.faezolmp.momeapp.presentation.screen.Scan

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.faezolmp.momeapp.presentation.ui.components.PlaceholderScreen

@Composable
fun ScanScreen(
    modifier: Modifier = Modifier,
    onConfirm: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    PlaceholderScreen(
        title = "Scan Struk",
        modifier = modifier,
        onBack = onBack,
        actions = listOf(
            "Lanjut ke Konfirmasi" to onConfirm
        )
    )
}
