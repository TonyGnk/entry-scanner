package com.tonygnk.entry_scanner.ui.screens.scanScreen

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.shouldShowRationale
import com.tonygnk.entry_scanner.R
import com.tonygnk.entry_scanner.utills.LottieAnimatedIcon


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NoCameraScreen(
    permissionStatus: PermissionStatus,
    askForPermission: () -> Unit,
) {
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.weight(1f))
        LottieAnimatedIcon(
            animationResource = R.raw.camera,
            size = 96
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "No access to camera",
            softWrap = true,
            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground)
        )
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            ),
            onClick = {
                if (permissionStatus.shouldShowRationale) {
                    // Rationale shown, so try to ask for permission again
                    askForPermission()
                } else {
                    // Direct to app settings if "Don't ask again" was selected
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.fromParts("package", context.packageName, null)
                    context.startActivity(intent)
                }
            }
        ) {
            Text(
                "Grant Permission",
                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onTertiary)
            )
        }
        Spacer(Modifier.weight(1f))
    }
}
