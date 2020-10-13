package com.example.stackoverflow.ui.main

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
    @Insert
    suspend fun insert(question: Question)

    @Query("DELETE FROM Question")
    suspend fun deleteAll()

    @Query("SELECT * FROM Question")
    fun findAll(): Flow<List<Question>>
}
