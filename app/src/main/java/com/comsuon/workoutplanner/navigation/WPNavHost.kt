package com.comsuon.workoutplanner.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.comsuon.workoutplanner.buildOptionArg
import com.comsuon.workoutplanner.navigation.Screens.Editor
import com.comsuon.workoutplanner.navigation.Screens.Home
import com.comsuon.workoutplanner.navigation.Screens.Player
import com.comsuon.workoutplanner.view.editor.Editor
import com.comsuon.workoutplanner.view.home.Home

@Composable
fun WPNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Home.name
) {
    NavHost(
        navController = navController,
        startDestination = Home.name
    ) {

        composable(route = Home.name) {
            Home(navController = navController, viewModel = hiltViewModel())
        }
        composable(
            route = "${Editor.name}${Editor.extras.buildOptionArg()}",
            arguments = listOf(navArgument(Editor.extras) { defaultValue = "" })
        ) { backStackEntry ->
            Editor(
                navController = navController,
                workoutId = backStackEntry.arguments?.getString(Editor.extras)
                    ?: "",
                viewModel = hiltViewModel()
            )
        }
        composable(route = Player.name) {}
    }
}