package com.example.githubsearchrepoapp.domain.search.interactor

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.githubsearchrepoapp.domain.search.model.SearchResult
import com.example.githubsearchrepoapp.domain.search.repository.SearchRepository
import kotlinx.coroutines.CoroutineScope
import java.nio.channels.Channel

class SearchUseCase(private val repository: SearchRepository) {

    operator fun invoke(query: String, coroutineScope: CoroutineScope): LiveData<PagedList<SearchResult>> =
        repository.search(query, coroutineScope)
}