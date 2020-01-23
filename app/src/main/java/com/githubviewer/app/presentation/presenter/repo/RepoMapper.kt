package com.githubviewer.app.presentation.presenter.repo

import com.githubviewer.app.domain.entity.repo.Repo
import com.githubviewer.app.presentation.presenter.repo.detail.entity.RepoDetailState
import com.githubviewer.app.presentation.presenter.repo.list.entity.RepoItemState

fun Repo.toItemState() = RepoItemState(id, name, htmlUrl, owner.login, description.orEmpty())

fun Repo.toDetailState() = RepoDetailState(htmlUrl)