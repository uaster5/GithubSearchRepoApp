package com.example.githubsearchrepoapp.common

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()

    val isSuccessful
        get() = this is Success && data != null
}