package com.example.githubsearchrepoapp.domain.search.interactor

import com.example.githubsearchrepoapp.domain.search.repository.SearchRepository

class DeleteStoredRepoUseCase(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(id: Int): Unit =
        repository.deleteStoredRepo(id)
}