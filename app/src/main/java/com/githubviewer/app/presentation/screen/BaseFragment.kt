package com.githubviewer.app.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.githubviewer.app.di.DI
import com.githubviewer.app.presentation.common.BackButtonListener
import toothpick.Toothpick

abstract class BaseFragment : Fragment(), BackButtonListener {

    @LayoutRes
    protected open val layoutResource: Int = View.NO_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.openScope(DI.APP_SCOPE).apply {
            Toothpick.inject(this@BaseFragment, this)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResource, container, false)
    }

    override fun onBackPressed(): Boolean = false

}