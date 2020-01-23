package com.githubviewer.app.di

import com.githubviewer.app.presentation.common.ErrorHandler
import com.githubviewer.app.presentation.common.app_messenger.AppMessageObserver
import com.githubviewer.app.presentation.common.app_messenger.AppMessenger
import com.githubviewer.app.presentation.common.app_messenger.LifecycleAppMessageObserver
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module

class PresentationModule : Module() {

    init {
        val cicerone = Cicerone.create()
        bind(Router::class.java).toInstance(cicerone.router)
        bind(NavigatorHolder::class.java).toInstance(cicerone.navigatorHolder)

        bind(AppMessenger::class.java).singletonInScope()
        bind(AppMessageObserver::class.java).singletonInScope()
        bind(LifecycleAppMessageObserver::class.java).singletonInScope()

        bind(ErrorHandler::class.java).singletonInScope()
    }

}