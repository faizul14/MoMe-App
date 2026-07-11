package com.faezolmp.momeapp.presentation.screen.Dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faezolmp.momeapp.core.domain.model.BudgetSetting
import com.faezolmp.momeapp.core.domain.model.Category
import com.faezolmp.momeapp.core.domain.model.Transaction
import com.faezolmp.momeapp.core.domain.usecase.BudgetUseCase
import com.faezolmp.momeapp.core.domain.usecase.CategoryUseCase
import com.faezolmp.momeapp.core.domain.usecase.TransactionUseCase
import com.faezolmp.momeapp.core.utils.DateUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DashboardViewModel(
    budgetUseCase: BudgetUseCase,
    categoryUseCase: CategoryUseCase,
    transactionUseCase: TransactionUseCase
) : ViewModel() {

    private val timeFormat = SimpleDateFormat("dd MMM, HH:mm", Locale("id", "ID"))

    val uiState: StateFlow<DashboardUiState> = combine(
        budgetUseCase.active(),
        categoryUseCase.all(),
        transactionUseCase.all()
    ) { budget, categories, transactions ->
        buildState(budget, categories, transactions)
    }.flowOn(Dispatchers.Default).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardUiState()
    )

    private fun buildState(
        budget: BudgetSetting?,
        categories: List<Category>,
        transactions: List<Transaction>
    ): DashboardUiState {
        if (budget == null) return DashboardUiState()

        val (start, end) = DateUtils.periodRange(budget.period, budget.weekStartDay, System.currentTimeMillis())
        val periodExpenses = transactions.filter { !it.isIncome && it.dateTime in start..end }
        val spent = periodExpenses.sumOf { it.amount }
        val remaining = (budget.amount - spent).coerceAtLeast(0L)

        val spentByCategory = periodExpenses.groupBy { it.categoryId }
            .mapValues { entry -> entry.value.sumOf { it.amount } }

        val categoryUi = categories.map { category ->
            DashboardCategoryUi(
                name = category.name,
                iconKey = category.iconKey,
                colorHex = category.colorHex,
                spent = spentByCategory[category.id] ?: 0L
            )
        }.sortedByDescending { it.spent }

        val categoryById = categories.associateBy { it.id }
        val recent = transactions.sortedByDescending { it.dateTime }.take(5).map { transaction ->
            val category = categoryById[transaction.categoryId]
            DashboardActivityUi(
                id = transaction.id,
                title = transaction.note.ifBlank { category?.name ?: "Transaksi" },
                time = timeFormat.format(Date(transaction.dateTime)),
                amount = transaction.amount,
                isIncome = transaction.isIncome,
                iconKey = if (transaction.isIncome) "income" else (category?.iconKey ?: "other"),
                colorHex = if (transaction.isIncome) "#2FB673" else (category?.colorHex ?: "#8A93AB")
            )
        }

        return DashboardUiState(
            budgetRemaining = remaining,
            budgetTotal = budget.amount,
            currencySymbol = budget.currencySymbol,
            motivation = motivationFor(remaining, budget.amount),
            categories = categoryUi,
            recentActivities = recent
        )
    }

    private fun motivationFor(remaining: Long, total: Long): String {
        if (total <= 0L) return "Atur budget kamu untuk mulai melacak."
        val ratio = remaining.toFloat() / total.toFloat()
        return when {
            ratio <= 0f -> "Budget hari ini sudah habis. Tahan dulu ya!"
            ratio < 0.3f -> "Hati-hati, sisa budget menipis."
            else -> "Kerja keras, tapi jangan lupa istirahat! Budget aman hari ini."
        }
    }
}
