package com.example.githubsearchrepoapp.data.search.dto

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("items") val items: List<SearchResultDto>,
    @SerializedName("total_count") val totalCount: Int
)