package com.example.githubsearchrepoapp.data.search.api

import com.example.githubsearchrepoapp.data.search.dto.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchApi {

    @Headers("Accept: application/vnd.github.mercy-preview+json")
    @GET("/search/repositories")
    suspend fun search(
        @Query("q") query: String,
        @Query("sort") sortType: String,
        @Query("order") order: String,
        @Query("page") page: Int,
        @Query("per_page") perPage : Int
    ): Response<SearchResponse>
}