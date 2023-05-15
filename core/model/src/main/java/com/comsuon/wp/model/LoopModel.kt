package com.comsuon.wp.model

data class LoopModel(
    var loopId: Long = 0,
    var loopName: String = "",
    var setCount: Int = 3, //default is 3
    var exerciseList: List<ExerciseModel> = listOf()
)