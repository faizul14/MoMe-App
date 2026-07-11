package com.faezolmp.momeapp.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget")
data class BudgetEntity(
    @PrimaryKey val id: Long = 1L,
    val period: String,
    val amount: Long,
    val weekStartDay: Int,
    val currencyCode: String,
    val currencySymbol: String,
    val decimalDigits: Int,
    val overLimitAlert: Boolean,
    val eveningReminder: Boolean,
    val warningThreshold: Float,
    val isActive: Boolean
)
