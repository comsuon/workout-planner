package com.comsuon.workoutplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.comsuon.workoutplanner.navigation.Screens
import com.comsuon.workoutplanner.ui.theme.WorkoutPlannerTheme
import com.comsuon.workoutplanner.view.editor.Editor
import com.comsuon.workoutplanner.view.Home
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutPlannerTheme {
                val allScreens = Screens.values().toList()
                val navController = rememberNavController()
                val backStackEntry = navController.currentBackStackEntryAsState()

                NavHost(
                    navController = navController,
                    startDestination = Screens.Home.name
                ) {

                    composable(route = Screens.Home.name) {
                        Home(navController = navController)
                    }
                    composable(route = Screens.Editor.name) {
                        Editor(navController = navController, viewModel = hiltViewModel())
                    }
                    composable(route = Screens.Player.name) {}
                }
            }
        }
    }
}