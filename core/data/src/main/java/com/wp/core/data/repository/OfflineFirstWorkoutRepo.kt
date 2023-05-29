package com.wp.core.data.repository

import com.comsuon.wp.database.dao.ExerciseDAO
import com.comsuon.wp.database.dao.LoopDAO
import com.comsuon.wp.database.dao.WorkoutDAO
import com.comsuon.wp.database.model.ExerciseEntity
import com.comsuon.wp.database.model.LoopEntity
import com.comsuon.wp.database.model.WorkoutData
import com.comsuon.wp.database.model.WorkoutEntity
import com.comsuon.wp.database.model.toEntity
import com.comsuon.wp.database.model.toLoopEntity
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

    override suspend fun getWorkout(id: Long): WorkoutModel {
        return workoutDAO.getWorkoutById(id).toModel()
    }

    override suspend fun deleteWorkout(id: Long) {
        val workoutData = workoutDAO.getWorkoutById(id)
        workoutDAO.deleteWorkout(workoutData.workoutEntity)
    }

    override suspend fun updateWorkoutData(data: WorkoutModel) {
        val workoutEntity = WorkoutEntity(
            name = data.workoutName,
            isFavourite = data.isFavourite,
            id = data.index
        )
        val workoutId = workoutDAO.insertWorkout(workoutEntity)
        //find the existing workout
        val existingWorkout = workoutDAO.getWorkoutById(workoutId).toModel()

        //find the deleted loop and delete in db
        existingWorkout.loopList.subtract(data.loopList.toSet()).forEach { loop ->
            loopDAO.deleteLoop(loop.toLoopEntity(workoutId))
        }
        data.loopList.forEachIndexed { index, loopModel ->
            loopModel.indexInWorkout = index
            val loopEntity = loopModel.toLoopEntity(workoutEntity.id)
            val loopId = loopDAO.insertLoop(loopEntity)

            //find the deleted exercises and delete in db
            val existingLoop = existingWorkout.loopList.filter { it.loopId == loopId }
                .firstOrNull()
            existingLoop?.exerciseList?.subtract(loopModel.exerciseList.toSet())
                ?.forEach { exercise ->
                    exerciseDAO.deleteExercise(exercise.toEntity(loopId))
                }
            loopModel.exerciseList.mapIndexed { exerciseIndex, exerciseModel ->
                exerciseModel.indexInLoop = exerciseIndex
                exerciseModel.toEntity(loopModel.loopId)
            }.also { exerciseDAO.insertAllExercise(it) }
        }
    }

    override suspend fun saveWorkoutData(data: WorkoutModel) {
        val workoutEntity = WorkoutEntity(name = data.workoutName)
        val workoutId = workoutDAO.insertWorkout(workoutEntity)
        data.loopList.forEachIndexed { index, loopModel ->
            val loopEntity =
                LoopEntity(
                    workoutId = workoutId,
                    setCount = loopModel.setCount,
                    indexInWorkout = index
                )
            val loopId = loopDAO.insertLoop(loopEntity)
            val exerciseList = loopModel.exerciseList.mapIndexed { exerciseIndex, exerciseModel ->
                ExerciseEntity(
                    loopId = loopId,
                    exerciseName = exerciseModel.exerciseName,
                    isTime = exerciseModel.isTime,
                    timePerRep = exerciseModel.timePerRep,
                    repCount = exerciseModel.repCount,
                    autoFinished = exerciseModel.autoFinished,
                    skipLastSet = exerciseModel.skipLastSet,
                    colorCode = exerciseModel.colorCode,
                    indexInLoop = exerciseIndex
                )
            }
            exerciseDAO.insertAllExercise(exerciseList)
        }
    }
}