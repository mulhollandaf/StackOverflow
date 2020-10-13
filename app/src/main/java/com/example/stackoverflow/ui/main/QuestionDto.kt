package com.example.stackoverflow.ui.main

data class QuestionDto(val title: String, val link: String, val is_answered: Boolean, val answer_count: Int)

data class QuestionsDto(val items: List<QuestionDto>)