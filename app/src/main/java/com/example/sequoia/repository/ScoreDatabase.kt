package com.example.sequoia.repository

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper

@Database(entities = [Score::class], version = 1, exportSchema = false)
abstract class ScoreDatabase: RoomDatabase() {
    abstract fun scoreDao(): ScoreDAO

    companion object{
        @Volatile
        private var INSTANCE: ScoreDatabase? = null

        fun getInstance(context: Context):ScoreDatabase{
            val tempinstance = INSTANCE
            if(tempinstance != null) {
                return tempinstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScoreDatabase::class.java, "dbScore"
                    )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
