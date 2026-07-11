package com.faezolmp.momeapp.core.utils

object ReceiptParser {

    private val totalKeywords = listOf(
        "grand total",
        "total belanja",
        "total bayar",
        "total pembayaran",
        "total tagihan",
        "total harga",
        "total",
        "jumlah",
        "tagihan"
    )

    private val excludeKeywords = listOf(
        "subtotal",
        "sub total",
        "total item",
        "total qty",
        "total diskon",
        "diskon",
        "kembali",
        "kembalian",
        "tunai",
        "cash",
        "npwp",
        "poin"
    )

    private val numberToken = Regex("\\d[\\d.,]*\\d|\\d")
    private val range = 100L..1_000_000_000L

    fun parseAmount(text: String): Long? {
        val lines = text.split("\n", "\r")

        val totalCandidates = mutableListOf<Long>()
        lines.forEachIndexed { index, line ->
            val lower = line.lowercase()
            if (excludeKeywords.any { lower.contains(it) }) return@forEachIndexed
            if (totalKeywords.any { lower.contains(it) }) {
                val inLine = amountsIn(line)
                if (inLine.isNotEmpty()) {
                    totalCandidates += inLine
                } else if (index + 1 < lines.size) {
                    totalCandidates += amountsIn(lines[index + 1])
                }
            }
        }
        totalCandidates.filter { it in range }.maxOrNull()?.let { return it }

        val moneyLike = lines
            .filter { line -> excludeKeywords.none { line.lowercase().contains(it) } }
            .flatMap { amountsIn(it, requireSeparator = true) }
            .filter { it in range }
        if (moneyLike.isNotEmpty()) return moneyLike.max()

        return lines.flatMap { amountsIn(it) }.filter { it in range }.maxOrNull()
    }

    private fun amountsIn(line: String, requireSeparator: Boolean = false): List<Long> {
        return numberToken.findAll(line).mapNotNull { match ->
            val raw = match.value
            if (requireSeparator && !raw.contains('.') && !raw.contains(',')) {
                null
            } else {
                normalize(raw)
            }
        }.toList()
    }

    private fun normalize(token: String): Long? {
        var value = token
        if (value.contains('.') && value.contains(',')) {
            val lastDot = value.lastIndexOf('.')
            val lastComma = value.lastIndexOf(',')
            if (lastComma > lastDot) {
                value = value.replace(".", "").substringBefore(',')
            } else {
                value = value.replace(",", "").substringBefore('.')
            }
        } else if (value.contains(',')) {
            val index = value.lastIndexOf(',')
            if (value.length - index - 1 == 2) value = value.substring(0, index)
            value = value.replace(",", "")
        } else if (value.contains('.')) {
            val index = value.lastIndexOf('.')
            if (value.length - index - 1 == 2) value = value.substring(0, index)
            value = value.replace(".", "")
        }
        return value.filter { it.isDigit() }.toLongOrNull()
    }
}
