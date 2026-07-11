package com.faezolmp.momeapp.presentation.screen.Onboarding

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.faezolmp.momeapp.presentation.ui.components.PlaceholderScreen

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onFinish: () -> Unit = {}
) {
    PlaceholderScreen(
        title = "Onboarding / Setup Budget",
        modifier = modifier,
        actions = listOf(
            "Selesai (ke Home)" to onFinish
        )
    )
}
