package com.comsuon.workoutplanner.view

data class WorkoutModel(
    var workoutName: String = "",
    var loopList: MutableList<LoopModel> = mutableListOf()
)

data class LoopModel(
    var loopName: String = "",
    var setCount: Int = 0,
    var exerciseList: MutableList<ExerciseModel> = mutableListOf()
)

data class ExerciseModel(
    var exerciseName: String = "",
    var repCount: String = "",
    var timePerRep: Long = 0,
    var autoFinished: Boolean = true,
    var skipLastSet: Boolean = false,
    var colorCode: String = "#FFFFFF"
)
