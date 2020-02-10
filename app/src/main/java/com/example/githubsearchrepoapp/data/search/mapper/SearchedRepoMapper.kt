package com.example.githubsearchrepoapp.data.search.mapper

import com.example.githubsearchrepoapp.common.Mapper
import com.example.githubsearchrepoapp.data.search.dto.RepoModelDto
import com.example.githubsearchrepoapp.domain.search.model.RepoModel

class SearchedRepoMapper: Mapper<RepoModelDto, RepoModel> {

    override fun map(item: RepoModelDto): RepoModel =
            with(item) {
                RepoModel(
                    id,
                    name,
                    description,
                    stars,
                    htmlUrl
                )
            }
}