package com.faezolmp.momeapp.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val amount: Long,
    val categoryId: Long,
    val dateTime: Long,
    val note: String,
    val source: String,
    val attachmentPath: String?,
    val isIncome: Boolean
)
