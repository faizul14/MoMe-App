package com.faezolmp.momeapp.core.domain.usecase

import com.faezolmp.momeapp.core.domain.model.Category
import com.faezolmp.momeapp.core.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

class CategoryInteractor(private val repository: CategoryRepository) : CategoryUseCase {
    override fun all(): Flow<List<Category>> = repository.getAll()
    override suspend fun add(category: Category): Long = repository.add(category)
    override suspend fun delete(id: Long) = repository.delete(id)
}
