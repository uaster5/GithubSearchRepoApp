package com.example.githubsearchrepoapp.presentation.search

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearchrepoapp.R
import com.example.githubsearchrepoapp.domain.search.model.SearchResult

class SearchResultListViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val name : TextView = view.findViewById(R.id.repo_name)
    private val description : TextView = view.findViewById(R.id.repo_description)
    private val star : TextView = view.findViewById(R.id.number_star)

    private var repo : SearchResult? = null

    init {
        view.setOnClickListener {
            repo.let {
                view.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it?.htmlUrl)))
            }
        }
    }

    fun bind(repo: SearchResult?) {
        if (repo == null) {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
            description.visibility = View.GONE
            star.text = resources.getString(R.string.unknown)
        } else {
            showRepoData(repo)
        }
    }

    private fun showRepoData(repo: SearchResult) {
        this.repo = repo
        name.text = repo.name

        // if the description is missing, hide the TextView
        var descriptionVisibility = View.GONE
        if (repo.description != null) {
            description.text = repo.description
            descriptionVisibility = View.VISIBLE
        }
        description.visibility = descriptionVisibility

        star.text = repo.stars.toString()
    }

    companion object {
        fun create(parent: ViewGroup): SearchResultListViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_repo, parent, false)
            return SearchResultListViewHolder(view)
        }
    }
}