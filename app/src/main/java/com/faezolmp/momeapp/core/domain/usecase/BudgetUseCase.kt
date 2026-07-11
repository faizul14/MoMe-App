package com.faezolmp.momeapp.core.domain.usecase

import com.faezolmp.momeapp.core.domain.model.BudgetSetting
import kotlinx.coroutines.flow.Flow

interface BudgetUseCase {
    fun active(): Flow<BudgetSetting?>
    suspend fun save(budget: BudgetSetting)
}
