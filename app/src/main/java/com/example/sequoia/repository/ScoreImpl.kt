package com.example.sequoia.repository
import android.content.Context
import org.json.JSONObject
import java.io.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
        val score = createJsonObj(gameId, gameScore).toString()
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
        if((file.exists() && !file.isDirectory) ==  false) {
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



    fun getAllScores(gameId: Int?, context: Context): List<Score>{
        val scores : MutableList<Score> = mutableListOf<Score>()
        val filename = games[gameId]
        val file = File(context.filesDir, filename)
        val fileReader = FileReader(file)
        val bufferedReader = BufferedReader(fileReader)
        val stringBuilder = StringBuilder()
        var line: String? = "Date : Score" + bufferedReader.readLine()
        while (line != null) {
            scores.add(createJsonObj(line))
            stringBuilder.append(line).append("\n")
            line = bufferedReader.readLine()
        }
        bufferedReader.close()
        val response = stringBuilder.toString()
        return scores
    }

    fun createJsonObj(score: String): Score{
        val jsonobj : JSONObject = JSONObject(score)
        val scoreobj : Score = Score(
            gameId = jsonobj.get("Id").toString().toInt(),
            gameScore = jsonobj.get("Score").toString().toInt(),
            gameDate = jsonobj.get("Date").toString())
        return scoreobj
    }

    fun getYValues(jsonlist : List<Score>): List<Int>{
        val itr = jsonlist.listIterator()
        val list: MutableList<Int> = mutableListOf()
        while (itr.hasNext()) {
            list.add(itr.next().score.toString().toInt())
        }
        return list
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