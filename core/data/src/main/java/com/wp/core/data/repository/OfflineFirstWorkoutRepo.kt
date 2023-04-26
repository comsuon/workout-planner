package com.wp.core.data.repository

import com.comsuon.wp.database.dao.ExerciseDAO
import com.comsuon.wp.database.dao.LoopDAO
import com.comsuon.wp.database.dao.WorkoutDAO
import com.comsuon.wp.database.model.ExerciseEntity
import com.comsuon.wp.database.model.LoopEntity
import com.comsuon.wp.database.model.WorkoutData
import com.comsuon.wp.database.model.WorkoutEntity
import com.comsuon.wp.database.model.toModel
import com.comsuon.wp.model.WorkoutModel
import javax.inject.Inject

class OfflineFirstWorkoutRepo @Inject constructor(
    private val workoutDAO: WorkoutDAO,
    private val exerciseDAO: ExerciseDAO,
    private val loopDAO: LoopDAO
) : WorkoutRepo {
    override suspend fun getWorkoutDataList(): List<WorkoutModel> {
        return workoutDAO.getWorkoutDataList().map(WorkoutData::toModel)
    }

    override suspend fun getWorkout(id: Int): WorkoutModel {
        return workoutDAO.getWorkoutById(id).toModel()
    }

    override suspend fun deleteWorkout(id: Int) {
        val workoutData = workoutDAO.getWorkoutById(id)
        workoutDAO.deleteWorkout(workoutData.workoutEntity)
    }

    override suspend fun saveWorkoutData(data: WorkoutModel) {
        val workoutEntity = WorkoutEntity(name = data.workoutName)
        val workoutId = workoutDAO.insertWorkout(workoutEntity)
        data.loopList.forEach { loopModel ->
            val loopEntity =
                LoopEntity(workoutId = workoutId.toInt(), setCount = loopModel.setCount)
            val loopId = loopDAO.insertLoop(loopEntity)
            val exerciseList = loopModel.exerciseList.map { exerciseModel ->
                ExerciseEntity(
                    loopId = loopId.toInt(),
                    exerciseName = exerciseModel.exerciseName,
                    isTime = exerciseModel.isTime,
                    timePerRep = exerciseModel.timePerRep,
                    repCount = exerciseModel.repCount,
                    autoFinished = exerciseModel.autoFinished,
                    skipLastSet = exerciseModel.skipLastSet,
                    colorCode = exerciseModel.colorCode.toString()
                )
            }
            exerciseDAO.insertAllExercise(exerciseList)
        }
    }
}