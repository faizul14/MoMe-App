package com.faezolmp.momeapp.presentation.screen.Confirm

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.faezolmp.momeapp.presentation.ui.components.PlaceholderScreen

@Composable
fun ConfirmTransactionScreen(
    modifier: Modifier = Modifier,
    onSave: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    PlaceholderScreen(
        title = "Konfirmasi Transaksi",
        modifier = modifier,
        onBack = onBack,
        actions = listOf(
            "Simpan (ke Home)" to onSave
        )
    )
}
