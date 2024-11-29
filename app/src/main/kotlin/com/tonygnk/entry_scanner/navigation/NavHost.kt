package com.tonygnk.entry_scanner.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.tonygnk.entry_scanner.App
import com.tonygnk.entry_scanner.core.common.utils.AndroidConnectivityObserver
import com.tonygnk.entry_scanner.di.viewModelFactory
import com.tonygnk.entry_scanner.ui.AppViewModel
import com.tonygnk.entry_scanner.ui.screens.scanScreen.QRCodeScanner
import com.tonygnk.entry_scanner.ui.screens.tableListScreen.other.NoInternetScreen
import com.tonygnk.entry_scanner.core.designsystem.theme.AppTransition
import com.tonygnk.entry_scanner.ui.screens.tableListScreen.TeamListScreen
import com.tonygnk.entry_scanner.utills.LocalAnimatedContentScope
import com.tonygnk.entry_scanner.utills.LocalSharedTransitionScope
import kotlinx.serialization.Serializable

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
fun MyNavHost(navController: NavHostController) {
    val context = LocalContext.current
    val model: AppViewModel = viewModel<AppViewModel>(
        factory = viewModelFactory {
            AppViewModel(
                repository = App.appModule.repository,
                connectivityObserver = AndroidConnectivityObserver(context)
            )
        }
    )

    SharedTransitionLayout {
        CompositionLocalProvider(value = LocalSharedTransitionScope provides this) {
            val isConnected by model.isConnected.collectAsStateWithLifecycle()

            AnimatedContent(
                targetState = isConnected, label = ""
            ) {
                when (it) {
                    true -> NavHost(
                        navController = navController,
                        startDestination = TopDestination.Table
                    ) {
                        route<TopDestination.Table> {
                            TeamListScreen(
                                model = model,
                                onNavigateToScanner = {
                                    navController.navigate(TopDestination.Scan)
                                }
                            )
                        }

                        route<TopDestination.Scan> {
                            QRCodeScanner(
                                goBack = { navController.navigateUp() },
                                model = model,
                            )
                        }
                    }

                    false -> NoInternetScreen()
                }
            }
        }
    }
}

@Serializable
sealed interface TopDestination {
    @Serializable
    data object Table : TopDestination

    @Serializable
    data object Scan : TopDestination

    @Serializable
    data object NewTeam : TopDestination
}

inline fun <reified R : Any> NavGraphBuilder.route(
    noinline enterTransition: () -> EnterTransition = AppTransition.nativeEnter,
    noinline exitTransition: () -> ExitTransition = AppTransition.nativeExit,
    noinline popEnterTransition: () -> EnterTransition? = AppTransition.nativeEnterPop,
    noinline popExitTransition: () -> ExitTransition = AppTransition.nativeExitPop,
    noinline content: @Composable (AnimatedContentScope.(NavBackStackEntry) -> Unit)
) {
    composable<R>(
        enterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() }
    ) {
        CompositionLocalProvider(LocalAnimatedContentScope provides this) {
            content(it)
        }
    }
}
