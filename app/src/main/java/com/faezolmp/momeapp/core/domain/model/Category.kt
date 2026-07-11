package com.faezolmp.momeapp.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Long = 0L,
    val name: String = "",
    val iconKey: String = "",
    val colorHex: String = "#000000",
    val isDefault: Boolean = false
) : Parcelable
