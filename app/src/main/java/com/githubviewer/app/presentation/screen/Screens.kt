package com.githubviewer.app.presentation.screen

import androidx.fragment.app.Fragment
import com.githubviewer.app.presentation.screen.repo.detail.RepoDetailFragment
import com.githubviewer.app.presentation.screen.repo.list.RepoListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    class RepoListScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = RepoListFragment()
    }

    class RepoDetailScreen(val repoUrl: String) : SupportAppScreen() {
        override fun getFragment(): Fragment = RepoDetailFragment.newInstance(repoUrl)
    }

}