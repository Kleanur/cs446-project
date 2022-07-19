package com.example.sequoia.repository

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ScoreRepo(private val scoreDao: ScoreDAO) {

    suspend fun insertScores(score : Score) = scoreDao.insert(score)

    suspend fun updateScores(score: Score) = scoreDao.update(score)

    suspend fun deleteScores(score: Score) = scoreDao.delete(score)

    fun getAllScores() : LiveData<List<Score>> = scoreDao.getAllScores()

    fun deleteAllScores() : Void = scoreDao.deleteAllScores()

}