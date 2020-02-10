package com.example.githubsearchrepoapp.data.search

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.githubsearchrepoapp.domain.search.model.SearchResult
import com.example.githubsearchrepoapp.domain.search.repository.SearchRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.githubsearchrepoapp.common.Mapper
import com.example.githubsearchrepoapp.data.search.datasource.SearchLocalDataSource
import com.example.githubsearchrepoapp.data.search.datasource.SearchRemoteDataSource
import com.example.githubsearchrepoapp.data.search.dto.SearchResultDto
import com.example.githubsearchrepoapp.presentation.search.BoundaryCondition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.nio.channels.Channel
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext


class SearchDataRepository(
    private val mapper: Mapper<SearchResultDto, SearchResult>,
    private val remoteDataSource: SearchRemoteDataSource,
    private val localDataSource: SearchLocalDataSource
) : SearchRepository {


    override fun search(query: String, coroutineScope: CoroutineScope): LiveData<PagedList<SearchResult>> {

        val dataSourceFactory = localDataSource.search(query).mapByPage { mapper.map(it) }
        val boundaryCallback = BoundaryCondition(
            query,
            remoteDataSource,
            localDataSource,
            PER_PAGE_COUNT,
            coroutineScope
        )

        return LivePagedListBuilder(dataSourceFactory, PER_PAGE_COUNT)
            .setBoundaryCallback(boundaryCallback)
            .build()
    }

    override suspend fun deleteStoredRepo(id: String) =
        localDataSource.deleteById(id)

    companion object {
        const val PER_PAGE_COUNT = 30
    }
}