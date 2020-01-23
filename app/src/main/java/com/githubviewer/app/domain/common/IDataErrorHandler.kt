package com.githubviewer.app.domain.common

interface IDataErrorHandler {
    fun proceed(error: Throwable): String
}