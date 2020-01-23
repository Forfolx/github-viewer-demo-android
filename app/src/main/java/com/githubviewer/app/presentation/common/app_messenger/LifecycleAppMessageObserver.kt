package com.githubviewer.app.presentation.common.app_messenger

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import javax.inject.Inject

class LifecycleAppMessageObserver @Inject constructor(
    private val appMessagesObserver: AppMessageObserver
) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun start() = appMessagesObserver.start()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() = appMessagesObserver.resume()

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() = appMessagesObserver.pause()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() = appMessagesObserver.destroy()

}