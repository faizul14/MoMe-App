package com.faezolmp.momeapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.core.content.IntentCompat
import com.faezolmp.momeapp.core.utils.ImageStorage
import com.faezolmp.momeapp.core.utils.ReceiptParser
import com.faezolmp.momeapp.presentation.navigation.MomeNavHost
import com.faezolmp.momeapp.presentation.navigation.SharePayload
import com.faezolmp.momeapp.presentation.ui.theme.MomeAppTheme

class MainActivity : ComponentActivity() {

    private val sharePayload = mutableStateOf<SharePayload?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleShareIntent(intent)
        setContent {
            MomeAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MomeNavHost(
                        sharePayload = sharePayload.value,
                        onShareConsumed = { sharePayload.value = null }
                    )
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleShareIntent(intent)
    }

    private fun handleShareIntent(intent: Intent?) {
        if (intent?.action != Intent.ACTION_SEND) return
        val type = intent.type ?: return
        when {
            type.startsWith("image/") -> {
                val uri = IntentCompat.getParcelableExtra(intent, Intent.EXTRA_STREAM, Uri::class.java)
                val path = uri?.let { ImageStorage.copyFromUri(this, it) }
                sharePayload.value = SharePayload(amount = 0L, attachmentPath = path)
            }
            type == "text/plain" -> {
                val text = intent.getStringExtra(Intent.EXTRA_TEXT).orEmpty()
                sharePayload.value = SharePayload(
                    amount = ReceiptParser.parseAmount(text) ?: 0L,
                    attachmentPath = null
                )
            }
        }
    }
}
