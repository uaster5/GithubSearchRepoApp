package com.example.githubsearchrepoapp.presentation.search

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.githubsearchrepoapp.common.Result
import com.example.githubsearchrepoapp.domain.search.interactor.SearchUseCase
import com.example.githubsearchrepoapp.domain.search.model.SearchResult
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlin.coroutines.CoroutineContext

class SearchViewModel(
    private val searchUseCase: SearchUseCase
) : ViewModel(), CoroutineScope {


    val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val queryLiveData = MutableLiveData<String>("")

    val reposLiveData: LiveData<PagedList<SearchResult>> =
        Transformations.switchMap(queryLiveData) {
        searchUseCase.invoke(it, CoroutineScope(coroutineContext))
    }


    fun searchRepo(query: String) {
        queryLiveData.postValue(query)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}