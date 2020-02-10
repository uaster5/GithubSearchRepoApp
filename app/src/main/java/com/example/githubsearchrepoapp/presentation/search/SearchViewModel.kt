package com.example.githubsearchrepoapp.presentation.search

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.githubsearchrepoapp.domain.search.interactor.DeleteStoredRepoUseCase
import com.example.githubsearchrepoapp.domain.search.interactor.SearchUseCase
import com.example.githubsearchrepoapp.domain.search.model.Listing
import com.example.githubsearchrepoapp.domain.search.model.RepoModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SearchViewModel(
    private val searchUseCase: SearchUseCase,
    private val deleteStoredRepoUseCase: DeleteStoredRepoUseCase
) : ViewModel(), CoroutineScope {

    val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val queryLiveData = MutableLiveData<String>()

    private val repoResult: LiveData<Listing<RepoModel>> = Transformations.map(queryLiveData) {
        searchUseCase(it, CoroutineScope(coroutineContext))
    }
    val networkState = Transformations.switchMap(repoResult) { it.networkState }

    val reposLiveData: LiveData<PagedList<RepoModel>> = Transformations.switchMap(repoResult) { it.pagedList }

    fun searchRepo(query: String) {
        queryLiveData.postValue(query)
    }

    fun deleteRepo(id: Int) {
        launch {
            deleteStoredRepoUseCase(id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}