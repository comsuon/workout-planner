package com.comsuon.workoutplanner.navigation

sealed class Screens(val name: String, val extras: String = "") {
    object Home : Screens("Home")
    object Editor : Screens("Editor", "workoutId")
    object Player : Screens("Player")
}