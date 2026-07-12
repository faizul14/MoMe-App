package com.faezolmp.momeapp.presentation.screen.Scan

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.faezolmp.momeapp.core.utils.ImageStorage
import com.faezolmp.momeapp.core.utils.ReceiptParser
import com.faezolmp.momeapp.presentation.ui.components.BottomTab
import com.faezolmp.momeapp.presentation.ui.components.MomeBottomBar
import com.faezolmp.momeapp.presentation.ui.components.PrimaryButton
import com.faezolmp.momeapp.presentation.ui.theme.BrandBackground
import com.faezolmp.momeapp.presentation.ui.theme.BrandNavy
import com.faezolmp.momeapp.presentation.ui.theme.PillBlueBg
import com.faezolmp.momeapp.presentation.ui.theme.ScannerBackground
import com.faezolmp.momeapp.presentation.ui.theme.ScannerCaption
import com.faezolmp.momeapp.presentation.ui.theme.ScannerFrame
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanScreen(
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {},
    onScanned: (Long, String) -> Unit = { _, _ -> },
    onDashboard: () -> Unit = {},
    onHistory: () -> Unit = {},
    onAdd: () -> Unit = {},
    onManage: () -> Unit = {}
) {
    val context = LocalContext.current
    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED
        )
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted -> hasPermission = granted }

    LaunchedEffect(Unit) {
        if (!hasPermission) permissionLauncher.launch(Manifest.permission.CAMERA)
    }

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
            Header(
                flashOn = flashOn,
                onClose = onClose,
                onToggleFlash = { flashOn = !flashOn },
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(ScannerBackground)
            ) {
                if (hasPermission) {
                    CameraArea(flashOn = flashOn, onScanned = onScanned)
                    ViewfinderOverlay()
                } else {
                    PermissionPrompt(
                        onGrant = { permissionLauncher.launch(Manifest.permission.CAMERA) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun CameraArea(flashOn: Boolean, onScanned: (Long, String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
        }
    }
    var processing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    DisposableEffect(lifecycleOwner) {
        controller.bindToLifecycle(lifecycleOwner)
        onDispose { controller.unbind() }
    }
    LaunchedEffect(flashOn) { runCatching { controller.enableTorch(flashOn) } }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                PreviewView(ctx).apply { this.controller = controller }
            },
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 28.dp)
        ) {
            CaptureButton(
                enabled = !processing,
                onClick = {
                    processing = true
                    capturePhoto(context, controller, scope) { amount, path ->
                        processing = false
                        onScanned(amount, path)
                    }
                }
            )
        }
        if (processing) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun CaptureButton(enabled: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(70.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = if (enabled) 0.9f else 0.4f))
            .clickable(enabled = enabled) { onClick() }
            .padding(6.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .border(3.dp, BrandNavy, CircleShape)
                .background(Color.White)
        )
    }
}

@Composable
private fun PermissionPrompt(onGrant: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        Text(
            text = "Butuh izin kamera untuk memindai struk.",
            color = ScannerCaption,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        PrimaryButton(text = "Izinkan Kamera", onClick = onGrant)
    }
}

@Composable
private fun ViewfinderOverlay() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val rectWidth = size.width * 0.72f
        val rectHeight = size.height * 0.5f
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
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Posisikan struk di dalam kotak lalu tekan tombol",
            color = ScannerCaption,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
        )
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

private fun capturePhoto(
    context: Context,
    controller: LifecycleCameraController,
    scope: CoroutineScope,
    onResult: (Long, String) -> Unit
) {
    val file = ImageStorage.newReceiptFile(context)
    val output = ImageCapture.OutputFileOptions.Builder(file).build()
    controller.takePicture(
        output,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                runOcr(context, file.absolutePath, scope, onResult)
            }

            override fun onError(exception: ImageCaptureException) {
                onResult(0L, file.absolutePath)
            }
        }
    )
}

private fun runOcr(context: Context, path: String, scope: CoroutineScope, onResult: (Long, String) -> Unit) {
    scope.launch {
        val image = runCatching {
            withContext(Dispatchers.IO) {
                InputImage.fromFilePath(context, Uri.fromFile(java.io.File(path)))
            }
        }.getOrNull()
        if (image == null) {
            onResult(0L, path)
            return@launch
        }
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                onResult(ReceiptParser.parseAmount(visionText.text) ?: 0L, path)
            }
            .addOnFailureListener {
                onResult(0L, path)
            }
            .addOnCompleteListener {
                recognizer.close()
            }
    }
}
