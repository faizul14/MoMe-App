package com.faezolmp.momeapp.presentation.screen.History

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.faezolmp.momeapp.core.utils.formatSignedRupiah
import com.faezolmp.momeapp.presentation.ui.components.BottomTab
import com.faezolmp.momeapp.presentation.ui.components.MomeBottomBar
import com.faezolmp.momeapp.presentation.ui.components.MomeChip
import com.faezolmp.momeapp.presentation.ui.components.SearchField
import com.faezolmp.momeapp.presentation.ui.theme.BrandBackground
import com.faezolmp.momeapp.presentation.ui.theme.BrandNavy
import com.faezolmp.momeapp.presentation.ui.theme.BrandSurface
import com.faezolmp.momeapp.presentation.ui.theme.ExpenseRed
import com.faezolmp.momeapp.presentation.ui.theme.FieldBg
import com.faezolmp.momeapp.presentation.ui.theme.FoodIconBg
import com.faezolmp.momeapp.presentation.ui.theme.FoodIconTint
import com.faezolmp.momeapp.presentation.ui.theme.FunIconBg
import com.faezolmp.momeapp.presentation.ui.theme.FunIconTint
import com.faezolmp.momeapp.presentation.ui.theme.IncomeGreen
import com.faezolmp.momeapp.presentation.ui.theme.MomeAppTheme
import com.faezolmp.momeapp.presentation.ui.theme.PillBlueBg
import com.faezolmp.momeapp.presentation.ui.theme.TextMuted
import com.faezolmp.momeapp.presentation.ui.theme.TextPrimary
import com.faezolmp.momeapp.presentation.ui.theme.TextSecondary
import com.faezolmp.momeapp.presentation.ui.theme.TransportIconBg
import com.faezolmp.momeapp.presentation.ui.theme.TransportIconTint

private enum class HistoryVisual { FOOD, TRANSPORT, INCOME, SHOPPING, MOVIE }

private data class HistoryItemUi(
    val title: String,
    val time: String,
    val category: String,
    val amount: Long,
    val isIncome: Boolean,
    val visual: HistoryVisual,
    val hasAttachment: Boolean
)

private data class HistoryGroup(
    val label: String,
    val date: String,
    val items: List<HistoryItemUi>
)

private fun sampleHistoryGroups(): List<HistoryGroup> = listOf(
    HistoryGroup(
        label = "HARI INI",
        date = "24 Sep 2024",
        items = listOf(
            HistoryItemUi("Makan Siang", "12:45", "Boga Resto", 45_000L, false, HistoryVisual.FOOD, true),
            HistoryItemUi("Ojek Online", "08:15", "Transportasi", 12_500L, false, HistoryVisual.TRANSPORT, false)
        )
    ),
    HistoryGroup(
        label = "KEMARIN",
        date = "23 Sep 2024",
        items = listOf(
            HistoryItemUi("Top up Dompet", "20:30", "Pemasukan", 500_000L, true, HistoryVisual.INCOME, false),
            HistoryItemUi("Belanja Mingguan", "16:45", "Supermarket", 215_300L, false, HistoryVisual.SHOPPING, true)
        )
    ),
    HistoryGroup(
        label = "MINGGU",
        date = "22 Sep 2024",
        items = listOf(
            HistoryItemUi("Tiket Bioskop", "19:00", "Hiburan", 105_000L, false, HistoryVisual.MOVIE, false)
        )
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    onOpenDetail: (Long) -> Unit = {},
    onDashboard: () -> Unit = {},
    onScan: () -> Unit = {},
    onAdd: () -> Unit = {},
    onManage: () -> Unit = {}
) {
    val filters = listOf("Semua", "Makanan", "Transportasi")
    var selectedFilter by remember { mutableIntStateOf(0) }
    val groups = remember { sampleHistoryGroups() }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = BrandBackground,
        bottomBar = {
            MomeBottomBar(
                activeTab = BottomTab.HISTORY,
                onDashboard = onDashboard,
                onHistory = {},
                onScan = onScan,
                onAdd = onAdd,
                onManage = onManage
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Header()
            Spacer(modifier = Modifier.height(16.dp))
            SearchField(placeholder = "Cari transaksi...")
            Spacer(modifier = Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                filters.forEachIndexed { index, filter ->
                    MomeChip(
                        text = filter,
                        selected = index == selectedFilter,
                        onClick = { selectedFilter = index },
                        icon = if (index == 0) Icons.Filled.Tune else null
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            groups.forEach { group ->
                GroupHeader(label = group.label, date = group.date)
                Spacer(modifier = Modifier.height(10.dp))
                group.items.forEach { item ->
                    HistoryRow(item = item, onClick = { onOpenDetail(1L) })
                    Spacer(modifier = Modifier.height(10.dp))
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun Header() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(PillBlueBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = null,
                tint = BrandNavy,
                modifier = Modifier.size(26.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "Riwayat",
            color = BrandNavy,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Filled.NotificationsNone,
            contentDescription = "Notifikasi",
            tint = BrandNavy,
            modifier = Modifier.size(22.dp)
        )
    }
}

@Composable
private fun GroupHeader(label: String, date: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = TextSecondary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )
        Text(text = date, color = TextSecondary, fontSize = 12.sp)
    }
}

@Composable
private fun HistoryRow(item: HistoryItemUi, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(BrandSurface)
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(visualBackground(item.visual)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = visualIcon(item.visual),
                contentDescription = null,
                tint = visualTint(item.visual),
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                color = TextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${item.time} • ${item.category}",
                color = TextSecondary,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = formatSignedRupiah(item.amount, item.isIncome),
            color = if (item.isIncome) IncomeGreen else ExpenseRed,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        if (item.hasAttachment) {
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(FieldBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Image,
                    contentDescription = null,
                    tint = TextMuted,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

private fun visualIcon(visual: HistoryVisual): ImageVector = when (visual) {
    HistoryVisual.FOOD -> Icons.Filled.Restaurant
    HistoryVisual.TRANSPORT -> Icons.Filled.TwoWheeler
    HistoryVisual.INCOME -> Icons.Filled.AccountBalanceWallet
    HistoryVisual.SHOPPING -> Icons.Filled.ShoppingBag
    HistoryVisual.MOVIE -> Icons.Filled.Movie
}

private fun visualBackground(visual: HistoryVisual): Color = when (visual) {
    HistoryVisual.FOOD -> FoodIconBg
    HistoryVisual.TRANSPORT -> TransportIconBg
    HistoryVisual.INCOME -> FunIconBg
    HistoryVisual.SHOPPING -> TransportIconBg
    HistoryVisual.MOVIE -> TransportIconBg
}

private fun visualTint(visual: HistoryVisual): Color = when (visual) {
    HistoryVisual.FOOD -> FoodIconTint
    HistoryVisual.TRANSPORT -> TransportIconTint
    HistoryVisual.INCOME -> FunIconTint
    HistoryVisual.SHOPPING -> TransportIconTint
    HistoryVisual.MOVIE -> TransportIconTint
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HistoryScreenPreview() {
    MomeAppTheme {
        HistoryScreen()
    }
}
