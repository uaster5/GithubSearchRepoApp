package com.example.githubsearchrepoapp.data.search.dao

import androidx.paging.DataSource
import androidx.room.*
import com.example.githubsearchrepoapp.data.search.dto.RepoModelDto

@Dao
interface SearchDao {

    // We cant make this fun suspend because of conflicts with return type
    @Query("SELECT * FROM $TABLE_NAME WHERE (name LIKE :query) OR (description LIKE :query) ORDER BY stars DESC")
    fun searchRepo(query: String): DataSource.Factory<Int, RepoModelDto>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepos(repos: List<RepoModelDto>)

    @Query("DELETE FROM $TABLE_NAME WHERE id = :id")
    suspend fun deleteById(id: Int)

    companion object {
        const val TABLE_NAME = "repos"
    }
}