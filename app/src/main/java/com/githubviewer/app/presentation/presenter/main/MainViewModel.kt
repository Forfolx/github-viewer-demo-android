package com.githubviewer.app.presentation.presenter.main

import com.githubviewer.app.presentation.presenter.BaseViewModel
import com.githubviewer.app.presentation.screen.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val router: Router
) : BaseViewModel() {

    override fun onColdStart() {
        router.newRootScreen(Screens.RepoListScreen())
    }

}