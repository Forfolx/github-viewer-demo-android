package com.githubviewer.app.presentation.screen.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.githubviewer.app.R
import com.githubviewer.app.di.*
import com.githubviewer.app.presentation.common.BackButtonListener
import com.githubviewer.app.presentation.common.app_messenger.LifecycleAppMessageObserver
import com.githubviewer.app.presentation.presenter.main.MainViewModel
import com.githubviewer.app.presentation.extension.viewModel
import com.githubviewer.app.presentation.screen.BaseFragment
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import toothpick.Toothpick
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel(MainViewModel::class.java)

    private val activityNavigator = SupportAppNavigator(this, R.id.fragment_container)

    private val currentFragment
        get() = supportFragmentManager.findFragmentById(R.id.fragment_container) as BaseFragment

    @Inject
    lateinit var navigationHolder: NavigatorHolder

    @Inject
    lateinit var appMessagesObserver: LifecycleAppMessageObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.openScope(DI.APP_SCOPE).apply {
            installModules(
                AppModule(applicationContext),
                DataModule(),
                DomainModule(),
                PresentationModule()
            )
            Toothpick.inject(this@MainActivity, this)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.apply {
            lifecycle.addObserver(this)
            if (savedInstanceState == null) {
                onColdStart()
            }
        }
        lifecycle.addObserver(appMessagesObserver)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationHolder.setNavigator(activityNavigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            Toothpick.closeScope(DI.APP_SCOPE)
        }
    }

    override fun onBackPressed() {
        val check = currentFragment != null
                && currentFragment is BackButtonListener
                && currentFragment.onBackPressed()
        if (check.not()) {
            super.onBackPressed()
        }
    }
}
