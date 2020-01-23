package com.githubviewer.app.data.repository.repo

import com.githubviewer.app.data.api.service.repo.response.RepoResponse
import com.githubviewer.app.data.api.service.repo.response.UserResponse
import com.githubviewer.app.domain.entity.repo.Repo
import com.githubviewer.app.domain.entity.user.User

fun RepoResponse.toDomain() = Repo(
    id,
    name,
    htmlUrl,
    owner.toDomain(),
    description
)

fun UserResponse.toDomain() = User(login)