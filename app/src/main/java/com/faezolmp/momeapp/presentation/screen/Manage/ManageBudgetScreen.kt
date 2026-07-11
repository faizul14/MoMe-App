package com.faezolmp.momeapp.presentation.screen.Manage

import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.faezolmp.momeapp.presentation.ui.components.BottomTab
import com.faezolmp.momeapp.presentation.ui.components.MomeBottomBar
import com.faezolmp.momeapp.presentation.ui.components.MomeCard
import com.faezolmp.momeapp.presentation.ui.components.PrimaryButton
import com.faezolmp.momeapp.presentation.ui.theme.BrandBackground
import com.faezolmp.momeapp.presentation.ui.theme.BrandNavy
import com.faezolmp.momeapp.presentation.ui.theme.BrandRingTrack
import com.faezolmp.momeapp.presentation.ui.theme.FieldBg
import com.faezolmp.momeapp.presentation.ui.theme.FoodIconBg
import com.faezolmp.momeapp.presentation.ui.theme.FoodIconTint
import com.faezolmp.momeapp.presentation.ui.theme.NavyCardLabel
import com.faezolmp.momeapp.presentation.ui.theme.PillBlueBg
import com.faezolmp.momeapp.presentation.ui.theme.TextPrimary
import com.faezolmp.momeapp.presentation.ui.theme.TextSecondary
import com.faezolmp.momeapp.presentation.ui.theme.WarnBg
import com.faezolmp.momeapp.presentation.ui.theme.WarnTint
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageBudgetScreen(
    modifier: Modifier = Modifier,
    onDashboard: () -> Unit = {},
    onHistory: () -> Unit = {},
    onScan: () -> Unit = {},
    onAdd: () -> Unit = {},
    onManage: () -> Unit = {}
) {
    val viewModel: BudgetViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    var amountText by remember { mutableStateOf("") }
    var overLimit by remember { mutableStateOf(true) }
    var eveningReminder by remember { mutableStateOf(false) }
    var threshold by remember { mutableFloatStateOf(0.8f) }
    var initialized by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.loaded) {
        if (state.loaded && !initialized) {
            amountText = state.amount.toString()
            overLimit = state.overLimitAlert
            eveningReminder = state.eveningReminder
            threshold = state.warningThreshold
            initialized = true
        }
    }

    LaunchedEffect(Unit) {
        viewModel.saved.collect {
            snackbarHostState.showSnackbar("Batas berhasil diperbarui")
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = BrandBackground,
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
            Spacer(modifier = Modifier.height(16.dp))
            Header()
            Spacer(modifier = Modifier.height(20.dp))
            HeroCard()
            Spacer(modifier = Modifier.height(16.dp))
            LimitCard(amountText = amountText, onAmountChange = { amountText = it.filter { c -> c.isDigit() } })
            Spacer(modifier = Modifier.height(22.dp))
            Text(
                text = "PENGINGAT & NOTIFIKASI",
                color = TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            ReminderCard(
                icon = Icons.Filled.Warning,
                iconBackground = WarnBg,
                iconTint = WarnTint,
                title = "Notifikasi Jika Melebihi Batas",
                subtitle = "Peringatan instan saat transaksi",
                checked = overLimit,
                onCheckedChange = { overLimit = it }
            )
            Spacer(modifier = Modifier.height(12.dp))
            ReminderCard(
                icon = Icons.Filled.NotificationsActive,
                iconBackground = FoodIconBg,
                iconTint = FoodIconTint,
                title = "Notifikasi Sisa Budget Sore Hari",
                subtitle = "Update rutin setiap jam 17:00",
                checked = eveningReminder,
                onCheckedChange = { eveningReminder = it }
            )
            Spacer(modifier = Modifier.height(22.dp))
            ThresholdCard(threshold = threshold, onThresholdChange = { threshold = it })
            Spacer(modifier = Modifier.height(24.dp))
            PrimaryButton(
                text = "Update Batas",
                onClick = {
                    viewModel.save(
                        amount = amountText.toLongOrNull() ?: 0L,
                        overLimitAlert = overLimit,
                        eveningReminder = eveningReminder,
                        warningThreshold = threshold
                    )
                },
                icon = Icons.Filled.SwapHoriz,
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
            text = "Atur Budget",
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
private fun HeroCard() {
    MomeCard(background = BrandNavy, padding = 20.dp) {
        Text(
            text = "TARGET DISIPLIN",
            color = NavyCardLabel,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Kontrol Keuangan Anda",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun LimitCard(amountText: String, onAmountChange: (String) -> Unit) {
    MomeCard(padding = 18.dp) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.AccountBalanceWallet,
                contentDescription = null,
                tint = BrandNavy,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "BATAS PENGELUARAN HARIAN",
                color = TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.5.sp
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(FieldBg)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Rp", color = TextSecondary, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(8.dp))
            BasicTextField(
                value = amountText,
                onValueChange = onAmountChange,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = TextStyle(
                    color = TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                cursorBrush = SolidColor(BrandNavy),
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Rata-rata pengeluaran harian Anda bulan lalu adalah Rp 420.000",
            color = TextSecondary,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun ReminderCard(
    icon: ImageVector,
    iconBackground: Color,
    iconTint: Color,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    MomeCard(padding = 14.dp) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(iconBackground),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = TextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = subtitle, color = TextSecondary, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
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
    }
}

@Composable
private fun ThresholdCard(threshold: Float, onThresholdChange: (Float) -> Unit) {
    MomeCard(padding = 18.dp) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Tune,
                contentDescription = null,
                tint = BrandNavy,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "AMBANG BATAS PERINGATAN",
                color = TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.5.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "${(threshold * 100).toInt()}%",
                color = BrandNavy,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Slider(
            value = threshold,
            onValueChange = onThresholdChange,
            valueRange = 0.5f..1f,
            colors = SliderDefaults.colors(
                thumbColor = BrandNavy,
                activeTrackColor = BrandNavy,
                inactiveTrackColor = BrandRingTrack
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "50%", color = TextSecondary, fontSize = 12.sp)
            Text(text = "100%", color = TextSecondary, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Beritahu saya jika sudah terpakai",
            color = TextSecondary,
            fontSize = 12.sp
        )
    }
}
