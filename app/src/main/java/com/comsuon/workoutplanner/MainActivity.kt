package com.comsuon.workoutplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.*
import com.comsuon.workoutplanner.navigation.Screens
import com.comsuon.workoutplanner.ui.theme.WorkoutPlannerTheme
import com.comsuon.workoutplanner.view.editor.Editor
import com.comsuon.workoutplanner.view.home.Home
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutPlannerTheme {
                val navController = rememberNavController()
                val backStackEntry = navController.currentBackStackEntryAsState()

                NavHost(
                    navController = navController,
                    startDestination = Screens.Home.name
                ) {

                    composable(route = Screens.Home.name) {
                        Home(navController = navController, viewModel = hiltViewModel())
                    }
                    composable(
                        route = "${Screens.Editor.name}${Screens.Editor.extras.buildOptionArg()}",
                        arguments = listOf(navArgument(Screens.Editor.extras) { defaultValue = "" })
                    ) { backStackEntry ->
                        Editor(
                            navController = navController,
                            workoutId = backStackEntry.arguments?.getString(Screens.Editor.extras)
                                ?: "",
                            viewModel = hiltViewModel()
                        )
                    }
                    composable(route = Screens.Player.name) {}
                }
            }
        }
    }
}

fun String.buildOptionArg() = "?$this={$this}"