package com.githubviewer.app.domain.entity.repo

import com.githubviewer.app.domain.entity.user.User

data class Repo(
    val id: Long,
    val name: String,
    val htmlUrl: String,
    val owner: User,
    val description: String?
)