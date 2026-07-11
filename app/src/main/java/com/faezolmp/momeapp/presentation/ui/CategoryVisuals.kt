package com.faezolmp.momeapp.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.faezolmp.momeapp.presentation.ui.theme.BrandNavy

fun categoryIconOf(iconKey: String): ImageVector = when (iconKey) {
    "food" -> Icons.Filled.Restaurant
    "transport" -> Icons.Filled.DirectionsCar
    "fun" -> Icons.Filled.SportsEsports
    "shopping" -> Icons.Filled.ShoppingBag
    "health" -> Icons.Filled.MedicalServices
    "income" -> Icons.Filled.AccountBalanceWallet
    else -> Icons.Filled.Category
}

fun categoryColorOf(colorHex: String): Color =
    runCatching { Color(android.graphics.Color.parseColor(colorHex)) }.getOrDefault(BrandNavy)

fun categoryBackgroundOf(colorHex: String): Color = categoryColorOf(colorHex).copy(alpha = 0.16f)

val CATEGORY_ICON_KEYS = listOf("food", "transport", "fun", "shopping", "health", "income")
