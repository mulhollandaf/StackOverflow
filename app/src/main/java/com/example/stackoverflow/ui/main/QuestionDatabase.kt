package com.example.stackoverflow.ui.main

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        Question::class,
    ],
    version = 1
)
abstract class QuestionDatabase : RoomDatabase() {

    abstract val questionDao: QuestionDao

    companion object {
        const val DATABASE_FILENAME = "question.db"
    }
}
