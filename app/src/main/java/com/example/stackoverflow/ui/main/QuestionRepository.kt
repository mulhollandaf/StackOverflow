package com.example.stackoverflow.ui.main

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.FetcherResult
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.StoreBuilder
import com.dropbox.android.external.store4.StoreRequest
import com.dropbox.android.external.store4.StoreResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class QuestionRepository
@Inject constructor(
    private val questionLocal: QuestionLocalDataSource,
){
        private val questionStore = StoreBuilder.from<QuestionKey, QuestionsDto, List<QuestionInfo>>(
            fetcher = Fetcher.ofResult { fetchQuestions() },
            sourceOfTruth = SourceOfTruth.Companion.of(
                reader = {getQuestionsFromDB()},
                writer = {_, questionsDto -> writeQuestionsForStore(questionsDto.items)}
            )
        ).build()

    private suspend fun fetchQuestions(): FetcherResult<QuestionsDto> =
        withContext(Dispatchers.IO) {
            try {
                val response = provideStackExchange().fetchQuestions()
                if (response.isSuccessful) {
                    response.body()?.let {
                        return@withContext FetcherResult.Data(it)
                    }
                }
                Timber.w("Failed to get questions | ${response.code()} | ${response.message()}")
            } catch (e: Exception) {
                Timber.e(e, "Failed to get questions")
                return@withContext FetcherResult.Error.Exception(e)
            }
            return@withContext FetcherResult.Error.Message("Failed to get questions")
        }

    fun getQuestions(force: Boolean = false): Flow<StoreResponse<List<QuestionInfo>>> {
        return questionStore.stream(StoreRequest.cached(QuestionKey, force))
    }

    private fun getQuestionsFromDB(): Flow<List<QuestionInfo>> {
        return questionLocal.getAllQuestions().mapLatest {
            it.map {question ->
                Timber.d("Mapping Question ${question.title}")
                QuestionInfo(question.title, question.link)
            }
        }
    }

    private suspend fun writeQuestionsForStore(questionsDto: List<QuestionDto>) {
        questionLocal.deleteAllQuestions()
        val question = questionsDto.mapNotNull {
            Timber.d("Writing Question ${it.title}")
            if (it.is_answered && it.answer_count > 1) {
                Question(title = it.title, link = it.link)
            }
            else {
                null
            }
        }
        questionLocal.insertQuestions(question)
    }

    private fun provideStackExchange(): Api {
        return Retrofit.Builder()
            .baseUrl("https://api.stackexchange.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

}

private object QuestionKey
