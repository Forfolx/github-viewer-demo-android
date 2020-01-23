package com.githubviewer.app.domain.repository.repo

import com.githubviewer.app.domain.entity.repo.Repo
import io.reactivex.Flowable
import io.reactivex.Single

interface RepoRepository {
    fun getAllPublicRepos(since: Int): Single<List<Repo>>
}