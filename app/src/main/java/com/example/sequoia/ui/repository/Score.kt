package com.example.sequoia.ui.repository

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Score(val gameId: Int, val gameScore: Int, val gameDate: String){
    val id :Int = gameId
    val score: Int = gameScore
    val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val date: LocalDateTime = LocalDateTime.parse(gameDate, pattern)
}

