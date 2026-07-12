package com.faezolmp.momeapp.core.utils

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

object AppLocale {

    const val INDONESIAN = "id"
    const val ENGLISH = "en"

    fun current(): String {
        val locales = AppCompatDelegate.getApplicationLocales()
        return if (locales.isEmpty) INDONESIAN else (locales[0]?.language ?: INDONESIAN)
    }

    fun set(tag: String) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(tag))
    }
}
