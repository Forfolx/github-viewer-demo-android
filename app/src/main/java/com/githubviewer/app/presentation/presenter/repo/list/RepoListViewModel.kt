package com.githubviewer.app.presentation.presenter.repo.list

import androidx.lifecycle.MutableLiveData
import com.githubviewer.app.domain.common.SchedulersProvider
import com.githubviewer.app.domain.entity.repo.Repo
import com.githubviewer.app.domain.repository.repo.RepoRepository
import com.githubviewer.app.presentation.common.ErrorHandler
import com.githubviewer.app.presentation.common.Paginator
import com.githubviewer.app.presentation.presenter.BaseViewModel
import com.githubviewer.app.presentation.presenter.repo.list.entity.RepoItemState
import com.githubviewer.app.presentation.presenter.repo.toItemState
import com.githubviewer.app.presentation.screen.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class RepoListViewModel @Inject constructor(
    private val router: Router,
    private val repoRepository: RepoRepository,
    private val schedulersProvider: SchedulersProvider,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    val repoListData = MutableLiveData<List<RepoItemState>>()

    private val paginator = Paginator({
        repoRepository
            .getAllPublicRepos(it)
            .observeOn(schedulersProvider.ui())
            .map { it.map(Repo::toItemState) }
    }, object : Paginator.ViewController<RepoItemState> {

        override fun showData(show: Boolean, data: List<RepoItemState>) {
            repoListData.value = data
        }

        override fun showEmptyError(show: Boolean, error: Throwable?) {
            error?.let { errorHandler.proceed(it) }
        }

    })

    fun loadItems() = paginator.loadNewPage()

    fun refresh() = paginator.refresh()

    fun onRepoClick(repoItemState: RepoItemState) = router.navigateTo(Screens.RepoDetailScreen(repoItemState.htmlUrl))

    override fun onCleared() {
        super.onCleared()
        paginator.release()
    }

}