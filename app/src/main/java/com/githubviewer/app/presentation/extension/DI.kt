package com.githubviewer.app.presentation.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.githubviewer.app.di.DI
import toothpick.Toothpick

fun <T : ViewModel> FragmentActivity.viewModel(clazz: Class<T>): Lazy<T> = lazy {
    ViewModelProviders.of(this, object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T
            = Toothpick.openScope(DI.APP_SCOPE).getInstance(modelClass)
    }).get(clazz)
}

fun <T : ViewModel> Fragment.viewModel(clazz: Class<T>): Lazy<T> = lazy {
    ViewModelProviders.of(this, object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T
            = Toothpick.openScope(DI.APP_SCOPE).getInstance(modelClass)
    }).get(clazz)
}