package com.example.sequoia.repository
import android.content.Context
import org.json.JSONObject
import java.io.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object ScoreImpl {

    fun createJsonObj(gameId: Int, gameScore: Int): JSONObject{
        val date = LocalDateTime.now()
        val score = JSONObject()
        score.put("Id", gameId)
        score.put("Score", gameScore)
        score.put("Date", date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        return score
    }

    fun addScore(gameId: Int, gameScore: Int, context: Context) {
        val score = createJsonObj(gameId, gameScore).toString() + "\n"
        val filename = games[gameId]
        val file = File(context.filesDir, filename)
        if(file.exists() && !file.isDirectory) {
            File(context.filesDir, filename).appendText(score)
        }
        else{
            file.createNewFile()
            val fileWriter = FileWriter(file)
            val bufferedWriter = BufferedWriter(fileWriter)
            bufferedWriter.write(score)
            bufferedWriter.close()
        }
        updateTree(context)
    }

    fun getScore(gameId: Int?, context: Context): String{
        val filename = games[gameId]
        val file = File(context.filesDir, filename)
        if(!(file.exists() && !file.isDirectory)) {
           return ""
        }
        val fileReader = FileReader(file)
        val bufferedReader = BufferedReader(fileReader)
        val stringBuilder = StringBuilder()
        var line: String? =  bufferedReader.readLine()
        while (line != null) {
            stringBuilder.append(line).append("\n")
            line = bufferedReader.readLine()
        }
        bufferedReader.close()
        val response = stringBuilder.toString()
        return response
    }


    fun getYValues(gameId: Int?, context: Context): List<Int>{
        val yValues : MutableList<Int> = mutableListOf()
        val filename = games[gameId]
        val file = File(context.filesDir, filename)
        if(!(file.exists() && !file.isDirectory)) {
            return Collections.emptyList()
        }
        val fileReader = FileReader(file)
        val bufferedReader = BufferedReader(fileReader)
        var line: String? =  bufferedReader.readLine()
        while (line != null) {
           // println(line)
            yValues+= (JSONObject(line).getInt("Score"))// JSONObject(line).get("Score").toString().toInt())
            line = bufferedReader.readLine()
        }
       // println(yValues)
        bufferedReader.close()
        return yValues
    }

    fun stringToJSON(score: String): Score{
        val jsonobj : JSONObject = JSONObject(score)
        val scoreobj : Score = Score(
            gameId = jsonobj.get("Id").toString().toInt(),
            gameScore = jsonobj.get("Score").toString().toInt(),
            gameDate = jsonobj.get("Date").toString())
        return scoreobj
    }

    fun deleteAllHistory(context: Context){
        for(i in 1..4){
            val filename = games[i]
            val file = File(context.filesDir, filename)
            if (file.exists() && !file.isDirectory) {
                file.createNewFile()
                val fileWriter = FileWriter(file)
                val bufferedWriter = BufferedWriter(fileWriter)
                bufferedWriter.write("")
                bufferedWriter.close()
            }
        }
        deleteTreeHistory(context)
    }


    // functions for Tree
    fun updateTree(context: Context) {
        val filename = games[treeid]
        val file = File(context.filesDir, filename)
        var currDate = LocalDateTime.now().toString()
        if(file.exists() && !file.isDirectory) {
            if(containsDate(file, currDate) == false)
                File(context.filesDir, filename).appendText(currDate)
        }
        else if(LocalDateTime.now().dayOfMonth == 1){
            file.createNewFile()
            val fileWriter = FileWriter(file)
            val bufferedWriter = BufferedWriter(fileWriter)
            bufferedWriter.write(currDate)
            bufferedWriter.close()
        }
        else{
            file.createNewFile()
            val fileWriter = FileWriter(file)
            val bufferedWriter = BufferedWriter(fileWriter)
            bufferedWriter.write(currDate)
            bufferedWriter.close()
        }
    }

    private fun containsDate(file: File, currDate: String): Boolean {
        val fileReader = FileReader(file)
        val bufferedReader = BufferedReader(fileReader)
        var line: String? = bufferedReader.readLine()
        var flag: Boolean = false
        while (line != null) {
            if (line.contains(currDate, ignoreCase = true))
                flag = true
            line = bufferedReader.readLine()
        }
        bufferedReader.close()
        return flag
    }

    fun getTreeStage(context: Context): Int {
        val filename = games[treeid]
        var lines = 0
        val file = File(context.filesDir, filename)
        if (file.exists() && !file.isDirectory) {
            val fileReader = FileReader(file)
            val reader = BufferedReader(fileReader)
            while (reader.readLine() != null)
                lines++
            reader.close()
        }
        return lines
    }

    fun deleteTreeHistory(context: Context){
        val filename = games[treeid]
        val file = File(context.filesDir, filename)
        if (file.exists() && !file.isDirectory) {
            file.createNewFile()
            val fileWriter = FileWriter(file)
            val bufferedWriter = BufferedWriter(fileWriter)
            bufferedWriter.write("")
            bufferedWriter.close()
        }
    }
}