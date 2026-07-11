package com.faezolmp.momeapp.presentation.screen.Dashboard

data class DashboardCategoryUi(
    val name: String,
    val iconKey: String,
    val colorHex: String,
    val spent: Long
)

data class DashboardActivityUi(
    val id: Long,
    val title: String,
    val time: String,
    val amount: Long,
    val isIncome: Boolean,
    val iconKey: String,
    val colorHex: String
)

data class DashboardUiState(
    val userName: String = "Fiscal Harmony",
    val budgetRemaining: Long = 0L,
    val budgetTotal: Long = 0L,
    val currencySymbol: String = "Rp",
    val motivation: String = "",
    val categories: List<DashboardCategoryUi> = emptyList(),
    val recentActivities: List<DashboardActivityUi> = emptyList()
)
