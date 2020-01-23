package com.githubviewer.app.presentation.extension

import android.view.View

fun <T : View> T.visible() {
    visibility = View.VISIBLE
}

fun <T : View> T.gone() {
    visibility = View.GONE
}

fun <T : View> T.invisible() {
    visibility = View.INVISIBLE
}

fun <T : View> T.visible(isVisible: Boolean) = if (isVisible) visible() else gone()

fun <T : View> T.gone(isGone: Boolean) = visible(isGone.not())

fun <T : View> T.invisible(isInvisible: Boolean) = if (isInvisible) invisible() else visible()