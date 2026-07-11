package com.faezolmp.momeapp.core.domain.repository

import com.faezolmp.momeapp.core.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAll(): Flow<List<Category>>
    suspend fun add(category: Category): Long
    suspend fun delete(id: Long)
}
