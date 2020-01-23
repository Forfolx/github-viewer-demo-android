package com.githubviewer.app.di

import android.util.Log
import com.githubviewer.app.BuildConfig
import com.githubviewer.app.data.api.ApiConfig
import com.githubviewer.app.data.api.ApiEndpointHolder
import com.githubviewer.app.data.api.interceptor.GitHubApiVersionHeaderInterceptor
import com.githubviewer.app.data.api.service.repo.RepoApi
import com.githubviewer.app.data.common.DataErrorHandler
import com.githubviewer.app.data.repository.repo.RepoRepositoryImpl
import com.githubviewer.app.domain.common.IDataErrorHandler
import com.githubviewer.app.domain.repository.repo.RepoRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import toothpick.config.Module
import javax.inject.Inject
import javax.inject.Provider

class DataModule : Module() {

    init {

        bind(IDataErrorHandler::class.java).to(DataErrorHandler::class.java).singletonInScope()

        bind(ApiEndpointHolder::class.java).to(ApiConfig::class.java).singletonInScope()

        bind(Gson::class.java).toProvider(GsonProvider::class.java).providesSingletonInScope()
        bind(OkHttpClient::class.java).toProvider(OkHttpClientProvider::class.java).providesSingletonInScope()
        bind(Retrofit::class.java).toProvider(RetrofitProvider::class.java).providesSingletonInScope()

        bind(RepoApi::class.java).toProvider(RepoApiProvider::class.java).providesSingletonInScope()

        bind(RepoRepository::class.java).to(RepoRepositoryImpl::class.java).singletonInScope()

    }

    internal class GsonProvider @Inject constructor() : Provider<Gson> {
        override fun get(): Gson = GsonBuilder().create()
    }

    internal class OkHttpClientProvider @Inject constructor() : Provider<OkHttpClient> {
        override fun get(): OkHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(GitHubApiVersionHeaderInterceptor())
            .addNetworkInterceptor(HttpLoggingInterceptor(
                HttpLoggingInterceptor.Logger { Log.d("logging", it) }
            ).apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.HEADERS
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .addNetworkInterceptor(HttpLoggingInterceptor(
                HttpLoggingInterceptor.Logger { Log.d("logging", it) }
            ).apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .build()
    }

    internal class RetrofitProvider @Inject constructor(
        private val okHttpClient: OkHttpClient,
        private val gson: Gson,
        private val apiEndpointHolder: ApiEndpointHolder
    ) : Provider<Retrofit> {
        override fun get(): Retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(apiEndpointHolder.getApiEndpoint())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    internal class RepoApiProvider @Inject constructor(
        private val retrofit: Retrofit
    ) : Provider<RepoApi> {
        override fun get(): RepoApi = retrofit.create(RepoApi::class.java)
    }

}