package com.faezolmp.momeapp.presentation.screen.Settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.faezolmp.momeapp.R
import com.faezolmp.momeapp.core.utils.AppLocale
import com.faezolmp.momeapp.presentation.ui.components.BottomTab
import com.faezolmp.momeapp.presentation.ui.components.MomeBottomBar
import com.faezolmp.momeapp.presentation.ui.components.MomeCard
import com.faezolmp.momeapp.presentation.ui.theme.BrandBackground
import com.faezolmp.momeapp.presentation.ui.theme.BrandNavy
import com.faezolmp.momeapp.presentation.ui.theme.BrandRingTrack
import com.faezolmp.momeapp.presentation.ui.theme.DividerColor
import com.faezolmp.momeapp.presentation.ui.theme.OrangeIconTint
import com.faezolmp.momeapp.presentation.ui.theme.PillBlueBg
import com.faezolmp.momeapp.presentation.ui.theme.TextMuted
import com.faezolmp.momeapp.presentation.ui.theme.TextPrimary
import com.faezolmp.momeapp.presentation.ui.theme.TextSecondary
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onEditProfile: () -> Unit = {},
    onCurrency: () -> Unit = {},
    onDashboard: () -> Unit = {},
    onHistory: () -> Unit = {},
    onScan: () -> Unit = {},
    onAdd: () -> Unit = {}
) {
    val viewModel: SettingsViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    var biometric by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }
    var currentLanguage by remember { mutableStateOf(AppLocale.current()) }

    if (showLanguageDialog) {
        LanguageDialog(
            current = currentLanguage,
            onDismiss = { showLanguageDialog = false },
            onSelect = { tag ->
                showLanguageDialog = false
                currentLanguage = tag
                AppLocale.set(tag)
            }
        )
    }

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
            Spacer(modifier = Modifier.height(16.dp))
            Header(profileName = state.profileName)
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = stringResource(R.string.settings_title),
                color = BrandNavy,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.settings_subtitle),
                color = OrangeIconTint,
                fontSize = 13.sp
            )
            Spacer(modifier = Modifier.height(20.dp))

            SectionLabel(text = stringResource(R.string.settings_section_account))
            MomeCard(padding = 6.dp) {
                SettingsRow(
                    icon = Icons.Filled.Person,
                    title = stringResource(R.string.settings_edit_profile),
                    onClick = onEditProfile
                )
            }
            Spacer(modifier = Modifier.height(18.dp))

            SectionLabel(text = stringResource(R.string.settings_section_notification))
            MomeCard(padding = 6.dp) {
                SettingsRow(
                    icon = Icons.Filled.CalendarMonth,
                    title = stringResource(R.string.settings_daily_reminder),
                    subtitle = stringResource(R.string.settings_daily_reminder_desc),
                    trailing = {
                        AppSwitch(checked = state.dailyReminder, onCheckedChange = viewModel::setDailyReminder)
                    }
                )
                RowDivider()
                SettingsRow(
                    icon = Icons.Filled.Warning,
                    title = stringResource(R.string.settings_budget_alert),
                    subtitle = stringResource(R.string.settings_budget_alert_desc, state.thresholdPercent),
                    trailing = {
                        AppSwitch(checked = state.budgetAlert, onCheckedChange = viewModel::setBudgetAlert)
                    }
                )
            }
            Spacer(modifier = Modifier.height(18.dp))

            SectionLabel(text = stringResource(R.string.settings_section_security))
            MomeCard(padding = 6.dp) {
                SettingsRow(
                    icon = Icons.Filled.Fingerprint,
                    title = stringResource(R.string.settings_biometric),
                    trailing = { AppSwitch(checked = biometric, onCheckedChange = { biometric = it }) }
                )
                RowDivider()
                SettingsRow(icon = Icons.Filled.Pin, title = stringResource(R.string.settings_set_pin))
            }
            Spacer(modifier = Modifier.height(18.dp))

            SectionLabel(text = stringResource(R.string.settings_section_preference))
            MomeCard(padding = 6.dp) {
                SettingsRow(
                    icon = Icons.Filled.Language,
                    title = stringResource(R.string.settings_language),
                    onClick = { showLanguageDialog = true },
                    trailing = { ValueChevron(value = languageLabel(currentLanguage)) }
                )
                RowDivider()
                SettingsRow(
                    icon = Icons.Filled.Payments,
                    title = "Mata Uang",
                    onClick = onCurrency,
                    trailing = { ValueChevron(value = "IDR (Rp)") }
                )
            }
            Spacer(modifier = Modifier.height(18.dp))

            SectionLabel(text = stringResource(R.string.settings_section_about))
            MomeCard(padding = 6.dp) {
                SettingsRow(icon = Icons.Filled.HelpOutline, title = stringResource(R.string.settings_faq))
                RowDivider()
                SettingsRow(icon = Icons.Filled.Shield, title = stringResource(R.string.settings_privacy))
            }
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.settings_version),
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
private fun languageLabel(tag: String): String =
    if (tag == AppLocale.ENGLISH) stringResource(R.string.language_english)
    else stringResource(R.string.language_indonesian)

@Composable
private fun LanguageDialog(
    current: String,
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.action_back), color = TextSecondary)
            }
        },
        title = {
            Text(
                text = stringResource(R.string.language_dialog_title),
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                LanguageOption(
                    label = stringResource(R.string.language_indonesian),
                    selected = current == AppLocale.INDONESIAN,
                    onClick = { onSelect(AppLocale.INDONESIAN) }
                )
                LanguageOption(
                    label = stringResource(R.string.language_english),
                    selected = current == AppLocale.ENGLISH,
                    onClick = { onSelect(AppLocale.ENGLISH) }
                )
            }
        }
    )
}

@Composable
private fun LanguageOption(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = if (selected) BrandNavy else TextPrimary,
            fontSize = 15.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            modifier = Modifier.weight(1f)
        )
        if (selected) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = BrandNavy,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun Header(profileName: String) {
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
            text = profileName,
            color = BrandNavy,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Filled.NotificationsNone,
            contentDescription = stringResource(R.string.action_notification),
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
            Text(text = title, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Medium)
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
