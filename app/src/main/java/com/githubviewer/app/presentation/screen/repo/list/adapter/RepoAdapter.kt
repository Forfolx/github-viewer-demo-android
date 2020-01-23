package com.githubviewer.app.presentation.screen.repo.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.githubviewer.app.R
import com.githubviewer.app.presentation.presenter.repo.list.entity.RepoItemState
import kotlinx.android.synthetic.main.item_repo.view.*

class RepoAdapter(
    private val clickListener: (RepoItemState) -> Unit
) : RecyclerView.Adapter<RepoAdapter.ViewHolder>() {

    private val items = mutableListOf<RepoItemState>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_repo, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    fun loadItems(items: List<RepoItemState>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(repoItemState: RepoItemState) {
            itemView.apply {
                vId.text = repoItemState.id.toString()
                vName.text = repoItemState.name
                vDescription.text = repoItemState.description
                vOwner.text = repoItemState.ownerLogin

                setOnClickListener { clickListener.invoke(repoItemState) }
            }
        }

    }

}