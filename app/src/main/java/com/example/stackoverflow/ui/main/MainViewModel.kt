package com.example.stackoverflow.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest

class MainViewModel
@ViewModelInject constructor(
    questionRepository: QuestionRepository
): ViewModel() {
    var questionFlow = questionRepository.getQuestions(false)
        .mapLatest {
            it.dataOrNull()
        }
        .filterNotNull()
        .distinctUntilChanged()
        .flowOn(Dispatchers.IO)

}