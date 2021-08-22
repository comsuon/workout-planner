package com.comsuon.workoutplanner.view

import androidx.compose.ui.graphics.Color
import com.comsuon.workoutplanner.ui.theme.EXERCISE_COLORS

data class WorkoutModel(
    var workoutName: String = "",
    var loopList: List<LoopModel> = listOf()
)

data class LoopModel(
    var loopName: String = "",
    var setCount: Int = 3, //default is 3
    var exerciseList: List<ExerciseModel> = listOf()
)

data class ExerciseModel(
    var exerciseName: String = "",
    var repCount: Int = 1,
    var timePerRep: Int = 3,
    var isTime: Boolean = false,
    var autoFinished: Boolean = true,
    var skipLastSet: Boolean = false,
    var colorCode: Color = EXERCISE_COLORS.random()
)
