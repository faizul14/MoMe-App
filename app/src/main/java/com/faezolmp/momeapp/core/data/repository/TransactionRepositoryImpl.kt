package com.faezolmp.momeapp.core.data.repository

import com.faezolmp.momeapp.core.data.local.dao.TransactionDao
import com.faezolmp.momeapp.core.domain.model.Transaction
import com.faezolmp.momeapp.core.domain.repository.TransactionRepository
import com.faezolmp.momeapp.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl(private val dao: TransactionDao) : TransactionRepository {

    override fun getAll(): Flow<List<Transaction>> =
        dao.getAll().map { list -> list.map(DataMapper::transactionToDomain) }

    override fun getRecent(limit: Int): Flow<List<Transaction>> =
        dao.getRecent(limit).map { list -> list.map(DataMapper::transactionToDomain) }

    override fun getBetween(start: Long, end: Long): Flow<List<Transaction>> =
        dao.getBetween(start, end).map { list -> list.map(DataMapper::transactionToDomain) }

    override fun getById(id: Long): Flow<Transaction?> =
        dao.getById(id).map { entity -> entity?.let(DataMapper::transactionToDomain) }

    override suspend fun add(transaction: Transaction): Long =
        dao.insert(DataMapper.transactionToEntity(transaction))

    override suspend fun delete(id: Long) = dao.deleteById(id)
}
