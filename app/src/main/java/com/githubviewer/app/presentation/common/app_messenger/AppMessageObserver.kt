package com.githubviewer.app.presentation.common.app_messenger

import android.content.Context
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposables
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class AppMessageObserver @Inject constructor(
    private val context: Context,
    private val screenMessenger: AppMessenger
) {

    private val disposables = CompositeDisposable()
    private var messengerDisposable = Disposables.disposed()
    private val messageBufferTrigger = BehaviorSubject.create<Boolean>()
    private val messagesBuffer = mutableListOf<IAppMessage>()

    init {
        start()
    }

    fun start() {
        if (disposables.size() != 0) return
        disposables.clear()
        val disposable = screenMessenger
            .observe()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                messagesBuffer.add(it)
                messageBufferTrigger.onNext(true)
            }
        disposables.add(disposable)
    }

    fun resume() {
        messengerDisposable = messageBufferTrigger
            .flatMapIterable { messagesBuffer.toList() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { message ->
                showMessage(message)
                messagesBuffer.clear()
            }
        disposables.add(messengerDisposable)
    }

    fun pause() {
        messengerDisposable.dispose()
    }

    fun destroy() {
        disposables.clear()
    }

    open fun showMessage(message: IAppMessage) = when (message) {
        is ToastAppMessage -> showToast(message)
        else -> showDefault(message)
    }

    open fun showToast(message: ToastAppMessage) {
        Toast.makeText(context, message.message, Toast.LENGTH_SHORT).show()
    }

    private fun showDefault(message: IAppMessage) {
        Toast.makeText(context, message.message, Toast.LENGTH_SHORT).show()
    }

}