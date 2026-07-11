package com.faezolmp.momeapp.presentation.screen.Detail

enum class DetailVisual { FOOD, TRANSPORT, FUN, SHOPPING, INCOME }

data class TransactionDetailUiState(
    val title: String,
    val amount: Long,
    val isIncome: Boolean,
    val categoryName: String,
    val visual: DetailVisual,
    val dateLabel: String,
    val timeLabel: String,
    val source: String,
    val note: String,
    val hasAttachment: Boolean,
    val currencySymbol: String = "Rp"
)

fun sampleTransactionDetail(): TransactionDetailUiState = TransactionDetailUiState(
    title = "Makan Siang",
    amount = 45_000L,
    isIncome = false,
    categoryName = "Makan & Minum",
    visual = DetailVisual.FOOD,
    dateLabel = "24 Sep 2024",
    timeLabel = "12:45",
    source = "Input Manual",
    note = "Makan siang di Boga Resto bersama tim.",
    hasAttachment = true
)
