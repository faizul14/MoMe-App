package com.faezolmp.momeapp.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.faezolmp.momeapp.core.data.local.dao.BudgetDao
import com.faezolmp.momeapp.core.data.local.dao.CategoryDao
import com.faezolmp.momeapp.core.data.local.dao.TransactionDao
import com.faezolmp.momeapp.core.data.local.entity.BudgetEntity
import com.faezolmp.momeapp.core.data.local.entity.CategoryEntity
import com.faezolmp.momeapp.core.data.local.entity.TransactionEntity

@Database(
    entities = [TransactionEntity::class, CategoryEntity::class, BudgetEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MomeDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao
    abstract fun budgetDao(): BudgetDao

    companion object {
        const val DATABASE_NAME = "mome.db"

        val seedCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                db.execSQL(
                    "INSERT INTO budget (id, period, amount, weekStartDay, currencyCode, currencySymbol, decimalDigits, overLimitAlert, eveningReminder, warningThreshold, isActive) " +
                        "VALUES (1, 'DAILY', 150000, 1, 'IDR', 'Rp', 0, 1, 0, 0.8, 1)"
                )
                val categories = listOf(
                    Triple("Makan & Minum", "food", "#E8912E"),
                    Triple("Transportasi", "transport", "#4B69C6"),
                    Triple("Hiburan", "fun", "#7A5AF8"),
                    Triple("Belanja", "shopping", "#DB4E96"),
                    Triple("Kesehatan", "health", "#2FB673")
                )
                categories.forEach { (name, iconKey, colorHex) ->
                    db.execSQL(
                        "INSERT INTO categories (name, iconKey, colorHex, isDefault) VALUES ('$name', '$iconKey', '$colorHex', 1)"
                    )
                }
            }
        }
    }
}
