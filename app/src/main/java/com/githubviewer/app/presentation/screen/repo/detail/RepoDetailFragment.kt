package com.githubviewer.app.presentation.screen.repo.detail

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.githubviewer.app.R
import com.githubviewer.app.presentation.extension.gone
import com.githubviewer.app.presentation.presenter.repo.detail.RepoDetailViewModel
import com.githubviewer.app.presentation.screen.BaseFragment
import com.githubviewer.app.presentation.extension.viewModel
import kotlinx.android.synthetic.main.fragment_repo_detail.*

class RepoDetailFragment : BaseFragment() {

    private val viewModel by viewModel(RepoDetailViewModel::class.java)

    override val layoutResource = R.layout.fragment_repo_detail

    private val repoUrl by lazy { arguments!![ARG_REPO_URL] as String }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(viewModel)

        toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back_white)
            setNavigationOnClickListener { backPressAction() }
        }

        vRepoWebPage.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            webChromeClient = RepoDetailWebChromeClient()
            if (savedInstanceState == null) {
                loadUrl(repoUrl)
            } else {
                vRepoWebPage.restoreState(savedInstanceState)
            }
        }

        vPageLoadingProgress.max = MAX_WEB_PAGE_PROGRESS

        vRepoWebPageRefresh.setOnRefreshListener { vRepoWebPage.reload() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        vRepoWebPage.saveState(outState)
    }

    override fun onBackPressed(): Boolean = true.also { backPressAction() }

    private fun backPressAction() {
        if (vRepoWebPage.canGoBack()) {
            vRepoWebPage.goBack()
        } else {
            viewModel.onBackPressed()
        }
    }

    inner class RepoDetailWebChromeClient : WebChromeClient() {

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            if (isAdded.not()) return

            val showProgress = newProgress < MAX_WEB_PAGE_PROGRESS

            vPageLoadingProgress.apply {
                progress = newProgress
                gone(showProgress.not())
            }

            vRepoWebPageRefresh.isRefreshing = showProgress
        }

        override fun onReceivedTitle(view: WebView?, title: String?) {
            if (isAdded.not()) return
            toolbar.title = title
        }

    }

    companion object {
        const val ARG_REPO_URL = "com.githubviewer.app.ARG_REPO_URL"
        const val MAX_WEB_PAGE_PROGRESS = 100

        fun newInstance(repoUrl: String) = RepoDetailFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_REPO_URL, repoUrl)
            }
        }
    }

}