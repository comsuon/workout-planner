package com.comsuon.workoutplanner.di

import android.content.Context
import androidx.room.Room
import com.comsuon.workoutplanner.repository.WorkoutRepoImpl
import com.comsuon.workoutplanner.repository.db.WorkoutDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    fun provideWorkoutDB(@ApplicationContext appContext: Context): WorkoutDB {
        return Room.databaseBuilder(
            appContext,
            WorkoutDB::class.java, "workout-planner"
        ).build()
    }

    @Provides
    fun getWorkoutRepo(appDB: WorkoutDB) = WorkoutRepoImpl(appDB)
}