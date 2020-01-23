package com.githubviewer.app.presentation.common

import com.githubviewer.app.domain.common.IDataErrorHandler
import com.githubviewer.app.presentation.common.app_messenger.AppMessenger
import javax.inject.Inject

class ErrorHandler @Inject constructor(
    private val dataErrorHandler: IDataErrorHandler,
    private val systemMessenger: AppMessenger
) {

    fun proceed(error: Throwable, messageListener: (String) -> Unit = systemMessenger::showMessage) {
        error.printStackTrace()
        val message = dataErrorHandler.proceed(error)
        messageListener.invoke(message)
    }

}