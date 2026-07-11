package com.faezolmp.momeapp.presentation.screen.Confirm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faezolmp.momeapp.core.domain.model.Category
import com.faezolmp.momeapp.core.domain.model.Transaction
import com.faezolmp.momeapp.core.domain.model.TransactionSource
import com.faezolmp.momeapp.core.domain.usecase.CategoryUseCase
import com.faezolmp.momeapp.core.domain.usecase.TransactionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ConfirmUiState(
    val amountText: String = "",
    val note: String = "",
    val attachmentPath: String? = null,
    val source: TransactionSource = TransactionSource.SCAN,
    val categories: List<Category> = emptyList(),
    val selectedCategoryId: Long? = null,
    val saved: Boolean = false
)

class ConfirmViewModel(
    private val categoryUseCase: CategoryUseCase,
    private val transactionUseCase: TransactionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConfirmUiState())
    val uiState: StateFlow<ConfirmUiState> = _uiState.asStateFlow()

    private var prefilled = false

    init {
        viewModelScope.launch {
            categoryUseCase.all().collect { categories ->
                _uiState.update { current ->
                    current.copy(
                        categories = categories,
                        selectedCategoryId = current.selectedCategoryId ?: categories.firstOrNull()?.id
                    )
                }
            }
        }
    }

    fun prefill(amount: Long, attachmentPath: String?, source: TransactionSource) {
        if (prefilled) return
        prefilled = true
        _uiState.update {
            it.copy(
                amountText = if (amount > 0L) amount.toString() else it.amountText,
                attachmentPath = attachmentPath,
                source = source
            )
        }
    }

    fun onAmountChange(value: String) {
        _uiState.update { it.copy(amountText = value.filter { c -> c.isDigit() }) }
    }

    fun onNoteChange(value: String) {
        _uiState.update { it.copy(note = value) }
    }

    fun onCategorySelected(id: Long) {
        _uiState.update { it.copy(selectedCategoryId = id) }
    }

    fun save() {
        val current = _uiState.value
        val amount = current.amountText.toLongOrNull() ?: 0L
        val categoryId = current.selectedCategoryId ?: return
        if (amount <= 0L) return
        viewModelScope.launch {
            transactionUseCase.add(
                Transaction(
                    amount = amount,
                    categoryId = categoryId,
                    dateTime = System.currentTimeMillis(),
                    note = current.note.trim(),
                    source = current.source,
                    attachmentPath = current.attachmentPath,
                    isIncome = false
                )
            )
            _uiState.update { it.copy(saved = true) }
        }
    }
}
