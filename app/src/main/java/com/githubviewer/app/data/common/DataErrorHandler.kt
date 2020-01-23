package com.githubviewer.app.data.common

import android.content.Context
import com.githubviewer.app.R
import com.githubviewer.app.domain.common.IDataErrorHandler
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DataErrorHandler @Inject constructor(
    private val context: Context
) : IDataErrorHandler {

    override fun proceed(error: Throwable): String = error.userMessage()

    private fun Throwable.userMessage() = when(this) {
        is HttpException -> userMessage()
        is IOException -> context.getString(R.string.network_error)
        else -> context.getString(R.string.unknown_error)
    }

    private fun HttpException.userMessage() = when(code()) {
        304 -> context.getString(R.string.not_modified_error)
        400 -> context.getString(R.string.bad_request_error)
        401 -> context.getString(R.string.unauthorized_error)
        403 -> context.getString(R.string.forbidden_error)
        404 -> context.getString(R.string.not_found_error)
        405 -> context.getString(R.string.method_not_allowed_error)
        409 -> context.getString(R.string.conflict_error)
        422 -> context.getString(R.string.unprocessable_error)
        500 -> context.getString(R.string.server_error_error)
        else -> "${code()} ${message()}"
    }

}