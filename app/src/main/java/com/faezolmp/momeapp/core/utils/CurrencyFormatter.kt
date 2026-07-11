package com.faezolmp.momeapp.core.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.math.abs

private val groupingSymbols = DecimalFormatSymbols(Locale.US).apply {
    groupingSeparator = '.'
    decimalSeparator = ','
}

fun formatRupiah(amount: Long, symbol: String = "Rp"): String {
    val formatter = DecimalFormat("#,###", groupingSymbols)
    return "$symbol ${formatter.format(amount)}"
}

fun formatCompact(amount: Long, symbol: String = "Rp"): String {
    val absValue = abs(amount)
    val body = when {
        absValue >= 1_000_000 -> {
            val millions = amount / 1_000_000.0
            val formatter = DecimalFormat("#,##0.#", groupingSymbols)
            "${formatter.format(millions)}jt"
        }
        absValue >= 1_000 -> "${amount / 1_000}k"
        else -> amount.toString()
    }
    return "$symbol $body"
}

fun formatSignedRupiah(amount: Long, isIncome: Boolean, symbol: String = "Rp"): String {
    val sign = if (isIncome) "+" else "-"
    return "$sign${formatRupiah(abs(amount), symbol)}"
}
