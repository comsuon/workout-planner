package com.comsuon.wp.core.domain

import com.wp.core.data.repository.WorkoutRepo
import javax.inject.Inject

class GetWorkoutListUseCase @Inject constructor(private val repository: WorkoutRepo) {
    suspend operator fun invoke() = repository.getWorkoutDataList()
}