package com.githubviewer.app.data.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class GitHubApiVersionHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(
        chain.request()
            .newBuilder()
            .addHeader(GithubApiVersionHeader.KEY, GithubApiVersionHeader.VALUE)
            .build()
    )

    companion object {
        object GithubApiVersionHeader {
            const val KEY = "Accept"
            const val VALUE = "application/vnd.github.v3+json"
        }
    }
}