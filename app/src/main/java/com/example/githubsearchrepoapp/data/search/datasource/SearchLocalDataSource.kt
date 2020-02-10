package com.example.githubsearchrepoapp.data.search.datasource

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.githubsearchrepoapp.common.ApplicationDatabase
import com.example.githubsearchrepoapp.data.search.dto.RepoModelDto
import kotlinx.coroutines.*
import java.lang.Exception

class SearchLocalDataSource(db: ApplicationDatabase) {

    private val dao = db.searchDao()

    suspend fun insert(list: List<RepoModelDto>) = coroutineScope {
        withContext(Dispatchers.IO) {
            dao.insertRepos(list)
        }
    }


    fun search(query: String): DataSource.Factory<Int, RepoModelDto> =
        dao.searchRepo(query)

    suspend fun deleteById(id: Int) {
        dao.deleteById(id)
    }
}