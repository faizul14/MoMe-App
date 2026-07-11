package com.faezolmp.momeapp.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.SpaceDashboard
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.faezolmp.momeapp.presentation.ui.theme.BrandNavy
import com.faezolmp.momeapp.presentation.ui.theme.BrandSurface
import com.faezolmp.momeapp.presentation.ui.theme.TextSecondary

enum class BottomTab { DASHBOARD, HISTORY, SCAN, ADD, MANAGE }

@Composable
fun MomeBottomBar(
    activeTab: BottomTab,
    onDashboard: () -> Unit,
    onHistory: () -> Unit,
    onScan: () -> Unit,
    onAdd: () -> Unit,
    onManage: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = BrandSurface,
        shadowElevation = 12.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                icon = Icons.Filled.SpaceDashboard,
                label = "Dashboard",
                active = activeTab == BottomTab.DASHBOARD,
                onClick = onDashboard,
                modifier = Modifier.weight(1f)
            )
            BottomNavItem(
                icon = Icons.Filled.History,
                label = "History",
                active = activeTab == BottomTab.HISTORY,
                onClick = onHistory,
                modifier = Modifier.weight(1f)
            )
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                ScanButton(onClick = onScan)
            }
            BottomNavItem(
                icon = Icons.Filled.Add,
                label = "Add",
                active = activeTab == BottomTab.ADD,
                onClick = onAdd,
                modifier = Modifier.weight(1f)
            )
            BottomNavItem(
                icon = Icons.Filled.GridView,
                label = "Manage",
                active = activeTab == BottomTab.MANAGE,
                onClick = onManage,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: ImageVector,
    label: String,
    active: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val color = if (active) BrandNavy else TextSecondary
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = label,
            color = color,
            fontSize = 11.sp,
            fontWeight = if (active) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

@Composable
private fun ScanButton(onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(BrandNavy)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.DocumentScanner,
                contentDescription = "Scan",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        Text(text = "Scan", color = TextSecondary, fontSize = 11.sp)
    }
}
