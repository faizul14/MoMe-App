package com.faezolmp.momeapp.presentation.screen.Manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faezolmp.momeapp.core.domain.model.BudgetSetting
import com.faezolmp.momeapp.core.domain.usecase.BudgetUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class BudgetUiState(
    val amount: Long = 0L,
    val currencySymbol: String = "Rp",
    val overLimitAlert: Boolean = true,
    val eveningReminder: Boolean = false,
    val warningThreshold: Float = 0.8f,
    val loaded: Boolean = false
)

class BudgetViewModel(
    private val budgetUseCase: BudgetUseCase
) : ViewModel() {

    val uiState: StateFlow<BudgetUiState> = budgetUseCase.active().map { budget ->
        if (budget == null) {
            BudgetUiState(loaded = true)
        } else {
            BudgetUiState(
                amount = budget.amount,
                currencySymbol = budget.currencySymbol,
                overLimitAlert = budget.overLimitAlert,
                eveningReminder = budget.eveningReminder,
                warningThreshold = budget.warningThreshold,
                loaded = true
            )
        }
    }.flowOn(Dispatchers.Default).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BudgetUiState()
    )

    private val _saved = MutableSharedFlow<Unit>()
    val saved: SharedFlow<Unit> = _saved.asSharedFlow()

    fun save(amount: Long, overLimitAlert: Boolean, eveningReminder: Boolean, warningThreshold: Float) {
        viewModelScope.launch {
            val current = budgetUseCase.active().first() ?: BudgetSetting()
            budgetUseCase.save(
                current.copy(
                    id = 1L,
                    amount = amount,
                    overLimitAlert = overLimitAlert,
                    eveningReminder = eveningReminder,
                    warningThreshold = warningThreshold,
                    isActive = true
                )
            )
            _saved.emit(Unit)
        }
    }
}
