package com.faezolmp.momeapp.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(
    val name: String = "",
    val email: String = ""
) : Parcelable
