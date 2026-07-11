package com.faezolmp.momeapp.core.domain.usecase

import com.faezolmp.momeapp.core.domain.model.Transaction
import com.faezolmp.momeapp.core.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

class TransactionInteractor(private val repository: TransactionRepository) : TransactionUseCase {
    override fun recent(limit: Int): Flow<List<Transaction>> = repository.getRecent(limit)
    override fun all(): Flow<List<Transaction>> = repository.getAll()
    override fun between(start: Long, end: Long): Flow<List<Transaction>> = repository.getBetween(start, end)
    override fun byId(id: Long): Flow<Transaction?> = repository.getById(id)
    override suspend fun add(transaction: Transaction): Long = repository.add(transaction)
    override suspend fun delete(id: Long) = repository.delete(id)
}
