package com.tonygnk.entry_scanner.ui.screens.scanScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.tonygnk.entry_scanner.ui.AppViewModel
import kotlinx.coroutines.delay


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QRCodeScanner(
    model: AppViewModel,
    goBack: () -> Unit = {}
) {
    //val state by model.teams.collectAsStateWithLifecycle()
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )
    val teams by model.teamsFlow.collectAsStateWithLifecycle()

    val theDialogHasShowed = remember { mutableStateOf(false) }
    val askForPermission = {
        cameraPermissionState.launchPermissionRequest()
    }
    LaunchedEffect(Unit) {
        theDialogHasShowed.value = true
        delay(700)
        askForPermission()
    }

    AnimatedContent(
        targetState = cameraPermissionState.status, label = "",
    ) { permissionStatus ->
        when (permissionStatus) {
            is PermissionStatus.Denied -> if (theDialogHasShowed.value) NoCameraScreen(
                askForPermission = askForPermission,
                permissionStatus = permissionStatus
            )

            is PermissionStatus.Granted -> ScanContentScreen(
                setArrived = { teamId, memberIndex ->
                    model.setArrived(teamId, memberIndex, true)
                },
                goBack = goBack,
                teams = teams
            )
        }
    }


}
