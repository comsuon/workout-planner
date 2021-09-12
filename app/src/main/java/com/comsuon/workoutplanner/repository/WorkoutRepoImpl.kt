package com.comsuon.workoutplanner.repository

import com.comsuon.workoutplanner.repository.db.WorkoutDB
import com.comsuon.workoutplanner.repository.db.entities.ExerciseEntity
import com.comsuon.workoutplanner.repository.db.entities.LoopEntity
import com.comsuon.workoutplanner.repository.db.entities.WorkoutEntity
import com.comsuon.workoutplanner.repository.db.pojo.WorkoutData
import com.comsuon.workoutplanner.repository.db.pojo.toModel
import com.comsuon.workoutplanner.view.WorkoutModel

class WorkoutRepoImpl constructor(
    private val appDB: WorkoutDB
) : WorkoutRepo {
    override suspend fun getWorkoutDataList(): List<WorkoutModel> {
        return appDB.workoutDao().getWorkoutDataList().map(WorkoutData::toModel)
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