package com.githubviewer.app.di

import android.content.Context
import com.githubviewer.app.common.AppSchedulers
import com.githubviewer.app.domain.common.SchedulersProvider
import toothpick.config.Module

class AppModule(context: Context) : Module() {

    init {
        bind(Context::class.java).toInstance(context)
        bind(SchedulersProvider::class.java).to(AppSchedulers::class.java).singletonInScope()
    }

}