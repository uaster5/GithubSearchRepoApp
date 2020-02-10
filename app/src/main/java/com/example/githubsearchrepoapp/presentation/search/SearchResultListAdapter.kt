package com.example.githubsearchrepoapp.presentation.search

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearchrepoapp.domain.search.model.SearchResult

class SearchResultListAdapter : PagedListAdapter<SearchResult, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        SearchResultListViewHolder.create(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            (holder as SearchResultListViewHolder).bind(repoItem)
        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SearchResult>() {
            override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult) =
                oldItem.id == newItem.id
        }
    }
}