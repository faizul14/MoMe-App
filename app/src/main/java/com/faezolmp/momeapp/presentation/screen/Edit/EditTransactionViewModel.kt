package com.faezolmp.momeapp.presentation.screen.Edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faezolmp.momeapp.core.domain.model.Category
import com.faezolmp.momeapp.core.domain.model.Transaction
import com.faezolmp.momeapp.core.domain.usecase.CategoryUseCase
import com.faezolmp.momeapp.core.domain.usecase.TransactionUseCase
import com.faezolmp.momeapp.core.notification.BudgetNotifier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EditUiState(
    val amountText: String = "",
    val note: String = "",
    val categories: List<Category> = emptyList(),
    val selectedCategoryId: Long? = null,
    val loaded: Boolean = false,
    val saved: Boolean = false
)

class EditTransactionViewModel(
    private val transactionUseCase: TransactionUseCase,
    private val categoryUseCase: CategoryUseCase,
    private val budgetNotifier: BudgetNotifier
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditUiState())
    val uiState: StateFlow<EditUiState> = _uiState.asStateFlow()

    private var original: Transaction? = null
    private var loadRequested = false

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

    fun load(id: Long) {
        if (loadRequested) return
        loadRequested = true
        viewModelScope.launch {
            val transaction = transactionUseCase.byId(id).first() ?: return@launch
            original = transaction
            _uiState.update {
                it.copy(
                    amountText = transaction.amount.toString(),
                    note = transaction.note,
                    selectedCategoryId = transaction.categoryId,
                    loaded = true
                )
            }
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
        val base = original ?: return
        if (amount <= 0L) return
        viewModelScope.launch {
            transactionUseCase.add(
                base.copy(
                    amount = amount,
                    categoryId = categoryId,
                    note = current.note.trim()
                )
            )
            budgetNotifier.checkAfterInsert()
            _uiState.update { it.copy(saved = true) }
        }
    }
}
