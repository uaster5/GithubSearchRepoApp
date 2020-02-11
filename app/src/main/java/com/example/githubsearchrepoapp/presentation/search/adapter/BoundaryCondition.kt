package com.example.githubsearchrepoapp.presentation.search.adapter

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.githubsearchrepoapp.data.search.datasource.SearchLocalDataSource
import com.example.githubsearchrepoapp.data.search.datasource.SearchRemoteDataSource
import com.example.githubsearchrepoapp.domain.search.model.RepoModel
import com.example.githubsearchrepoapp.domain.search.model.network.NetworkState
import kotlinx.coroutines.*
import java.lang.Exception

class BoundaryCondition(
    private val query: String,
    private val service: SearchRemoteDataSource,
    private val cache: SearchLocalDataSource,
    private val pageSize: Int,
    private val scope: CoroutineScope
) : PagedList.BoundaryCallback<RepoModel>() {

    val networkState = MutableLiveData<NetworkState>(NetworkState.LOADED)

    private var lastRequestedPage = 1

    override fun onZeroItemsLoaded() {
        requestAndSaveData(query)
    }

    override fun onItemAtEndLoaded(itemAtEnd: RepoModel) {
        requestAndSaveData(query)
    }

    private fun requestAndSaveData(query: String) {
        scope.launch {
            if (networkState.value != NetworkState.LOADING) {
                networkState.postValue(NetworkState.LOADING)
                try {
                    val repos = service.search(query, lastRequestedPage, pageSize)
                    if (repos.isSuccessful) {
                        cache.insert(requireNotNull(repos.body()).items)
                        networkState.postValue(NetworkState.LOADED)
                        lastRequestedPage++
                    }
                } catch (e: Exception) {
                    networkState.postValue(NetworkState.error(e.message))
                }
            }
        }
    }
}

