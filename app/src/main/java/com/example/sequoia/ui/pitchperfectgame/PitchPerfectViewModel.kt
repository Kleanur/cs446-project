package com.example.sequoia.ui.pitchperfectgame

import android.app.Application
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.MutableState
import com.example.sequoia.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.IOException
import java.io.InputStream

class PitchPerfectViewModel constructor(application: Application) : BaseViewModel(application) {

    private var mediaPlayer = MediaPlayer()
    private var countDownTimer: CountDownTimer? = null

    private var randomPitchSong: Song? = null
    private var selectedAnswerSong: Song? = null

    private val pitchSongsMutableList: MutableList<Song> = mutableListOf()
    private val answerPitchSongsMutableList: MutableList<Song> = mutableListOf()

    private val _answerMutableState: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val answerMutableState: StateFlow<Boolean?> = _answerMutableState

    private val _gameRoundMutableState: MutableStateFlow<Int> = MutableStateFlow(1)
    val gameRoundState: StateFlow<Int> = _gameRoundMutableState

    init {
        kotlin.runCatching {
            // Load Pitch songs from asset folder
            loadPitchSongs()
        }.onSuccess {
            // In case we need to handle the success case here.
            Log.d("PITCH_SCREEN - Song List Size", pitchSongsMutableList.size.toString())
        }.onFailure {
            // In case we need to handle the failure case here.
        }
    }

    fun choosePitchRandomSong(): Song? {
        // clear the list of answer songs
        answerPitchSongsMutableList.clear()
        // Randomly choose a song within the list of songs
        val randomInt = generateRandomNumber()
        Log.d("PITCH_SCREEN - randomly generated number", randomInt.toString())
        randomPitchSong = pitchSongsMutableList[randomInt]
        // add the randomly chosen song to our answers list
        randomPitchSong?.let { song ->
            answerPitchSongsMutableList.add(song)
        }
        initializeAnswerPitchSongsSet()
        return randomPitchSong
    }

    fun chooseAnswerPitchSong(indexNumber: Int): Song? {
        // Randomly choose a song within the list of songs
        return if (answerPitchSongsMutableList.isEmpty().not()) {
            answerPitchSongsMutableList[indexNumber]
        } else null
    }

    private fun initializeAnswerPitchSongsSet() {
        val newPitchSongsList: MutableList<Song> = pitchSongsMutableList
        newPitchSongsList.remove(randomPitchSong)
        for (i in 0..gameRoundState.value) {
            val randomInt = (0 until newPitchSongsList.size).random()
            Log.d("PITCH_SCREEN - randomly generated number answer pitch", randomInt.toString())
            val randomSong = newPitchSongsList[randomInt]
            answerPitchSongsMutableList.add(randomSong)
        }
        answerPitchSongsMutableList.shuffled()
    }

    private fun generateRandomNumber(): Int {
        return (0 until pitchSongsMutableList.size).random()
    }

    private fun loadPitchSongs() {
        val path = "pitchsongs"
        // Load All the songs in asset folder
        context.assets?.list(path)?.forEach { fileName ->
            // Grab each song's name (fileName) and open that particular song as InputStream
            val inputStream: InputStream = context.assets.open("$path/$fileName")
            // Get the size of that particular song's inputStream to create a ByteArray of proper size
            val size: Int = inputStream.available()
            // Create a ByteArray with the opened song's size so that we can write the song's bytes into that
            val buffer = ByteArray(size)
            // Read the input stream into the byte array we created (called buffer)
            inputStream.read(buffer)
            // Add the song to the list of available pitch songs
            // The song object we created as below, has songName, song, and filePath properties.
            pitchSongsMutableList.add(
                Song(
                    songName = fileName,
                    song = buffer,
                    filePath = "$path/$fileName"
                )
            )
        }
    }

    fun updateSelectedPitchAnswerSong(answer: Song) {
        _answerMutableState.value = (answer.songName == randomPitchSong?.songName)
    }

    fun playRandomSongForTenSeconds(song: Song) {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            countDownTimer?.cancel()
        }
        mediaPlayer = MediaPlayer()
        //set up MediaPlayer
        try {
            // Open a random song from asset folder
            val assetFileDescriptor =
                context.assets.openFd("pitchsongs/${song.songName}")
            // Set the data source of MediaPlayer for our randomly selected song
            mediaPlayer.setDataSource(
                assetFileDescriptor.fileDescriptor,
                assetFileDescriptor.startOffset,
                assetFileDescriptor.length
            )
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        try {
            // Preparing MediaPlayer before playing a song.
            mediaPlayer.prepare()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        // start playing song
        mediaPlayer.start()
        // CountDownTimer to manage stop playing song after 10 seconds
        countDownTimer = object : CountDownTimer(10_000L, 1_000L) {
            override fun onTick(millisUntilFinished: Long) {
                // Nothing needs to be done within this override.
            }

            override fun onFinish() {
                // Stop playing song after 10 seconds
                mediaPlayer.stop()
            }
        }
        // CountDownTimer to manage stopping the mediaPlayer after 10 seconds
        countDownTimer?.start()
    }

    fun verifyAnswer(): Boolean {
        return randomPitchSong?.songName == selectedAnswerSong?.songName
    }

    fun stopMediaPlayer(){
        mediaPlayer.stop()
    }

    fun resetPlayerAnswer(){
        _answerMutableState.value = null
    }

    fun nextRound() {
        _gameRoundMutableState.value = (gameRoundState.value + 1)
    }

    data class AnswerState(
        var song: Song,
        var checkState: MutableState<Boolean>
    )

    data class Song(
        var songName: String,
        var song: ByteArray,
        var filePath: String,
        var isSelected: Boolean = false,
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Song

            if (songName != other.songName) return false
            if (!song.contentEquals(other.song)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = songName.hashCode()
            result = 31 * result + song.contentHashCode()
            return result
        }
    }
}