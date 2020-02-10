package com.example.githubsearchrepoapp.presentation.search

import androidx.paging.PagedList
import com.example.githubsearchrepoapp.data.search.datasource.SearchLocalDataSource
import com.example.githubsearchrepoapp.data.search.datasource.SearchRemoteDataSource
import com.example.githubsearchrepoapp.domain.search.model.SearchResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class BoundaryCondition(
    private val query: String,
    private val service: SearchRemoteDataSource,
    private val cache: SearchLocalDataSource,
    private val pageSize: Int,
    private val scope: CoroutineScope
) : PagedList.BoundaryCallback<SearchResult>() {

    private var lastRequestedPage = 1

    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        requestAndSaveData(query)
    }

    override fun onItemAtEndLoaded(itemAtEnd: SearchResult) {
        requestAndSaveData(query)
    }

    private fun requestAndSaveData(query: String) {
        if (isRequestInProgress) return
        isRequestInProgress = true
        scope.launch {
            try {
               val repos =  service.search(query, lastRequestedPage, pageSize)
                if (repos.isSuccessful) {
                    cache.insert(requireNotNull(repos.body()).items) {
                        lastRequestedPage++
                        isRequestInProgress = false
                    }
                }
            } catch (e: Exception) {
                print(e.localizedMessage)
            }
        }
    }
}

