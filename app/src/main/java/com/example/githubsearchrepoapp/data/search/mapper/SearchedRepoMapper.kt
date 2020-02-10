package com.example.githubsearchrepoapp.data.search.mapper

import com.example.githubsearchrepoapp.common.Mapper
import com.example.githubsearchrepoapp.data.search.dto.SearchResultDto
import com.example.githubsearchrepoapp.domain.search.model.SearchResult

class SearchedRepoMapper: Mapper<SearchResultDto, SearchResult> {

    override fun map(item: SearchResultDto): SearchResult =
            with(item) {
                SearchResult(
                    id,
                    name,
                    description,
                    stars,
                    htmlUrl
                )
            }
}