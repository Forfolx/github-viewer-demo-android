package com.githubviewer.app.data.api.service.repo.response

import com.google.gson.annotations.SerializedName

class RepoResponse(

    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("html_url")
    val htmlUrl: String,

    @SerializedName("owner")
    val owner: UserResponse,

    @SerializedName("description")
    val description: String?

)