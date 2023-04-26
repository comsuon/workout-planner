package com.comsuon.wp.database.di

import com.comsuon.wp.database.WorkoutDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    @Singleton
    fun provideWorkoutDao(workoutDB: WorkoutDB) = workoutDB.workoutDao()

    @Provides
    @Singleton
    fun provideExerciseDao(workoutDB: WorkoutDB) = workoutDB.exerciseDao()

    @Provides
    @Singleton
    fun provideLoopDao(workoutDB: WorkoutDB) = workoutDB.loopDao()
}