package com.example.sequoia.repository

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
public interface ScoreDAO {

    @Insert
    suspend fun insert(x: Score): Void

    @Update
    suspend fun update(x: Score): Void

    @Delete
    suspend fun delete(x: Score): Void

    @Query("DELETE FROM dbScores")
    fun deleteAllScores(): Void

    @Query("SELECT * FROM dbScores")
    fun getAllScores(): LiveData<List<Score>>

    //TODO: maybe get all of one game id

    //TODO: maybe add get query
}