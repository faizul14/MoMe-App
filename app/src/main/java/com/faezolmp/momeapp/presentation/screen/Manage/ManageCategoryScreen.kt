package com.faezolmp.momeapp.presentation.screen.Manage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.DragIndicator
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import com.faezolmp.momeapp.presentation.ui.components.BottomTab
import com.faezolmp.momeapp.presentation.ui.components.MomeBottomBar
import com.faezolmp.momeapp.presentation.ui.components.ScreenLabel
import com.faezolmp.momeapp.presentation.ui.components.SearchField
import com.faezolmp.momeapp.presentation.ui.theme.BrandBackground
import com.faezolmp.momeapp.presentation.ui.theme.BrandNavy
import com.faezolmp.momeapp.presentation.ui.theme.BrandRingTrack
import com.faezolmp.momeapp.presentation.ui.theme.BrandSurface
import com.faezolmp.momeapp.presentation.ui.theme.FoodIconBg
import com.faezolmp.momeapp.presentation.ui.theme.FoodIconTint
import com.faezolmp.momeapp.presentation.ui.theme.MomeAppTheme
import com.faezolmp.momeapp.presentation.ui.theme.OrangeIconBg
import com.faezolmp.momeapp.presentation.ui.theme.OrangeIconTint
import com.faezolmp.momeapp.presentation.ui.theme.PillBlueBg
import com.faezolmp.momeapp.presentation.ui.theme.PinkIconBg
import com.faezolmp.momeapp.presentation.ui.theme.PinkIconTint
import com.faezolmp.momeapp.presentation.ui.theme.PurpleIconBg
import com.faezolmp.momeapp.presentation.ui.theme.PurpleIconTint
import com.faezolmp.momeapp.presentation.ui.theme.TextMuted
import com.faezolmp.momeapp.presentation.ui.theme.TextPrimary
import com.faezolmp.momeapp.presentation.ui.theme.TextSecondary
import com.faezolmp.momeapp.presentation.ui.theme.TransportIconBg
import com.faezolmp.momeapp.presentation.ui.theme.TransportIconTint

private enum class ManageCategoryVisual { FOOD, TRANSPORT, FUN, SHOPPING, HEALTH }

private data class ManageCategoryUi(
    val name: String,
    val transactionInfo: String,
    val visual: ManageCategoryVisual
)

private fun sampleManageCategories(): List<ManageCategoryUi> = listOf(
    ManageCategoryUi("Makan & Minum", "12 transaksi bulan ini", ManageCategoryVisual.FOOD),
    ManageCategoryUi("Transportasi", "8 transaksi bulan ini", ManageCategoryVisual.TRANSPORT),
    ManageCategoryUi("Hiburan", "4 transaksi bulan ini", ManageCategoryVisual.FUN),
    ManageCategoryUi("Belanja", "15 transaksi bulan ini", ManageCategoryVisual.SHOPPING),
    ManageCategoryUi("Kesehatan", "2 transaksi bulan ini", ManageCategoryVisual.HEALTH)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageCategoryScreen(
    modifier: Modifier = Modifier,
    onAddCategory: () -> Unit = {},
    onEditCategory: (String) -> Unit = {},
    onDashboard: () -> Unit = {},
    onHistory: () -> Unit = {},
    onScan: () -> Unit = {},
    onAdd: () -> Unit = {},
    onManage: () -> Unit = {}
) {
    val categories = sampleManageCategories()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = BrandBackground,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddCategory,
                containerColor = BrandNavy,
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Tambah")
            }
        },
        bottomBar = {
            MomeBottomBar(
                activeTab = BottomTab.MANAGE,
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
            ScreenLabel(text = "Manajemen Kategori")
            Spacer(modifier = Modifier.height(12.dp))
            Header()
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "Manajemen Kategori",
                color = BrandNavy,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Kelola kategori pengeluaran Anda agar anggaran lebih teratur.",
                color = TextSecondary,
                fontSize = 13.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            SearchField(placeholder = "Cari kategori...")
            Spacer(modifier = Modifier.height(16.dp))
            categories.forEach { category ->
                CategoryManageRow(category = category, onEdit = { onEditCategory(category.name) })
                Spacer(modifier = Modifier.height(12.dp))
            }
            Spacer(modifier = Modifier.height(4.dp))
            AddCategoryButton(onClick = onAddCategory)
            Spacer(modifier = Modifier.height(16.dp))
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
            text = "Fiscal Harmony",
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
private fun CategoryManageRow(category: ManageCategoryUi, onEdit: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(BrandSurface)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(visualBackground(category.visual)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = visualIcon(category.visual),
                contentDescription = null,
                tint = visualTint(category.visual),
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = category.name,
                color = TextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = category.transactionInfo, color = TextSecondary, fontSize = 12.sp)
        }
        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = "Edit",
            tint = TextMuted,
            modifier = Modifier
                .size(20.dp)
                .clickable { onEdit() }
        )
        Spacer(modifier = Modifier.width(14.dp))
        Icon(
            imageVector = Icons.Filled.DragIndicator,
            contentDescription = null,
            tint = TextMuted,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun AddCategoryButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(BrandSurface)
            .border(1.dp, BrandRingTrack, RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = null,
            tint = BrandNavy,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Tambah Kategori Baru",
            color = BrandNavy,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

private fun visualIcon(visual: ManageCategoryVisual): ImageVector = when (visual) {
    ManageCategoryVisual.FOOD -> Icons.Filled.Restaurant
    ManageCategoryVisual.TRANSPORT -> Icons.Filled.DirectionsCar
    ManageCategoryVisual.FUN -> Icons.Filled.SportsEsports
    ManageCategoryVisual.SHOPPING -> Icons.Filled.ShoppingBag
    ManageCategoryVisual.HEALTH -> Icons.Filled.MedicalServices
}

private fun visualBackground(visual: ManageCategoryVisual): Color = when (visual) {
    ManageCategoryVisual.FOOD -> OrangeIconBg
    ManageCategoryVisual.TRANSPORT -> TransportIconBg
    ManageCategoryVisual.FUN -> PurpleIconBg
    ManageCategoryVisual.SHOPPING -> PinkIconBg
    ManageCategoryVisual.HEALTH -> FoodIconBg
}

private fun visualTint(visual: ManageCategoryVisual): Color = when (visual) {
    ManageCategoryVisual.FOOD -> OrangeIconTint
    ManageCategoryVisual.TRANSPORT -> TransportIconTint
    ManageCategoryVisual.FUN -> PurpleIconTint
    ManageCategoryVisual.SHOPPING -> PinkIconTint
    ManageCategoryVisual.HEALTH -> FoodIconTint
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ManageCategoryScreenPreview() {
    MomeAppTheme {
        ManageCategoryScreen()
    }
}
