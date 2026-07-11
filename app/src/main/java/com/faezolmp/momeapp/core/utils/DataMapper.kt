package com.faezolmp.momeapp.core.utils

import com.faezolmp.momeapp.core.data.local.entity.BudgetEntity
import com.faezolmp.momeapp.core.data.local.entity.CategoryEntity
import com.faezolmp.momeapp.core.data.local.entity.TransactionEntity
import com.faezolmp.momeapp.core.domain.model.BudgetPeriod
import com.faezolmp.momeapp.core.domain.model.BudgetSetting
import com.faezolmp.momeapp.core.domain.model.Category
import com.faezolmp.momeapp.core.domain.model.ExampleModel
import com.faezolmp.momeapp.core.domain.model.Transaction
import com.faezolmp.momeapp.core.domain.model.TransactionSource

object DataMapper {
    fun mapperExampleModelFromLayerDataToLayerDomain(data: String): ExampleModel {
        return ExampleModel(
            dataExample = data
        )
    }

    fun sortMapper(data: String) = ExampleModel(
        dataExample = data
    )

    fun transactionToDomain(entity: TransactionEntity): Transaction = Transaction(
        id = entity.id,
        amount = entity.amount,
        categoryId = entity.categoryId,
        dateTime = entity.dateTime,
        note = entity.note,
        source = runCatching { TransactionSource.valueOf(entity.source) }.getOrDefault(TransactionSource.MANUAL),
        attachmentPath = entity.attachmentPath,
        isIncome = entity.isIncome
    )

    fun transactionToEntity(domain: Transaction): TransactionEntity = TransactionEntity(
        id = domain.id,
        amount = domain.amount,
        categoryId = domain.categoryId,
        dateTime = domain.dateTime,
        note = domain.note,
        source = domain.source.name,
        attachmentPath = domain.attachmentPath,
        isIncome = domain.isIncome
    )

    fun categoryToDomain(entity: CategoryEntity): Category = Category(
        id = entity.id,
        name = entity.name,
        iconKey = entity.iconKey,
        colorHex = entity.colorHex,
        isDefault = entity.isDefault
    )

    fun categoryToEntity(domain: Category): CategoryEntity = CategoryEntity(
        id = domain.id,
        name = domain.name,
        iconKey = domain.iconKey,
        colorHex = domain.colorHex,
        isDefault = domain.isDefault
    )

    fun budgetToDomain(entity: BudgetEntity): BudgetSetting = BudgetSetting(
        id = entity.id,
        period = runCatching { BudgetPeriod.valueOf(entity.period) }.getOrDefault(BudgetPeriod.DAILY),
        amount = entity.amount,
        weekStartDay = entity.weekStartDay,
        currencyCode = entity.currencyCode,
        currencySymbol = entity.currencySymbol,
        decimalDigits = entity.decimalDigits,
        overLimitAlert = entity.overLimitAlert,
        eveningReminder = entity.eveningReminder,
        warningThreshold = entity.warningThreshold,
        isActive = entity.isActive
    )

    fun budgetToEntity(domain: BudgetSetting): BudgetEntity = BudgetEntity(
        id = 1L,
        period = domain.period.name,
        amount = domain.amount,
        weekStartDay = domain.weekStartDay,
        currencyCode = domain.currencyCode,
        currencySymbol = domain.currencySymbol,
        decimalDigits = domain.decimalDigits,
        overLimitAlert = domain.overLimitAlert,
        eveningReminder = domain.eveningReminder,
        warningThreshold = domain.warningThreshold,
        isActive = domain.isActive
    )
}
