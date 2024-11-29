package com.tonygnk.entry_scanner.ui.screens.scanScreen

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.tonygnk.entry_scanner.BarcodeAnalyzer
import com.tonygnk.entry_scanner.R
import com.tonygnk.entry_scanner.data.model.Participant
import com.tonygnk.entry_scanner.data.model.Team
import kotlinx.coroutines.delay


@Composable
fun ScanContentScreen(
    setArrived: (String, Int) -> Unit,
    goBack: () -> Unit = {},
    teams: List<Team>
) {
    val contextOuter = LocalContext.current
    val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> = remember {
        ProcessCameraProvider.getInstance(contextOuter)
    }

    val localLifecycleOwner = LocalLifecycleOwner.current

    val qrString = remember { mutableStateOf("") }
    val qrBoundingBox = remember { mutableStateOf<Rect?>(null) }

    val coroutineScope = rememberCoroutineScope()

    AndroidView(
        factory = { context ->
            val previewView = androidx.camera.view.PreviewView(context)

            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview
                .Builder()
                .build()
                .apply {
                    surfaceProvider = previewView.surfaceProvider
                }

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .apply {
                    setAnalyzer(
                        ContextCompat.getMainExecutor(context),
                        BarcodeAnalyzer(coroutineScope) { qr ->
                            qrString.value = qr
                            delay(4500)
                            qrString.value = ""
                        }
                    )
                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    localLifecycleOwner,
                    cameraSelector,
                    preview,
                    imageAnalysis
                )
            } catch (e: Exception) {
                Log.e("QRCodeScanner", "Camera binding failed", e)
            }

            previewView
        },
        modifier = Modifier.fillMaxSize()
    )

    val state = if (qrString.value.isNotBlank()) {
        val qr = qrString.value
        //The first 10 characters of the QR code are the team ID and the rest is the member index
        val teamId = qr.substring(0, 20)
        val teamExist: Team? = teams.find { it.id == teamId }
        if (teamExist != null) {
            val memberIndex = qr.substring(20).toInt()
            val memberExist: Participant? = teamExist.members.getOrNull(memberIndex)
            if (memberExist != null && !memberExist.hasArrived) {
                setArrived(teamId, memberIndex)
                ScanState.SCAN
            } else if (memberExist != null) ScanState.SCANNED else ScanState.ERROR
        } else ScanState.ERROR

    } else ScanState.BLANK

    Box(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 15.dp, vertical = 9.dp)
    ) {
        IconButton(
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceContainerLowest),
            onClick = goBack
        ) {
            Icon(
                tint = MaterialTheme.colorScheme.onSurface,
                painter = painterResource(id = R.drawable.back_large),
                contentDescription = ""
            )
        }
    }

    AnimatedScannedBox(
        state = state,
    )
}

enum class ScanState {
    SCAN,
    SCANNED,
    BLANK,
    ERROR
}
