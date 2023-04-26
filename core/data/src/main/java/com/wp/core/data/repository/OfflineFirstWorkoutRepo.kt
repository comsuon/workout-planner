package com.wp.core.data.repository

import com.comsuon.wp.model.WorkoutModel

class OfflineFirstWorkoutRepo constructor(
    private val appDB: WorkoutDB
) : WorkoutRepo {
    override suspend fun getWorkoutDataList(): List<WorkoutModel> {
        return appDB.workoutDao().getWorkoutDataList().map(WorkoutData::toModel)
    }

    override suspend fun getWorkout(id: Int): WorkoutModel {
        return appDB.workoutDao().getWorkoutById(id).toModel()
    }

    override suspend fun deleteWorkout(id: Int) {
        val workoutData = appDB.workoutDao().getWorkoutById(id)
        appDB.workoutDao().deleteWorkout(workoutData.workoutEntity)
    }

    override suspend fun saveWorkoutData(data: WorkoutModel) {
        val workoutEntity = WorkoutEntity(name = data.workoutName)
        val workoutId = appDB.workoutDao().insertWorkout(workoutEntity)
        data.loopList.forEach { loopModel ->
            val loopEntity =
                LoopEntity(workoutId = workoutId.toInt(), setCount = loopModel.setCount)
            val loopId = appDB.loopDao().insertLoop(loopEntity)
            val exerciseList = loopModel.exerciseList.map { exerciseModel ->
                ExerciseEntity(
                    loopId = loopId.toInt(),
                    exerciseName = exerciseModel.exerciseName,
                    isTime = exerciseModel.isTime,
                    timePerRep = exerciseModel.timePerRep,
                    repCount = exerciseModel.repCount,
                    autoFinished = exerciseModel.autoFinished,
                    skipLastSet = exerciseModel.skipLastSet,
                    colorCode = exerciseModel.colorCode.value.toString()
                )
            }
            appDB.exerciseDao().insertAllExercise(exerciseList)
        }
    }
}