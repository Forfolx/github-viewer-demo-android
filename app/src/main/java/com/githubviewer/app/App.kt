package com.githubviewer.app

import android.app.Application
import com.githubviewer.app.di.*
import timber.log.Timber
import toothpick.Toothpick

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initLogger()
        //initTootpick()
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initTootpick() {
        Toothpick.openScope(DI.APP_SCOPE)
            .installModules(
                AppModule(this),
                DataModule(),
                DomainModule(),
                PresentationModule()
            )
    }

}