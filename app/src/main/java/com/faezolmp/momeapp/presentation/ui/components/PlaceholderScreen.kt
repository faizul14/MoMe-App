package com.faezolmp.momeapp.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PlaceholderScreen(
    title: String,
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null,
    actions: List<Pair<String, () -> Unit>> = emptyList()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
    ) {
        Text(text = title, style = MaterialTheme.typography.headlineSmall)
        Text(text = "Placeholder — Fase 0", style = MaterialTheme.typography.bodyMedium)
        actions.forEach { (label, onClick) ->
            Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
                Text(label)
            }
        }
        if (onBack != null) {
            OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text("Kembali")
            }
        }
    }
}
