package com.githubviewer.app.data.repository.repo

import com.githubviewer.app.data.api.service.repo.RepoApi
import com.githubviewer.app.data.api.service.repo.response.RepoResponse
import com.githubviewer.app.domain.common.SchedulersProvider
import com.githubviewer.app.domain.entity.repo.Repo
import com.githubviewer.app.domain.repository.repo.RepoRepository
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class RepoRepositoryImpl @Inject constructor(
    private val repoApi: RepoApi,
    private val schedulersProvider: SchedulersProvider
) : RepoRepository {

    override fun getAllPublicRepos(since: Int): Single<List<Repo>> = repoApi
        .allPublicRepos(since)
        .subscribeOn(schedulersProvider.io())
        .map { it.map(RepoResponse::toDomain) }

}