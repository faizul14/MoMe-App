package com.faezolmp.momeapp.presentation.screen.AddManual

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.faezolmp.momeapp.presentation.ui.components.PlaceholderScreen

@Composable
fun AddManualScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {}
) {
    PlaceholderScreen(
        title = "Tambah Transaksi Manual",
        modifier = modifier,
        onBack = onBack
    )
}
