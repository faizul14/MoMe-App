package com.faezolmp.momeapp.core.domain.repository

import com.faezolmp.momeapp.core.domain.model.BudgetSetting
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {
    fun getActive(): Flow<BudgetSetting?>
    suspend fun save(budget: BudgetSetting)
}
