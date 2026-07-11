package com.faezolmp.momeapp.presentation.screen.Detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faezolmp.momeapp.core.domain.model.Category
import com.faezolmp.momeapp.core.domain.model.Transaction
import com.faezolmp.momeapp.core.domain.model.TransactionSource
import com.faezolmp.momeapp.core.domain.usecase.CategoryUseCase
import com.faezolmp.momeapp.core.domain.usecase.TransactionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModel(
    private val transactionUseCase: TransactionUseCase,
    private val categoryUseCase: CategoryUseCase
) : ViewModel() {

    private val locale = Locale("id", "ID")
    private val dateFormat = SimpleDateFormat("dd MMM yyyy", locale)
    private val timeFormat = SimpleDateFormat("HH:mm", locale)

    private val transactionId = MutableStateFlow<Long?>(null)

    val uiState: StateFlow<TransactionDetailUiState> = transactionId.flatMapLatest { id ->
        if (id == null) {
            flowOf(TransactionDetailUiState())
        } else {
            combine(transactionUseCase.byId(id), categoryUseCase.all()) { transaction, categories ->
                if (transaction == null) {
                    TransactionDetailUiState(loaded = true)
                } else {
                    buildState(transaction, categories)
                }
            }
        }
    }.flowOn(Dispatchers.Default).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TransactionDetailUiState()
    )

    fun load(id: Long) {
        transactionId.value = id
    }

    fun delete() {
        val id = transactionId.value ?: return
        viewModelScope.launch { transactionUseCase.delete(id) }
    }

    private fun buildState(transaction: Transaction, categories: List<Category>): TransactionDetailUiState {
        val category = categories.find { it.id == transaction.categoryId }
        return TransactionDetailUiState(
            title = transaction.note.ifBlank { category?.name ?: "Transaksi" },
            amount = transaction.amount,
            isIncome = transaction.isIncome,
            categoryName = category?.name ?: "Lainnya",
            iconKey = if (transaction.isIncome) "income" else (category?.iconKey ?: "other"),
            colorHex = if (transaction.isIncome) "#2FB673" else (category?.colorHex ?: "#8A93AB"),
            dateLabel = dateFormat.format(Date(transaction.dateTime)),
            timeLabel = timeFormat.format(Date(transaction.dateTime)),
            source = sourceLabel(transaction.source),
            note = transaction.note.ifBlank { "-" },
            hasAttachment = transaction.attachmentPath != null,
            attachmentPath = transaction.attachmentPath,
            loaded = true
        )
    }

    private fun sourceLabel(source: TransactionSource): String = when (source) {
        TransactionSource.MANUAL -> "Input Manual"
        TransactionSource.SCAN -> "Scan Struk"
        TransactionSource.SHARE -> "Dibagikan"
    }
}
