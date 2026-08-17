package com.imposter.app.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

object Routes {
    const val SELECT = "select"
    const val GROUP = "group"
    const val CONFIG = "config"
    const val REVEAL = "reveal"
    const val RESOLVE = "resolve"
}

@Composable
fun ImposterApp(
    lastCrash: String? = null,
    onClearCrash: () -> Unit = {},
) {
    val navController = rememberNavController()
    val gameViewModel: GameViewModel = viewModel()

    NavHost(navController = navController, startDestination = Routes.SELECT) {
        composable(Routes.SELECT) {
            GameSelectScreen(
                onPickGame = { type ->
                    gameViewModel.selectGameType(type)
                    navController.navigate(Routes.GROUP)
                },
                lastCrash = lastCrash,
                onClearCrash = onClearCrash,
            )
        }
        composable(Routes.GROUP) {
            GroupSetupScreen(
                viewModel = gameViewModel,
                onContinue = { navController.navigate(Routes.CONFIG) },
            )
        }
        composable(Routes.CONFIG) {
            ConfigScreen(
                viewModel = gameViewModel,
                onStart = { navController.navigate(Routes.REVEAL) },
            )
        }
        composable(Routes.REVEAL) {
            RevealScreen(
                viewModel = gameViewModel,
                onFinished = {
                    navController.navigate(Routes.RESOLVE) {
                        popUpTo(Routes.REVEAL) { inclusive = true }
                    }
                },
            )
        }
        composable(Routes.RESOLVE) {
            ResolveScreen(
                viewModel = gameViewModel,
                onNewRound = {
                    gameViewModel.dealAgain()
                    navController.navigate(Routes.REVEAL) {
                        popUpTo(Routes.CONFIG) { inclusive = false }
                    }
                },
                onNewCategory = {
                    navController.navigate(Routes.CONFIG) {
                        popUpTo(Routes.CONFIG) { inclusive = true }
                    }
                },
                onHome = {
                    navController.navigate(Routes.SELECT) {
                        popUpTo(Routes.SELECT) { inclusive = true }
                    }
                },
            )
        }
    }
}
