package com.faezolmp.momeapp.core.domain.usecase

import com.faezolmp.momeapp.core.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryUseCase {
    fun all(): Flow<List<Category>>
    suspend fun add(category: Category): Long
    suspend fun delete(id: Long)
}
