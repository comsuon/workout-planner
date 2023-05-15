package com.comsuon.wp.core.domain

import com.wp.core.data.repository.WorkoutRepo
import javax.inject.Inject

class DeleteWorkoutUseCase @Inject() constructor( private val repository: WorkoutRepo) {
    suspend operator fun invoke(id: Long) = repository.deleteWorkout(id)
}