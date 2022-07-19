package com.example.sequoia.repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.RoomDatabase
import com.example.sequoia.repository.ScoreDatabase.Companion.getInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ScoreViewModel(application : Application) : AndroidViewModel(application){
    private val repository : ScoreRepo
    private var readAll : LiveData<List<Score>>
    init {
        val scoreDB = ScoreDatabase.getInstance(application).scoreDao()
        repository = ScoreRepo(scoreDB)
        readAll = repository.getAllScores()
    }

    fun addScore(score : Score){
        viewModelScope.launch(IO){
            repository.insertScores(score)
        }
    }
}