package com.faezolmp.momeapp.presentation.screen.History

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.faezolmp.momeapp.presentation.ui.components.PlaceholderScreen

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    onOpenDetail: (Long) -> Unit = {},
    onBack: () -> Unit = {}
) {
    PlaceholderScreen(
        title = "Riwayat Transaksi",
        modifier = modifier,
        onBack = onBack,
        actions = listOf(
            "Lihat Detail (contoh)" to { onOpenDetail(1L) }
        )
    )
}
