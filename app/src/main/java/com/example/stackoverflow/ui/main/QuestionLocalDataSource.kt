package com.example.stackoverflow.ui.main

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionLocalDataSource
@Inject constructor(
    databaseWrapper: QuestionDatabaseWrapper
) {
    private val questionDao = databaseWrapper.getQuestionDao()
    fun getAllQuestions(): Flow<List<Question>> {
        return questionDao.findAll()
    }

    suspend fun deleteAllQuestions() {
        questionDao.deleteAll()
    }

    suspend fun insertQuestions(questions: List<Question>) {
        questions.forEach {
            questionDao.insert(it)
        }
    }
}
