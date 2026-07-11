package com.faezolmp.momeapp.presentation.screen.Detail

data class TransactionDetailUiState(
    val title: String = "",
    val amount: Long = 0L,
    val isIncome: Boolean = false,
    val categoryName: String = "",
    val iconKey: String = "other",
    val colorHex: String = "#8A93AB",
    val dateLabel: String = "",
    val timeLabel: String = "",
    val source: String = "",
    val note: String = "",
    val hasAttachment: Boolean = false,
    val currencySymbol: String = "Rp",
    val loaded: Boolean = false
)

fun sampleTransactionDetail(): TransactionDetailUiState = TransactionDetailUiState(
    title = "Makan Siang",
    amount = 45_000L,
    isIncome = false,
    categoryName = "Makan & Minum",
    iconKey = "food",
    colorHex = "#E8912E",
    dateLabel = "24 Sep 2024",
    timeLabel = "12:45",
    source = "Input Manual",
    note = "Makan siang di Boga Resto bersama tim.",
    hasAttachment = true,
    loaded = true
)
