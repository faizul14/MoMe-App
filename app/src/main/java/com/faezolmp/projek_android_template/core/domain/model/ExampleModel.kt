package com.faezolmp.projek_android_template.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExampleModel(
    val dataExample: String = "example"
) : Parcelable