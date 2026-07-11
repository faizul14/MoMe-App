package com.faezolmp.momeapp.core.data.repository

import com.faezolmp.momeapp.core.data.local.dao.CategoryDao
import com.faezolmp.momeapp.core.domain.model.Category
import com.faezolmp.momeapp.core.domain.repository.CategoryRepository
import com.faezolmp.momeapp.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl(private val dao: CategoryDao) : CategoryRepository {

    override fun getAll(): Flow<List<Category>> =
        dao.getAll().map { list -> list.map(DataMapper::categoryToDomain) }

    override suspend fun add(category: Category): Long =
        dao.insert(DataMapper.categoryToEntity(category))

    override suspend fun delete(id: Long) = dao.deleteById(id)
}
