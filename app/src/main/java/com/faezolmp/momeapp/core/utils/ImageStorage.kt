package com.faezolmp.momeapp.core.utils

import android.content.Context
import android.net.Uri
import java.io.File

object ImageStorage {

    private fun receiptsDir(context: Context): File =
        File(context.filesDir, "receipts").apply { mkdirs() }

    fun newReceiptFile(context: Context): File =
        File(receiptsDir(context), "receipt_${System.currentTimeMillis()}.jpg")

    fun copyFromUri(context: Context, uri: Uri): String? {
        val target = newReceiptFile(context)
        return runCatching {
            context.contentResolver.openInputStream(uri)?.use { input ->
                target.outputStream().use { output -> input.copyTo(output) }
            }
            target.absolutePath
        }.getOrNull()
    }
}
