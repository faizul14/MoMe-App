package com.faezolmp.momeapp.presentation.screen.Manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faezolmp.momeapp.core.domain.model.Category
import com.faezolmp.momeapp.core.domain.usecase.CategoryUseCase
import com.faezolmp.momeapp.core.domain.usecase.TransactionUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ManageCategoryItemUi(
    val id: Long,
    val name: String,
    val iconKey: String,
    val colorHex: String,
    val transactionInfo: String
)

data class ManageCategoryUiState(
    val categories: List<ManageCategoryItemUi> = emptyList()
)

class ManageCategoryViewModel(
    private val categoryUseCase: CategoryUseCase,
    transactionUseCase: TransactionUseCase
) : ViewModel() {

    val uiState: StateFlow<ManageCategoryUiState> = combine(
        categoryUseCase.all(),
        transactionUseCase.all()
    ) { categories, transactions ->
        ManageCategoryUiState(
            categories = categories.map { category ->
                val count = transactions.count { it.categoryId == category.id }
                ManageCategoryItemUi(
                    id = category.id,
                    name = category.name,
                    iconKey = category.iconKey,
                    colorHex = category.colorHex,
                    transactionInfo = "$count transaksi"
                )
            }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ManageCategoryUiState()
    )

    fun addCategory(name: String, iconKey: String, colorHex: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            categoryUseCase.add(
                Category(name = name.trim(), iconKey = iconKey, colorHex = colorHex, isDefault = false)
            )
        }
    }
}
