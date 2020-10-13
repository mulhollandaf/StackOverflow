package com.example.stackoverflow.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest

class MainViewModel
@ViewModelInject constructor(
    questionRepository: QuestionRepository
): ViewModel() {
    private val _eventChannel: ViewModelChannel<Event> = ViewModelChannel(this)
    val eventChannel: ReceiveChannel<Event> = _eventChannel
    var questionFlow = questionRepository.getQuestions(true)
        .mapLatest {
            it.dataOrNull()
        }
        .filterNotNull()
        .distinctUntilChanged()
        .flowOn(Dispatchers.IO)


    fun openUrl(link: String) {
        _eventChannel.sendAsync(Event.OpenUrl(link))
    }

    sealed class Event {
        data class OpenUrl(val url: String) : Event()
    }

}