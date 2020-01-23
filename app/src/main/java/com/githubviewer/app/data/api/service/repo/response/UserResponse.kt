package com.githubviewer.app.data.api.service.repo.response

import com.google.gson.annotations.SerializedName

class UserResponse(
    @SerializedName("login")
    val login: String
)