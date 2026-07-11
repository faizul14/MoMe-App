package com.faezolmp.momeapp.core.domain.usecase

import com.faezolmp.momeapp.core.domain.model.BudgetSetting
import com.faezolmp.momeapp.core.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow

class BudgetInteractor(private val repository: BudgetRepository) : BudgetUseCase {
    override fun active(): Flow<BudgetSetting?> = repository.getActive()
    override suspend fun save(budget: BudgetSetting) = repository.save(budget)
}
