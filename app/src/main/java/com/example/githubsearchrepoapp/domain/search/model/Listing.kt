package com.example.githubsearchrepoapp.domain.search.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.githubsearchrepoapp.domain.search.model.network.NetworkState

/**
 * Data class that is necessary for a UI to show a listing and interact w/ the rest of the system
 */
data class Listing<T>(
        val pagedList: LiveData<PagedList<T>>,
        val networkState: LiveData<NetworkState>
    )