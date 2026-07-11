package com.faezolmp.momeapp.core.domain.repository

import com.faezolmp.momeapp.core.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getAll(): Flow<List<Transaction>>
    fun getRecent(limit: Int): Flow<List<Transaction>>
    fun getBetween(start: Long, end: Long): Flow<List<Transaction>>
    fun getById(id: Long): Flow<Transaction?>
    suspend fun add(transaction: Transaction): Long
    suspend fun delete(id: Long)
}
