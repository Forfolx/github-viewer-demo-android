package com.githubviewer.app.presentation.presenter.repo.detail

import com.githubviewer.app.presentation.presenter.BaseViewModel
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class RepoDetailViewModel @Inject constructor(
    private val router: Router
) : BaseViewModel() {

    fun onBackPressed() = router.exit()

}