package com.faezolmp.momeapp.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BudgetSetting(
    val id: Long = 0L,
    val period: BudgetPeriod = BudgetPeriod.DAILY,
    val amount: Long = 0L,
    val weekStartDay: Int = 1,
    val currencyCode: String = "IDR",
    val currencySymbol: String = "Rp",
    val decimalDigits: Int = 0,
    val overLimitAlert: Boolean = true,
    val eveningReminder: Boolean = false,
    val warningThreshold: Float = 0.8f,
    val isActive: Boolean = true
) : Parcelable
