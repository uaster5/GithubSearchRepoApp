package com.example.githubsearchrepoapp.common

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubsearchrepoapp.data.search.dao.SearchDao
import com.example.githubsearchrepoapp.data.search.dto.RepoModelDto

@Database(entities = [RepoModelDto::class], version = 1, exportSchema = false)
abstract class ApplicationDatabase: RoomDatabase() {

    abstract fun searchDao(): SearchDao
}