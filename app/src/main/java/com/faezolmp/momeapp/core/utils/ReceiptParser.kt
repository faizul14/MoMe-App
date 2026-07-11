package com.faezolmp.momeapp.core.utils

object ReceiptParser {

    private val amountRegex = Regex("\\d{1,3}(?:[.,]\\d{3})+|\\d{4,}")

    fun parseAmount(text: String): Long? {
        return amountRegex.findAll(text)
            .mapNotNull { match -> match.value.replace(".", "").replace(",", "").toLongOrNull() }
            .filter { it in 100..100_000_000 }
            .maxOrNull()
    }
}
