package com.faezolmp.momeapp.core.data.repository

import com.faezolmp.momeapp.core.data.local.dao.BudgetDao
import com.faezolmp.momeapp.core.domain.model.BudgetSetting
import com.faezolmp.momeapp.core.domain.repository.BudgetRepository
import com.faezolmp.momeapp.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BudgetRepositoryImpl(private val dao: BudgetDao) : BudgetRepository {

    override fun getActive(): Flow<BudgetSetting?> =
        dao.getActive().map { entity -> entity?.let(DataMapper::budgetToDomain) }

    override suspend fun save(budget: BudgetSetting) =
        dao.upsert(DataMapper.budgetToEntity(budget))
}
