package com.faezolmp.momeapp.presentation.screen.Settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faezolmp.momeapp.core.domain.model.BudgetSetting
import com.faezolmp.momeapp.core.domain.usecase.BudgetUseCase
import com.faezolmp.momeapp.core.domain.usecase.ProfileUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SettingsUiState(
    val profileName: String = "Fiscal Harmony",
    val dailyReminder: Boolean = false,
    val budgetAlert: Boolean = true,
    val thresholdPercent: Int = 80
)

class SettingsViewModel(
    private val profileUseCase: ProfileUseCase,
    private val budgetUseCase: BudgetUseCase
) : ViewModel() {

    val uiState: StateFlow<SettingsUiState> = combine(
        profileUseCase.get(),
        budgetUseCase.active()
    ) { profile, budget ->
        SettingsUiState(
            profileName = profile?.name?.takeIf { it.isNotBlank() } ?: "Fiscal Harmony",
            dailyReminder = budget?.eveningReminder ?: false,
            budgetAlert = budget?.overLimitAlert ?: true,
            thresholdPercent = ((budget?.warningThreshold ?: 0.8f) * 100).toInt()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SettingsUiState()
    )

    fun setDailyReminder(enabled: Boolean) = updateBudget { it.copy(eveningReminder = enabled) }

    fun setBudgetAlert(enabled: Boolean) = updateBudget { it.copy(overLimitAlert = enabled) }

    private fun updateBudget(transform: (BudgetSetting) -> BudgetSetting) {
        viewModelScope.launch {
            val current = budgetUseCase.active().first() ?: BudgetSetting()
            budgetUseCase.save(transform(current.copy(id = 1L, isActive = true)))
        }
    }
}
