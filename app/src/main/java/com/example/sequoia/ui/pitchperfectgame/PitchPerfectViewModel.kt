package com.example.sequoia.ui.pitchperfectgame

import android.app.Application
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.util.Log
import com.example.sequoia.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.IOException
import java.io.InputStream
import kotlin.random.Random
import kotlin.random.nextInt

class PitchPerfectViewModel constructor(application: Application) : BaseViewModel(application) {

    private var mediaPlayer = MediaPlayer()
    private var countDownTimer: CountDownTimer? = null

    private var randomPitchSong: Song? = null

    // list of all the songs used in pitch game
    private val pitchSongsMutableList: MutableList<Song> = mutableListOf()

    // list of all the answer songs used in pitch game
    private val answerPitchSongsMutableList: MutableList<Song> = mutableListOf()

    // list of all the already picked answer songs
    private val alreadyPickedAnswerPitchSongsMutableLIst: MutableList<Song?> = mutableListOf()

    private val _answerMutableState: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val answerMutableState: StateFlow<Boolean?> = _answerMutableState

    private val _selectedSongMutableState: MutableStateFlow<Song?> = MutableStateFlow(null)
    val selectedSongMutableState: StateFlow<Song?> = _selectedSongMutableState

    private val _gameRoundMutableState: MutableStateFlow<Int> = MutableStateFlow(1)
    val gameRoundState: StateFlow<Int> = _gameRoundMutableState

    private val _randomlyGeneratedIndexes:
            MutableStateFlow<MutableList<Int>> =
        MutableStateFlow(mutableListOf())
    val randomlyGeneratedIndexes: StateFlow<MutableList<Int>> = _randomlyGeneratedIndexes

    private val _playerAttemptMutableState: MutableStateFlow<Int> = MutableStateFlow(2)
    val playerAttemptState: StateFlow<Int> = _playerAttemptMutableState

    private val _scoreMutableState: MutableStateFlow<Int> = MutableStateFlow(0)
    val scoreState: StateFlow<Int> = _scoreMutableState

    private val alreadyPickedRandomNumber: MutableList<Int> = mutableListOf()

    private val _animateMelodyIcon: MutableStateFlow<Float?> = MutableStateFlow(null)
    val animateMelodyIcon: StateFlow<Float?> = _animateMelodyIcon

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
        // clear generated random number list
        alreadyPickedRandomNumber.clear()
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
        // add the remaining songs to the answer list
        populateAnswerPitchSongsList()
        return randomPitchSong
    }

    fun chooseAnswerPitchSong(indexNumber: Int): Song? {
        // Randomly choose a song within the list of songs
        return if (answerPitchSongsMutableList.isEmpty().not()) {
            answerPitchSongsMutableList[indexNumber]
        } else null
    }

    fun populateRandomlyGeneratedMutableStateList() {
        _randomlyGeneratedIndexes.value.add(generateUniqueRandomNumber(gameRoundState.value))
    }

    private fun generateUniqueRandomNumber(upperBound: Int): Int {
        val randomNumber = Random.nextInt(0..upperBound)
        return if (alreadyPickedRandomNumber.contains(randomNumber).not()) {
            randomNumber
        } else {
            generateUniqueRandomNumber(upperBound)
        }
    }

    private fun populateAnswerPitchSongsList() {
        val newPitchSongsList: MutableList<Song> = pitchSongsMutableList
        if (newPitchSongsList.remove(randomPitchSong)) {
            //Toccata-and-Fugue-in-D-minor-BWV-565-[AudioTrimmer.com].mp3
            for (i in 0 until gameRoundState.value) {
                val randomInt = (0 until newPitchSongsList.size).random()
                Log.d(
                    "PITCH_SCREEN - randomly generated number answer pitch",
                    randomInt.toString()
                )
                val randomSong = newPitchSongsList[randomInt]
                answerPitchSongsMutableList.add(randomSong)
            }
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
        _selectedSongMutableState.value = answer
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

    fun verifyAnswer() {
        _answerMutableState.value =
            (_selectedSongMutableState.value?.songName == randomPitchSong?.songName)
        stopMediaPlayer()
    }

    private fun stopMediaPlayer() {
        mediaPlayer.stop()
    }

    fun resetPlayerAnswer() {
        _answerMutableState.value = null
    }

    fun animateMelodyIcon(animationParameters: Float?) {
        _animateMelodyIcon.value = animationParameters
    }

    fun nextRound() {
        answerPitchSongsMutableList.clear()
        randomPitchSong = null
        _gameRoundMutableState.value = (gameRoundState.value + 1)
    }

    fun addScore() {

        _scoreMutableState.value = (scoreState.value + 1)
    }

    fun loseAttempt() {
        _playerAttemptMutableState.value = (playerAttemptState.value - 1)
    }

    fun resetGame() {
        _playerAttemptMutableState.value = 2
        _gameRoundMutableState.value = 1
        _answerMutableState.value = null
        randomPitchSong = null
    }


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

