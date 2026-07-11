package com.faezolmp.momeapp.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    val id: Long = 0L,
    val amount: Long = 0L,
    val categoryId: Long = 0L,
    val dateTime: Long = 0L,
    val note: String = "",
    val source: TransactionSource = TransactionSource.MANUAL,
    val attachmentPath: String? = null
) : Parcelable
