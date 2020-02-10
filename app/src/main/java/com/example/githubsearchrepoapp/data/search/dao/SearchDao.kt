package com.example.githubsearchrepoapp.data.search.dao

import androidx.paging.DataSource
import androidx.room.*
import com.example.githubsearchrepoapp.data.search.dto.SearchResultDto

@Dao
interface SearchDao {

    // We cant make this fun suspend because of conflicts with return type
    @Query("SELECT * FROM $TABLE_NAME WHERE (name LIKE :query) OR (description LIKE :query) ORDER BY stars DESC")
    fun searchRepo(query: String): DataSource.Factory<Int, SearchResultDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepos(repos: List<SearchResultDto>)

    @Query("DELETE FROM $TABLE_NAME WHERE id = :id")
    suspend fun deleteById(id: String)

    companion object {
        const val TABLE_NAME = "repos"
    }
}