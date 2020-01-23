package com.githubviewer.app.presentation.screen.repo.list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.githubviewer.app.R
import com.githubviewer.app.presentation.presenter.repo.list.RepoListViewModel
import com.githubviewer.app.presentation.screen.BaseFragment
import com.githubviewer.app.presentation.extension.viewModel
import com.githubviewer.app.presentation.screen.repo.list.adapter.RepoAdapter
import kotlinx.android.synthetic.main.fragment_pero_list.*

class RepoListFragment : BaseFragment() {

    private val viewModel by viewModel(RepoListViewModel::class.java)

    override val layoutResource = R.layout.fragment_pero_list

    private val repoAdapter = RepoAdapter {
        viewModel.onRepoClick(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.refresh()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(viewModel)

        vRepoList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = repoAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val visibleItemCount = layoutManager?.childCount ?: 0
                    val totalItemCount = layoutManager?.itemCount ?: 0
                    val pastVisibleItems = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                    if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                        if (vRepoListSwipeRefresh.isRefreshing.not()) {
                            vRepoListSwipeRefresh.isRefreshing = true
                            viewModel.loadItems()
                        }
                    }
                }
            })
        }

        viewModel.repoListData.observe(this, Observer {
            vRepoListSwipeRefresh.isRefreshing = false
            repoAdapter.loadItems(it)
        })

        vRepoListSwipeRefresh.isRefreshing = true
        vRepoListSwipeRefresh.setOnRefreshListener(viewModel::refresh)
    }

}