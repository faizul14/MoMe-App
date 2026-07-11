package com.faezolmp.momeapp.presentation.ui

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class RupiahVisualTransformation(private val separator: Char = '.') : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text
        if (digits.isEmpty()) {
            return TransformedText(AnnotatedString(""), OffsetMapping.Identity)
        }

        val grouped = digits.reversed().chunked(3).joinToString(separator.toString()).reversed()

        val originalToTransformed = IntArray(digits.length + 1)
        val transformedToOriginal = IntArray(grouped.length + 1)
        var count = 0
        originalToTransformed[0] = 0
        transformedToOriginal[0] = 0
        for (index in grouped.indices) {
            if (grouped[index] != separator) {
                count++
                originalToTransformed[count] = index + 1
            }
            transformedToOriginal[index + 1] = count
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val safe = offset.coerceIn(0, digits.length)
                return originalToTransformed[safe]
            }

            override fun transformedToOriginal(offset: Int): Int {
                val safe = offset.coerceIn(0, grouped.length)
                return transformedToOriginal[safe]
            }
        }

        return TransformedText(AnnotatedString(grouped), offsetMapping)
    }
}
