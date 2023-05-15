package com.comsuon.wp.core.domain

import com.wp.core.data.repository.WorkoutRepo
import javax.inject.Inject

class GetWorkoutUseCase @Inject constructor(private val repository: WorkoutRepo) {
    suspend operator fun invoke(workoutId: Long) = repository.getWorkout(workoutId)
}