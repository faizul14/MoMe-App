package com.faezolmp.momeapp.presentation.screen.Settings

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
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.faezolmp.momeapp.presentation.ui.components.BottomTab
import com.faezolmp.momeapp.presentation.ui.components.MomeBottomBar
import com.faezolmp.momeapp.presentation.ui.components.MomeCard
import com.faezolmp.momeapp.presentation.ui.components.ScreenLabel
import com.faezolmp.momeapp.presentation.ui.theme.BrandBackground
import com.faezolmp.momeapp.presentation.ui.theme.BrandNavy
import com.faezolmp.momeapp.presentation.ui.theme.BrandRingTrack
import com.faezolmp.momeapp.presentation.ui.theme.DividerColor
import com.faezolmp.momeapp.presentation.ui.theme.MomeAppTheme
import com.faezolmp.momeapp.presentation.ui.theme.OrangeIconTint
import com.faezolmp.momeapp.presentation.ui.theme.PillBlueBg
import com.faezolmp.momeapp.presentation.ui.theme.TextMuted
import com.faezolmp.momeapp.presentation.ui.theme.TextPrimary
import com.faezolmp.momeapp.presentation.ui.theme.TextSecondary
import com.faezolmp.momeapp.presentation.ui.theme.WarnBg
import com.faezolmp.momeapp.presentation.ui.theme.WarnTint
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onCurrency: () -> Unit = {},
    onLogout: () -> Unit = {},
    onDashboard: () -> Unit = {},
    onHistory: () -> Unit = {},
    onScan: () -> Unit = {},
    onAdd: () -> Unit = {}
) {
    var dailyReminder by remember { mutableStateOf(true) }
    var budgetAlert by remember { mutableStateOf(true) }
    var biometric by remember { mutableStateOf(false) }
    var darkMode by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = BrandBackground,
        bottomBar = {
            MomeBottomBar(
                activeTab = BottomTab.MANAGE,
                onDashboard = onDashboard,
                onHistory = onHistory,
                onScan = onScan,
                onAdd = onAdd,
                onManage = {}
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
            ScreenLabel(text = "Pengaturan Umum")
            Spacer(modifier = Modifier.height(12.dp))
            Header()
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "Pengaturan Aplikasi",
                color = BrandNavy,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Kelola akun dan preferensi finansial Anda.",
                color = OrangeIconTint,
                fontSize = 13.sp
            )
            Spacer(modifier = Modifier.height(20.dp))

            SectionLabel(text = "AKUN")
            MomeCard(padding = 6.dp) {
                SettingsRow(icon = Icons.Filled.Person, title = "Edit Profil")
                RowDivider()
                SettingsRow(icon = Icons.Filled.Lock, title = "Ubah Kata Sandi")
            }
            Spacer(modifier = Modifier.height(18.dp))

            SectionLabel(text = "NOTIFIKASI")
            MomeCard(padding = 6.dp) {
                SettingsRow(
                    icon = Icons.Filled.CalendarMonth,
                    title = "Pengingat Harian",
                    subtitle = "Catat pengeluaran setiap sore",
                    trailing = { AppSwitch(checked = dailyReminder, onCheckedChange = { dailyReminder = it }) }
                )
                RowDivider()
                SettingsRow(
                    icon = Icons.Filled.Warning,
                    title = "Alert Anggaran",
                    subtitle = "Notifikasi saat limit > 80%",
                    trailing = { AppSwitch(checked = budgetAlert, onCheckedChange = { budgetAlert = it }) }
                )
            }
            Spacer(modifier = Modifier.height(18.dp))

            SectionLabel(text = "KEAMANAN")
            MomeCard(padding = 6.dp) {
                SettingsRow(
                    icon = Icons.Filled.Fingerprint,
                    title = "Kunci Biometrik",
                    trailing = { AppSwitch(checked = biometric, onCheckedChange = { biometric = it }) }
                )
                RowDivider()
                SettingsRow(icon = Icons.Filled.Pin, title = "Atur PIN")
            }
            Spacer(modifier = Modifier.height(18.dp))

            SectionLabel(text = "PREFERENSI")
            MomeCard(padding = 6.dp) {
                SettingsRow(
                    icon = Icons.Filled.Language,
                    title = "Bahasa",
                    trailing = { ValueChevron(value = "Indonesia") }
                )
                RowDivider()
                SettingsRow(
                    icon = Icons.Filled.Payments,
                    title = "Mata Uang",
                    onClick = onCurrency,
                    trailing = { ValueChevron(value = "IDR (Rp)") }
                )
                RowDivider()
                SettingsRow(
                    icon = Icons.Filled.DarkMode,
                    title = "Mode Gelap",
                    trailing = { AppSwitch(checked = darkMode, onCheckedChange = { darkMode = it }) }
                )
            }
            Spacer(modifier = Modifier.height(18.dp))

            SectionLabel(text = "BANTUAN & TENTANG")
            MomeCard(padding = 6.dp) {
                SettingsRow(icon = Icons.Filled.HelpOutline, title = "FAQ")
                RowDivider()
                SettingsRow(icon = Icons.Filled.Shield, title = "Kebijakan Privasi")
            }
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(WarnBg)
                    .clickable { onLogout() }
                    .padding(vertical = 14.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Logout,
                    contentDescription = null,
                    tint = WarnTint,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Keluar Sesi",
                    color = WarnTint,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "v2.4.1 Build 2024",
                color = TextMuted,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
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
private fun SectionLabel(text: String) {
    Text(
        text = text,
        color = TextSecondary,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.5.sp,
        modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
    )
}

@Composable
private fun SettingsRow(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit = {},
    trailing: @Composable () -> Unit = { ChevronIcon() }
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = TextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            if (subtitle != null) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = subtitle, color = TextSecondary, fontSize = 12.sp)
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        trailing()
    }
}

@Composable
private fun RowDivider() {
    Divider(color = DividerColor, modifier = Modifier.padding(horizontal = 10.dp))
}

@Composable
private fun ChevronIcon() {
    Icon(
        imageVector = Icons.Filled.KeyboardArrowRight,
        contentDescription = null,
        tint = TextMuted,
        modifier = Modifier.size(22.dp)
    )
}

@Composable
private fun ValueChevron(value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = value, color = OrangeIconTint, fontSize = 13.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.width(4.dp))
        ChevronIcon()
    }
}

@Composable
private fun AppSwitch(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            checkedTrackColor = BrandNavy,
            checkedBorderColor = BrandNavy,
            uncheckedThumbColor = Color.White,
            uncheckedTrackColor = BrandRingTrack,
            uncheckedBorderColor = BrandRingTrack
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SettingsScreenPreview() {
    MomeAppTheme {
        SettingsScreen()
    }
}
