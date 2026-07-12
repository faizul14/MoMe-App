package com.faezolmp.momeapp.core.notification

import android.content.Context
import com.faezolmp.momeapp.core.domain.usecase.BudgetUseCase
import com.faezolmp.momeapp.core.domain.usecase.TransactionUseCase
import com.faezolmp.momeapp.core.utils.DateUtils
import com.faezolmp.momeapp.core.utils.formatRupiah
import kotlinx.coroutines.flow.first

class BudgetNotifier(
    private val context: Context,
    private val budgetUseCase: BudgetUseCase,
    private val transactionUseCase: TransactionUseCase
) {

    suspend fun checkAfterInsert() {
        val budget = budgetUseCase.active().first() ?: return
        if (!budget.overLimitAlert || budget.amount <= 0L) return
        val (start, end) = DateUtils.periodRange(budget.period, budget.weekStartDay, System.currentTimeMillis())
        val spent = transactionUseCase.between(start, end).first()
            .filter { !it.isIncome }
            .sumOf { it.amount }

        val thresholdAmount = (budget.amount * budget.warningThreshold).toLong()
        val spentText = formatRupiah(spent, budget.currencySymbol)
        val limitText = formatRupiah(budget.amount, budget.currencySymbol)

        when {
            spent > budget.amount -> MomeNotifications.show(
                context = context,
                id = MomeNotifications.NOTIF_OVER_LIMIT,
                title = "Budget Terlampaui",
                message = "Pengeluaran $spentText sudah melebihi batas $limitText."
            )
            spent >= thresholdAmount -> MomeNotifications.show(
                context = context,
                id = MomeNotifications.NOTIF_OVER_LIMIT,
                title = "Mendekati Batas",
                message = "Pengeluaran $spentText sudah mencapai ${(budget.warningThreshold * 100).toInt()}% dari batas $limitText."
            )
        }
    }
}
