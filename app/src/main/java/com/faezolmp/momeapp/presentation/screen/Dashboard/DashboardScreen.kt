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
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.SpaceDashboard
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.faezolmp.momeapp.core.utils.formatCompact
import com.faezolmp.momeapp.core.utils.formatRupiah
import com.faezolmp.momeapp.core.utils.formatSignedRupiah
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
import com.faezolmp.momeapp.presentation.ui.theme.FoodIconBg
import com.faezolmp.momeapp.presentation.ui.theme.FoodIconTint
import com.faezolmp.momeapp.presentation.ui.theme.FunAmountText
import com.faezolmp.momeapp.presentation.ui.theme.FunIconBg
import com.faezolmp.momeapp.presentation.ui.theme.FunIconTint
import com.faezolmp.momeapp.presentation.ui.theme.IncomeGreen
import com.faezolmp.momeapp.presentation.ui.theme.MomeAppTheme
import com.faezolmp.momeapp.presentation.ui.theme.PillBlueBg
import com.faezolmp.momeapp.presentation.ui.theme.PillBlueText
import com.faezolmp.momeapp.presentation.ui.theme.TextPrimary
import com.faezolmp.momeapp.presentation.ui.theme.TextSecondary
import com.faezolmp.momeapp.presentation.ui.theme.TransportIconBg
import com.faezolmp.momeapp.presentation.ui.theme.TransportIconTint

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    state: DashboardUiState,
    modifier: Modifier = Modifier,
    onSeeAllActivities: () -> Unit = {},
    onActivityClick: (ActivityUi) -> Unit = {},
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
            Spacer(modifier = Modifier.height(12.dp))
            ScreenLabel()
            Spacer(modifier = Modifier.height(12.dp))
            HeaderProfile(userName = state.userName, onNotifications = onNotifications)
            Spacer(modifier = Modifier.height(20.dp))
            BudgetCard(state = state)
            Spacer(modifier = Modifier.height(24.dp))
            CategorySection(state = state)
            Spacer(modifier = Modifier.height(24.dp))
            SectionHeader(
                title = "Aktivitas Terakhir",
                actionText = "Lihat Semua",
                onActionClick = onSeeAllActivities
            )
            Spacer(modifier = Modifier.height(12.dp))
            state.recentActivities.forEach { activity ->
                TransactionRow(
                    icon = activityIcon(activity.visual),
                    title = activity.title,
                    subtitle = activity.time,
                    amountText = formatSignedRupiah(activity.amount, activity.isIncome, state.currencySymbol),
                    amountColor = if (activity.isIncome) IncomeGreen else ExpenseRed,
                    onClick = { onActivityClick(activity) }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ScreenLabel() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Filled.SpaceDashboard,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "Dashboard Utama",
            color = TextSecondary,
            fontSize = 13.sp
        )
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
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(ExpenseRed)
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
private fun CategorySection(state: DashboardUiState) {
    state.mainCategory?.let { main ->
        val remaining = main.remaining ?: 0L
        val denominator = main.spent + remaining
        val progress = if (denominator > 0L) main.spent.toFloat() / denominator.toFloat() else 0f
        CategoryProgressItem(
            icon = categoryIcon(main.visual),
            iconBackground = categoryBackground(main.visual),
            iconTint = categoryTint(main.visual),
            name = main.name,
            amountText = formatRupiah(main.spent, state.currencySymbol),
            progress = progress,
            remainingText = "Sisa: ${formatCompact(remaining, state.currencySymbol)}"
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        state.smallCategories.forEach { category ->
            CategorySmallCard(
                icon = categoryIcon(category.visual),
                iconBackground = categoryBackground(category.visual),
                iconTint = categoryTint(category.visual),
                name = category.name,
                amountText = formatRupiah(category.spent, state.currencySymbol),
                amountColor = if (category.visual == CategoryVisual.FUN) FunAmountText else TextPrimary,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

private fun categoryIcon(visual: CategoryVisual): ImageVector = when (visual) {
    CategoryVisual.FOOD -> Icons.Filled.Restaurant
    CategoryVisual.TRANSPORT -> Icons.Filled.DirectionsCar
    CategoryVisual.FUN -> Icons.Filled.SportsEsports
}

private fun categoryBackground(visual: CategoryVisual): Color = when (visual) {
    CategoryVisual.FOOD -> FoodIconBg
    CategoryVisual.TRANSPORT -> TransportIconBg
    CategoryVisual.FUN -> FunIconBg
}

private fun categoryTint(visual: CategoryVisual): Color = when (visual) {
    CategoryVisual.FOOD -> FoodIconTint
    CategoryVisual.TRANSPORT -> TransportIconTint
    CategoryVisual.FUN -> FunIconTint
}

private fun activityIcon(visual: ActivityVisual): ImageVector = when (visual) {
    ActivityVisual.SHOPPING -> Icons.Filled.ShoppingBag
    ActivityVisual.COFFEE -> Icons.Filled.LocalCafe
    ActivityVisual.INCOME -> Icons.Filled.AccountBalanceWallet
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DashboardScreenPreview() {
    MomeAppTheme {
        DashboardScreen(state = sampleDashboardState())
    }
}
