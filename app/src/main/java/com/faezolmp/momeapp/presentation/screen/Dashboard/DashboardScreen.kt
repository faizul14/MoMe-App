package com.faezolmp.momeapp.presentation.screen.Dashboard

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
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.faezolmp.momeapp.core.utils.formatCompact
import com.faezolmp.momeapp.core.utils.formatRupiah
import com.faezolmp.momeapp.core.utils.formatSignedRupiah
import com.faezolmp.momeapp.presentation.ui.categoryBackgroundOf
import com.faezolmp.momeapp.presentation.ui.categoryColorOf
import com.faezolmp.momeapp.presentation.ui.categoryIconOf
import com.faezolmp.momeapp.presentation.ui.components.BottomTab
import com.faezolmp.momeapp.presentation.ui.components.BudgetRing
import com.faezolmp.momeapp.presentation.ui.components.CategoryProgressItem
import com.faezolmp.momeapp.presentation.ui.components.CategorySmallCard
import com.faezolmp.momeapp.presentation.ui.components.MomeBottomBar
import com.faezolmp.momeapp.presentation.ui.components.SectionHeader
import com.faezolmp.momeapp.presentation.ui.components.TransactionRow
import com.faezolmp.momeapp.presentation.ui.theme.BrandBackground
import com.faezolmp.momeapp.presentation.ui.theme.BrandNavy
import com.faezolmp.momeapp.presentation.ui.theme.BrandSurface
import com.faezolmp.momeapp.presentation.ui.theme.ExpenseRed
import com.faezolmp.momeapp.presentation.ui.theme.IncomeGreen
import com.faezolmp.momeapp.presentation.ui.theme.MomeAppTheme
import com.faezolmp.momeapp.presentation.ui.theme.PillBlueBg
import com.faezolmp.momeapp.presentation.ui.theme.PillBlueText
import com.faezolmp.momeapp.presentation.ui.theme.TextPrimary
import com.faezolmp.momeapp.presentation.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    state: DashboardUiState,
    modifier: Modifier = Modifier,
    onSeeAllActivities: () -> Unit = {},
    onActivityClick: (Long) -> Unit = {},
    onManageCategories: () -> Unit = {},
    onNotifications: () -> Unit = {},
    onDashboard: () -> Unit = {},
    onHistory: () -> Unit = {},
    onScan: () -> Unit = {},
    onAdd: () -> Unit = {},
    onManage: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = BrandBackground,
        bottomBar = {
            MomeBottomBar(
                activeTab = BottomTab.DASHBOARD,
                onDashboard = onDashboard,
                onHistory = onHistory,
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
            Spacer(modifier = Modifier.height(16.dp))
            HeaderProfile(userName = state.userName, onNotifications = onNotifications)
            Spacer(modifier = Modifier.height(20.dp))
            BudgetCard(state = state)
            Spacer(modifier = Modifier.height(24.dp))
            CategorySection(state = state, onCategoryClick = onManageCategories)
            Spacer(modifier = Modifier.height(24.dp))
            SectionHeader(
                title = "Aktivitas Terakhir",
                actionText = "Lihat Semua",
                onActionClick = onSeeAllActivities
            )
            Spacer(modifier = Modifier.height(12.dp))
            if (state.recentActivities.isEmpty()) {
                EmptyActivity()
            } else {
                state.recentActivities.forEach { activity ->
                    TransactionRow(
                        icon = categoryIconOf(activity.iconKey),
                        title = activity.title,
                        subtitle = activity.time,
                        amountText = formatSignedRupiah(activity.amount, activity.isIncome, state.currencySymbol),
                        amountColor = if (activity.isIncome) IncomeGreen else ExpenseRed,
                        onClick = { onActivityClick(activity.id) }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun HeaderProfile(userName: String, onNotifications: () -> Unit) {
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
            text = userName,
            color = BrandNavy,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(BrandSurface)
                .clickable { onNotifications() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.NotificationsNone,
                contentDescription = "Notifikasi",
                tint = BrandNavy,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

@Composable
private fun BudgetCard(state: DashboardUiState) {
    val progress = if (state.budgetTotal > 0L) {
        (state.budgetTotal - state.budgetRemaining).toFloat() / state.budgetTotal.toFloat()
    } else {
        0f
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(BrandSurface)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(PillBlueBg)
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            Text(
                text = "SISA BUDGET HARI INI",
                color = PillBlueText,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.5.sp
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        BudgetRing(progress = progress) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = formatCompact(state.budgetRemaining, state.currencySymbol),
                    color = TextPrimary,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "dari ${formatCompact(state.budgetTotal, state.currencySymbol)}",
                    color = TextSecondary,
                    fontSize = 13.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = state.motivation,
            color = BrandNavy,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun CategorySection(state: DashboardUiState, onCategoryClick: () -> Unit) {
    val main = state.categories.firstOrNull()
    val small = state.categories.drop(1).take(2)
    if (main != null) {
        val progress = if (state.budgetTotal > 0L) main.spent.toFloat() / state.budgetTotal.toFloat() else 0f
        CategoryProgressItem(
            icon = categoryIconOf(main.iconKey),
            iconBackground = categoryBackgroundOf(main.colorHex),
            iconTint = categoryColorOf(main.colorHex),
            name = main.name,
            amountText = formatRupiah(main.spent, state.currencySymbol),
            progress = progress,
            remainingText = "Terpakai ${formatCompact(main.spent, state.currencySymbol)}",
            progressColor = categoryColorOf(main.colorHex),
            modifier = Modifier.clickable { onCategoryClick() }
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
    if (small.isNotEmpty()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            small.forEach { category ->
                CategorySmallCard(
                    icon = categoryIconOf(category.iconKey),
                    iconBackground = categoryBackgroundOf(category.colorHex),
                    iconTint = categoryColorOf(category.colorHex),
                    name = category.name,
                    amountText = formatRupiah(category.spent, state.currencySymbol),
                    amountColor = TextPrimary,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onCategoryClick() }
                )
            }
        }
    }
}

@Composable
private fun EmptyActivity() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(BrandSurface)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Belum ada transaksi.",
            color = TextSecondary,
            fontSize = 13.sp
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DashboardScreenPreview() {
    MomeAppTheme {
        DashboardScreen(
            state = DashboardUiState(
                budgetRemaining = 50_000L,
                budgetTotal = 150_000L,
                motivation = "Budget aman hari ini.",
                categories = listOf(
                    DashboardCategoryUi("Makan & Minum", "food", "#E8912E", 65_000L),
                    DashboardCategoryUi("Transportasi", "transport", "#4B69C6", 20_000L),
                    DashboardCategoryUi("Hiburan", "fun", "#7A5AF8", 15_000L)
                ),
                recentActivities = listOf(
                    DashboardActivityUi(1L, "Belanja Mingguan", "24 Sep, 10:45", 120_000L, false, "shopping", "#DB4E96"),
                    DashboardActivityUi(2L, "Transfer Masuk", "23 Sep, 16:15", 500_000L, true, "income", "#2FB673")
                )
            )
        )
    }
}
