package com.faezolmp.momeapp.presentation.screen.AddManual

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faezolmp.momeapp.core.domain.model.Category
import com.faezolmp.momeapp.core.domain.model.Transaction
import com.faezolmp.momeapp.core.domain.model.TransactionSource
import com.faezolmp.momeapp.core.domain.usecase.CategoryUseCase
import com.faezolmp.momeapp.core.domain.usecase.TransactionUseCase
import com.faezolmp.momeapp.core.notification.BudgetNotifier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AddTransactionUiState(
    val amountText: String = "",
    val note: String = "",
    val isIncome: Boolean = false,
    val categories: List<Category> = emptyList(),
    val selectedCategoryId: Long? = null,
    val saved: Boolean = false
)

class AddTransactionViewModel(
    private val categoryUseCase: CategoryUseCase,
    private val transactionUseCase: TransactionUseCase,
    private val budgetNotifier: BudgetNotifier
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddTransactionUiState())
    val uiState: StateFlow<AddTransactionUiState> = _uiState.asStateFlow()

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

    fun onAmountChange(value: String) {
        val digits = value.filter { it.isDigit() }
        _uiState.update { it.copy(amountText = digits) }
    }

    fun onNoteChange(value: String) {
        _uiState.update { it.copy(note = value) }
    }

    fun onIncomeChange(isIncome: Boolean) {
        _uiState.update { it.copy(isIncome = isIncome) }
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
                    source = TransactionSource.MANUAL,
                    attachmentPath = null,
                    isIncome = current.isIncome
                )
            )
            budgetNotifier.checkAfterInsert()
            _uiState.update { it.copy(saved = true) }
        }
    }
}
