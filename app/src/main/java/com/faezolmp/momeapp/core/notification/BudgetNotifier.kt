package com.faezolmp.momeapp.core.notification

import android.content.Context
import com.faezolmp.momeapp.core.domain.usecase.BudgetUseCase
import com.faezolmp.momeapp.core.domain.usecase.TransactionUseCase
import com.faezolmp.momeapp.core.utils.DateUtils
import com.faezolmp.momeapp.core.utils.formatRupiah
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class BudgetNotifier(
    private val context: Context,
    private val budgetUseCase: BudgetUseCase,
    private val transactionUseCase: TransactionUseCase
) {

    suspend fun checkAfterInsert() = withContext(Dispatchers.Default) {
        val budget = budgetUseCase.active().first() ?: return@withContext
        if (!budget.overLimitAlert) return@withContext
        val (start, end) = DateUtils.periodRange(budget.period, budget.weekStartDay, System.currentTimeMillis())
        val spent = transactionUseCase.between(start, end).first()
            .filter { !it.isIncome }
            .sumOf { it.amount }
        if (spent > budget.amount) {
            MomeNotifications.show(
                context = context,
                id = MomeNotifications.NOTIF_OVER_LIMIT,
                title = "Budget Terlampaui",
                message = "Pengeluaran ${formatRupiah(spent, budget.currencySymbol)} sudah melebihi batas ${formatRupiah(budget.amount, budget.currencySymbol)}."
            )
        }
    }
}
