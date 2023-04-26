package com.comsuon.wp.model

data class WorkoutModel(
    var index: Int = 0,
    var workoutName: String = "",
    var loopList: List<LoopModel> = listOf(),
    var isFavourite: Boolean = false
)
