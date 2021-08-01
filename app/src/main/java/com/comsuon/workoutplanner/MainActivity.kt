package com.comsuon.workoutplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.comsuon.workoutplanner.navigation.Screens
import com.comsuon.workoutplanner.ui.theme.WorkoutPlannerTheme
import com.comsuon.workoutplanner.view.Home

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutPlannerTheme {
                val allScreens = Screens.values().toList()
                val navController = rememberNavController()
                val backStackEntry = navController.currentBackStackEntryAsState()

                Scaffold() { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screens.Home.name
                    ) {

                        composable(route = Screens.Home.name) {
                            Home(navController = navController)
                        }
                        composable(route = Screens.Editor.name) {}
                        composable(route = Screens.Player.name) {}
                    }
                }
            }
        }
    }
}