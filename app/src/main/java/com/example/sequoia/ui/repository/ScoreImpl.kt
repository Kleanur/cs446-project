package com.example.sequoia.ui.repository
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.sequoia.ui.theme.games
import org.json.JSONObject
import java.io.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ScoreImpl {

    public fun createJsonObj(gameId: Int, gameScore: Int): JSONObject{
        val date = LocalDateTime.now()
        val score = JSONObject()
        score.put("Id", gameId)
        score.put("Score", gameScore)
        score.put("Date", date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        return score
    }


    public fun addScore(gameId: Int, gameScore: Int, context: Context) {
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


    public fun getScore(gameId: Int, context: Context): String{
        val filename = games[gameId]
        val file = File(context.filesDir, filename)
        val fileReader = FileReader(file)
        val bufferedReader = BufferedReader(fileReader)
        val stringBuilder = StringBuilder()
        var line: String? = bufferedReader.readLine()
        while (line != null) {
            stringBuilder.append(line).append("\n")
            line = bufferedReader.readLine()
        }
        bufferedReader.close()

        val response = stringBuilder.toString()
        System.out.println(response)
        return response
    }

    /*public fun deleteScores(context: Context): String{

    }*/
}