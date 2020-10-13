package com.example.stackoverflow.ui.main

import retrofit2.Response
import retrofit2.http.GET

interface Api {

    @GET("2.2/questions?order=desc&sort=activity&site=stackoverflow")
    suspend fun fetchQuestions(
    ): Response<QuestionsDto>
}

