package com.wp.core.data.di

import com.wp.core.data.repository.OfflineFirstWorkoutRepo
import com.wp.core.data.repository.WorkoutRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsWorkoutRepo(
        workoutRepo: OfflineFirstWorkoutRepo
    ): WorkoutRepo

}