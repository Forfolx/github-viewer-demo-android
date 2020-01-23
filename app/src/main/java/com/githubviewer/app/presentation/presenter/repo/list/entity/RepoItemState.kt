package com.githubviewer.app.presentation.presenter.repo.list.entity

data class RepoItemState(
    val id: Long,
    val name: String,
    val htmlUrl: String,
    val ownerLogin: String,
    val description: String
)