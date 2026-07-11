package com.faezolmp.momeapp.presentation.screen.Dashboard

enum class CategoryVisual { FOOD, TRANSPORT, FUN }

enum class ActivityVisual { SHOPPING, COFFEE, INCOME }

data class CategorySpendUi(
    val name: String,
    val visual: CategoryVisual,
    val spent: Long,
    val remaining: Long? = null
)

data class ActivityUi(
    val title: String,
    val time: String,
    val amount: Long,
    val isIncome: Boolean,
    val visual: ActivityVisual
)

data class DashboardUiState(
    val userName: String = "",
    val budgetRemaining: Long = 0L,
    val budgetTotal: Long = 0L,
    val currencySymbol: String = "Rp",
    val motivation: String = "",
    val mainCategory: CategorySpendUi? = null,
    val smallCategories: List<CategorySpendUi> = emptyList(),
    val recentActivities: List<ActivityUi> = emptyList()
)

fun sampleDashboardState(): DashboardUiState = DashboardUiState(
    userName = "Fiscal Harmony",
    budgetRemaining = 50_000L,
    budgetTotal = 150_000L,
    currencySymbol = "Rp",
    motivation = "Kerja keras, tapi jangan lupa istirahat! Budget aman hari ini.",
    mainCategory = CategorySpendUi(
        name = "Makan & Minum",
        visual = CategoryVisual.FOOD,
        spent = 65_000L,
        remaining = 35_000L
    ),
    smallCategories = listOf(
        CategorySpendUi(name = "Transport", visual = CategoryVisual.TRANSPORT, spent = 20_000L),
        CategorySpendUi(name = "Hiburan", visual = CategoryVisual.FUN, spent = 15_000L)
    ),
    recentActivities = listOf(
        ActivityUi("Belanja Mingguan", "Hari ini, 10:45 AM", 120_000L, false, ActivityVisual.SHOPPING),
        ActivityUi("Kopi Pagi", "Hari ini, 08:20 AM", 25_000L, false, ActivityVisual.COFFEE),
        ActivityUi("Transfer Masuk", "Kemarin, 04:15 PM", 500_000L, true, ActivityVisual.INCOME)
    )
)
