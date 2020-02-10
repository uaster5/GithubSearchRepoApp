package com.example.githubsearchrepoapp.presentation.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearchrepoapp.R
import com.example.githubsearchrepoapp.domain.search.model.RepoModel

class SearchResultListViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    private val name : TextView = view.findViewById(R.id.repoName)
    private val description : TextView = view.findViewById(R.id.repoDescription)
    private val star : TextView = view.findViewById(R.id.numberStar)
    val button : TextView = view.findViewById(R.id.removeButton)

    private var repo : RepoModel? = null

    fun bind(repo: RepoModel, onDeleteClick: (Int) -> Unit ) {
            this.repo = repo
            name.text = repo.name

            var descriptionVisibility = View.GONE
            if (repo.description != null) {
                description.text = repo.description
                descriptionVisibility = View.VISIBLE
            }
            description.visibility = descriptionVisibility

            star.text = repo.stars.toString()

            button.setOnClickListener {
                onDeleteClick(repo.id)
            }
        }

    companion object {
        fun create(parent: ViewGroup): SearchResultListViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_repo, parent, false)
            return SearchResultListViewHolder(view)
        }
    }
}