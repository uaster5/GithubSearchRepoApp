package com.example.githubsearchrepoapp.domain.search.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.githubsearchrepoapp.domain.search.model.SearchResult
import kotlinx.coroutines.CoroutineScope
import java.nio.channels.Channel

interface SearchRepository {

    fun search(query: String, coroutineScope: CoroutineScope): LiveData<PagedList<SearchResult>>

    suspend fun deleteStoredRepo(id: String)
}