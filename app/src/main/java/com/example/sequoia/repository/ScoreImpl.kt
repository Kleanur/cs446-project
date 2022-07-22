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
        println(gameId)
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
    }


    fun getScore(gameId: Int?, context: Context): String{
        val filename = games[gameId]
        val file = File(context.filesDir, filename)
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

    /*public fun getStringScores(jsonlist : List<Score>): String{
        //val itr: Iterator<Score> = jsonlist.Iterator(jsonlist.size)

        var list: String = ""
        list += "Date : Score\n"
        while (itr.hasNext()) {
           var tscore = itr.next()
           list+= tscore.stringdate + " : " + tscore.score.toString() + "\n"
        }
        return list
    }*/

    fun getYValues(jsonlist : List<Score>): List<Int>{
        val itr = jsonlist.listIterator()
        val list: MutableList<Int> = mutableListOf()
        while (itr.hasNext()) {
            list.add(itr.next().score.toString().toInt())
        }
        return list
    }

    /*public fun deleteScores(context: Context): String{

    }*/
}