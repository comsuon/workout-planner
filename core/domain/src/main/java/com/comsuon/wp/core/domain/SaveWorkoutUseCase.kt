package com.comsuon.wp.core.domain

import com.comsuon.wp.model.WorkoutModel
import com.wp.core.data.repository.WorkoutRepo
import javax.inject.Inject

class SaveWorkoutUseCase @Inject constructor(private val workoutRepository: WorkoutRepo) {
    suspend operator fun invoke(workout: WorkoutModel) {
        if (workout.index != 0L) {
            workoutRepository.updateWorkoutData(workout)
        } else {
            workoutRepository.saveWorkoutData(workout)
        }
    }
}