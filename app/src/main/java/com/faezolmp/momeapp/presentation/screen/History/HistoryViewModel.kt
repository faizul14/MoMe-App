package com.faezolmp.momeapp.presentation.screen.History

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faezolmp.momeapp.core.domain.model.Category
import com.faezolmp.momeapp.core.domain.model.Transaction
import com.faezolmp.momeapp.core.domain.usecase.CategoryUseCase
import com.faezolmp.momeapp.core.domain.usecase.TransactionUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class HistoryItemUi(
    val id: Long,
    val title: String,
    val subtitle: String,
    val amount: Long,
    val isIncome: Boolean,
    val iconKey: String,
    val colorHex: String,
    val hasAttachment: Boolean,
    val attachmentPath: String?
)

data class HistoryGroupUi(
    val label: String,
    val date: String,
    val items: List<HistoryItemUi>
)

data class HistoryUiState(
    val groups: List<HistoryGroupUi> = emptyList()
)

class HistoryViewModel(
    transactionUseCase: TransactionUseCase,
    categoryUseCase: CategoryUseCase
) : ViewModel() {

    private val locale = Locale("id", "ID")
    private val dayKeyFormat = SimpleDateFormat("yyyyMMdd", locale)
    private val dateFormat = SimpleDateFormat("dd MMM yyyy", locale)
    private val dayNameFormat = SimpleDateFormat("EEEE", locale)
    private val timeFormat = SimpleDateFormat("HH:mm", locale)

    val uiState: StateFlow<HistoryUiState> = combine(
        transactionUseCase.all(),
        categoryUseCase.all()
    ) { transactions, categories ->
        HistoryUiState(groups = buildGroups(transactions, categories))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HistoryUiState()
    )

    private fun buildGroups(
        transactions: List<Transaction>,
        categories: List<Category>
    ): List<HistoryGroupUi> {
        val now = System.currentTimeMillis()
        val todayKey = dayKeyFormat.format(Date(now))
        val yesterdayKey = dayKeyFormat.format(Date(now - 24L * 60L * 60L * 1000L))

        return transactions
            .sortedByDescending { it.dateTime }
            .groupBy { dayKeyFormat.format(Date(it.dateTime)) }
            .map { (key, items) ->
                val first = Date(items.first().dateTime)
                val label = when (key) {
                    todayKey -> "HARI INI"
                    yesterdayKey -> "KEMARIN"
                    else -> dayNameFormat.format(first).uppercase(locale)
                }
                HistoryGroupUi(
                    label = label,
                    date = dateFormat.format(first),
                    items = items.map { transaction ->
                        val category = categories.find { it.id == transaction.categoryId }
                        HistoryItemUi(
                            id = transaction.id,
                            title = transaction.note.ifBlank { category?.name ?: "Transaksi" },
                            subtitle = "${timeFormat.format(Date(transaction.dateTime))} • ${category?.name ?: "Lainnya"}",
                            amount = transaction.amount,
                            isIncome = transaction.isIncome,
                            iconKey = if (transaction.isIncome) "income" else (category?.iconKey ?: "other"),
                            colorHex = if (transaction.isIncome) "#2FB673" else (category?.colorHex ?: "#8A93AB"),
                            hasAttachment = transaction.attachmentPath != null,
                            attachmentPath = transaction.attachmentPath
                        )
                    }
                )
            }
    }
}
