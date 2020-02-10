package com.example.githubsearchrepoapp.presentation.search.adapter

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearchrepoapp.R
import com.example.githubsearchrepoapp.domain.search.model.RepoModel

class SearchResultListAdapter(
    private val onDeleteAction: (Int) -> Unit,
    private val onShareAction: (String) -> Unit
) : PagedListAdapter<RepoModel, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        SearchResultListViewHolder.create(parent).apply {
            val listener = View.OnClickListener { view ->
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val item = requireNotNull(getItem(adapterPosition))
                    when (view.id) {
                        R.id.removeButton -> onDeleteAction(item.id)
                        R.id.item -> onShareAction(requireNotNull(item.htmlUrl))
                    }
                }
            }
            view.setOnClickListener(listener)
            button.setOnClickListener(listener)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            (holder as SearchResultListViewHolder).bind(repoItem, onDeleteAction)
        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RepoModel>() {
            override fun areContentsTheSame(oldItem: RepoModel, newItem: RepoModel): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: RepoModel, newItem: RepoModel) =
                oldItem.id == newItem.id
        }
    }
}