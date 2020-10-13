package com.example.stackoverflow.ui.main

import android.app.Application
import androidx.room.Room
import org.dbtools.android.room.CloseableDatabaseWrapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionDatabaseWrapper
@Inject constructor(
    context: Application
) : CloseableDatabaseWrapper<QuestionDatabase>(context)
{
    override fun createDatabase(): QuestionDatabase {
        return Room.databaseBuilder(context, QuestionDatabase::class.java, QuestionDatabase.DATABASE_FILENAME)
            .build()
    }

    fun getQuestionDao() = getDatabase().questionDao
}
