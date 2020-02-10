package com.example.githubsearchrepoapp.domain.search.interactor

import com.example.githubsearchrepoapp.domain.search.model.Listing
import com.example.githubsearchrepoapp.domain.search.model.RepoModel
import com.example.githubsearchrepoapp.domain.search.repository.SearchRepository
import kotlinx.coroutines.CoroutineScope

class SearchUseCase(private val repository: SearchRepository) {

    operator fun invoke(query: String, coroutineScope: CoroutineScope): Listing<RepoModel> =
        repository.search(query, coroutineScope)
}