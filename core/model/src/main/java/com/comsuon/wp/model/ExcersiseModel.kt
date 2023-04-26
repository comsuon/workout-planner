package com.comsuon.wp.model

data class ExerciseModel(
    var exerciseName: String = "",
    var repCount: Int = 1,
    var timePerRep: Int = 3,
    var isTime: Boolean = false,
    var autoFinished: Boolean = true,
    var skipLastSet: Boolean = false,
    var colorCode: Int = 0
)
