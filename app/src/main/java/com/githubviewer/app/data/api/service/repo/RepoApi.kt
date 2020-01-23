package com.githubviewer.app.data.api.service.repo

import com.githubviewer.app.data.api.service.repo.response.RepoResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RepoApi {

    @GET("repositories")
    fun allPublicRepos(@Query("since") since: Int): Single<List<RepoResponse>>

}