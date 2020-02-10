package com.example.githubsearchrepoapp.data.search.datasource

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.githubsearchrepoapp.common.ApplicationDatabase
import com.example.githubsearchrepoapp.data.search.dto.SearchResultDto
import kotlinx.coroutines.*
import java.lang.Exception

class SearchLocalDataSource(db: ApplicationDatabase) {

    private val dao = db.searchDao()

     suspend fun insert(list: List<SearchResultDto>, insertFinished: () -> Unit) = coroutineScope {
         withContext(Dispatchers.IO) {
             try {
                 dao.insertRepos(list)
                 insertFinished()
             } catch (e:Exception) {
                 print(e.message)
             }
         }
     }


    fun search(query: String): DataSource.Factory<Int, SearchResultDto> =
            dao.searchRepo(query)


    suspend fun deleteById(id: String) {
        dao.deleteById(id)
    }

}