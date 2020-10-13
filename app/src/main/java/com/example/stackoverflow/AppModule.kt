package com.example.stackoverflow

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

class AppModule {
    @Module
    @InstallIn(ApplicationComponent::class)
    class AppModule
}