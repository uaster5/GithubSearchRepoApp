package com.example.githubsearchrepoapp.data.search.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.githubsearchrepoapp.data.search.dao.SearchDao
import com.google.gson.annotations.SerializedName

@Entity(tableName = SearchDao.TABLE_NAME)
data class RepoModelDto(
    @PrimaryKey
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("stargazers_count") val stars: Int,
    @SerializedName("html_url") val htmlUrl: String
)