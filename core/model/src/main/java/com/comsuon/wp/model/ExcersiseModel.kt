package com.comsuon.wp.model

data class ExerciseModel(
    var exerciseId: Long = 0,
    var exerciseName: String = "",
    var repCount: Int = 1,
    var timePerRep: Int = 3,
    var isTime: Boolean = false,
    var autoFinished: Boolean = true,
    var skipLastSet: Boolean = false,
    var indexInLoop: Int = 0,
    var colorCode: String = ""
)
