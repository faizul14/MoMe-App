package com.faezolmp.momeapp.core.domain.usecase

import com.faezolmp.momeapp.core.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionUseCase {
    fun recent(limit: Int): Flow<List<Transaction>>
    fun all(): Flow<List<Transaction>>
    fun between(start: Long, end: Long): Flow<List<Transaction>>
    fun byId(id: Long): Flow<Transaction?>
    suspend fun add(transaction: Transaction): Long
    suspend fun delete(id: Long)
}
