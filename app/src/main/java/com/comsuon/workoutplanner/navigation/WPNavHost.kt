package com.comsuon.workoutplanner.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.comsuon.workoutplanner.buildOptionArg
import com.comsuon.wp.collections.navigation.collectionRoute
import com.comsuon.wp.collections.ui.Home
import com.comsuon.wp.feature.editor.navigation.editorExtras
import com.comsuon.wp.feature.editor.navigation.editorRoute
import com.comsuon.wp.feature.editor.views.Editor

@Composable
fun WPNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = collectionRoute
) {
    NavHost(
        navController = navController,
        startDestination = collectionRoute
    ) {

        composable(route = collectionRoute) {
            Home(navController = navController, viewModel = hiltViewModel())
        }
        composable(
            route = "${editorRoute}${editorExtras.buildOptionArg()}",
            arguments = listOf(navArgument(editorExtras) { defaultValue = "" })
        ) { backStackEntry ->
            Editor(
                navController = navController,
                workoutId = backStackEntry.arguments?.getString(editorExtras)?.toLongOrNull() ?: 0L,
                viewModel = hiltViewModel()
            )
        }
        composable(route = "player_route") {}
    }
}