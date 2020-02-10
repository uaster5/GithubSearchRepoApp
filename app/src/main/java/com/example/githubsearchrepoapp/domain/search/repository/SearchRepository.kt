package com.example.githubsearchrepoapp.domain.search.repository

import com.example.githubsearchrepoapp.domain.search.model.Listing
import com.example.githubsearchrepoapp.domain.search.model.RepoModel
import kotlinx.coroutines.CoroutineScope

interface SearchRepository {

    fun search(query: String, coroutineScope: CoroutineScope): Listing<RepoModel>

    suspend fun deleteStoredRepo(id: Int)
}