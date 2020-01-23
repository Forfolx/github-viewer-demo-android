package com.githubviewer.app.presentation.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    private val disposables = CompositeDisposable()

    open fun onColdStart() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected open fun onCreate() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected open fun onStart() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected open fun onResume() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected open fun onPause() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected fun onStop() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected open fun onDestroy() {}

    protected fun Disposable.untilDestroy() = this.addTo(disposables)

    protected fun <T> Observable<T>.lifeSubscribe(
        action: (T) -> Unit
    ) : Disposable = subscribe { action.invoke(it) }.untilDestroy()

    protected fun <T> Observable<T>.lifeSubscribe(
        action: (T) -> Unit,
        errorAction: (Throwable) -> Unit
    ) : Disposable = subscribe({ action.invoke(it) }, { errorAction.invoke(it) }).untilDestroy()

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

}