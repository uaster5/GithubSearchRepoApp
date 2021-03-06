package com.example.githubsearchrepoapp.data.search.datasource

import com.example.githubsearchrepoapp.data.search.api.SearchApi
import com.example.githubsearchrepoapp.data.search.dto.RepoModelDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class SearchRemoteDataSource(private val api: SearchApi) {

    suspend fun search(
        query: String,
        page: Int,
        perPage: Int
    ) = api.search(
        query + IN_QUALIFIER,
        SEARCH_SORT_TYPE,
        SEARCH_ORDER,
        page,
        perPage
    )


    private companion object {
        const val SEARCH_SORT_TYPE = "stars"
        const val SEARCH_ORDER = "desc"
        const val IN_QUALIFIER = "in:name,description"
    }
}