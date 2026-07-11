package com.faezolmp.momeapp.presentation.screen.Scan

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.faezolmp.momeapp.presentation.ui.components.BottomTab
import com.faezolmp.momeapp.presentation.ui.components.MomeBottomBar
import com.faezolmp.momeapp.presentation.ui.components.ScreenLabel
import com.faezolmp.momeapp.presentation.ui.theme.BrandBackground
import com.faezolmp.momeapp.presentation.ui.theme.BrandNavy
import com.faezolmp.momeapp.presentation.ui.theme.PillBlueBg
import com.faezolmp.momeapp.presentation.ui.theme.ScannerBackground
import com.faezolmp.momeapp.presentation.ui.theme.ScannerCaption
import com.faezolmp.momeapp.presentation.ui.theme.ScannerFrame
import com.faezolmp.momeapp.presentation.ui.theme.MomeAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanScreen(
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {},
    onDashboard: () -> Unit = {},
    onHistory: () -> Unit = {},
    onAdd: () -> Unit = {},
    onManage: () -> Unit = {}
) {
    var flashOn by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = BrandBackground,
        bottomBar = {
            MomeBottomBar(
                activeTab = BottomTab.SCAN,
                onDashboard = onDashboard,
                onHistory = onHistory,
                onScan = {},
                onAdd = onAdd,
                onManage = onManage
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
//            ScreenLabel(text = "Scan Struk Otomatis", modifier = Modifier.padding(horizontal = 20.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Header(
                flashOn = flashOn,
                onClose = onClose,
                onToggleFlash = { flashOn = !flashOn },
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Viewfinder(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun Header(
    flashOn: Boolean,
    onClose: () -> Unit,
    onToggleFlash: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Tutup",
            tint = BrandNavy,
            modifier = Modifier
                .size(24.dp)
                .clickable { onClose() }
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Scan Struk",
            color = BrandNavy,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = if (flashOn) Icons.Filled.FlashOn else Icons.Filled.FlashOff,
            contentDescription = "Flash",
            tint = BrandNavy,
            modifier = Modifier
                .size(22.dp)
                .clickable { onToggleFlash() }
        )
        Spacer(modifier = Modifier.width(12.dp))
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(PillBlueBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = null,
                tint = BrandNavy,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun Viewfinder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(ScannerBackground)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val rectWidth = size.width * 0.72f
            val rectHeight = size.height * 0.6f
            val left = (size.width - rectWidth) / 2f
            val top = (size.height - rectHeight) / 2f
            val right = left + rectWidth
            val bottom = top + rectHeight
            val corner = 30.dp.toPx()
            val strokeWidth = 4.dp.toPx()

            drawLine(ScannerFrame, Offset(left, top), Offset(left + corner, top), strokeWidth, StrokeCap.Round)
            drawLine(ScannerFrame, Offset(left, top), Offset(left, top + corner), strokeWidth, StrokeCap.Round)
            drawLine(ScannerFrame, Offset(right, top), Offset(right - corner, top), strokeWidth, StrokeCap.Round)
            drawLine(ScannerFrame, Offset(right, top), Offset(right, top + corner), strokeWidth, StrokeCap.Round)
            drawLine(ScannerFrame, Offset(left, bottom), Offset(left + corner, bottom), strokeWidth, StrokeCap.Round)
            drawLine(ScannerFrame, Offset(left, bottom), Offset(left, bottom - corner), strokeWidth, StrokeCap.Round)
            drawLine(ScannerFrame, Offset(right, bottom), Offset(right - corner, bottom), strokeWidth, StrokeCap.Round)
            drawLine(ScannerFrame, Offset(right, bottom), Offset(right, bottom - corner), strokeWidth, StrokeCap.Round)

            val midY = top + rectHeight / 2f
            drawLine(
                ScannerFrame.copy(alpha = 0.7f),
                Offset(left, midY),
                Offset(right, midY),
                2.dp.toPx()
            )
        }
        Text(
            text = "Posisikan struk di dalam kotak untuk memindai",
            color = ScannerCaption,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ScanScreenPreview() {
    MomeAppTheme {
        ScanScreen()
    }
}
