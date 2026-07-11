package com.faezolmp.momeapp.presentation.screen.Detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.faezolmp.momeapp.presentation.ui.components.PlaceholderScreen

@Composable
fun TransactionDetailScreen(
    transactionId: Long,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {}
) {
    PlaceholderScreen(
        title = "Detail Transaksi #$transactionId",
        modifier = modifier,
        onBack = onBack
    )
}
