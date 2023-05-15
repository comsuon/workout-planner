package com.comsuon.wp.core.domain

import com.comsuon.wp.model.WorkoutModel
import com.wp.core.data.repository.WorkoutRepo
import javax.inject.Inject

class ReorderLoopUseCase @Inject constructor(repo: WorkoutRepo) {
    suspend operator fun invoke(workoutId: WorkoutModel, fromIndex: Int, toIndex: Int) {

    }
}