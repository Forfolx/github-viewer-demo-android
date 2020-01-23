package com.githubviewer.app.presentation.common.app_messenger

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import javax.inject.Inject

open class AppMessenger @Inject constructor() {

    private val messageRelay = PublishRelay.create<IAppMessage>()

    fun observe(): Observable<IAppMessage> = messageRelay.hide()

    fun showMessage(message: String) = messageRelay.accept(ToastAppMessage(message))
    fun showMessage(message: IAppMessage) = messageRelay.accept(message)

}