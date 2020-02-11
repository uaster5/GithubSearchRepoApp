package com.example.githubsearchrepoapp.data.search

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.githubsearchrepoapp.domain.search.model.RepoModel
import com.example.githubsearchrepoapp.domain.search.repository.SearchRepository
import com.example.githubsearchrepoapp.common.Mapper
import com.example.githubsearchrepoapp.data.search.datasource.SearchLocalDataSource
import com.example.githubsearchrepoapp.data.search.datasource.SearchRemoteDataSource
import com.example.githubsearchrepoapp.data.search.dto.RepoModelDto
import com.example.githubsearchrepoapp.domain.search.model.Listing
import com.example.githubsearchrepoapp.data.search.utils.BoundaryCondition
import kotlinx.coroutines.CoroutineScope

class SearchDataRepository(
    private val mapper: Mapper<RepoModelDto, RepoModel>,
    private val remoteDataSource: SearchRemoteDataSource,
    private val localDataSource: SearchLocalDataSource
) : SearchRepository {

    override fun search(query: String, coroutineScope: CoroutineScope): Listing<RepoModel> {

        val config: PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPrefetchDistance(5)
            .setInitialLoadSizeHint(PER_PAGE_COUNT)
            .setPageSize(PER_PAGE_COUNT)
            .build()

        val dataSourceFactory = localDataSource.search(query).mapByPage { mapper.map(it) }
        val boundaryCallback = BoundaryCondition(
            query,
            remoteDataSource,
            localDataSource,
            PER_PAGE_COUNT * 3,
            coroutineScope
        )

        val data = LivePagedListBuilder(dataSourceFactory, config)
            .setBoundaryCallback(boundaryCallback)
            .build()


        return Listing(
            pagedList = data,
            networkState = boundaryCallback.networkState
        )
    }

    override suspend fun deleteStoredRepo(id: Int) =
        localDataSource.deleteById(id)


    companion object {
        const val PER_PAGE_COUNT = 30
    }
}