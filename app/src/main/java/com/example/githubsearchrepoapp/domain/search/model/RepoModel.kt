package com.example.githubsearchrepoapp.domain.search.model

data class RepoModel(
    val id: Int,
    val name: String,
    val description: String?,
    val stars: Int,
    val htmlUrl: String
)