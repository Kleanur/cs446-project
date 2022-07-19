package com.example.sequoia.repository

import androidx.room.ColumnInfo
import java.time.LocalDateTime
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.time.format.DateTimeFormatter

@Entity(tableName = "dbScores")
class Score(private var dateTime: LocalDateTime = LocalDateTime.now(), var game:Int = 0, var gameScore:Int= 0) {

    @PrimaryKey(autoGenerate = true)
    private var id:Int? = null

    @ColumnInfo(name = "dateTime")
    private var date:String= dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

    @ColumnInfo(name= "gameId")
    private var gameId:Int = game

    @ColumnInfo(name = "score")
    private var score:Int = gameScore
}