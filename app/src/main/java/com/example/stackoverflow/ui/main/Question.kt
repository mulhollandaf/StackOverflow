package com.example.stackoverflow.ui.main

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var title: String,
    var link: String
)
